package ru.job4j.accident.repo.hb;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.accident.model.jpa.AccidentType;

import java.util.Iterator;

@Ignore
public class AccidentTypeStoreTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    @Test
    public void whenAddAndFindById() {
        AccidentTypeStore store = new AccidentTypeStore();
        store.setSessionFactory(sf);
        AccidentType type = AccidentType.of("n");
        store.save(type);
        Assert.assertEquals(type.getName(), store.findById(type.getId()).getName());
    }

    @Test
    public void whenFindAll() {
        AccidentTypeStore store = new AccidentTypeStore();
        store.setSessionFactory(sf);
        AccidentType type1 = AccidentType.of("n1");
        AccidentType type2 = AccidentType.of("n2");
        store.save(type1);
        store.save(type2);
        Iterator<AccidentType> typeIterator = store.findAll().iterator();
        Assert.assertEquals(typeIterator.next().getName(), type1.getName());
        Assert.assertEquals(typeIterator.next().getName(), type2.getName());
    }

    @Test
    public void whenUpdate() {
        AccidentTypeStore store = new AccidentTypeStore();
        store.setSessionFactory(sf);
        AccidentType type1 = AccidentType.of("n1");
        store.save(type1);
        type1.setName("n2");
        store.save(type1);
        Assert.assertEquals(type1.getName(), store.findById(type1.getId()).getName());

    }

}