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
import ro.quador.bizplace.domain.Judet;
import ro.quador.bizplace.repository.JudetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JudetResource REST controller.
 *
 * @see JudetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class JudetResourceTest {

    private static final String DEFAULT_NUME = "SAMPLE_TEXT";
    private static final String UPDATED_NUME = "UPDATED_TEXT";
    

   @Inject
   private JudetRepository judetRepository;

   private MockMvc restJudetMockMvc;

   private Judet judet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JudetResource judetResource = new JudetResource();
        ReflectionTestUtils.setField(judetResource, "judetRepository", judetRepository);
        this.restJudetMockMvc = MockMvcBuilders.standaloneSetup(judetResource).build();
    }

    @Before
    public void initTest() {
        judet = new Judet();
        judet.setNume(DEFAULT_NUME);
    }

    @Test
    @Transactional
    public void createJudet() throws Exception {
        // Validate the database is empty
        assertThat(judetRepository.findAll()).hasSize(0);

        // Create the Judet
        restJudetMockMvc.perform(post("/app/rest/judets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(judet)))
                .andExpect(status().isOk());

        // Validate the Judet in the database
        List<Judet> judets = judetRepository.findAll();
        assertThat(judets).hasSize(1);
        Judet testJudet = judets.iterator().next();
        assertThat(testJudet.getNume()).isEqualTo(DEFAULT_NUME);;
    }

    @Test
    @Transactional
    public void getAllJudets() throws Exception {
        // Initialize the database
        judetRepository.saveAndFlush(judet);

        // Get all the judets
        restJudetMockMvc.perform(get("/app/rest/judets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(judet.getId().intValue()))
                .andExpect(jsonPath("$.[0].nume").value(DEFAULT_NUME.toString()));
    }

    @Test
    @Transactional
    public void getJudet() throws Exception {
        // Initialize the database
        judetRepository.saveAndFlush(judet);

        // Get the judet
        restJudetMockMvc.perform(get("/app/rest/judets/{id}", judet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(judet.getId().intValue()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJudet() throws Exception {
        // Get the judet
        restJudetMockMvc.perform(get("/app/rest/judets/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJudet() throws Exception {
        // Initialize the database
        judetRepository.saveAndFlush(judet);

        // Update the judet
        judet.setNume(UPDATED_NUME);
        restJudetMockMvc.perform(post("/app/rest/judets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(judet)))
                .andExpect(status().isOk());

        // Validate the Judet in the database
        List<Judet> judets = judetRepository.findAll();
        assertThat(judets).hasSize(1);
        Judet testJudet = judets.iterator().next();
        assertThat(testJudet.getNume()).isEqualTo(UPDATED_NUME);;
    }

    @Test
    @Transactional
    public void deleteJudet() throws Exception {
        // Initialize the database
        judetRepository.saveAndFlush(judet);

        // Get the judet
        restJudetMockMvc.perform(delete("/app/rest/judets/{id}", judet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Judet> judets = judetRepository.findAll();
        assertThat(judets).hasSize(0);
    }
}
