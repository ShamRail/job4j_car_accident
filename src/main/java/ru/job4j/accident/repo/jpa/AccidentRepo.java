package ru.job4j.accident.repo.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.jpa.Accident;

import java.util.List;

public interface AccidentRepo extends CrudRepository<Accident, Integer> {
    @Query("from Accident a join fetch a.type join fetch a.ruleAccidents")
    List<Accident> findAllWithRules();
}
