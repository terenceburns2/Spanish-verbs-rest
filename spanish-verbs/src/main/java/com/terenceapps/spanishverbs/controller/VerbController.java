package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.service.UserService;
import com.terenceapps.spanishverbs.service.VerbService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// In hindsight, it would have been better if I created a unique id for each verb instead of using
// a composite key. This would have allowed me to use the id as means of targeting a specific resource within
// a collection; verb within verbs.

@RestController
@Validated
public class VerbController {

    private final VerbService verbService;

    public VerbController(VerbService verbService, UserService userService) {
        this.verbService = verbService;
    }

    @GetMapping("/verbs")
    public ResponseEntity<VerbConjugated> getNonSavedConjugatedVerb(Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        Optional<VerbConjugated> verb = verbService.getNonSavedConjugatedVerb(userId);

        return verb.map(verbConjugated -> new ResponseEntity<>(verbConjugated, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/verbs")
    public ResponseEntity<String> saveVerb(@Valid @RequestBody Verb verb, Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        verbService.saveVerb(verb, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verbs/conjugated")
    public ResponseEntity<VerbConjugated> getConjugationsOfVerb(@Valid @RequestBody Verb verb) {
        Optional<VerbConjugated> conjugatedVerb = verbService.getConjugationsOfVerb(verb);

        return conjugatedVerb.map(verbConjugated -> new ResponseEntity<>(verbConjugated, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/verbs/saved")
    public ResponseEntity<List<Verb>> getSavedVerbs(Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        Optional<List<Verb>> verbs = verbService.getSavedVerbs(userId);

        return verbs.map(verbList -> new ResponseEntity<>(verbList, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(List.of(), HttpStatus.OK));

    }

    @DeleteMapping("/verbs")
    public ResponseEntity<String> unsaveVerb(@Valid @RequestBody Verb verb, Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        verbService.unsaveVerb(verb, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
