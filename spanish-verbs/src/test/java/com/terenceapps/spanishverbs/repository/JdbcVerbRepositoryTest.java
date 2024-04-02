package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;

@JdbcTest
@Sql("/test-data.sql")
@Sql(scripts = {"/delete-test-data.sql"}, executionPhase = AFTER_TEST_CLASS)
class JdbcVerbRepositoryTest {

    private final JdbcTemplate jdbcTemplate;
    private final VerbRepository verbRepository;

    @Autowired
    public JdbcVerbRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.verbRepository = new JdbcVerbRepository(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "saved");
    }

    @Test
    public void save_shouldSaveToDatabase() {
        BigDecimal userid = BigDecimal.valueOf(1);
        verbRepository.save("devorar", "Indicativo", "Presente", userid);
        Optional<List<Verb>> verbs = verbRepository.findAllSaved(userid);
        assertEquals(1, verbs.get().size());
    }

    @Test
    public void save_shouldOnlySaveOnce() {
        BigDecimal userid = BigDecimal.valueOf(1);
        verbRepository.save("devorar", "Indicativo", "Presente", userid);
        verbRepository.save("devorar", "Indicativo", "Presente", userid);
        Optional<List<Verb>> verbs = verbRepository.findAllSaved(BigDecimal.valueOf(1));
        assertEquals(1, verbs.get().size());
    }

    @Test
    public void findAllSaved_shouldRetrieveAllStoredEntries() {
        BigDecimal userid = BigDecimal.valueOf(1);
        verbRepository.save("devorar", "Indicativo", "Presente", userid);
        verbRepository.save("abandonar", "Indicativo", "Presente", userid);
        Optional<List<Verb>> verbs = verbRepository.findAllSaved(userid);
        assertEquals(2, verbs.get().size());
    }

    @Test
    public void unsave_shouldDeleteFromDatabase() {
        BigDecimal userid = BigDecimal.valueOf(1);
        verbRepository.save("devorar", "Indicativo", "Presente", userid);
        verbRepository.unsave("devorar", "Indicativo", "Presente", userid);
        Optional<List<Verb>> verbs = verbRepository.findAllSaved(userid);
        assertEquals(0, verbs.get().size());
    }

    @Test
    public void unsave_shouldBeIdempotent() {
        BigDecimal userid = BigDecimal.valueOf(1);
        verbRepository.save("devorar", "Indicativo", "Presente", userid);
        verbRepository.unsave("devorar", "Indicativo", "Presente", userid);
        verbRepository.unsave("devorar", "Indicativo", "Presente", userid);
        Optional<List<Verb>> verbs = verbRepository.findAllSaved(userid);
        assertEquals(0, verbs.get().size());
    }

    @Test
    public void findByKey_shouldReturnMatch() {
        verbRepository.save("devorar", "Indicativo", "Presente", BigDecimal.valueOf(1));
        Optional<VerbConjugated> verbConjugated = verbRepository.findByCompositeKey("devorar", "Indicativo", "Presente");
        assertTrue(verbConjugated.isPresent());
    }

    @Test
    public void findByKey_shouldNotReturn() {
        Optional<VerbConjugated> verbConjugated = verbRepository.findByCompositeKey("something", "Indicativo", "Presente");
        assertFalse(verbConjugated.isPresent());
    }

    @Test
    public void findNonSaved_shouldReturnFirstNonSavedMatch() {
        BigDecimal userid = BigDecimal.valueOf(1);

        Optional<VerbConjugated> verbConjugated = verbRepository.findNonSaved(userid);
        assertTrue(verbConjugated.isPresent());

        VerbConjugated verbConjugatedUnwrapped = verbConjugated.get();

        verbRepository.save(verbConjugatedUnwrapped.getInfinitive(), verbConjugatedUnwrapped.getMood(),
                verbConjugatedUnwrapped.getTense(), userid);

        Optional<VerbConjugated> secondVerbConjugated = verbRepository.findNonSaved(userid);
        assertTrue(secondVerbConjugated.isPresent());

        VerbConjugated secondVerbConjugatedUnwrapped = secondVerbConjugated.get();

        assertNotEquals(secondVerbConjugatedUnwrapped.toString(), verbConjugatedUnwrapped.toString());
    }
}