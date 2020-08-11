package ru.job4j.accident.repo.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.jpa.Accident;

public interface AccidentRepo extends CrudRepository<Accident, Integer> {
}
