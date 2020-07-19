package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repo.AccidentJdbcTemplate;
import ru.job4j.accident.repo.AccidentMemoryRepository;
import ru.job4j.accident.repo.AccidentTypeJdbcTemplate;
import ru.job4j.accident.repo.RuleJdbcTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccidentService {

    private final AccidentJdbcTemplate repository;

    private final AccidentTypeJdbcTemplate accidentTypeRepo;

    private final RuleJdbcTemplate ruleRepository;

    public AccidentService(AccidentJdbcTemplate repository, AccidentTypeJdbcTemplate accidentTypeRepo, RuleJdbcTemplate ruleRepository) {
        this.repository = repository;
        this.accidentTypeRepo = accidentTypeRepo;
        this.ruleRepository = ruleRepository;
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
        return accidentTypeRepo.findAll();
    }

    public Collection<Rule> findAllRules() {
        return ruleRepository.findAll();
    }

    public Set<Rule> mapRules(String[] ids) {
        return Arrays.stream(ids)
                .map(Integer::parseInt)
                .map(ruleRepository::findById)
                .collect(Collectors.toSet());
    }
}
