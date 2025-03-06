package ru.temets.credit_management_system.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.temets.credit_management_system.entity.CreditApplication;
import ru.temets.credit_management_system.entity.CreditContract;

import java.util.List;

@Repository
public class CreditApplicationDAO {

    private final SessionFactory sessionFactory;

    public CreditApplicationDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(CreditApplication application)
    {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(application);
            session.getTransaction().commit();
        }
    }

    public void update(CreditApplication application)
    {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(application);
            session.getTransaction().commit();
        }
    }

    public CreditApplication findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CreditApplication.class, id);
        }
    }

    public CreditApplication findByClientId(Long clientId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CreditApplication ca where ca.client.id = :clientId",
                    CreditApplication.class).setParameter("clientId", clientId).uniqueResult();
        }
    }

    public List<CreditApplication> findByStatus(CreditApplication.StatusType status) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CreditApplication ca where ca.status = :status",
                    CreditApplication.class).setParameter("status", status).list();
        }
    }

    public List<CreditApplication> findByApplicationSign(CreditContract.SignatureStatus sign) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CreditApplication ca where ca.contract.signatureStatus = :sign",
                    CreditApplication.class).setParameter("sign", sign).list();
        }
    }

    public List<CreditApplication> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CreditApplication", CreditApplication.class).list();
        }
    }
}
