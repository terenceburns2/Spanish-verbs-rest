package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.service.UserService;
import com.terenceapps.spanishverbs.service.VerbService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class VerbController {

    private final VerbService verbService;

    public VerbController(VerbService verbService, UserService userService) {
        this.verbService = verbService;
    }

    @GetMapping("/new-conjugated-verb")
    public ResponseEntity<VerbConjugated> getNonSavedConjugatedVerb(Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        Optional<VerbConjugated> verb = verbService.getNonSavedConjugatedVerb(userId);

        return verb.map(verbConjugated -> new ResponseEntity<>(verbConjugated, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody Verb verb, Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        verbService.save(verb, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/conjugated-verb")
    public ResponseEntity<VerbConjugated> getConjugatedVerb(
            @NotBlank(message = "The infinitive is required.") @RequestParam String infinitive,
            @NotBlank(message = "The mood is required.") @RequestParam String mood,
            @NotBlank(message = "The tense is required.") @RequestParam String tense) {
        Optional<VerbConjugated> verb = verbService.getConjugatedVerb(infinitive, mood, tense);

        return verb.map(verbConjugated -> new ResponseEntity<>(verbConjugated, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/saved-verbs")
    public ResponseEntity<List<Verb>> getVerbs(Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        Optional<List<Verb>> verbs = verbService.getSavedVerbs(userId);

        return verbs.map(verbList -> new ResponseEntity<>(verbList, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(List.of(), HttpStatus.OK));

    }

    @DeleteMapping("/unsave")
    public ResponseEntity<String> unsave(@RequestBody Verb verbDto, Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        verbService.unsave(verbDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
