package ru.job4j.accident.repo.hb;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.Rule;
import ru.job4j.accident.model.jpa.RuleAccident;

import java.util.Iterator;

import static org.junit.Assert.*;

@Ignore
public class RuleStoreTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Test
    public void whenAdd() {
        RuleStore store = new RuleStore();
        store.setSessionFactory(sf);
        Rule rule = Rule.of("n");
        store.save(rule);
        assertEquals(rule.getName(), store.findById(rule.getId()).getName());
    }

    @Test
    public void whenUpdate() {
        RuleStore store = new RuleStore();
        store.setSessionFactory(sf);
        Rule rule = Rule.of("n");
        store.save(rule);
        rule.setName("n2");
        store.save(rule);
        assertEquals(rule.getName(), store.findById(rule.getId()).getName());
    }

    @Test
    public void whenFindAll() {
        RuleStore store = new RuleStore();
        store.setSessionFactory(sf);
        Rule rule1 = Rule.of("n1");
        Rule rule2 = Rule.of("n2");
        store.save(rule1);
        store.save(rule2);
        Iterator<Rule> iterator = store.findAll().iterator();
        assertEquals(iterator.next().getName(), rule1.getName());
        assertEquals(iterator.next().getName(), rule2.getName());
    }

    @Test
    public void whenFindByAccident() {
        AccidentStore storeAccident = new AccidentStore();
        RuleStore store = new RuleStore();
        store.setSessionFactory(sf);
        storeAccident.setSessionFactory(sf);

        Rule rule1 = Rule.of("n1");
        Rule rule2 = Rule.of("n2");
        store.save(rule1);
        store.save(rule2);
        Accident accident = new Accident();
        storeAccident.save(accident);
        store.save(new RuleAccident(accident, rule1));
        store.save(new RuleAccident(accident, rule2));

        Iterator<Rule> iterator = store.findByAccident(accident).iterator();
        assertEquals(iterator.next().getName(), rule1.getName());
        assertEquals(iterator.next().getName(), rule2.getName());
    }

    @Test
    public void whenDeleteByAccident() {
        AccidentStore storeAccident = new AccidentStore();
        RuleStore store = new RuleStore();
        store.setSessionFactory(sf);
        storeAccident.setSessionFactory(sf);

        Rule rule1 = Rule.of("n1");
        Rule rule2 = Rule.of("n2");
        store.save(rule1);
        store.save(rule2);
        Accident accident = new Accident();
        storeAccident.save(accident);
        store.save(new RuleAccident(accident, rule1));
        store.save(new RuleAccident(accident, rule2));

        store.deleteByAccident(accident);
        Iterator<Rule> iterator = store.findByAccident(accident).iterator();
        assertFalse(iterator.hasNext());
    }

}