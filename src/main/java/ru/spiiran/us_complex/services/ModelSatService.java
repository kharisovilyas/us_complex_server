package ru.spiiran.us_complex.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;
import ru.spiiran.us_complex.repositories.modelsat.ModelSatRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Transactional
public class ModelSatService {
    @Autowired
    private ModelSatRepository modelSatRepository;
    @Value("${image.ms.upload.dir}") // Путь к директории для загружаемых изображений
    private String uploadDir;

    public dtoMessage addModelSat(dtoModelSat dtoModelSat) {
        try {
            modelSatRepository.save(new ModelSatEntity(dtoModelSat));
            return new dtoMessage("SUCCESS", "Модель спутника успешно добавлена.");
        } catch (Exception e) {
            e.printStackTrace();
            return new dtoMessage("ERROR", "Ошибка при добавлении модели спутника: " + e.getMessage());
        }
    }

    public dtoMessage uploadImageOfModelSat(Long id, MultipartFile file) {
        Optional<ModelSatEntity> optionalModelSatEntity = modelSatRepository.findById(id);
        if (optionalModelSatEntity.isPresent()) {
            ModelSatEntity existingModelSatEntity = optionalModelSatEntity.get();
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            try {
                Files.createFile(filePath);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                existingModelSatEntity.setImageFileName(fileName);
                modelSatRepository.save(existingModelSatEntity);
                return new dtoMessage("SUCCESS", "Image upload");
            } catch (IOException e) {
                e.printStackTrace();
                return new dtoMessage("ERROR", "Image not upload");
            }
        } else {
            return new dtoMessage("ERROR", "Model Sat with this id not found");
        }
    }

    public ResponseEntity<Resource> getImageOfModelSat(@PathVariable String imageName) {
        // Логика для отправки изображения по его имени
        Path imagePath = Paths.get(uploadDir, imageName);
        try {
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public dtoMessage updateModelSat(Long id, dtoModelSat dtoModelSat) {
        Optional<ModelSatEntity> optionalModelSatEntity = modelSatRepository.findById(id);
        if (optionalModelSatEntity.isPresent()) {
            ModelSatEntity existingModelSatEntity = optionalModelSatEntity.get();
            existingModelSatEntity.setModelName(dtoModelSat.getModelName());
            existingModelSatEntity.setDescription(dtoModelSat.getDescription());
            modelSatRepository.save(existingModelSatEntity);
            return new dtoMessage("SUCCESS", "Модель КА c id = " + id + "обновлена успешно");
        } else {
            return new dtoMessage("ERROR", "Модель КА с id = " + id + "не найдена в базе данных");
        }
    }

    public dtoMessage deleteModelSat(Long id) {
        Optional<ModelSatEntity> optionalModelSatEntity = modelSatRepository.findById(id);
        if (optionalModelSatEntity.isPresent()) {
            ModelSatEntity modelSatEntity = optionalModelSatEntity.get();
            // Получаем путь к изображению
            String imageFileName = modelSatEntity.getImageFileName();
            // Проверяем, что путь к изображению не пустой
            if (imageFileName != null && !imageFileName.isEmpty()) {
                // Создаем объект File для файла изображения
                File imageFile = new File(uploadDir + File.separator + imageFileName);
                // Проверяем, существует ли файл изображения
                if (imageFile.exists()) {
                    // Удаляем файл изображения
                    if (!imageFile.delete()) {
                        // Не удалось удалить файл изображения
                        return new dtoMessage("ERROR", "Не удалось удалить изображение модели КА с id = " + id);
                    }
                }
            }
            // Удаляем модель КА из репозитория
            modelSatRepository.delete(modelSatEntity);
            return new dtoMessage("SUCCESS", "Модель КА с id = " + id + " удалена успешно");
        } else {
            return new dtoMessage("ERROR", "Модель КА с id = " + id + " не найдена в базе данных");
        }
    }

}

