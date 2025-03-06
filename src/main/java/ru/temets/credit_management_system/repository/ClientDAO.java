package ru.temets.credit_management_system.repository;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.temets.credit_management_system.entity.Client;

import java.util.List;

@Repository
public class ClientDAO {

    private final SessionFactory sessionFactory;


    public ClientDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Client client)
    {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(client);
            session.getTransaction().commit();
        }
    }

    public void update(Client client) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(client);
            session.getTransaction().commit();
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.remove(client);
            }
            session.getTransaction().commit();
        }
    }

    public Client findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Client.class, id);
        }
    }

    public List<Client> findAll() {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Session: " + session);
            return session.createQuery("from Client", Client.class).list();
        }
    }

    public List<Client> search(String firstName, String lastName, String middleName, String phoneNumber, String identityNumber) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("SELECT c FROM Client c LEFT JOIN c.identity i WHERE 1=1");
            if (firstName != null) hql.append(" AND c.firstName = :firstName");
            if (lastName != null) hql.append(" AND c.lastName = :lastName");
            if (middleName != null) hql.append(" AND c.middleName = :middleName");
            if (phoneNumber != null) hql.append(" AND c.phoneNumber = :phoneNumber");
            if (identityNumber != null) hql.append(" AND i.passportNumber = :passportNumber");

            Query query = session.createQuery(hql.toString(), Client.class);
            if (firstName != null) query.setParameter("firstName", firstName);
            if (lastName != null) query.setParameter("lastName", lastName);
            if (middleName != null) query.setParameter("middleName", middleName);
            if (phoneNumber != null) query.setParameter("phoneNumber", phoneNumber);
            if (identityNumber != null) query.setParameter("passportNumber", identityNumber);

            return query.getResultList();
        }
    }
}
