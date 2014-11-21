package eu.factorx.myrmex.kernel.db.person;

import java.util.List;

public interface PersonService {
    void add(Person person);
    void deleteAll();
    List<Person> getAll();
}
