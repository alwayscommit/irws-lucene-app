package com.tcd.lucene.analyzer;

import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilter.*;

public class WordNetAnalyzer extends StopwordAnalyzerBase {
    private final Path currentRelativePath = Paths.get("").toAbsolutePath();
    
    public WordNetAnalyzer(CharArraySet stopWordSet) {
    	super(stopWordSet);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new StandardTokenizer();
        int flags = SPLIT_ON_CASE_CHANGE | SPLIT_ON_NUMERICS | GENERATE_NUMBER_PARTS | GENERATE_WORD_PARTS;
        TokenStream tokenStream = new WordDelimiterGraphFilter(source, flags, null);
        tokenStream = new EnglishPossessiveFilter(tokenStream);
        tokenStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream = new TrimFilter(tokenStream);
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new StopFilter(tokenStream, getStopwordSet());
        tokenStream = new FlattenGraphFilter(new WordDelimiterGraphFilter(tokenStream, flags, null));
        tokenStream = new FlattenGraphFilter(new SynonymGraphFilter(tokenStream, createSynonymMap(), true));
        tokenStream = new PorterStemFilter(tokenStream);
        tokenStream = new SnowballFilter(tokenStream, new EnglishStemmer());
        return new TokenStreamComponents(source, tokenStream);
    }

    private SynonymMap createSynonymMap() {
        try {
            File file = new File(currentRelativePath +  "/src/main/resources/wn_s.pl");
            InputStream stream = new FileInputStream(file);
            Reader rulesReader = new InputStreamReader(stream);
            SynonymMap.Builder parser = new WordnetSynonymParser(true, false, new KeywordAnalyzer());
            ((WordnetSynonymParser) parser).parse(rulesReader);
            return parser.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
