package ru.job4j.accident.model.jpa;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "accident")
public class Accident implements Comparable<Accident> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name = "";

    @Column(name = "description")
    private String text = "";

    private String address = "";

    @ManyToOne
    private AccidentType type;

    @OneToMany(mappedBy = "accident")
    private Set<RuleAccident> ruleAccidents = new LinkedHashSet<>();

    public Accident() { }

    public Accident(int id, String name, String text, String address) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.address = address;
    }

    public Accident(String name, String text, String address, AccidentType type) {
        this.name = name;
        this.text = text;
        this.address = address;
        this.type = type;
    }

    public Set<RuleAccident> getRuleAccidents() {
        return ruleAccidents;
    }

    public void setRuleAccidents(Set<RuleAccident> ruleAccidents) {
        this.ruleAccidents = ruleAccidents;
    }

    public AccidentType getType() {
        return type;
    }

    public void setType(AccidentType type) {
        this.type = type;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accident accident = (Accident) o;
        return id == accident.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Accident(%d, %s, %s, %s)",
                id, name, text, address
        );
    }

    @Override
    public int compareTo(Accident o) {
        return Integer.compare(id, o.id);
    }
}