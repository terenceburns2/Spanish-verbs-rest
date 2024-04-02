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

    public void saveVerb(Verb verb, BigDecimal userId) {
        verbRepository.save(verb.getInfinitive(), verb.getMood(), verb.getTense(), userId);
    }

    public void unsaveVerb(Verb verb, BigDecimal userId) {
        verbRepository.unsave(verb.getInfinitive(), verb.getMood(), verb.getTense(), userId);
    }

    public Optional<VerbConjugated> getConjugationsOfVerb(Verb verb) {
        return verbRepository.findByCompositeKey(verb.getInfinitive(), verb.getMood(), verb.getTense());
    }

    public Optional<VerbConjugated> getNonSavedConjugatedVerb(BigDecimal userId) {
        return verbRepository.findNonSaved(userId);
    }

    public Optional<List<Verb>> getSavedVerbs(BigDecimal userId) {
        return verbRepository.findAllSaved(userId);
    }
}
