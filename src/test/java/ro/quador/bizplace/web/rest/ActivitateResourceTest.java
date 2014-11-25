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
import ro.quador.bizplace.domain.Activitate;
import ro.quador.bizplace.repository.ActivitateRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivitateResource REST controller.
 *
 * @see ActivitateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ActivitateResourceTest {

    private static final String DEFAULT_NUME = "SAMPLE_TEXT";
    private static final String UPDATED_NUME = "UPDATED_TEXT";
    

   @Inject
   private ActivitateRepository activitateRepository;

   private MockMvc restActivitateMockMvc;

   private Activitate activitate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivitateResource activitateResource = new ActivitateResource();
        ReflectionTestUtils.setField(activitateResource, "activitateRepository", activitateRepository);
        this.restActivitateMockMvc = MockMvcBuilders.standaloneSetup(activitateResource).build();
    }

    @Before
    public void initTest() {
        activitate = new Activitate();
        activitate.setNume(DEFAULT_NUME);
    }

    @Test
    @Transactional
    public void createActivitate() throws Exception {
        // Validate the database is empty
        assertThat(activitateRepository.findAll()).hasSize(0);

        // Create the Activitate
        restActivitateMockMvc.perform(post("/app/rest/activitates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activitate)))
                .andExpect(status().isOk());

        // Validate the Activitate in the database
        List<Activitate> activitates = activitateRepository.findAll();
        assertThat(activitates).hasSize(1);
        Activitate testActivitate = activitates.iterator().next();
        assertThat(testActivitate.getNume()).isEqualTo(DEFAULT_NUME);;
    }

    @Test
    @Transactional
    public void getAllActivitates() throws Exception {
        // Initialize the database
        activitateRepository.saveAndFlush(activitate);

        // Get all the activitates
        restActivitateMockMvc.perform(get("/app/rest/activitates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(activitate.getId().intValue()))
                .andExpect(jsonPath("$.[0].nume").value(DEFAULT_NUME.toString()));
    }

    @Test
    @Transactional
    public void getActivitate() throws Exception {
        // Initialize the database
        activitateRepository.saveAndFlush(activitate);

        // Get the activitate
        restActivitateMockMvc.perform(get("/app/rest/activitates/{id}", activitate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(activitate.getId().intValue()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivitate() throws Exception {
        // Get the activitate
        restActivitateMockMvc.perform(get("/app/rest/activitates/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivitate() throws Exception {
        // Initialize the database
        activitateRepository.saveAndFlush(activitate);

        // Update the activitate
        activitate.setNume(UPDATED_NUME);
        restActivitateMockMvc.perform(post("/app/rest/activitates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activitate)))
                .andExpect(status().isOk());

        // Validate the Activitate in the database
        List<Activitate> activitates = activitateRepository.findAll();
        assertThat(activitates).hasSize(1);
        Activitate testActivitate = activitates.iterator().next();
        assertThat(testActivitate.getNume()).isEqualTo(UPDATED_NUME);;
    }

    @Test
    @Transactional
    public void deleteActivitate() throws Exception {
        // Initialize the database
        activitateRepository.saveAndFlush(activitate);

        // Get the activitate
        restActivitateMockMvc.perform(delete("/app/rest/activitates/{id}", activitate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Activitate> activitates = activitateRepository.findAll();
        assertThat(activitates).hasSize(0);
    }
}
