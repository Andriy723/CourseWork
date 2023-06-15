package ua.lviv.iot.algo.part1.courseWork.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class EquipmentAnalyzer {

    @Id
    private Integer id;

    private String dateOfSelling;
    private String nameOfEquipment;
    private String guarantee;
    private String placeWhereWasSold;
    private String additionalInfo;

    public static final String HEADERS = "id, dateOfSelling, " +
            "nameOfEquipment, guarantee," +
            " placeWhereWasSold, additionalInfo";

    public final String getHeaders(){
        return HEADERS;
    }

    public String toCSV(){
        return id + ", " + dateOfSelling + ", " + nameOfEquipment + ", "
                + guarantee + ", " + placeWhereWasSold + ", " + additionalInfo;
    }

    public static @NotNull String getCurrentTime() throws IOException {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (formatter != null) {

            return currentDate.format(formatter);
        } else {
            throw new IOException("Not Found");
        }
    }

    public static final EquipmentAnalyzer equipmentAnalyzer = new EquipmentAnalyzer();
    public static EquipmentAnalyzer fromCSV(final String csvLine) {

        String[] values = csvLine.split(", ");

        equipmentAnalyzer.setId(Integer.parseInt(values[0]));
        equipmentAnalyzer.setDateOfSelling(values[1]);
        equipmentAnalyzer.setNameOfEquipment(values[2]);
        equipmentAnalyzer.setGuarantee(values[3]);
        equipmentAnalyzer.setPlaceWhereWasSold(values[4]);
        equipmentAnalyzer.setAdditionalInfo(values[5]);

        return equipmentAnalyzer;
    }
}
