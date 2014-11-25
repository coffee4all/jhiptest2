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
import java.util.List;

import ro.quador.bizplace.Application;
import ro.quador.bizplace.domain.Tipabonament;
import ro.quador.bizplace.repository.TipabonamentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipabonamentResource REST controller.
 *
 * @see TipabonamentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TipabonamentResourceTest {

    private static final String DEFAULT_NUME = "SAMPLE_TEXT";
    private static final String UPDATED_NUME = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_ANUNTURI = 0;
    private static final Integer UPDATED_ANUNTURI = 1;
    
    private static final Integer DEFAULT_COMPANII = 0;
    private static final Integer UPDATED_COMPANII = 1;
    
    private static final Integer DEFAULT_VALABILITATE = 0;
    private static final Integer UPDATED_VALABILITATE = 1;
    
   private static final Boolean DEFAULT_EPUBLIC = false;
   private static final Boolean UPDATED_EPUBLIC = true;

   @Inject
   private TipabonamentRepository tipabonamentRepository;

   private MockMvc restTipabonamentMockMvc;

   private Tipabonament tipabonament;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipabonamentResource tipabonamentResource = new TipabonamentResource();
        ReflectionTestUtils.setField(tipabonamentResource, "tipabonamentRepository", tipabonamentRepository);
        this.restTipabonamentMockMvc = MockMvcBuilders.standaloneSetup(tipabonamentResource).build();
    }

    @Before
    public void initTest() {
        tipabonament = new Tipabonament();
        tipabonament.setNume(DEFAULT_NUME);
        tipabonament.setAnunturi(DEFAULT_ANUNTURI);
        tipabonament.setCompanii(DEFAULT_COMPANII);
        tipabonament.setValabilitate(DEFAULT_VALABILITATE);
        tipabonament.setEpublic(DEFAULT_EPUBLIC);
    }

    @Test
    @Transactional
    public void createTipabonament() throws Exception {
        // Validate the database is empty
        assertThat(tipabonamentRepository.findAll()).hasSize(0);

        // Create the Tipabonament
        restTipabonamentMockMvc.perform(post("/app/rest/tipabonaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipabonament)))
                .andExpect(status().isOk());

        // Validate the Tipabonament in the database
        List<Tipabonament> tipabonaments = tipabonamentRepository.findAll();
        assertThat(tipabonaments).hasSize(1);
        Tipabonament testTipabonament = tipabonaments.iterator().next();
        assertThat(testTipabonament.getNume()).isEqualTo(DEFAULT_NUME);
        assertThat(testTipabonament.getAnunturi()).isEqualTo(DEFAULT_ANUNTURI);
        assertThat(testTipabonament.getCompanii()).isEqualTo(DEFAULT_COMPANII);
        assertThat(testTipabonament.getValabilitate()).isEqualTo(DEFAULT_VALABILITATE);
        assertThat(testTipabonament.getEpublic()).isEqualTo(DEFAULT_EPUBLIC);;
    }

    @Test
    @Transactional
    public void getAllTipabonaments() throws Exception {
        // Initialize the database
        tipabonamentRepository.saveAndFlush(tipabonament);

        // Get all the tipabonaments
        restTipabonamentMockMvc.perform(get("/app/rest/tipabonaments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(tipabonament.getId().intValue()))
                .andExpect(jsonPath("$.[0].nume").value(DEFAULT_NUME.toString()))
                .andExpect(jsonPath("$.[0].anunturi").value(DEFAULT_ANUNTURI))
                .andExpect(jsonPath("$.[0].companii").value(DEFAULT_COMPANII))
                .andExpect(jsonPath("$.[0].valabilitate").value(DEFAULT_VALABILITATE))
                .andExpect(jsonPath("$.[0].epublic").value(DEFAULT_EPUBLIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getTipabonament() throws Exception {
        // Initialize the database
        tipabonamentRepository.saveAndFlush(tipabonament);

        // Get the tipabonament
        restTipabonamentMockMvc.perform(get("/app/rest/tipabonaments/{id}", tipabonament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tipabonament.getId().intValue()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME.toString()))
            .andExpect(jsonPath("$.anunturi").value(DEFAULT_ANUNTURI))
            .andExpect(jsonPath("$.companii").value(DEFAULT_COMPANII))
            .andExpect(jsonPath("$.valabilitate").value(DEFAULT_VALABILITATE))
            .andExpect(jsonPath("$.epublic").value(DEFAULT_EPUBLIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipabonament() throws Exception {
        // Get the tipabonament
        restTipabonamentMockMvc.perform(get("/app/rest/tipabonaments/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipabonament() throws Exception {
        // Initialize the database
        tipabonamentRepository.saveAndFlush(tipabonament);

        // Update the tipabonament
        tipabonament.setNume(UPDATED_NUME);
        tipabonament.setAnunturi(UPDATED_ANUNTURI);
        tipabonament.setCompanii(UPDATED_COMPANII);
        tipabonament.setValabilitate(UPDATED_VALABILITATE);
        tipabonament.setEpublic(UPDATED_EPUBLIC);
        restTipabonamentMockMvc.perform(post("/app/rest/tipabonaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipabonament)))
                .andExpect(status().isOk());

        // Validate the Tipabonament in the database
        List<Tipabonament> tipabonaments = tipabonamentRepository.findAll();
        assertThat(tipabonaments).hasSize(1);
        Tipabonament testTipabonament = tipabonaments.iterator().next();
        assertThat(testTipabonament.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testTipabonament.getAnunturi()).isEqualTo(UPDATED_ANUNTURI);
        assertThat(testTipabonament.getCompanii()).isEqualTo(UPDATED_COMPANII);
        assertThat(testTipabonament.getValabilitate()).isEqualTo(UPDATED_VALABILITATE);
        assertThat(testTipabonament.getEpublic()).isEqualTo(UPDATED_EPUBLIC);;
    }

    @Test
    @Transactional
    public void deleteTipabonament() throws Exception {
        // Initialize the database
        tipabonamentRepository.saveAndFlush(tipabonament);

        // Get the tipabonament
        restTipabonamentMockMvc.perform(delete("/app/rest/tipabonaments/{id}", tipabonament.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tipabonament> tipabonaments = tipabonamentRepository.findAll();
        assertThat(tipabonaments).hasSize(0);
    }
}
