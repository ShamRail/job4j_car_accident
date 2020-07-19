package ru.job4j.accident.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class AccidentTypeJdbcTemplate {

    private final JdbcTemplate db;

    private final RowMapper<AccidentType> accidentTypeRowMapper = (resultSet, i) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(resultSet.getInt("id"));
        accidentType.setName(resultSet.getString("name"));
        return accidentType;
    };

    public AccidentTypeJdbcTemplate(JdbcTemplate db) {
        this.db = db;
    }

    public Collection<AccidentType> findAll() {
        return db.query(
                "select * from acc_type",
                accidentTypeRowMapper
        );
    }

    public AccidentType findById(int id) {
        return db.queryForObject(
                "select * from acc_type where id = ?",
                accidentTypeRowMapper, id
        );
    }

    public AccidentType save(AccidentType accidentType) {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "insert into acc_type(name) values (?)";
        db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, accidentType.getName());
            return ps;
        }, holder);
        if (holder.getKey() != null) {
            accidentType.setId(holder.getKey().intValue());
        }
        return accidentType;
    }

}
