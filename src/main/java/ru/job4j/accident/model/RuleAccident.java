package ru.job4j.accident.model;

public class RuleAccident {

    private int id;

    private int accidentId;

    private int ruleId;

    public RuleAccident() { }

    public RuleAccident(int accidentId, int ruleId) {
        this.accidentId = accidentId;
        this.ruleId = ruleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(int accidentId) {
        this.accidentId = accidentId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
}
