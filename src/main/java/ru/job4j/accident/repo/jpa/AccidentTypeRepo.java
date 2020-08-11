package ru.job4j.accident.repo.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.jpa.AccidentType;

public interface AccidentTypeRepo extends CrudRepository<AccidentType, Integer> {
}
