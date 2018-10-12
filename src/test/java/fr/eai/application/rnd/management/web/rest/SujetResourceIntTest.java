package fr.eai.application.rnd.management.web.rest;

import fr.eai.application.rnd.management.RndManagementApplicationApp;

import fr.eai.application.rnd.management.domain.Sujet;
import fr.eai.application.rnd.management.repository.SujetRepository;
import fr.eai.application.rnd.management.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static fr.eai.application.rnd.management.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.eai.application.rnd.management.domain.enumeration.ContextOrigineEnum;
/**
 * Test class for the SujetResource REST controller.
 *
 * @see SujetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RndManagementApplicationApp.class)
public class SujetResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUIVI = false;
    private static final Boolean UPDATED_SUIVI = true;

    private static final Integer DEFAULT_ID_EVOLUTION = 1;
    private static final Integer UPDATED_ID_EVOLUTION = 2;

    private static final ContextOrigineEnum DEFAULT_ORIGINE = ContextOrigineEnum.PRODUCT;
    private static final ContextOrigineEnum UPDATED_ORIGINE = ContextOrigineEnum.SERVICE;

    @Autowired
    private SujetRepository sujetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSujetMockMvc;

    private Sujet sujet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SujetResource sujetResource = new SujetResource(sujetRepository);
        this.restSujetMockMvc = MockMvcBuilders.standaloneSetup(sujetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sujet createEntity(EntityManager em) {
        Sujet sujet = new Sujet()
            .libelle(DEFAULT_LIBELLE)
            .detail(DEFAULT_DETAIL)
            .suivi(DEFAULT_SUIVI)
            .idEvolution(DEFAULT_ID_EVOLUTION)
            .origine(DEFAULT_ORIGINE);
        return sujet;
    }

    @Before
    public void initTest() {
        sujet = createEntity(em);
    }

    @Test
    @Transactional
    public void createSujet() throws Exception {
        int databaseSizeBeforeCreate = sujetRepository.findAll().size();

        // Create the Sujet
        restSujetMockMvc.perform(post("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujet)))
            .andExpect(status().isCreated());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeCreate + 1);
        Sujet testSujet = sujetList.get(sujetList.size() - 1);
        assertThat(testSujet.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSujet.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testSujet.isSuivi()).isEqualTo(DEFAULT_SUIVI);
        assertThat(testSujet.getIdEvolution()).isEqualTo(DEFAULT_ID_EVOLUTION);
        assertThat(testSujet.getOrigine()).isEqualTo(DEFAULT_ORIGINE);
    }

    @Test
    @Transactional
    public void createSujetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sujetRepository.findAll().size();

        // Create the Sujet with an existing ID
        sujet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSujetMockMvc.perform(post("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujet)))
            .andExpect(status().isBadRequest());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSujets() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        // Get all the sujetList
        restSujetMockMvc.perform(get("/api/sujets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sujet.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].suivi").value(hasItem(DEFAULT_SUIVI.booleanValue())))
            .andExpect(jsonPath("$.[*].idEvolution").value(hasItem(DEFAULT_ID_EVOLUTION)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE.toString())));
    }
    
    @Test
    @Transactional
    public void getSujet() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        // Get the sujet
        restSujetMockMvc.perform(get("/api/sujets/{id}", sujet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sujet.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.suivi").value(DEFAULT_SUIVI.booleanValue()))
            .andExpect(jsonPath("$.idEvolution").value(DEFAULT_ID_EVOLUTION))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSujet() throws Exception {
        // Get the sujet
        restSujetMockMvc.perform(get("/api/sujets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSujet() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        int databaseSizeBeforeUpdate = sujetRepository.findAll().size();

        // Update the sujet
        Sujet updatedSujet = sujetRepository.findById(sujet.getId()).get();
        // Disconnect from session so that the updates on updatedSujet are not directly saved in db
        em.detach(updatedSujet);
        updatedSujet
            .libelle(UPDATED_LIBELLE)
            .detail(UPDATED_DETAIL)
            .suivi(UPDATED_SUIVI)
            .idEvolution(UPDATED_ID_EVOLUTION)
            .origine(UPDATED_ORIGINE);

        restSujetMockMvc.perform(put("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSujet)))
            .andExpect(status().isOk());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeUpdate);
        Sujet testSujet = sujetList.get(sujetList.size() - 1);
        assertThat(testSujet.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSujet.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testSujet.isSuivi()).isEqualTo(UPDATED_SUIVI);
        assertThat(testSujet.getIdEvolution()).isEqualTo(UPDATED_ID_EVOLUTION);
        assertThat(testSujet.getOrigine()).isEqualTo(UPDATED_ORIGINE);
    }

    @Test
    @Transactional
    public void updateNonExistingSujet() throws Exception {
        int databaseSizeBeforeUpdate = sujetRepository.findAll().size();

        // Create the Sujet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSujetMockMvc.perform(put("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujet)))
            .andExpect(status().isBadRequest());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSujet() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        int databaseSizeBeforeDelete = sujetRepository.findAll().size();

        // Get the sujet
        restSujetMockMvc.perform(delete("/api/sujets/{id}", sujet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sujet.class);
        Sujet sujet1 = new Sujet();
        sujet1.setId(1L);
        Sujet sujet2 = new Sujet();
        sujet2.setId(sujet1.getId());
        assertThat(sujet1).isEqualTo(sujet2);
        sujet2.setId(2L);
        assertThat(sujet1).isNotEqualTo(sujet2);
        sujet1.setId(null);
        assertThat(sujet1).isNotEqualTo(sujet2);
    }
}
