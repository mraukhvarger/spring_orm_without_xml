package com.raukhvarger.examples.spring_orm.db;

import com.raukhvarger.examples.spring_orm.db.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by jerde on 05.11.2016.
 */

@Component
public class DAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Person person) {
        em.persist(person);
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM Person p").getResultList();
    }

}
