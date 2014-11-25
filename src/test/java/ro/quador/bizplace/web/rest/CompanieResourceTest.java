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
import ro.quador.bizplace.domain.Companie;
import ro.quador.bizplace.repository.CompanieRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanieResource REST controller.
 *
 * @see CompanieResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CompanieResourceTest {

    private static final String DEFAULT_CUI = "SAMPLE_TEXT";
    private static final String UPDATED_CUI = "UPDATED_TEXT";
    
    private static final String DEFAULT_NUME = "SAMPLE_TEXT";
    private static final String UPDATED_NUME = "UPDATED_TEXT";
    
    private static final String DEFAULT_ADRESA = "SAMPLE_TEXT";
    private static final String UPDATED_ADRESA = "UPDATED_TEXT";
    
    private static final String DEFAULT_TELEFON = "SAMPLE_TEXT";
    private static final String UPDATED_TELEFON = "UPDATED_TEXT";
    
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    
    private static final String DEFAULT_WEBSITE = "SAMPLE_TEXT";
    private static final String UPDATED_WEBSITE = "UPDATED_TEXT";
    
    private static final LocalDate DEFAULT_CREATALA = new LocalDate(0L);
    private static final LocalDate UPDATED_CREATALA = new LocalDate();
    

   @Inject
   private CompanieRepository companieRepository;

   private MockMvc restCompanieMockMvc;

   private Companie companie;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanieResource companieResource = new CompanieResource();
        ReflectionTestUtils.setField(companieResource, "companieRepository", companieRepository);
        this.restCompanieMockMvc = MockMvcBuilders.standaloneSetup(companieResource).build();
    }

    @Before
    public void initTest() {
        companie = new Companie();
        companie.setCui(DEFAULT_CUI);
        companie.setNume(DEFAULT_NUME);
        companie.setAdresa(DEFAULT_ADRESA);
        companie.setTelefon(DEFAULT_TELEFON);
        companie.setEmail(DEFAULT_EMAIL);
        companie.setWebsite(DEFAULT_WEBSITE);
        companie.setCreatala(DEFAULT_CREATALA);
    }

    @Test
    @Transactional
    public void createCompanie() throws Exception {
        // Validate the database is empty
        assertThat(companieRepository.findAll()).hasSize(0);

        // Create the Companie
        restCompanieMockMvc.perform(post("/app/rest/companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companie)))
                .andExpect(status().isOk());

        // Validate the Companie in the database
        List<Companie> companies = companieRepository.findAll();
        assertThat(companies).hasSize(1);
        Companie testCompanie = companies.iterator().next();
        assertThat(testCompanie.getCui()).isEqualTo(DEFAULT_CUI);
        assertThat(testCompanie.getNume()).isEqualTo(DEFAULT_NUME);
        assertThat(testCompanie.getAdresa()).isEqualTo(DEFAULT_ADRESA);
        assertThat(testCompanie.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testCompanie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanie.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCompanie.getCreatala()).isEqualTo(DEFAULT_CREATALA);;
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        // Get all the companies
        restCompanieMockMvc.perform(get("/app/rest/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(companie.getId().intValue()))
                .andExpect(jsonPath("$.[0].cui").value(DEFAULT_CUI.toString()))
                .andExpect(jsonPath("$.[0].nume").value(DEFAULT_NUME.toString()))
                .andExpect(jsonPath("$.[0].adresa").value(DEFAULT_ADRESA.toString()))
                .andExpect(jsonPath("$.[0].telefon").value(DEFAULT_TELEFON.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].website").value(DEFAULT_WEBSITE.toString()))
                .andExpect(jsonPath("$.[0].creatala").value(DEFAULT_CREATALA.toString()));
    }

    @Test
    @Transactional
    public void getCompanie() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        // Get the companie
        restCompanieMockMvc.perform(get("/app/rest/companies/{id}", companie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(companie.getId().intValue()))
            .andExpect(jsonPath("$.cui").value(DEFAULT_CUI.toString()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME.toString()))
            .andExpect(jsonPath("$.adresa").value(DEFAULT_ADRESA.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.creatala").value(DEFAULT_CREATALA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanie() throws Exception {
        // Get the companie
        restCompanieMockMvc.perform(get("/app/rest/companies/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanie() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        // Update the companie
        companie.setCui(UPDATED_CUI);
        companie.setNume(UPDATED_NUME);
        companie.setAdresa(UPDATED_ADRESA);
        companie.setTelefon(UPDATED_TELEFON);
        companie.setEmail(UPDATED_EMAIL);
        companie.setWebsite(UPDATED_WEBSITE);
        companie.setCreatala(UPDATED_CREATALA);
        restCompanieMockMvc.perform(post("/app/rest/companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companie)))
                .andExpect(status().isOk());

        // Validate the Companie in the database
        List<Companie> companies = companieRepository.findAll();
        assertThat(companies).hasSize(1);
        Companie testCompanie = companies.iterator().next();
        assertThat(testCompanie.getCui()).isEqualTo(UPDATED_CUI);
        assertThat(testCompanie.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testCompanie.getAdresa()).isEqualTo(UPDATED_ADRESA);
        assertThat(testCompanie.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testCompanie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanie.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCompanie.getCreatala()).isEqualTo(UPDATED_CREATALA);;
    }

    @Test
    @Transactional
    public void deleteCompanie() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        // Get the companie
        restCompanieMockMvc.perform(delete("/app/rest/companies/{id}", companie.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Companie> companies = companieRepository.findAll();
        assertThat(companies).hasSize(0);
    }
}
