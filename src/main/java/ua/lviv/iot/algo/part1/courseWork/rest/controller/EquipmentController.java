package ua.lviv.iot.algo.part1.courseWork.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.algo.part1.courseWork.rest.business.EquipmentService;
import ua.lviv.iot.algo.part1.courseWork.rest.model.EquipmentAnalyzer;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private static EquipmentService equipmentService;

    public EquipmentController(final EquipmentService equipmentService) {
        EquipmentController.equipmentService = equipmentService;
    }

    @GetMapping
    public List<EquipmentAnalyzer> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentAnalyzer> getEquipmentAnalyzerById(
            final @PathVariable Integer id) {

        EquipmentAnalyzer equipmentAnalyzer = equipmentService.getEquipment(id);

        if (equipmentAnalyzer != null) {
            return ResponseEntity.ok(equipmentAnalyzer);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentAnalyzer> createEquipmentAnalyzer(
            final @RequestBody EquipmentAnalyzer equipmentAnalyzer)
            throws IOException {

        equipmentService.postEquipment(equipmentAnalyzer);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(equipmentAnalyzer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentAnalyzer> updateEquipmentAnalyzer(
       final @PathVariable Integer id,
       final @RequestBody EquipmentAnalyzer equipmentAnalyzer)
            throws IOException {

        equipmentAnalyzer.setId(id);

        EquipmentAnalyzer updatedEquipment =
                equipmentService.putEquipment(id, equipmentAnalyzer);

        if (updatedEquipment != null) {
            return ResponseEntity.ok(updatedEquipment);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentAnalyzer(
            final @PathVariable Integer id) throws IOException {

        if (equipmentService.getMap().containsKey(id)) {

            equipmentService.deleteEquipment(id);

            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
