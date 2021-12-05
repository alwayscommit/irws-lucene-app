package com.tcd.lucene.search;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import com.tcd.lucene.analyzer.WordNetAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import com.tcd.lucene.model.DocumentQuery;
import com.tcd.lucene.util.Constants.LuceneDocument;

/**
 * This class is responsible for setting up the search configuration as well as perform the search
 * It uses MultiFieldQueryParser to query on multiple fields
 *
 */
public class LuceneSearcher {
	private IndexSearcher indexSearcher;
	// Limit the number of search results we get
	private static int MAX_RESULTS = 1000;
	
	private static final String OUTPUT_FILE = "../output.txt";

	public LuceneSearcher(Path indexPath, Similarity similarity) throws IOException {
		System.out.println("Searching documents...");
		IndexReader indexReader = DirectoryReader.open(FSDirectory.open(indexPath));
		this.indexSearcher = new IndexSearcher(indexReader);
		this.indexSearcher.setSimilarity(similarity);
	}

	/**
	 * Executes the list of queries and generates output file
	 * @param queryList
	 * @param analyzer
	 * @throws IOException
	 * @throws ParseException
	 */
	public void searchAndGenerateOutput(List<DocumentQuery> queryList, Analyzer analyzer) throws IOException, ParseException {
		FileWriter fw = new FileWriter(OUTPUT_FILE);
	    PrintWriter pw = new PrintWriter(fw);

		for (DocumentQuery docQuery: queryList) {
			MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] {"headline", "body"}, analyzer, getBoosts());
			BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
			parser.setDefaultOperator(Operator.OR);

			Query titleQuery = parser.parse(QueryParser.escape(docQuery.getQueryTitle()));
			Query descriptionQuery = parser.parse(QueryParser.escape(docQuery.getDescription()));
			Query narrativeQuery = parser.parse(QueryParser.escape(docQuery.getNarrative()));

			booleanQuery.add(new BoostQuery(narrativeQuery, 1.4f), BooleanClause.Occur.SHOULD);
			booleanQuery.add(new BoostQuery(titleQuery, 4f), BooleanClause.Occur.SHOULD);
			booleanQuery.add(new BoostQuery(descriptionQuery, 2.5f), BooleanClause.Occur.SHOULD);

			ScoreDoc[] scoreDocs = this.indexSearcher.search(booleanQuery.build(), MAX_RESULTS).scoreDocs;
			this.addScoreToOutputFile(docQuery.getQueryNumber(), scoreDocs, pw);
		}
		System.out.println("Output file generated!");
		fw.close();
		pw.close();
	}
	
	/*
	 * Generates output file for the scores
	 */

	private HashMap<String, Float> getBoosts() {
		HashMap<String, Float> boosts = new HashMap<String, Float>();
		// revisit these booster values
		boosts.put("headline", 0.6f);
		boosts.put("body", 0.8f);
		return boosts;
	}

	public void addScoreToOutputFile (String queryId, ScoreDoc[] hits, PrintWriter pw) throws IOException {
		for (int i = 0; i < hits.length; i++)
		{
			Document hitDoc = this.indexSearcher.doc(hits[i].doc);
			String queryOutput = queryId  + " Q0 " + hitDoc.get(LuceneDocument.DOCUMENT_ID) + " 1 " + hits[i].score + " STANDARD";
			pw.println(queryOutput);
//			System.out.println(queryOutput);
		}
	}
		
	
}
