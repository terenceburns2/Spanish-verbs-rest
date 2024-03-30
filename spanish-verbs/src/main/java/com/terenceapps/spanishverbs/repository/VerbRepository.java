package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface VerbRepository {
    void save(String infinitive, String mood, String tense, BigDecimal userId);
    void unsave(String infinitive, String mood, String tense, BigDecimal userId);
    Optional<List<Verb>> findAllSaved(BigDecimal userId);
    Optional<VerbConjugated> findByCompositeKey(String infinitive, String mood, String tense);
    Optional<VerbConjugated> findNonSaved(BigDecimal userId);
}
