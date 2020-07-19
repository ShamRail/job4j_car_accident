package ru.job4j.accident.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.RuleAccident;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collection;
import java.util.LinkedHashSet;

@Repository
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    private AccidentTypeJdbcTemplate accidentTypeDB;

    private RuleJdbcTemplate ruleDB;

    private final RowMapper<Accident> accidentRowMapper = (resultSet, i) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("description"));
        accident.setAddress(resultSet.getString("address"));
        accident.setType(accidentTypeDB.findById(resultSet.getInt("type_id")));
        accident.setRules(new LinkedHashSet<>(ruleDB.findByAccident(accident)));
        return accident;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc, AccidentTypeJdbcTemplate accidentTypeDB, RuleJdbcTemplate ruleDB) {
        this.jdbc = jdbc;
        this.accidentTypeDB = accidentTypeDB;
        this.ruleDB = ruleDB;
        this.initData();
    }

    private void initData() {

        AccidentType type1 = AccidentType.of("Две машины");
        AccidentType type2 = AccidentType.of("Машина и человек");
        AccidentType type3 = AccidentType.of("Машина и велосипед");

        accidentTypeDB.save(type1);
        accidentTypeDB.save(type2);
        accidentTypeDB.save(type3);

        Accident accident1 = new Accident("name2", "text2", "address2", type1);
        Accident accident2 = new Accident("name2", "text2", "address2", type2);
        Accident accident3 = new Accident("name3", "text3", "address3", type3);
        Accident accident4 = new Accident("name4", "text4", "address4", type1);
        Accident accident5 = new Accident("name5", "text5", "address5", type2);

        save(accident1);
        save(accident2);
        save(accident3);
        save(accident4);
        save(accident5);

        Rule rule1 = Rule.of("Статья 123 .. Рф");
        Rule rule2 = Rule.of("Статья 456 .. Рф");
        Rule rule3 = Rule.of("Статья 789 .. Рф");

        ruleDB.save(rule1);
        ruleDB.save(rule2);
        ruleDB.save(rule3);

        ruleDB.save(new RuleAccident(accident1.getId(), rule1.getId()));
        ruleDB.save(new RuleAccident(accident1.getId(), rule2.getId()));
        ruleDB.save(new RuleAccident(accident2.getId(), rule3.getId()));
        ruleDB.save(new RuleAccident(accident3.getId(), rule2.getId()));
        ruleDB.save(new RuleAccident(accident4.getId(), rule3.getId()));

    }

    public Collection<Accident> findAll() {
        return jdbc.query(
                "select * from accident",
                accidentRowMapper
        );
    }

    public Accident saveOrUpdate(Accident accident) {
        return accident.getId() == 0 ? save(accident) : update(accident);
    }

    public Accident save(Accident accident) {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "insert into accident(name, description, address, type_id) VALUES (?, ?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            if (accident.getType() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, accident.getType().getId());
            }
            return ps;
        }, holder);
        Number id = holder.getKey();
        if (id != null) {
            accident.setId((Integer) id);
            for (Rule rule : accident.getRules()) {
                ruleDB.save(new RuleAccident(accident.getId(), rule.getId()));
            }
        }
        return accident;
    }

    public Accident update(Accident accident) {
        ruleDB.deleteByAccident(accident);
        jdbc.update(
                "update accident set name = ?, description = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(),
                (accident.getType() == null ? null : accident.getType().getId()),
                accident.getId()
        );
        for (Rule rule : accident.getRules()) {
            ruleDB.save(new RuleAccident(accident.getId(), rule.getId()));
        }
        return accident;
    }

    public Accident findById(int id) {
        return jdbc.queryForObject(
                "select * from accident where id = ?", accidentRowMapper, id
        );
    }


}
