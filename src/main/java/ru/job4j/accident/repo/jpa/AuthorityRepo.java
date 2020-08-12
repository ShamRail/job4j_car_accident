package ru.job4j.accident.repo.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.jpa.Authority;

public interface AuthorityRepo extends CrudRepository<Authority, Integer> {
    Authority findByAuthority(String authority);
}
