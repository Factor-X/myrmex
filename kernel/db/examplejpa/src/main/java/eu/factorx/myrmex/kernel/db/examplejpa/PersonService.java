package eu.factorx.myrmex.kernel.db.examplejpa;

import java.util.List;

public interface PersonService {
    void add(Person person);
    void deleteAll();
    List<Person> getAll();
}
