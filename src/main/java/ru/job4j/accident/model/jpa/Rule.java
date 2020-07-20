package ru.job4j.accident.model.jpa;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "acc_rule")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "rule")
    private Set<RuleAccident> ruleAccidents = new LinkedHashSet<>();

    public static Rule of(int id, String name) {
        Rule rule = new Rule();
        rule.id = id;
        rule.name = name;
        return rule;
    }

    public static Rule of(String name) {
        Rule rule = new Rule();
        rule.name = name;
        return rule;
    }

    public Set<RuleAccident> getRuleAccidents() {
        return ruleAccidents;
    }

    public void setRuleAccidents(Set<RuleAccident> ruleAccidents) {
        this.ruleAccidents = ruleAccidents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rule rule = (Rule) o;
        return id == rule.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Rule(%d, %s)", id, name);
    }
}