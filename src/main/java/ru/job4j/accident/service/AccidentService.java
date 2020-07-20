package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.AccidentType;
import ru.job4j.accident.model.jpa.Rule;
import ru.job4j.accident.model.jpa.RuleAccident;
import ru.job4j.accident.repo.hb.AccidentStore;
import ru.job4j.accident.repo.hb.AccidentTypeStore;
import ru.job4j.accident.repo.hb.RuleStore;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccidentService {

    private final AccidentStore repository;

    private final AccidentTypeStore accidentTypeRepo;

    private final RuleStore ruleRepository;

    public AccidentService(AccidentStore repository, AccidentTypeStore accidentTypeRepo, RuleStore ruleRepository) {
        this.repository = repository;
        this.accidentTypeRepo = accidentTypeRepo;
        this.ruleRepository = ruleRepository;
    }

    public Collection<ru.job4j.accident.model.Accident> findAll() {
        Collection<Accident> all = repository.findAll();
        Collection<ru.job4j.accident.model.Accident> accidents = new LinkedList<>();
        for (Accident accident : all) {
            ru.job4j.accident.model.Accident dto = new ru.job4j.accident.model.Accident(
                    accident.getId(), accident.getName(), accident.getText(), accident.getAddress()
            );
            dto.setType(ru.job4j.accident.model.AccidentType.of(
                    accident.getType().getId(), accident.getType().getName())
            );
            dto.setRules(
                    ruleRepository.findByAccident(accident).stream().map(
                            r -> ru.job4j.accident.model.Rule.of(r.getId(), r.getName())
                    ).collect(Collectors.toSet())
            );
            accidents.add(dto);
        }
        return accidents;
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

    public Set<RuleAccident> mapRules(String[] ids, Accident accident) {
        return Arrays.stream(ids)
                .map(Integer::parseInt)
                .map(id -> new RuleAccident(accident, ruleRepository.findById(id)))
                .collect(Collectors.toSet());
    }

    public ru.job4j.accident.model.Accident findAccidentWithRules(int id) {
        ru.job4j.accident.model.Accident accident = new ru.job4j.accident.model.Accident();
        Accident accidentFromDB = repository.findById(id);
        if (accidentFromDB != null) {
            accident = new ru.job4j.accident.model.Accident(
                    accidentFromDB.getId(), accidentFromDB.getName(), accidentFromDB.getText(), accidentFromDB.getAddress()
            );
            accident.setType(ru.job4j.accident.model.AccidentType.of(
                    accidentFromDB.getType().getId(), accidentFromDB.getType().getName())
            );
            accident.setRules(
                    ruleRepository.findByAccident(accidentFromDB).stream().map(
                            r -> ru.job4j.accident.model.Rule.of(r.getId(), r.getName())
                    ).collect(Collectors.toSet())
            );
        }
        return accident;
    }
}
