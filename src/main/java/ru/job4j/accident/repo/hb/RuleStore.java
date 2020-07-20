package ru.job4j.accident.repo.hb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.Rule;
import ru.job4j.accident.model.jpa.RuleAccident;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Repository
public class RuleStore {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private LocalSessionFactoryBean localSessionFactoryBean;

    @Autowired
    public void setLocalSessionFactoryBean(LocalSessionFactoryBean localSessionFactoryBean) {
        this.localSessionFactoryBean = localSessionFactoryBean;
    }

    @PostConstruct
    public void initSession() {
        sessionFactory = localSessionFactoryBean.getObject();
    }

    public Rule save(Rule rule) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(rule);
            session.getTransaction().commit();
            return rule;
        }
    }

    public Collection<Rule> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Rule> rules = session.createQuery("from Rule ").list();
            session.getTransaction().commit();
            return rules;
        }
    }

    public Rule findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Rule rule = session.get(Rule.class, id);
            session.getTransaction().commit();
            return rule;
        }
    }

    public Collection<Rule> findByAccident(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "select rule from RuleAccident ra join Rule rule on ra.rule.id = rule.id where ra.accident = :accident"
            );
            query.setParameter("accident", accident);
            List<Rule> rules = query.list();
            session.getTransaction().commit();
            return rules;
        }
    }

    public RuleAccident save(RuleAccident ruleAccident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(ruleAccident);
            session.getTransaction().commit();
            return ruleAccident;
        }
    }

    public void deleteByAccident(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from RuleAccident ra where ra.accident = :accident");
            query.setParameter("accident", accident);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

}
