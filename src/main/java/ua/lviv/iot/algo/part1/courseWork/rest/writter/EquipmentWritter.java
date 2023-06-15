package ua.lviv.iot.algo.part1.courseWork.rest.writter;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.courseWork.rest.model.EquipmentAnalyzer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class EquipmentWritter {

    public static final String PATH = "src/main/resources/";

    public void writeToCSV(final EquipmentAnalyzer equipmentAnalyzer,
                           String PATH) throws IOException {

        final String nameOfClass = getClass().getSimpleName();

        String nameOfFile = PATH + nameOfClass
                + EquipmentAnalyzer.getCurrentTime() + ".csv";

        boolean fileExists = Files.exists(Path.of(nameOfFile));

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(nameOfFile, StandardCharsets.UTF_8, true))) {

            if (!fileExists) {
                writer.write(equipmentAnalyzer.getHeaders() + "\n");
            }
            writer.write(equipmentAnalyzer.toCSV() + "\n");

        } catch (Exception e) {
            System.out.println("Error:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<Integer, EquipmentAnalyzer> readFromCSV(final String fileName) {
        File[] files = getArrayOfCurrentFiles(fileName);

        Map<Integer, EquipmentAnalyzer> equipmentAnalyzerHashMap = new HashMap<>();

        boolean fileExists = Files.exists(Path.of(fileName));

        if (files != null) {

            if (fileExists) {

                for (File file : files) {

                    try (BufferedReader reader = new BufferedReader(

                            new FileReader(file, StandardCharsets.UTF_8)))
                    {
                        String strings;

                        reader.readLine();

                        while ((strings = reader.readLine()) != null) {

                            EquipmentAnalyzer equipmentAnalyzer =
                                    EquipmentAnalyzer.fromCSV(strings);

                            equipmentAnalyzerHashMap.put(
                                    equipmentAnalyzer.getId(),
                                    equipmentAnalyzer);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error was detected: "
                                + e.getMessage());

                        throw new RuntimeException(
                                "Error occurred while reading from CSV");
                    }
                }
            }
        }

        return equipmentAnalyzerHashMap;
    }

    public static int getLastId(final EquipmentAnalyzer equipmentAnalyzer) {

        List<String> files = getFiles();

        int maxId = 0;

        for (String file : files) {

            if (equipmentAnalyzer == null) {
                continue;
            }

            try (BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(PATH + file,
                            StandardCharsets.UTF_8))) {

                String strings;

                bufferedReader.readLine();

                while ((strings = bufferedReader.readLine()) != null) {

                    String[] values = strings.split(", ");

                    if (values.length > 0) {

                        int id = Integer.parseInt(values[0]);

                        if (id > maxId) {

                            maxId = id;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

                System.err.println("Error while reading the file:\n" + e.getMessage());
            }
        }

        return maxId;
    }

    public static @NotNull List<String> getFiles() {
        File file = new File(PATH);

        String[] fileNames = file.list();

        if (fileNames != null) {

            return Arrays.asList(fileNames);

        } else {
            return new LinkedList<>();
        }
    }

    public void deleteById(final Integer id, final String PATH) throws IOException {

        File file1 = getFile(id, PATH);

        if (file1 == null) {
            return;
        }

        List<String> lines = write(file1, id);

        String name = file1.getName();

        boolean deleteSuccess = file1.delete();

        if (deleteSuccess) {

            try (var bufferedWriter = new BufferedWriter(new FileWriter(
                    PATH + name,
                    StandardCharsets.UTF_8))) {

                for (String line : lines) {
                    bufferedWriter.write(line + "\n");
                }
            }
        } else {
            throw new RemoteException("Error while remoting");
        }
    }

    public File getFile(final Integer id, final String PATH) throws IOException {
        File[] files = getArrayOfCurrentFiles(PATH);

        return getChangeForFiles(files, id);
    }

    public List<String> write(final File file, final Integer id) throws IOException {

        List<String> lines = new ArrayList<>();

        try (var bufferedReader = new BufferedReader(

                new FileReader(file, StandardCharsets.UTF_8))) {

            String HEADER = bufferedReader.readLine();

            lines.add(HEADER);

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {

                String[] split = currentLine.split(", ", 2);

                int currentId = Integer.parseInt(split[0]);

                if (currentId == id) {
                    continue;
                }

                lines.add(currentLine);
            }
        }
        return lines;
    }

    public File getChangeForFiles(final File[] files, final Integer id)
            throws IOException {

        File file1 = null;

        if (files != null) {
            for (File file : files) {

                try (var bufferedReader = new BufferedReader(new FileReader(file,
                        StandardCharsets.UTF_8))) {

                    bufferedReader.readLine();

                    String currentLine;

                    while ((currentLine = bufferedReader.readLine()) != null) {

                        String[] split = currentLine.split(", ", 2);
                        int currentId = Integer.parseInt(split[0]);

                        if (currentId == id) {
                            file1 = file;
                            break;
                        }
                    }
                }
            }
        }
        return file1;
    }

    public File[] getArrayOfCurrentFiles(final String PATH) {
        File file = new File(PATH);

        LocalDate currentDate = LocalDate.now();

        YearMonth currentYearMonth = YearMonth.from(currentDate);

        File[] files = file.listFiles();

        if (files == null) {
            return null;
        }

        return Arrays.stream(files).filter(file1 -> {
                    String fileName = file1.getName();

                    if (fileName.matches(
                    "EquipmentWritter\\d{4}-\\d{2}-\\d{2}.csv")) {

                        String fileDateStr = fileName.substring(20, 25);
                        LocalDate fileDate = LocalDate.parse(fileDateStr);
                        YearMonth fileYearMonth = YearMonth.from(fileDate);

                        return fileYearMonth.equals(currentYearMonth);

                    } else {
                        return false;
                    }
                })
                .toArray(File[]::new);
    }

    public void putEquipmentAnalyzer(final Integer id,
                           final EquipmentAnalyzer equipmentAnalyzer,
                           final String PATH_files) throws IOException {

        File file = getFile(id, PATH_files);

        if (file == null) {
            return;
        }

        List<String> written = write(file, id);

        String name = file.getName();

        boolean deleteSuccess = file.delete();

        if (deleteSuccess) {
            BufferedWriter bufferedWriter = null;

            try {

                bufferedWriter = new BufferedWriter(
                        new FileWriter(PATH_files + name,
                                StandardCharsets.UTF_8));

                for (String w : written) {
                    bufferedWriter.write(w + "\n");
                }
                bufferedWriter.write(equipmentAnalyzer.toCSV() + "\n");

            } catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            finally {

                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
        } else {
            throw new RemoteException("Error while remoting");
        }
    }
}
