package ru.job4j.accident.repo.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.RuleAccident;

import java.util.List;

public interface RuleAccidentRepo extends CrudRepository<RuleAccident, Integer> {

    List<RuleAccident> findByAccident(Accident accident);

}
