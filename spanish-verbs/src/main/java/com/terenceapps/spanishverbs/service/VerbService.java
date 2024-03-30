package com.terenceapps.spanishverbs.service;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.repository.VerbRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VerbService {

    private final VerbRepository verbRepository;

    public VerbService(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    public void save(Verb verb, BigDecimal userId) {
        verbRepository.save(verb.getInfinitive(), verb.getMood(), verb.getTense(), userId);
    }

    public void unsave(Verb verb, BigDecimal userId) {
        verbRepository.unsave(verb.getInfinitive(), verb.getMood(), verb.getTense(), userId);
    }

    public Optional<VerbConjugated> getConjugatedVerb(String infinitive, String mood, String tense) {
        return verbRepository.findByCompositeKey(infinitive, mood, tense);
    }

    public Optional<VerbConjugated> getNonSavedConjugatedVerb(BigDecimal userId) {
        return verbRepository.findNonSaved(userId);
    }

    public Optional<List<Verb>> getSavedVerbs(BigDecimal userId) {
        return verbRepository.findAllSaved(userId);
    }
}
