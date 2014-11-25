package ro.quador.bizplace.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import java.util.List;

import ro.quador.bizplace.Application;
import ro.quador.bizplace.domain.Abonament;
import ro.quador.bizplace.repository.AbonamentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AbonamentResource REST controller.
 *
 * @see AbonamentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AbonamentResourceTest {

    private static final Integer DEFAULT_ANUNTURI = 0;
    private static final Integer UPDATED_ANUNTURI = 1;
    
    private static final Integer DEFAULT_COMPANII = 0;
    private static final Integer UPDATED_COMPANII = 1;
    
    private static final Integer DEFAULT_VALABILITATE = 0;
    private static final Integer UPDATED_VALABILITATE = 1;
    
    private static final LocalDate DEFAULT_CREATLA = new LocalDate(0L);
    private static final LocalDate UPDATED_CREATLA = new LocalDate();
    

   @Inject
   private AbonamentRepository abonamentRepository;

   private MockMvc restAbonamentMockMvc;

   private Abonament abonament;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AbonamentResource abonamentResource = new AbonamentResource();
        ReflectionTestUtils.setField(abonamentResource, "abonamentRepository", abonamentRepository);
        this.restAbonamentMockMvc = MockMvcBuilders.standaloneSetup(abonamentResource).build();
    }

    @Before
    public void initTest() {
        abonament = new Abonament();
        abonament.setAnunturi(DEFAULT_ANUNTURI);
        abonament.setCompanii(DEFAULT_COMPANII);
        abonament.setValabilitate(DEFAULT_VALABILITATE);
        abonament.setCreatla(DEFAULT_CREATLA);
    }

    @Test
    @Transactional
    public void createAbonament() throws Exception {
        // Validate the database is empty
        assertThat(abonamentRepository.findAll()).hasSize(0);

        // Create the Abonament
        restAbonamentMockMvc.perform(post("/app/rest/abonaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(abonament)))
                .andExpect(status().isOk());

        // Validate the Abonament in the database
        List<Abonament> abonaments = abonamentRepository.findAll();
        assertThat(abonaments).hasSize(1);
        Abonament testAbonament = abonaments.iterator().next();
        assertThat(testAbonament.getAnunturi()).isEqualTo(DEFAULT_ANUNTURI);
        assertThat(testAbonament.getCompanii()).isEqualTo(DEFAULT_COMPANII);
        assertThat(testAbonament.getValabilitate()).isEqualTo(DEFAULT_VALABILITATE);
        assertThat(testAbonament.getCreatla()).isEqualTo(DEFAULT_CREATLA);;
    }

    @Test
    @Transactional
    public void getAllAbonaments() throws Exception {
        // Initialize the database
        abonamentRepository.saveAndFlush(abonament);

        // Get all the abonaments
        restAbonamentMockMvc.perform(get("/app/rest/abonaments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(abonament.getId().intValue()))
                .andExpect(jsonPath("$.[0].anunturi").value(DEFAULT_ANUNTURI))
                .andExpect(jsonPath("$.[0].companii").value(DEFAULT_COMPANII))
                .andExpect(jsonPath("$.[0].valabilitate").value(DEFAULT_VALABILITATE))
                .andExpect(jsonPath("$.[0].creatla").value(DEFAULT_CREATLA.toString()));
    }

    @Test
    @Transactional
    public void getAbonament() throws Exception {
        // Initialize the database
        abonamentRepository.saveAndFlush(abonament);

        // Get the abonament
        restAbonamentMockMvc.perform(get("/app/rest/abonaments/{id}", abonament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(abonament.getId().intValue()))
            .andExpect(jsonPath("$.anunturi").value(DEFAULT_ANUNTURI))
            .andExpect(jsonPath("$.companii").value(DEFAULT_COMPANII))
            .andExpect(jsonPath("$.valabilitate").value(DEFAULT_VALABILITATE))
            .andExpect(jsonPath("$.creatla").value(DEFAULT_CREATLA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbonament() throws Exception {
        // Get the abonament
        restAbonamentMockMvc.perform(get("/app/rest/abonaments/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbonament() throws Exception {
        // Initialize the database
        abonamentRepository.saveAndFlush(abonament);

        // Update the abonament
        abonament.setAnunturi(UPDATED_ANUNTURI);
        abonament.setCompanii(UPDATED_COMPANII);
        abonament.setValabilitate(UPDATED_VALABILITATE);
        abonament.setCreatla(UPDATED_CREATLA);
        restAbonamentMockMvc.perform(post("/app/rest/abonaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(abonament)))
                .andExpect(status().isOk());

        // Validate the Abonament in the database
        List<Abonament> abonaments = abonamentRepository.findAll();
        assertThat(abonaments).hasSize(1);
        Abonament testAbonament = abonaments.iterator().next();
        assertThat(testAbonament.getAnunturi()).isEqualTo(UPDATED_ANUNTURI);
        assertThat(testAbonament.getCompanii()).isEqualTo(UPDATED_COMPANII);
        assertThat(testAbonament.getValabilitate()).isEqualTo(UPDATED_VALABILITATE);
        assertThat(testAbonament.getCreatla()).isEqualTo(UPDATED_CREATLA);;
    }

    @Test
    @Transactional
    public void deleteAbonament() throws Exception {
        // Initialize the database
        abonamentRepository.saveAndFlush(abonament);

        // Get the abonament
        restAbonamentMockMvc.perform(delete("/app/rest/abonaments/{id}", abonament.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Abonament> abonaments = abonamentRepository.findAll();
        assertThat(abonaments).hasSize(0);
    }
}
