package com.tcd.lucene.analyzer;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.HyphenatedWordsFilter;
import org.apache.lucene.analysis.miscellaneous.HyphenatedWordsFilterFactory;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.tartarus.snowball.ext.EnglishStemmer;

public class CustomAnalyzer extends StopwordAnalyzerBase {

	  public CustomAnalyzer(CharArraySet stopWordSet) {
	    super(stopWordSet);
	  }
	  /*
	   *  EnglishMinimalStemFilter, HyphenatedWordsFilter, ASCIIFoldingFilter, EnglishPossessiveFilter
	   */
	  @Override
	  protected TokenStreamComponents createComponents(String arg0) {
	    final Tokenizer tokenizer = new StandardTokenizer();

	    // Add additional token filters: lowercase, English stopwords, porter
	    // stemming
	    TokenStream tokenStream = new EnglishPossessiveFilter(tokenizer);
	    tokenStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream = new TrimFilter(tokenStream);
	    tokenStream = new LowerCaseFilter(tokenStream);
	    tokenStream = new StopFilter(tokenStream, this.stopwords);
	    //tokenStream = new KStemFilter(tokenStream);
	    tokenStream = new EnglishMinimalStemFilter(tokenStream);
	    tokenStream = new EnglishPossessiveFilter(tokenStream);
	    tokenStream = new HyphenatedWordsFilter(tokenStream);
	    tokenStream = new ASCIIFoldingFilter(tokenStream);
	    tokenStream = new PorterStemFilter(tokenStream);
        tokenStream = new SnowballFilter(tokenStream, new EnglishStemmer());
//        tokenStream = new LengthFilter(tokenStream, 5, 25);
//        tokenStream = new ShingleFilter(tokenStream, 2);
	    tokenStream = new TrimFilter(tokenStream);

	    TokenStreamComponents tokenStreamComponents = new TokenStreamComponents(tokenizer, tokenStream);
	    return tokenStreamComponents;
	  }
	}