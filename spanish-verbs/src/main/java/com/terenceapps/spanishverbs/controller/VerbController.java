package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.service.UserService;
import com.terenceapps.spanishverbs.service.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class VerbController {

    private final VerbService verbService;

    private Logger logger = Logger.getLogger(VerbController.class.getName());

    public VerbController(VerbService verbService, UserService userService) {
        this.verbService = verbService;
    }

    @GetMapping("/new-conjugated-verb")
    public ResponseEntity<VerbConjugated> getNonSavedConjugatedVerb(Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        Optional<VerbConjugated> verb = verbService.getNonSavedConjugatedVerb(userId);

        if (verb.isPresent()) {
            return new ResponseEntity<>(verb.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Verb verbDto, Authentication authentication) {
        BigDecimal userId = ((User) authentication.getPrincipal()).getId();
        verbService.save(verbDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/conjugated-verb")
    public ResponseEntity<VerbConjugated> getConjugatedVerb(@RequestParam String infinitive, @RequestParam String mood, @RequestParam String tense) {
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
