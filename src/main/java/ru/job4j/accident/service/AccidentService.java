package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repo.AccidentMemoryRepository;

import java.util.Collection;

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

}
