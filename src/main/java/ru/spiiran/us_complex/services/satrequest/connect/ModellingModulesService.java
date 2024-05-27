package ru.spiiran.us_complex.services.satrequest.connect;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.dto_smao.*;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentConstellation;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentSatEarth;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.CatalogEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.constellation.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.satrequest.CatalogRepository;
import ru.spiiran.us_complex.repositories.satrequest.RequestRepository;
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
public class ModellingModulesService {
    @Value("${template.connect.pro42}")
    private String templateDir;
    @Value("${generic.modelling.start.pro42}")
    private String genericDir;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private EarthPointRepository earthPointRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ConstellationArbitraryRepository constellationArbitraryRepository;
    private static final String targetDirectoryPath = "../../42_complex/42-Complex/Ballistic";

    public ModellingModulesService() {
    }

    public void genericPro42Files(ModellingType modellingType) throws IOException {
        String fileSim = templateDir + "/Inp_Sim.txt";
        String fileOrb = templateDir + "/Orb_XXi.txt";
        String fileSC = templateDir + "/SC_XXi.txt";
        // Создание объекта Path для директории Pro42 ./Ballistic
        // директория ../../42_complex/42-Complex/Ballistic
        Path modellingPro42BallisticDirectory = Paths.get(targetDirectoryPath);

        //Удаление предыдущих файлов из директории модуля моделирования ./Ballistic
        FileUtils.deleteDirectoryContents(modellingPro42BallisticDirectory);

        // Создание уникальной директории
        String directoryName = generateUniqueDirectoryName();

        // Путь к созданной директории
        genericDir += "/" + directoryName;

        // Создание объекта Path для директории в СМАО
        Path directory = Paths.get(genericDir);

        // Создание уникальной директории
        Files.createDirectories(directory);

        //Генерация ИД для Pro42 на основе данных БД для формирования ИД для СМАО
        createSimFile(fileSim); // Файл с параметрами моделирования
        switch (modellingType) {
            //TODO: modellingType - определяет какой модуль моделирования запускать
            case AssessmentSatEarth -> addEarthPointToSimFile(fileSim);
            case AssessmentConstructionConstellation -> addOrderToSimFile(fileSim);
        }
        createOrbFiles(fileOrb); // Файлы орбит
        createSCFiles(fileSC); // Файлы КА

        // Копирование файлов из созданной папки в директорию сервера ./Ballistic - для формирования ИД
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path sourceFile, BasicFileAttributes attrs) throws IOException {
                Path targetFile = modellingPro42BallisticDirectory.resolve(directory.relativize(sourceFile));
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void addEarthPointToSimFile(String fileSim) throws IOException {
        String keyword = "Ground Stations"; // Ключевое слово для поиска в файле
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

        // Читаем исходный файл построчно и заменяем информацию о земных точках
        File file = new File(fileSim);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder updatedContent = new StringBuilder();
        String line;
        boolean foundKeyword = false; // Флаг, указывающий, найдено ли ключевое слово

        while ((line = reader.readLine()) != null) {
            updatedContent.append(line).append("\n");
            if (!foundKeyword && line.contains(keyword)) { // Если ключевое слово найдено
                updatedContent.append(earthPointInformation).append("\n"); // Добавляем информацию о земных точках
                foundKeyword = true;
                // Пропускаем следующие строки, соответствующие предыдущим точкам,
                // если они были, чтобы избежать дублирования
                for (int i = 0; i < earthPoints.size(); i++) {
                    reader.readLine();
                }
            }
        }

        // Записываем обновленное содержимое в файл
        String filePath = genericDir + "/Inp_Sim.txt"; // Путь к файлу
        File uploadFile = new File(filePath);
        FileWriter writer = new FileWriter(uploadFile);
        writer.write(updatedContent.toString());


    }

    private void addOrderToSimFile(String fileSim) throws IOException {
        String keyword = "Ground Stations"; // Ключевое слово для поиска в файле
        List<CatalogEntity> catalogEntities = catalogRepository.findAll(); // Получаем список земных точек

        // Формируем строку с информацией о земных точках
        StringBuilder earthPointInformation = new StringBuilder();
        earthPointInformation
                .append(catalogEntities.size())
                .append("                                            ! Number of Ground Stations"); // Количество точек

        for (CatalogEntity catalogEntity : catalogEntities) {
            earthPointInformation
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

        // Читаем исходный файл построчно и заменяем информацию о земных точках
        File file = new File(fileSim);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder updatedContent = new StringBuilder();
        String line;
        boolean foundKeyword = false; // Флаг, указывающий, найдено ли ключевое слово

        while ((line = reader.readLine()) != null) {
            updatedContent.append(line).append("\n");
            if (!foundKeyword && line.contains(keyword)) { // Если ключевое слово найдено
                updatedContent.append(earthPointInformation).append("\n"); // Добавляем информацию о земных точках
                foundKeyword = true;
                // Пропускаем следующие строки, соответствующие предыдущим точкам,
                // если они были, чтобы избежать дублирования
                for (int i = 0; i < catalogEntities.size(); i++) {
                    reader.readLine();
                }
            }
        }

        // Записываем обновленное содержимое в файл
        String filePath = genericDir + "/Inp_Sim.txt"; // Путь к файлу
        File uploadFile = new File(filePath);
        FileWriter writer = new FileWriter(uploadFile);
        writer.write(updatedContent.toString());
    }

    private List<String> copyResponsePro42BallisticModelling() throws InterruptedException, IOException, EntityNotFoundException {
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

    public void genericJsonSMAOFiles(List<String> resultsFromPro42) throws EntityNotFoundException, JSONException, IOException {

        List<RequestEntity> requestEntities = requestRepository.findAll();
        List<Satellite> satellites =
                constellationArbitraryRepository
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

        //TODO: На этом моменте если потребуется передавать JSON то придется возвращать строку json

        // Получаем текущую директорию
        String currentDirectory = System.getProperty("user.dir");

        // Создаем путь к файлу в текущей директории
        String filePath = currentDirectory + "/event.json";

        // Создаем объект FileWriter для записи в файл
        FileWriter writer = new FileWriter(filePath);

        // Записываем JSON-строку в файл
        writer.write(json);

        // Закрываем FileWriter после использования
        writer.close();
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
                requestEntities.get(0).getRequestId()
        );
    }

    private Event createSatelliteEvent(List<Satellite> satellites, Parameters parameters, String resultJSON) throws JSONException {
        // Получаем данные о спутнике из репозитория (пример)
        coArbitraryConstruction satellite = constellationArbitraryRepository.findAll().get(0);

        FlightData flightData = parseJSON(resultJSON);

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

    private FlightData parseJSON(String resultJSON) {
        // Удаляем "flightData": из строки JSON
        String cleanedJSON = resultJSON.replace("\"flightData\":", "");

        // Удаляем кавычки из начала и конца строки и убираем экранирование символов
        String unescapedJSON = removeQuotesAndUnescape(cleanedJSON);

        // Используем Gson для преобразования JSON-строки в объект FlightData
        Gson gson = new Gson();

        return gson.fromJson(unescapedJSON, FlightData.class);
    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        // Удаляем кавычки из начала и конца строки
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        // Убираем экранирование символов
        return StringEscapeUtils.unescapeJson(noQuotes);
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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "modelling_" + now.format(formatter);
    }

    private void createSCFiles(String fileSC) throws IOException {
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
            String filePath = genericDir + "/SC_XX" + arbitraryConstructionList.indexOf(arbitraryConstruction) + ".txt";

            // Запись измененного содержимого обратно в файл
            File uploadFile = new File(filePath);
            FileWriter writer = new FileWriter(uploadFile);
            writer.write(stringBuilder.toString());
            writer.close();
        }
    }

    private void createOrbFiles(String fileOrb) throws IOException {
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

            //10000.0   0.1                   !  Sim Duration, Step Size [sec]
            String simulationControlLine = (systemEntity.getModelingEnd() - systemEntity.getStartTime())
                    + "   " + systemEntity.getStep().toString()
                    + "                   !  Sim Duration, Step Size [sec]";

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

    public enum ModellingType {
        OneSatellite,
        ConstellationSatEarth,
        ConstellationNetwork,
        ConstellationDelivery,
        AssessmentConstructionConstellation,
        AssessmentSatEarth
    }

    public List<dtoAssessmentSatEarth> assessmentSatEarth() throws IOException, InterruptedException {
        // Генерирует файлы ИД для отправки их в Pro42
        // Копирует файлы в рабочую директорию движка
        genericPro42Files(ModellingType.AssessmentSatEarth);

        // Генерирует файлы ИД для отправки их в Pro42
        List<String> ballisticModellingData = copyResponsePro42BallisticModelling();

        return convertToAssessmentSatEarth(ballisticModellingData);
    }

    private List<dtoAssessmentSatEarth> convertToAssessmentSatEarth(List<String> ballisticModellingData) {
        return null;
    }

    public List<dtoAssessmentConstellation> assessmentConstellation() throws IOException, InterruptedException {
        // Генерирует файлы ИД для отправки их в Pro42
        // Копирует файлы в рабочую директорию движка
        genericPro42Files(ModellingType.AssessmentConstructionConstellation);

        // Генерирует файлы ИД для отправки их в Pro42
        List<String> ballisticModellingData = copyResponsePro42BallisticModelling();

        return convertToAssessmentConstellation(ballisticModellingData);
    }

    private List<dtoAssessmentConstellation> convertToAssessmentConstellation(List<String> ballisticModellingData) {
        return null;
    }

    public dtoMessage modellingOneSat() {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            genericPro42Files(ModellingType.OneSatellite);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            genericJsonSMAOFiles(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }

    }

    public dtoMessage modellingConstellationSatEarth() {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            genericPro42Files(ModellingType.ConstellationSatEarth);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            genericJsonSMAOFiles(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    public dtoMessage modellingConstellationNetwork() {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            genericPro42Files(ModellingType.ConstellationNetwork);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            genericJsonSMAOFiles(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    public dtoMessage modellingConstellationDelivery() {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            genericPro42Files(ModellingType.ConstellationDelivery);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            genericJsonSMAOFiles(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }
}
