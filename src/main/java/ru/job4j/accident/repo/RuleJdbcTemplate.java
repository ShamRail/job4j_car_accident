package ru.job4j.accident.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.RuleAccident;

import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class RuleJdbcTemplate {

    private final JdbcTemplate db;

    private final RowMapper<Rule> ruleRowMapper = (resultSet, i) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    private final RowMapper<RuleAccident> ruleAccidentRowMapper = (resultSet, i) -> {
        RuleAccident ruleAccident = new RuleAccident();
        ruleAccident.setId(resultSet.getInt("id"));
        ruleAccident.setAccidentId(resultSet.getInt("acc_id"));
        ruleAccident.setRuleId(resultSet.getInt("rule_id"));
        return ruleAccident;
    };

    public RuleJdbcTemplate(JdbcTemplate db) {
        this.db = db;
    }

    public Rule save(Rule rule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into acc_rule(name) values (?)";
        db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, rule.getName());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            rule.setId((Integer) keyHolder.getKey());
        }
        return rule;
    }

    public RuleAccident save(RuleAccident ruleAccident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into rule_acc(acc_id, rule_id) values (?, ?)";
        db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setInt(1, ruleAccident.getAccidentId());
            ps.setInt(2, ruleAccident.getRuleId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            ruleAccident.setId((Integer) keyHolder.getKey());
        }
        return ruleAccident;
    }

    public Collection<Rule> findAll() {
        return db.query(
                "select * from acc_rule",
                ruleRowMapper
        );
    }

    public Collection<Rule> findByAccident(Accident accident) {
        return db.query(
                ""
                + "select rule.id as id, rule.name as name "
                + "from acc_rule rule inner join rule_acc ra on rule.id = ra.rule_id "
                + "where ra.acc_id = ? "
                + "",
                ruleRowMapper,
                accident.getId()
        );
    }

    public Rule findById(int id) {
        return db.queryForObject(
                "select * from acc_rule where id = ?",
                ruleRowMapper,
                id
        );
    }

    public void deleteByAccident(Accident accident) {
        db.update(
                "delete from rule_acc where acc_id = ?",
                accident.getId()
        );
    }
}
