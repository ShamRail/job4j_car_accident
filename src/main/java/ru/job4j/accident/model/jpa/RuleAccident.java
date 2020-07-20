package ru.job4j.accident.model.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rule_acc")
public class RuleAccident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Accident accident;

    @ManyToOne
    private Rule rule;

    public RuleAccident() { }

    public RuleAccident(Accident accident, Rule rule) {
        this.accident = accident;
        this.rule = rule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RuleAccident that = (RuleAccident) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "RuleAccident(rule: %d, accident: %d)",
                rule.getId(), accident.getId()
        );
    }
}
