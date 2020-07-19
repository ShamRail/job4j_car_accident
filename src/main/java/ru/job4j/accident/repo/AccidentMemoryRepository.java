package ru.job4j.accident.repo;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemoryRepository {

    private final Map<Integer, Accident> accidents = new HashMap<>();

    private final AtomicInteger id = new AtomicInteger(0);

    private final Map<Integer, AccidentType> types = new HashMap<>();

    private final AtomicInteger typeId = new AtomicInteger(0);

    private final Map<Integer, Rule> rules = new HashMap<>();

    private final AtomicInteger ruleId = new AtomicInteger(0);

    public AccidentMemoryRepository() {
        accidents.put(id.incrementAndGet(), new Accident(
                id.get(), "name1", "text1", "address1"
        ));
        accidents.put(id.incrementAndGet(), new Accident(
                id.get(), "name2", "text2", "address2"
        ));
        accidents.put(id.incrementAndGet(), new Accident(
                id.get(), "name3", "text3", "address3"
        ));
        accidents.put(id.incrementAndGet(), new Accident(
                id.get(), "name4", "text4", "address4"
        ));
        accidents.put(id.incrementAndGet(), new Accident(
                id.get(), "name5", "text5", "address5"
        ));

        types.put(typeId.incrementAndGet(), AccidentType.of(typeId.get(), "Две машины"));
        types.put(typeId.incrementAndGet(), AccidentType.of(typeId.get(), "Машина и человек"));
        types.put(typeId.incrementAndGet(), AccidentType.of(typeId.get(), "Машина и велосипед"));

        rules.put(ruleId.incrementAndGet(), Rule.of(ruleId.get(), "Статья 123 .. Рф"));
        rules.put(ruleId.incrementAndGet(), Rule.of(ruleId.get(), "Статья 456 .. Рф"));
        rules.put(ruleId.incrementAndGet(), Rule.of(ruleId.get(), "Статья 789 .. Рф"));
    }

    public Collection<AccidentType> findAllTypes() {
        return types.values();
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Accident saveOrUpdate(Accident accident) {
        return accident.getId() == 0 ? save(accident) : update(accident);
    }

    public Accident save(Accident accident) {
        id.incrementAndGet();
        accident.setId(id.get());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public Accident update(Accident accident) {
        if (accidents.containsKey(accident.getId())) {
            accidents.put(accident.getId(), accident);
        }
        return accident;
    }

    public Accident findById(int id) {
        return accidents.getOrDefault(id, new Accident());
    }

    public Collection<Rule> findAllRules() {
        return rules.values();
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }

}
