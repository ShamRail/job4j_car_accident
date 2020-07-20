package ru.job4j.accident.repo.hb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.jpa.AccidentType;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Repository
public class AccidentTypeStore {

    private SessionFactory sessionFactory;

    private LocalSessionFactoryBean localSessionFactoryBean;

    @Autowired
    public void setLocalSessionFactoryBean(LocalSessionFactoryBean localSessionFactoryBean) {
        this.localSessionFactoryBean = localSessionFactoryBean;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @PostConstruct
    public void initSession() {
        sessionFactory = localSessionFactoryBean.getObject();
    }

    public Collection<AccidentType> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<AccidentType> list = session.createQuery("from AccidentType").list();
            session.getTransaction().commit();
            return list;
        }
    }

    public AccidentType findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            AccidentType type = session.get(AccidentType.class, id);
            session.getTransaction().commit();
            return type;
        }
    }

    public AccidentType save(AccidentType accidentType) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(accidentType);
            session.getTransaction().commit();
        }
        return accidentType;
    }


}
