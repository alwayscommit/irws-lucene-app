package com.tcd.lucene.analyzer;

import com.tcd.lucene.util.IndexingUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SynonymsAnalyzer extends StopwordAnalyzerBase {
    private final Path currentRelativePath = Paths.get("").toAbsolutePath();

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer source = new ClassicTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(source);
        tokenStream = new EnglishPossessiveFilter(tokenStream);
        tokenStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream = new FlattenGraphFilter(new WordDelimiterGraphFilter(tokenStream, WordDelimiterGraphFilter.SPLIT_ON_NUMERICS |
                WordDelimiterGraphFilter.GENERATE_WORD_PARTS | WordDelimiterGraphFilter.GENERATE_NUMBER_PARTS |
                WordDelimiterGraphFilter.PRESERVE_ORIGINAL , null));
        tokenStream = new SynonymGraphFilter(tokenStream, createSynonymMap(), true);
        tokenStream = new KStemFilter(tokenStream);
        tokenStream = new PorterStemFilter(tokenStream);
        List<String> stopWords = null;
        try {
            stopWords = IndexingUtils.getStopwordsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tokenStream = new StopFilter(tokenStream, StopFilter.makeStopSet(stopWords, true));
        return new TokenStreamComponents(source, tokenStream);
    }

    // create synonym mapping for countries
    private SynonymMap createSynonymMap() {
        SynonymMap synMap = new SynonymMap(null, null, 0);
        try {
            BufferedReader countries = new BufferedReader(new FileReader(currentRelativePath + "/src/main/resources/countries_list.txt"));

            final SynonymMap.Builder builder = new SynonymMap.Builder(true);
            String country = countries.readLine();
            while(country != null) {
                builder.add(new CharsRef("country"), new CharsRef(country), true);
                builder.add(new CharsRef("countries"), new CharsRef(country), true);
                country = countries.readLine();
            }

            synMap = builder.build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage() + " when trying to initialize a synonym map");
        }
        return synMap;
    }
}