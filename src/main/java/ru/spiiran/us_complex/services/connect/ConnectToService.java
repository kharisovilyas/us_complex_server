package ru.spiiran.us_complex.services.connect;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.modelling.dto_pro42.FlightData;
import ru.spiiran.us_complex.model.dto.modelling.dto_smao.*;
import ru.spiiran.us_complex.model.dto.modelling.request.dtoViewWindowRequest;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.CatalogEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.constellation.SatelliteRepository;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.satrequest.CatalogRepository;
import ru.spiiran.us_complex.repositories.satrequest.RequestRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;
import ru.spiiran.us_complex.utils.files.DirectoryNameGenerator;
import ru.spiiran.us_complex.utils.files.FileUtils;
import ru.spiiran.us_complex.utils.files.ParserJSON;

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
public class ConnectToService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private EarthPointRepository earthPointRepository;

    @Autowired
    private SatelliteRepository satelliteRepository;

    // Директория с примерами заполнения ИД для Про42
    @Value("${template.connect.pro42}")
    private String templateDir;

    // Директория со сгенерированными ИД для Про42 (в АРМ)
    @Value("${generic.modelling.start.pro42}")
    private String generic42Dir;

    // Директория со сгенерированными ИД для СМАО (в АРМ)
    @Value("${generic.modelling.start.smao}")
    private String genericSMAODir;

    // Директория модуля моделирования СМАО
    @Value("${smao.directory}")
    private String smaoDirectory;

    // Директория движка Про42
    @Value("${pro42.directory}")
    private String pro42Directory;


    public void genericPro42Files(dtoViewWindowRequest viewWindow, ModellingModulesService.ModellingType modellingType) throws IOException {
        String fileSim = templateDir + "/Inp_Sim.txt";
        String fileOrb = templateDir + "/Orb_XXi.txt";
        String fileSC = templateDir + "/SC_XXi.txt";
        String fileCsg = templateDir + "/CsgFlag.txt";
        // Создание объекта Path для директории Pro42 ./Ballistic
        // директория ../../42_complex/42-Complex/Ballistic
        Path modellingPro42BallisticDirectory = Paths.get(pro42Directory);

        //Удаление предыдущих файлов из директории модуля моделирования ./Ballistic
        FileUtils.deleteDirectoryContents(modellingPro42BallisticDirectory);

        // Создание уникальной директории
        String directoryName = generateUniqueDirectoryName();

        //Создания поддиректории для разных типов моделирования
        String specialDir = switch (modellingType){
            case OneSatellite -> "one_sat";
            case AssessmentSatEarth -> "window_earth";
            case AssessmentConstructionConstellation -> "window_order";
            case ConstellationNetwork -> "const_net";
            case ConstellationDelivery -> "const_delivery";
            case ConstellationSatEarth -> "const_earth_sat";
        };

        // Путь к созданной директории
        String genericDir = this.generic42Dir + "/" + specialDir + "/" + directoryName;

        // Создание объекта Path для директории в АРМ
        Path directory = Paths.get(genericDir);

        // Создание уникальной директории
        Files.createDirectories(directory);

        //Генерация ИД для Pro42 на основе данных БД для формирования ИД для СМАО
        createSimFile(viewWindow, fileSim, genericDir, modellingType); // Файл с параметрами моделирования
        crateCsgFlagFile(fileCsg, genericDir, modellingType);
        createOrbFiles(fileOrb, genericDir); // Файлы орбит
        createSCFiles(fileSC, genericDir); // Файлы КА

        // Копирование файлов из созданной папки АРМа в директорию Pro42 ./Ballistic - для формирования ИД
        copyFilesToDirectory(directory, modellingPro42BallisticDirectory);
    }

    private void copyFilesToDirectory(Path currentDirectory, Path targetDirectory) throws IOException {
        Files.walkFileTree(currentDirectory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path sourceFile, BasicFileAttributes attrs) throws IOException {
                Path targetFile = targetDirectory.resolve(currentDirectory.relativize(sourceFile));
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void crateCsgFlagFile(String fileCsg, String genericDir, ModellingModulesService.ModellingType modellingType) throws IOException {
        // Чтение файла
        File file = new File(fileCsg);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        String keyword = getKeyword(modellingType);
        //TRUE					!  Flag Flight Data calculate
        String replacement = "TRUE\t\t\t\t\t!  " + keyword;

        // Чтение файла построчно и замена строк с ключевыми фразами
        while ((line = reader.readLine()) != null) {
            if (line.contains(keyword)) {
                stringBuilder.append(replacement).append("\n");
            } else {
                stringBuilder.append(line).append("\n");
            }
        }
        reader.close();

        // Создаем путь к файлу в текущей директории
        String filePath = genericDir + "/CsgFlag.txt";

        // Запись измененного содержимого обратно в файл
        File uploadFile = new File(filePath);
        FileWriter writer = new FileWriter(uploadFile);
        writer.write(stringBuilder.toString());
        writer.close();
    }

    private String getKeyword(ModellingModulesService.ModellingType modellingType) {
        String keyword = "";
        /*
        FALSE					!  Flag Flight Data calculate
        FALSE					!  Flag View Window calculate
        FALSE					!  Flag Sc to Gs Plan Contact calculate
        FALSE					!  Flag Sc to Gs Plan Contact calculate
        */
        switch (modellingType) {
            //TODO: modellingType - определяет какой модуль моделирования запускать
            case OneSatellite -> keyword = "Flag Flight Data calculate";
            case AssessmentConstructionConstellation, AssessmentSatEarth -> keyword = "Flag View Window calculate";
        }
        return keyword;
    }

    private String formingStringOfEarthPoint() {
        List<EarthPointEntity> earthPoints = earthPointRepository.findAll(); // Получаем список земных точек

        // Формируем строку с информацией о земных точках
        StringBuilder earthPointInformation = new StringBuilder();
        earthPointInformation
                .append(earthPoints.size())
                .append("                                            ! Number of Ground Stations"); // Количество точек

        for (EarthPointEntity earthPoint : earthPoints) {
            earthPointInformation
                    .append("\nTRUE  EARTH  ")
                    .append(earthPoint.getLongitude())
                    .append("  ")
                    .append(earthPoint.getLatitude())
                    .append("  ")
                    .append("\"")
                    .append(earthPoint.getNameEarthPoint())
                    .append("\"")
                    .append("             ! Exists, World, Lng, Lat, Label"); // Информация о каждой точке
        }

        return earthPointInformation.toString();
    }

    private String formingStringOfOrder() {
        List<CatalogEntity> catalogEntities = catalogRepository.findAll(); // Получаем список земных точек

        // Формируем строку с информацией о земных точках
        StringBuilder orderInformation = new StringBuilder();
        orderInformation
                .append(catalogEntities.size())
                .append("                                            ! Number of Ground Stations"); // Количество точек

        for (CatalogEntity catalogEntity : catalogEntities) {
            orderInformation
                    .append("\nTRUE  EARTH  ")
                    .append(catalogEntity.getLon())
                    .append("  ")
                    .append(catalogEntity.getLon())
                    .append("  ")
                    .append("\"")
                    .append(catalogEntity.getGoalName())
                    .append("\"")
                    .append("             ! Exists, World, Lng, Lat, Label"); // Информация о каждой точке
        }

        return orderInformation.toString();
    }

    public List<String> copyResponsePro42BallisticModelling() throws InterruptedException, IOException, EntityNotFoundException {
        // Создаем список команд для выполнения
        List<String> command = new ArrayList<>();
        // Добавляем команду cd для изменения рабочей директории
        command.add("cd ../../42_complex/42-Complex/Pro42 && ./42-Complex ../Ballistic");

        // Создаем процесс с указанными командами
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", String.join(" ", command));

        // Запуск команды
        Process process = processBuilder.start();

        // Получение вывода команды
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Список для хранения JSON-строк
        List<String> jsonList = new ArrayList<>();

        // Переменная для хранения текущей строки вывода
        String line;

        // Строка для хранения текущего JSON-объекта
        StringBuilder jsonBuilder = new StringBuilder();

        // Чтение вывода построчно
        while ((line = reader.readLine()) != null) {
            // Проверяем, содержит ли текущая строка открывающую скобку
            if (line.contains("{")) {
                // Начинается новый JSON-объект
                jsonBuilder.setLength(0); // Очищаем буфер
            }

            // Добавляем текущую строку к текущему JSON-объекту
            jsonBuilder.append(line);

            // Проверяем, содержит ли текущая строка закрывающую скобку
            if (line.contains("}")) {
                // JSON-объект завершен, добавляем его в список
                jsonList.add(jsonBuilder.toString());
            }
        }
        return jsonList;
    }

    public void genericJsonSMAOFile(List<String> resultsFromPro42) throws EntityNotFoundException, JSONException, IOException {

        List<RequestEntity> requestEntities = requestRepository.findAll();
        List<Satellite> satellites =
                satelliteRepository
                        .findAll()
                        .stream()
                        .map(Satellite::new)
                        .collect(Collectors.toList());

        Parameters parameters = createParameters();

        Event event = createEventForNode(satellites, parameters);
        Event eventSat = createSatelliteEvent(satellites, parameters, resultsFromPro42.get(0));
        EventRequest eventRequest = createEventRequest(requestEntities);

        // Преобразование объектов Event в JSON строки
        Gson gson = new Gson();
        String jsonEvent = gson.toJson(event);
        String jsonEventSat = gson.toJson(eventSat);
        String jsonEventRequest = gson.toJson(eventRequest);

        // Склеиваем две JSON строки
        String json = jsonEvent + "\n" + jsonEventSat + "\n" + jsonEventRequest;

        // Создание уникальной директории
        String directoryName = generateUniqueDirectoryName();

        // Путь к созданной директории
        String genericDir = this.genericSMAODir + "/" + directoryName;

        // Создание объекта Path для директории в АРМ
        Path directory = Paths.get(genericDir);

        // Создание всех необходимых директорий
        Files.createDirectories(directory);

        String nameOfGenericFile = "/event.json";

        // Создаем путь к файлу в текущей директории
        String filePath = genericDir + nameOfGenericFile;

        // Запись измененного содержимого обратно в файл
        File uploadFile = new File(filePath);
        FileWriter writer = new FileWriter(uploadFile);
        writer.write(json);
        writer.close();

        copyFilesToDirectory(directory, Paths.get(smaoDirectory));
    }
    private Event createEventForNode(List<Satellite> satellites, Parameters parameters) {
        EarthPointEntity earthPointEntity = earthPointRepository.findAll().get(0);
        IdNodeEntity idNodeEntity = earthPointEntity.getIdNodeEntity();
        return new Event(
                "E00",
                new Node(idNodeEntity),
                satellites,
                parameters,
                true
        );
    }

    private EventRequest createEventRequest(List<RequestEntity> requestEntities) {
        List<Order> orderList = new ArrayList<>();
        for (RequestEntity request : requestEntities) {
            orderList.add(
                    new Order(request)
            );
        }
        return new EventRequest(
                requestEntities.get(0).getTime(),
                orderList,
                "E20",
                requestEntities.get(0).getNodeEntity().getNodeId()
        );
    }

    private Event createSatelliteEvent(List<Satellite> satellites, Parameters parameters, String resultJSON) throws JSONException {
        // Получаем данные о спутнике из репозитория (пример)
        SatelliteEntity satellite = satelliteRepository.findAll().get(0);

        // Удаляем "flightData": из строки JSON
        String cleanedJSON = resultJSON.replace("\"flightData\":", "");

        FlightData flightData = ParserJSON.parseFlightDataJSON(cleanedJSON);

        // Создаем объект Event и добавляем к нему flightData
        return new Event(
                "E00",
                new Node(
                        satellite.getIdNodeEntity()
                ),
                satellites,
                parameters,
                true,
                flightData
        );

    }

    private Parameters createParameters() throws EntityNotFoundException {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            return new Parameters(optionalSystemEntity.get());
        } else {
            throw new EntityNotFoundException("System parameters not exist");
        }

    }

    // Метод для создания уникальной директории на основе текущего времени
    private String generateUniqueDirectoryName() {
        DirectoryNameGenerator generator = DirectoryNameGenerator.getInstance();
        return generator.generateUniqueDirectoryName();
    }

    private void createSCFiles(String fileSC, String genericDir) throws IOException {
        List<SatelliteEntity> satelliteEntityList = satelliteRepository.findAll();
        for (SatelliteEntity satellite : satelliteEntityList) {
            //"S/C"                         !  Label
            String label = "\"" + satellite.getSatelliteId() + "\"" + "                         !  Label";

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
            String filePath = genericDir + "/SC_XX" + satelliteEntityList.indexOf(satellite) + ".txt";

            // Запись измененного содержимого обратно в файл
            File uploadFile = new File(filePath);
            FileWriter writer = new FileWriter(uploadFile);
            writer.write(stringBuilder.toString());
            writer.close();
        }
    }

    private void createOrbFiles(String fileOrb, String genericDir) throws IOException {
        //400.0  2.0                    !  Min Altitude (km), Eccentricity
        //52.0                          !  Inclination (deg)
        //180.0                         !  Right Ascension of Ascending Node (deg)
        //0.0                           !  Argument of Periapsis (deg)
        //0.0                           !  True Anomaly (deg)
        List<SatelliteEntity> satelliteEntities = satelliteRepository.findAll();
        for (SatelliteEntity satellite : satelliteEntities) {
            String parameters = satellite.getAltitude() + " " + satellite.getEccentricity() + "                    !  Min Altitude (km), Eccentricity\n" +
                    satellite.getIncline() + "                          !  Inclination (deg)\n" +
                    satellite.getLongitudeAscendingNode() + "                         !  Right Ascension of Ascending Node (deg)\n" +
                    satellite.getPerigeeWidthArgument() + "                           !  Argument of Periapsis (deg)\n" +
                    satellite.getTrueAnomaly() + "                           !  True Anomaly (deg)";
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
            String filePath = genericDir + "/Orb_XX" + satelliteEntities.indexOf(satellite) + ".txt";

            // Запись измененного содержимого обратно в файл
            File uploadFile = new File(filePath);
            FileWriter writer = new FileWriter(uploadFile);
            writer.write(stringBuilder.toString());
            writer.close();
        }
    }


    private void createSimFile(dtoViewWindowRequest viewWindow, String fileSim, String genericDir, ModellingModulesService.ModellingType modellingType) throws IOException, EntityNotFoundException {
        // Ключевые фразы для поиска
        String[] keywords = {"Simulation Control", "Reference Orbits", "Spacecraft", "* Environment  *", "Ground Station"};

        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            SystemEntity systemEntity = optionalSystemEntity.get();

            long modellingDuration = systemEntity.getModelingEnd() - systemEntity.getModelingBegin();
            double modellingStep = systemEntity.getStep();

            if (viewWindow != null){
                modellingDuration = viewWindow.getModellingEnd() - viewWindow.getModellingBegin();
                modellingStep = viewWindow.getModellingStep();
            }

            //10000.0   0.1                   !  Sim Duration, Step Size [sec]
            String simulationControlLine = modellingDuration
                    + "   " + modellingStep
                    + "                   !  Sim Duration, Step Size [sec]";

            List<SatelliteEntity> arbitraryConstructions = satelliteRepository.findAll();

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
            replacements.add(
                    replacementsGroundStation(modellingType)
            );

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
                } else if (
                        line.contains(keywords[4]) &&
                                (modellingType.equals(ModellingModulesService.ModellingType.AssessmentSatEarth) || modellingType.equals(ModellingModulesService.ModellingType.AssessmentConstructionConstellation))
                ) {
                    stringBuilder.append(replacements.get(4)).append("\n");
                    reader.readLine(); // Пропускаем следующую строку
                    reader.readLine(); // Пропускаем следующую строку
                    reader.readLine(); // Пропускаем следующую строку
                    reader.readLine(); // Пропускаем следующую строку
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

    private String replacementsGroundStation(ModellingModulesService.ModellingType modellingType) {
        return switch (modellingType){
            case AssessmentConstructionConstellation -> formingStringOfOrder();
            case AssessmentSatEarth -> formingStringOfEarthPoint();
            default -> "";
        };
    }

    public List<String> copyResponseSMAOModelling() throws IOException {
        // Создаем список команд для выполнения
        List<String> command = new ArrayList<>();
        // Добавляем команду cd для изменения рабочей директории
        command.add("cd ../../smao_complex/SMAO-Complex && ./SMAO-Complex");

        // Создаем процесс с указанными командами
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", String.join(" ", command));

        // Запуск команды
        Process process = processBuilder.start();

        // Получение вывода команды
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Переменная для хранения текущей строки вывода
        String line;

        // Строка для хранения текущего JSON-объекта
        StringBuilder jsonBuilder = new StringBuilder();

        // Чтение вывода построчно
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        return ParserJSON.splitJsonFromResponse(jsonBuilder.toString());
    }

    public List<String> copyResponsePro42Modelling() throws InterruptedException, IOException {
        // Создаем список команд для выполнения
        List<String> command = new ArrayList<>();
        // Добавляем команду cd для изменения рабочей директории
        command.add("cd ../../42_complex/42-Complex/Pro42 && ./42-Complex ../Ballistic");

        // Создаем процесс с указанными командами
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", String.join(" ", command));

        // Запуск команды
        Process process = processBuilder.start();

        // Получение вывода команды
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Переменная для хранения текущей строки вывода
        String line;

        // Чтение вывода построчно
        StringBuilder jsonBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        return ParserJSON.toListJsonFromResponse(jsonBuilder.toString());
    }

    public String copyResponseSMAOModelling1() {
        return null;
    }
}
