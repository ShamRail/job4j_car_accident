package ru.job4j.accident.repo.hb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.AccidentType;
import ru.job4j.accident.model.jpa.Rule;
import ru.job4j.accident.model.jpa.RuleAccident;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

//@Repository
public class AccidentStore {

    private SessionFactory sessionFactory;

    private RuleStore ruleStore;

    private AccidentTypeStore accidentTypeStore;

    private LocalSessionFactoryBean localSessionFactoryBean;

    @Autowired
    public void setLocalSessionFactoryBean(LocalSessionFactoryBean localSessionFactoryBean) {
        this.localSessionFactoryBean = localSessionFactoryBean;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setRuleStore(RuleStore ruleStore) {
        this.ruleStore = ruleStore;
    }

    @Autowired
    public void setAccidentTypeStore(AccidentTypeStore accidentTypeStore) {
        this.accidentTypeStore = accidentTypeStore;
    }

    @PostConstruct
    private void initData() {

        sessionFactory = localSessionFactoryBean.getObject();

//        AccidentType type1 = AccidentType.of("Две машины");
//        AccidentType type2 = AccidentType.of("Машина и человек");
//        AccidentType type3 = AccidentType.of("Машина и велосипед");
//
//        accidentTypeStore.save(type1);
//        accidentTypeStore.save(type2);
//        accidentTypeStore.save(type3);
//
//        Accident accident1 = new Accident("name2", "text2", "address2", type1);
//        Accident accident2 = new Accident("name2", "text2", "address2", type2);
//        Accident accident3 = new Accident("name3", "text3", "address3", type3);
//        Accident accident4 = new Accident("name4", "text4", "address4", type1);
//        Accident accident5 = new Accident("name5", "text5", "address5", type2);
//
//        save(accident1);
//        save(accident2);
//        save(accident3);
//        save(accident4);
//        save(accident5);
//
//        Rule rule1 = Rule.of("Статья 123 .. Рф");
//        Rule rule2 = Rule.of("Статья 456 .. Рф");
//        Rule rule3 = Rule.of("Статья 789 .. Рф");
//
//        ruleStore.save(rule1);
//        ruleStore.save(rule2);
//        ruleStore.save(rule3);
//
//        ruleStore.save(new RuleAccident(accident1, rule1));
//        ruleStore.save(new RuleAccident(accident1, rule2));
//        ruleStore.save(new RuleAccident(accident2, rule3));
//        ruleStore.save(new RuleAccident(accident3, rule2));
//        ruleStore.save(new RuleAccident(accident4, rule3));

    }

    public Accident saveOrUpdate(Accident accident) {
        return accident.getId() == 0 ? save(accident) : update(accident);
    }

    public Accident save(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            accident.setType(accidentTypeStore.findById(accident.getType().getId()));
            session.save(accident);
            session.getTransaction().commit();
        }
        for (RuleAccident rule : accident.getRuleAccidents()) {
            ruleStore.save(rule);
        }
        return accident;
    }

    public Accident findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Accident accident = session.get(Accident.class, id);
            session.getTransaction().commit();
            return accident;
        }
    }

    public Collection<Accident> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Accident> accidents =
                    session.createQuery("from Accident ").list();
            session.getTransaction().commit();
            return accidents;
        }
    }

    public Accident update(Accident accident) {
        ruleStore.deleteByAccident(accident);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
        }
        for (RuleAccident rule : accident.getRuleAccidents()) {
            ruleStore.save(rule);
        }
        return accident;
    }

}
