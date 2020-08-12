package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.jpa.Accident;
import ru.job4j.accident.model.jpa.AccidentType;
import ru.job4j.accident.model.jpa.Rule;
import ru.job4j.accident.model.jpa.RuleAccident;
import ru.job4j.accident.repo.jpa.AccidentRepo;
import ru.job4j.accident.repo.jpa.AccidentTypeRepo;
import ru.job4j.accident.repo.jpa.RuleAccidentRepo;
import ru.job4j.accident.repo.jpa.RuleRepo;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccidentService {

    private AccidentRepo accidentRepo;

    private RuleRepo ruleRepo;

    private AccidentTypeRepo accidentTypeRepo;

    private RuleAccidentRepo ruleAccidentRepo;

    @Autowired
    public void setAccidentRepo(AccidentRepo accidentRepo) {
        this.accidentRepo = accidentRepo;
    }

    @Autowired
    public void setRuleRepo(RuleRepo ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    @Autowired
    public void setAccidentTypeRepo(AccidentTypeRepo accidentTypeRepo) {
        this.accidentTypeRepo = accidentTypeRepo;
    }

    @Autowired
    public void setRuleAccidentRepo(RuleAccidentRepo ruleAccidentRepo) {
        this.ruleAccidentRepo = ruleAccidentRepo;
    }

//    @PostConstruct
//    public void init() {
//        AccidentType type1 = AccidentType.of("Две машины");
//        AccidentType type2 = AccidentType.of("Машина и человек");
//        AccidentType type3 = AccidentType.of("Машина и велосипед");
//
//        accidentTypeRepo.save(type1);
//        accidentTypeRepo.save(type2);
//        accidentTypeRepo.save(type3);
//
//        Accident accident1 = new Accident("name2", "text2", "address2", type1);
//        Accident accident2 = new Accident("name2", "text2", "address2", type2);
//        Accident accident3 = new Accident("name3", "text3", "address3", type3);
//        Accident accident4 = new Accident("name4", "text4", "address4", type1);
//        Accident accident5 = new Accident("name5", "text5", "address5", type2);
//
//        accidentRepo.save(accident1);
//        accidentRepo.save(accident2);
//        accidentRepo.save(accident3);
//        accidentRepo.save(accident4);
//        accidentRepo.save(accident5);
//
//        Rule rule1 = Rule.of("Статья 123 .. Рф");
//        Rule rule2 = Rule.of("Статья 456 .. Рф");
//        Rule rule3 = Rule.of("Статья 789 .. Рф");
//
//        ruleRepo.save(rule1);
//        ruleRepo.save(rule2);
//        ruleRepo.save(rule3);
//
//        ruleAccidentRepo.save(new RuleAccident(accident1, rule1));
//        ruleAccidentRepo.save(new RuleAccident(accident1, rule2));
//        ruleAccidentRepo.save(new RuleAccident(accident2, rule3));
//        ruleAccidentRepo.save(new RuleAccident(accident3, rule2));
//        ruleAccidentRepo.save(new RuleAccident(accident4, rule3));
//    }

    public Collection<ru.job4j.accident.model.Accident> findAll() {
        Collection<Accident> all = new ArrayList<>();
        accidentRepo.findAll().forEach(all::add);
        Collection<ru.job4j.accident.model.Accident> accidents = new LinkedList<>();
        for (Accident accident : all) {
            ru.job4j.accident.model.Accident dto = new ru.job4j.accident.model.Accident(
                    accident.getId(), accident.getName(), accident.getText(), accident.getAddress()
            );
            dto.setType(ru.job4j.accident.model.AccidentType.of(
                    accident.getType().getId(), accident.getType().getName())
            );
            dto.setRules(
                    ruleAccidentRepo.findByAccident(accident).stream().map(
                            r -> ru.job4j.accident.model.Rule.of(r.getRule().getId(), r.getRule().getName())
                    ).collect(Collectors.toSet())
            );
            accidents.add(dto);
        }
        return accidents;
    }

    public Accident saveOrUpdate(Accident accident) {
        accidentRepo.save(accident);
        ruleAccidentRepo.saveAll(accident.getRuleAccidents());
        return accident;
    }

    public Accident findById(int id) {
        return accidentRepo.findById(id).orElse(new Accident());
    }

    public Collection<AccidentType> findAllTypes() {
        Collection<AccidentType> all = new ArrayList<>();
        accidentTypeRepo.findAll().forEach(all::add);
        return all;
    }

    public Collection<Rule> findAllRules() {
        Collection<Rule> all = new ArrayList<>();
        ruleRepo.findAll().forEach(all::add);
        return all;
    }

    public Set<RuleAccident> mapRules(String[] ids, Accident accident) {
        return Arrays.stream(ids)
                .map(Integer::parseInt)
                .map(id -> new RuleAccident(accident, ruleRepo.findById(id).orElse(new Rule())))
                .collect(Collectors.toSet());
    }

    public ru.job4j.accident.model.Accident findAccidentWithRules(int id) {
        ru.job4j.accident.model.Accident accident = new ru.job4j.accident.model.Accident();
        Accident accidentFromDB = accidentRepo.findById(id).orElse(null);
        if (accidentFromDB != null) {
            accident = new ru.job4j.accident.model.Accident(
                    accidentFromDB.getId(), accidentFromDB.getName(), accidentFromDB.getText(), accidentFromDB.getAddress()
            );
            accident.setType(ru.job4j.accident.model.AccidentType.of(
                    accidentFromDB.getType().getId(), accidentFromDB.getType().getName())
            );
            accident.setRules(
                    ruleAccidentRepo.findByAccident(accidentFromDB).stream().map(
                            r -> ru.job4j.accident.model.Rule.of(r.getRule().getId(), r.getRule().getName())
                    ).collect(Collectors.toSet())
            );
        }
        return accident;
    }
}
