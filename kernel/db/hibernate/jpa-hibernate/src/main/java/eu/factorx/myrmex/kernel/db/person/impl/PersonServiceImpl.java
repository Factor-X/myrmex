package eu.factorx.myrmex.kernel.db.person.impl;

import java.util.List;

import javax.persistence.EntityManager;

import eu.factorx.myrmex.kernel.db.person.Person;
import eu.factorx.myrmex.kernel.db.person.PersonService;

public class PersonServiceImpl implements PersonService {
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) { 
        this.em = em;
    }

    @Override
    public void add(Person person) {
        em.persist(person);
        em.flush();
    }
    
    public void deleteAll() {
        em.createQuery("delete from Person").executeUpdate();
        em.flush();
    }

    @Override
    public List<Person> getAll() {
        return em.createQuery("select p from Person p", Person.class).getResultList();
    }

}
