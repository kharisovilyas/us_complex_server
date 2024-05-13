package ru.spiiran.us_complex.services.satrequest.connect;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.constellation.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;
import ru.spiiran.us_complex.utils.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConnectPro42Service {
    @Value("${template.connect.pro42}")
    private String templateDir;
    @Value("${generic.modelling.start.pro42}")
    private String genericDir;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private ConstellationArbitraryRepository constellationArbitraryRepository;
    private static final String targetDirectoryPath = "../../42_complex/42-Complex/Ballistic";

    public dtoMessage startModelling() {
        String fileSim = templateDir + "/Inp_Sim.txt";
        String fileOrb = templateDir + "/Orb_XXi.txt";
        String fileSC = templateDir + "/SC_XXi.txt";
        try {
            //Удаление предыдущих файлов из директории модуля моделирования

            FileUtils.deleteDirectoryContents(Paths.get(targetDirectoryPath));

            // Создание уникальной директории
            String directoryName = generateUniqueDirectoryName();
            // Путь к созданной директории
            genericDir += "/" + directoryName;

            // Создание объекта Path
            Path directory = Paths.get(genericDir);

            // Создание директории, если её не существует
            Files.createDirectories(directory);
            createSimFile(fileSim);
            createOrbFiles(fileOrb);
            createSCFiles(fileSC);

            //Копирование файлов из созданной папки в директорию сервера ./Ballistic - для формирования ИД
            // директория ../../42_complex/42-Complex/Ballistic

            // Путь к целевой директории
            Path targetDirectory = Paths.get(targetDirectoryPath);

            // Копирование файлов из исходной директории в целевую директорию
            Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path sourceFile, BasicFileAttributes attrs) throws IOException {
                    Path targetFile = targetDirectory.resolve(directory.relativize(sourceFile));
                    Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });

            return new dtoMessage("SUCCESS", "Connect success");
        } catch (IOException | EntityNotFoundException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    // Метод для создания уникальной директории на основе текущего времени
    private String generateUniqueDirectoryName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "modelling_" + now.format(formatter);
    }

    private void createSCFiles(String fileSC) throws IOException {
        String keyword = "42: Spacecraft Description File";
        List<coArbitraryConstruction> arbitraryConstructionList = constellationArbitraryRepository.findAll();
        for (coArbitraryConstruction arbitraryConstruction : arbitraryConstructionList) {
            //"S/C"                         !  Label
            String label = "\"" + arbitraryConstruction.getID() + "\"" + "                         !  Label";

            // Чтение файла
            File file = new File(fileSC);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            int currentLine = 0;

            // Чтение файла построчно и замена строк с ключевыми фразами
            while ((line = reader.readLine()) != null) {
                currentLine++;
                stringBuilder.append(line).append("\n");
                if (currentLine == 2) {
                    stringBuilder.append(label).append("\n");
                    reader.readLine();
                }
            }
            reader.close();
            // Создаем путь к файлу в текущей директории
            String filePath = genericDir + "/SC_XX" + arbitraryConstructionList.indexOf(arbitraryConstruction) +".txt";

            // Запись измененного содержимого обратно в файл
            File uploadFile = new File(filePath);
            FileWriter writer = new FileWriter(uploadFile);
            writer.write(stringBuilder.toString());
            writer.close();
        }
    }

    private void createOrbFiles(String fileOrb) throws IOException {
        String keyword = "Use these lines if CENTRAL";
        //400.0  2.0                    !  Min Altitude (km), Eccentricity
        //52.0                          !  Inclination (deg)
        //180.0                         !  Right Ascension of Ascending Node (deg)
        //0.0                           !  Argument of Periapsis (deg)
        //0.0                           !  True Anomaly (deg)
        List<coArbitraryConstruction> arbitraryConstructionList = constellationArbitraryRepository.findAll();
        for (coArbitraryConstruction arbitraryConstruction : arbitraryConstructionList) {
            String parameters = arbitraryConstruction.getAltitude() + " " + arbitraryConstruction.getEccentricity() + "                    !  Min Altitude (km), Eccentricity\n" +
                    arbitraryConstruction.getIncline() + "                          !  Inclination (deg)\n" +
                    arbitraryConstruction.getLongitudeAscendingNode() + "                         !  Right Ascension of Ascending Node (deg)\n" +
                    arbitraryConstruction.getPerigeeWidthArgument() + "                           !  Argument of Periapsis (deg)\n" +
                    arbitraryConstruction.getTrueAnomaly() + "                           !  True Anomaly (deg)";
            File file = new File(fileOrb);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            int currentLine = 0;

            // Чтение файла построчно и замена строк с ключевыми фразами
            while ((line = reader.readLine()) != null) {
                currentLine++;
                stringBuilder.append(line).append("\n");
                if (currentLine == 15) {
                    stringBuilder.append(parameters).append("\n");
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                }
            }
            reader.close();
            // Создаем путь к файлу в текущей директории
            String filePath = genericDir + "/Orb_XX" + arbitraryConstructionList.indexOf(arbitraryConstruction) + ".txt";

            // Запись измененного содержимого обратно в файл
            File uploadFile = new File(filePath);
            FileWriter writer = new FileWriter(uploadFile);
            writer.write(stringBuilder.toString());
            writer.close();
        }
    }


    private void createSimFile(String fileSim) throws IOException, EntityNotFoundException {
        // Ключевые фразы для поиска
        String[] keywords = {"Simulation Control", "Reference Orbits", "Spacecraft", "* Environment  *"};

        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            SystemEntity systemEntity = optionalSystemEntity.get();

            String simulationControlLine = String.format(
                    "%-60s",
                    (systemEntity.getModelingEnd() - systemEntity.getStartTime())
                            + " " + systemEntity.getStep().toString()
                            + " !  Sim Duration, Step Size [sec]"
            );

            List<coArbitraryConstruction> arbitraryConstructions = constellationArbitraryRepository.findAll();

            //1                               !  Number of Reference Orbits
            //TRUE   Orb_LEO.txt              !  Input file name for Orb 0
            String referenceOrbits = arbitraryConstructions.size() + "                               !  Number of Reference Orbits\n" +
                    arbitraryConstructions.stream()
                            .map(file -> "TRUE   Orb_XX" + arbitraryConstructions.indexOf(file) + ".txt              !  Input file name for Orb " + arbitraryConstructions.indexOf(file))
                            .collect(Collectors.joining("\n"));

            //1                               !  Number of Spacecraft
            //TRUE  0 SC_Empty.txt           !  Existence, RefOrb, Input file for SC 0

            String spacecraft = arbitraryConstructions.size() + "                               !  Number of Spacecraft\n" +
                    arbitraryConstructions.stream()
                            .map(file -> "TRUE  " + arbitraryConstructions.indexOf(file) + " SC_XX" + arbitraryConstructions.indexOf(file) + ".txt           !  Existence, RefOrb, Input file for SC " + arbitraryConstructions.indexOf(file))
                            .collect(Collectors.joining("\n"));

            // Получаем время в формате Unix
            long unixTime = systemEntity.getStartTime();

            // Конвертируем время Unix в объект LocalDateTime
            Instant instant = Instant.ofEpochSecond(unixTime);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

            // Форматируем дату и время в указанный формат
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH mm ss.SS");
            String formattedDateTime = dateTime.format(formatter);

            // Создаем строку environment
            String environment = formattedDateTime.substring(0, 10) + "                      !  Date (UTC) (Month, Day, Year)\n" +
                    formattedDateTime.substring(11) + "                     !  Time (UTC) (Hr,Min,Sec)";
            // Содержимое для замены
            List<String> replacements = new ArrayList<>();
            replacements.add(simulationControlLine);
            replacements.add(referenceOrbits);
            replacements.add(spacecraft);
            replacements.add(environment);

            // Чтение файла
            File file = new File(fileSim);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // Чтение файла построчно и замена строк с ключевыми фразами
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                if (line.contains(keywords[0])) {
                    // Пропускаем 2 строки после найденной
                    stringBuilder.append(reader.readLine()).append("\n");
                    stringBuilder.append(replacements.get(0)).append("\n");
                    reader.readLine(); // Пропускаем следующую строку

                } else if (line.contains(keywords[1])) {
                    // Пропускаем одну строку после найденной
                    stringBuilder.append(replacements.get(1)).append("\n");
                    reader.readLine(); // Пропускаем следующую строку
                    reader.readLine(); // Пропускаем следующую строку
                } else if (line.contains(keywords[2])) {
                    // Пропускаем одну строку после найденной
                    stringBuilder.append(replacements.get(2)).append("\n");
                    reader.readLine(); // Пропускаем следующую строку
                    reader.readLine(); // Пропускаем следующую строку
                } else if (line.contains(keywords[3])) {
                    // Пропускаем одну строку после найденной
                    stringBuilder.append(replacements.get(3)).append("\n");
                    reader.readLine(); // Пропускаем следующую строку
                    reader.readLine(); // Пропускаем следующую строку
                }
            }
            reader.close();

            // Создаем путь к файлу в текущей директории
            String filePath = genericDir + "/Inp_Sim.txt";

            // Запись измененного содержимого обратно в файл
            File uploadFile = new File(filePath);
            FileWriter writer = new FileWriter(uploadFile);
            writer.write(stringBuilder.toString());
            writer.close();
        } else {
            throw new EntityNotFoundException("System information not added");
        }
    }

}
