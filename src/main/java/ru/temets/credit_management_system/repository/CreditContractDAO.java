package ru.temets.credit_management_system.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.temets.credit_management_system.entity.CreditContract;

import java.util.List;

@Repository
public class CreditContractDAO {

    private final SessionFactory sessionFactory;


    public CreditContractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(CreditContract application)
    {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(application);
            session.getTransaction().commit();
        }
    }

    public void update(CreditContract application)
    {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(application);
            session.getTransaction().commit();
        }
    }

    public CreditContract findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CreditContract.class, id);
        }
    }

    public List<CreditContract> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CreditApplication", CreditContract.class).list();
        }
    }
}
