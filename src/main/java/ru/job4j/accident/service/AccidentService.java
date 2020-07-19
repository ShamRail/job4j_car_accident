package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repo.AccidentMemoryRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccidentService {

    private AccidentMemoryRepository repository;

    public AccidentService(AccidentMemoryRepository repository) {
        this.repository = repository;
    }

    public Collection<Accident> findAll() {
        return repository.findAll();
    }

    public Accident saveOrUpdate(Accident accident) {
        return repository.saveOrUpdate(accident);
    }

    public Accident findById(int id) {
        return repository.findById(id);
    }

    public Collection<AccidentType> findAllTypes() {
        return repository.findAllTypes();
    }

    public Collection<Rule> findAllRules() {
        return repository.findAllRules();
    }

    public Set<Rule> mapRules(String[] ids) {
        return Arrays.stream(ids)
                .map(Integer::parseInt)
                .map(id -> repository.findRuleById(id))
                .collect(Collectors.toSet());
    }
}
