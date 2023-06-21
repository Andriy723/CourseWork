package ua.lviv.iot.algo.part1.courseWork.rest.business;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.courseWork.rest.model.EquipmentAnalyzer;
import ua.lviv.iot.algo.part1.courseWork.rest.writter.EquipmentWritter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EquipmentService {
    private static EquipmentWritter equipmentWritter;
    private final Map<Integer, EquipmentAnalyzer> equipmentAnalyzerMap;
    private final AtomicInteger nextAvailable;

    @Autowired
    public EquipmentService(final @NotNull EquipmentWritter equipmentWritter) {
        EquipmentService.equipmentWritter = equipmentWritter;
        this.equipmentAnalyzerMap =
          equipmentWritter.readFromCSV(EquipmentWritter.PATH);
        this.nextAvailable = new AtomicInteger(
          EquipmentWritter.getLastId(EquipmentAnalyzer.equipmentAnalyzer));
    }

    public Map<Integer, EquipmentAnalyzer> getMap() {
        return new HashMap<>(equipmentAnalyzerMap);
    }

    public List<EquipmentAnalyzer> getAllEquipment() {
        return List.copyOf(equipmentAnalyzerMap.values());
    }

    public EquipmentAnalyzer getEquipment(final Integer id) {
        return equipmentAnalyzerMap.get(id);
    }

    public void postEquipment(final @NotNull
        EquipmentAnalyzer equipmentAnalyzer) throws IOException {

        int newId = nextAvailable.incrementAndGet();

        equipmentAnalyzer.setId(newId);

        equipmentAnalyzerMap.put(newId, equipmentAnalyzer);

        equipmentWritter.writeToCSV(equipmentAnalyzer, EquipmentWritter.PATH);
    }

    public EquipmentAnalyzer putEquipment(final Integer id,
      final EquipmentAnalyzer equipmentAnalyzer) throws IOException {

        if (equipmentAnalyzerMap.containsKey(id)) {

            equipmentAnalyzer.setId(id);

            equipmentAnalyzerMap.replace(id, equipmentAnalyzer);

            equipmentWritter.putEquipmentAnalyzer(id, equipmentAnalyzer,
                    EquipmentWritter.PATH);

            return equipmentAnalyzer;
        } else {
            return null;
        }
    }

    public void deleteEquipment(final Integer id) throws IOException {
        if (equipmentAnalyzerMap.containsKey(id)) {

            equipmentAnalyzerMap.remove(id);

            equipmentWritter.deleteById(id, EquipmentWritter.PATH);
        }
    }
}
