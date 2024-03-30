package com.terenceapps.spanishverbs.repository.util;

import com.terenceapps.spanishverbs.model.VerbConjugated;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapper implements org.springframework.jdbc.core.RowMapper<VerbConjugated> {
    @Override
    public VerbConjugated mapRow(ResultSet row, int rowNum) throws SQLException {
        VerbConjugated verb = new VerbConjugated();
        verb.setInfinitive(row.getString("infinitive"));
        verb.setMood(row.getString("mood"));
        verb.setTense(row.getString("tense"));
        verb.setVerbEnglish(row.getString("verb_english"));
        verb.setForm1s(row.getString("form_1s"));
        verb.setForm2s(row.getString("form_2s"));
        verb.setForm3s(row.getString("form_3s"));
        verb.setForm1p(row.getString("form_1p"));
        verb.setForm2p(row.getString("form_2p"));
        verb.setForm3p(row.getString("form_3p"));

        return verb;
    }
}
