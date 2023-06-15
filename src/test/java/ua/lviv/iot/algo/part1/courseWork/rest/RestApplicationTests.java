package ua.lviv.iot.algo.part1.courseWork.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ua.lviv.iot.algo.part1.courseWork.rest.model.EquipmentAnalyzer;
import ua.lviv.iot.algo.part1.courseWork.rest.writter.EquipmentWritter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
class RestApplicationTests {

	public static final String PATH1 = "src/main/resources/1";
	public static final String PATH2 = "src/main/resources/2";
	public static final String PATH3 = "src/main/resources/3";
	public static final String PATH4 = "src/main/resources/4";
	public static final String PATH5 = "src/main/resources/5";

	@AfterEach
	public void setUp() throws IOException {
		Files.deleteIfExists(Path.of(PATH1));
		Files.deleteIfExists(Path.of(PATH2));
		Files.deleteIfExists(Path.of(PATH3));
		Files.deleteIfExists(Path.of(PATH4));
		Files.deleteIfExists(Path.of(PATH5));
	}

	@Test
	public void testGetLastId() throws IOException {
		EquipmentAnalyzer equipmentAnalyzer =
		new EquipmentAnalyzer(50, "rr",
		"rr", "rr", "rr", "rr");

		equipmentAnalyzer.setId(50);

		EquipmentWritter equipmentWritter1 = new EquipmentWritter();

		equipmentWritter1.writeToCSV(equipmentAnalyzer, PATH2);

		Integer lastId = EquipmentWritter.getLastId(equipmentAnalyzer);

		Assertions.assertEquals(50, lastId);
	}

	@Test
	public void testWriteToCSV() throws IOException {
		EquipmentWritter equipmentWritter3 = new EquipmentWritter();


		EquipmentAnalyzer equipmentAnalyzer = new EquipmentAnalyzer(2,
				"12-12-2022", "4333334",
				"eeeee", "eeeee", " eee");


		equipmentWritter3.writeToCSV(equipmentAnalyzer, PATH1);

		Path resultFile = new File(PATH1 + "EquipmentAnalyzer" +
				EquipmentAnalyzer.getCurrentTime() + ".csv").toPath();

		assertNotNull(resultFile);
	}

	@Test
	public void testReadFromCSV() throws IOException {
		EquipmentWritter equipmentWritter2 = new EquipmentWritter();

		var equipmentAnalyzerMap =
				equipmentWritter2.readFromCSV(PATH3 +
						EquipmentAnalyzer.getCurrentTime() + ".csv");

		Assertions.assertTrue(equipmentAnalyzerMap.isEmpty());
	}

	@Test
	public void testPutEquipment() throws IOException {

		EquipmentWritter equipmentWritter4 = new EquipmentWritter();

		EquipmentAnalyzer equipmentAnalyzer3 = new EquipmentAnalyzer(0,
				"1", "2",
				"3", "4", "5");

		EquipmentAnalyzer equipmentAnalyzer4 = new EquipmentAnalyzer(0,
				"1", "2",
				"3", "4", "5");

		equipmentWritter4.putEquipmentAnalyzer(1, equipmentAnalyzer3,
				PATH4 + EquipmentAnalyzer.getCurrentTime() + ".csv");

		equipmentWritter4.putEquipmentAnalyzer(2, equipmentAnalyzer4,
				PATH4 + EquipmentAnalyzer.getCurrentTime() + ".csv");

		equipmentWritter4.getArrayOfCurrentFiles(PATH4);

		Assertions.assertNotEquals(equipmentAnalyzer4, equipmentAnalyzer3);
	}

	@Test
	public void testDelete() throws IOException {

		EquipmentWritter equipmentWritter5 = new EquipmentWritter();

		EquipmentAnalyzer equipmentAnalyzer11 = new EquipmentAnalyzer(0,
				"1", "1",
				"1", "1", "1");

		equipmentWritter5.writeToCSV(equipmentAnalyzer11, PATH5);

		equipmentWritter5.deleteById(0, PATH5);

		Path resultFile = new File(PATH5
				+ "EquipmentWritter"
				+ EquipmentAnalyzer.getCurrentTime()
				+ ".csv").toPath();

		List<String> result = Files.readAllLines(resultFile);

		equipmentWritter5.getChangeForFiles(null, 2);

		Assertions.assertFalse(result.isEmpty());
	}
}
