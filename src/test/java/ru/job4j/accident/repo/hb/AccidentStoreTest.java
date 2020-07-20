package ru.job4j.accident.repo.hb;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.AccidentType;
import ru.job4j.accident.model.jpa.Rule;
import ru.job4j.accident.model.jpa.RuleAccident;

import java.util.Iterator;
import java.util.Set;

public class AccidentStoreTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    @Test
    public void whenAddWithType() {
        AccidentStore store = new AccidentStore();
        AccidentTypeStore typeStore = new AccidentTypeStore();
        store.setSessionFactory(sf);
        typeStore.setSessionFactory(sf);
        store.setAccidentTypeStore(typeStore);

        AccidentType type = AccidentType.of("type");
        typeStore.save(type);
        Accident accident = new Accident("n", "t", "a", type);
        store.save(accident);
        Accident out = store.findById(accident.getId());
        Assert.assertEquals(accident.getName(), out.getName());
        Assert.assertEquals(accident.getText(), out.getText());
        Assert.assertEquals(accident.getAddress(), out.getAddress());
        Assert.assertEquals(accident.getType().getName(), out.getType().getName());
    }

    @Test
    public void whenFindAll() {
        AccidentStore store = new AccidentStore();
        AccidentTypeStore typeStore = new AccidentTypeStore();
        store.setSessionFactory(sf);
        typeStore.setSessionFactory(sf);
        store.setAccidentTypeStore(typeStore);

        AccidentType type = AccidentType.of("type");
        typeStore.save(type);
        Accident accident = new Accident("n", "t", "a", type);
        store.save(accident);
        Accident out = store.findAll().iterator().next();
        Assert.assertEquals(accident.getName(), out.getName());
        Assert.assertEquals(accident.getText(), out.getText());
        Assert.assertEquals(accident.getAddress(), out.getAddress());
        Assert.assertEquals(accident.getType().getName(), out.getType().getName());
    }

    @Test
    public void whenSaveWithRulesAndType() {
        AccidentStore store = new AccidentStore();
        AccidentTypeStore typeStore = new AccidentTypeStore();
        RuleStore ruleStore = new RuleStore();
        store.setSessionFactory(sf);
        typeStore.setSessionFactory(sf);
        ruleStore.setSessionFactory(sf);
        store.setAccidentTypeStore(typeStore);

        AccidentType type = AccidentType.of("type");
        typeStore.save(type);
        Accident accident = new Accident("n", "t", "a", type);
        store.save(accident);
        Rule rule1 = Rule.of("r1");
        Rule rule2 = Rule.of("r2");
        ruleStore.save(rule1);
        ruleStore.save(rule2);
        ruleStore.save(new RuleAccident(accident, rule1));
        ruleStore.save(new RuleAccident(accident, rule2));

        Accident out = store.findById(accident.getId());
        Iterator<Rule> iterator = ruleStore.findByAccident(accident).iterator();

        Assert.assertEquals(accident.getName(), out.getName());
        Assert.assertEquals(accident.getText(), out.getText());
        Assert.assertEquals(accident.getAddress(), out.getAddress());
        Assert.assertEquals(accident.getType().getName(), out.getType().getName());
        Assert.assertEquals(rule1.getName(), iterator.next().getName());
        Assert.assertEquals(rule2.getName(), iterator.next().getName());
    }

    @Test
    public void whenUpdateWithRulesAndType() {
        AccidentStore store = new AccidentStore();
        AccidentTypeStore typeStore = new AccidentTypeStore();
        RuleStore ruleStore = new RuleStore();
        store.setSessionFactory(sf);
        store.setRuleStore(ruleStore);
        typeStore.setSessionFactory(sf);
        ruleStore.setSessionFactory(sf);
        store.setAccidentTypeStore(typeStore);

        AccidentType type = AccidentType.of("type");
        typeStore.save(type);
        Accident accident = new Accident("n", "t", "a", type);
        store.save(accident);
        Rule rule1 = Rule.of("r1");
        Rule rule2 = Rule.of("r2");
        ruleStore.save(rule1);
        ruleStore.save(rule2);
        ruleStore.save(new RuleAccident(accident, rule1));
        ruleStore.save(new RuleAccident(accident, rule2));

        Rule rule3 = Rule.of("r3");
        AccidentType accidentType = AccidentType.of("new type");
        ruleStore.save(rule3);
        typeStore.save(accidentType);
        accident.setType(accidentType);
        accident.setRuleAccidents(Set.of(
                new RuleAccident(accident, rule3)
        ));
        store.update(accident);

        Accident out = store.findById(accident.getId());
        Iterator<Rule> iterator = ruleStore.findByAccident(accident).iterator();

        Assert.assertEquals(accident.getName(), out.getName());
        Assert.assertEquals(accident.getText(), out.getText());
        Assert.assertEquals(accident.getAddress(), out.getAddress());
        Assert.assertEquals(accident.getType().getName(), out.getType().getName());
        Assert.assertEquals(rule3.getName(), iterator.next().getName());
    }

}