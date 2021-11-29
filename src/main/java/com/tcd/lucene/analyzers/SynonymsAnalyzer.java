package com.tcd.lucene.analyzers;

import com.tcd.lucene.util.IndexingUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SynonymsAnalyzer extends StopwordAnalyzerBase {

    public static final CharArraySet STOP_WORDS_SET;
    private final Path currentRelativePath = Paths.get("").toAbsolutePath();


    static {
        // default stop words
        final List<String> stopWords = Arrays.asList("i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "you're",
                "you've", "you'll", "you'd", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself",
                "she", "she's", "her", "hers", "herself", "it", "it's", "its", "itself", "they", "them", "their",
                "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "that'll", "these", "those",
                "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does",
                "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of",
                "at", "by", "for", "with", "about", "between", "into", "through", "during", "before", "after", "above",
                "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further",
                "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few",
                "more", "most", "other", "some", "such", "only", "own", "same", "so", "than", "too", "very", "s", "t",
                "can", "will", "just", "don", "don't", "should", "should've", "now", "d", "ll", "m", "re", "ve");
        final CharArraySet stopSet = new CharArraySet(stopWords, false);
        STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
    }

    private final CharArraySet stemExclusionSet;


    // Analyzer using default stop words set
    public SynonymsAnalyzer() {
        this(STOP_WORDS_SET);
    }

    // Builds analyzer with given stop words set.
    public SynonymsAnalyzer(CharArraySet stopwords) {
        this(stopwords, CharArraySet.EMPTY_SET);
    }


    // Builds an analyzer with given stop words set
    // If a non-empty stem exclusion set is given then this analyzer
    public SynonymsAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionSet) {
        super(stopwords);
        this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
    }


    // Returns read only version of the default stop words
    public static CharArraySet getDefaultStopSet() {
        return STOP_WORDS_SET;
    }

    // Tokenizing input stream, similar to english analyzer class but adding synonym map
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer source = new StandardTokenizer();
        TokenStream result = new EnglishPossessiveFilter(source);
        result = new LowerCaseFilter(result);
        result = new TrimFilter(result);
        result = new FlattenGraphFilter(new WordDelimiterGraphFilter(result, WordDelimiterGraphFilter.SPLIT_ON_NUMERICS |
                WordDelimiterGraphFilter.GENERATE_WORD_PARTS | WordDelimiterGraphFilter.GENERATE_NUMBER_PARTS |
                WordDelimiterGraphFilter.PRESERVE_ORIGINAL , null));
        result = new FlattenGraphFilter(new SynonymGraphFilter(result, createSynonymMap(), true));
        result = new PorterStemFilter(result);
        //result = new StopFilter(result, stopwords);
        if (!stemExclusionSet.isEmpty())
            result = new SetKeywordMarkerFilter(result, stemExclusionSet);
        try {
            result = new StopFilter(result, StopFilter.makeStopSet(IndexingUtils.getStopwordsList(), true));
        }
        catch ( URISyntaxException | IOException e){
            e.printStackTrace();
        }
        //result = new SnowballFilter(result, new EnglishStemmer());
        return new TokenStreamComponents(source, result);
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        return new LowerCaseFilter(in);
    }

    // Create synonym map
    private SynonymMap createSynonymMap() {
        SynonymMap synMap = new SynonymMap(null, null, 0);
        try {
            BufferedReader countries = new BufferedReader(new FileReader(currentRelativePath + "\\src\\main\\resources\\countries_list.txt"));

            final SynonymMap.Builder builder = new SynonymMap.Builder(true);
            String country = countries.readLine();

            while(country != null) {
                builder.add(new CharsRef("country"), new CharsRef(country), true);
                builder.add(new CharsRef("countries"), new CharsRef(country), true);
                country = countries.readLine();
            }

            synMap = builder.build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage() + "occurred when trying to create synonym map");
        }
        return synMap;
    }
}
