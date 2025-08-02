package com.cogneworx.intelsync.analysis.service;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntityExtractionService {

    private final NameFinderME personFinder;

    public EntityExtractionService(@Value("${opennlp.model.person}") Resource personModelFile) throws Exception {
        try (InputStream modelIn = personModelFile.getInputStream()) {
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            this.personFinder = new NameFinderME(model);
        }
    }

    public List<String> extractPersons(String text) {
        List<String> results = new ArrayList<>();

        String[] tokens = SimpleTokenizer.INSTANCE.tokenize(text);
        Span[] spans = personFinder.find(tokens);

        for (Span span : spans) {
            StringBuilder name = new StringBuilder();
            for (int i = span.getStart(); i < span.getEnd(); i++) {
                name.append(tokens[i]).append(" ");
            }
            results.add(name.toString().trim());
        }

        return results;
    }
}
