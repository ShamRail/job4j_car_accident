package ru.job4j.accident.repo.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.jpa.User;

public interface UserRepo extends CrudRepository<User, Integer> {
}
