import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;
import ru.spiiran.us_complex.repositories.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.ConstellationRepository;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;
import ru.spiiran.us_complex.services.ConstellationService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConstellationServiceTest {

    @Mock
    private ConstellationRepository constellationRepository;

    @Mock
    private ConstellationArbitraryRepository constellationArbitraryRepository;

    @Mock
    private IdNodeRepository idNodeRepository;

    @Mock
    private StatusGeneralRepository statusGeneralRepository;

    @InjectMocks
    private ConstellationService constellationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddConstellationWithArbitraryFormation() {
        // Arrange
        dtoConstellation dto = new dtoConstellation();
        dto.setArbitraryFormation(true);
        dto.setConstellationArbitraryList(new ArrayList<>());

        when(constellationService.createNewGeneralStatus()).thenReturn(new generalStatusEntity());
        when(constellationRepository.save(any(ConstellationEntity.class))).thenReturn(new ConstellationEntity());
        when(statusGeneralRepository.save(any(generalStatusEntity.class))).thenReturn(new generalStatusEntity());
        when(constellationService.getConstellationArbitraryList(anyList())).thenReturn(new ArrayList<>());

        // Act
        dtoMessage result = constellationService.addConstellation(dto);

        // Assert
        assertNotNull(result);
        assertEquals("INSERT CONSTELLATION DETAILED SUCCESS", result.getType());
        assertEquals("Constellation Detailed added successfully", result.getMessage());
    }

    @Test
    public void testAddConstellationWithOverview() {
        // Arrange
        dtoConstellation dto = new dtoConstellation();
        dto.setArbitraryFormation(false);
        dto.setConstellationOverviewList(new ArrayList<>());

        when(constellationService.createNewGeneralStatus()).thenReturn(new generalStatusEntity());
        when(constellationRepository.save(any(ConstellationEntity.class))).thenReturn(new ConstellationEntity());
        when(statusGeneralRepository.save(any(generalStatusEntity.class))).thenReturn(new generalStatusEntity());

        // Act
        dtoMessage result = constellationService.addConstellation(dto);

        // Assert
        assertNotNull(result);
        assertEquals("INSERT CONSTELLATION OVERVIEW SUCCESS", result.getType());
        assertEquals("Constellation Overview added successfully", result.getMessage());
    }
}
