package com.tcd.lucene.search;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;
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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
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
	 * @throws Exception 
	 */
	public void searchAndGenerateOutput(List<DocumentQuery> queryList, Analyzer analyzer, Path directoryPath) throws Exception {
		FileWriter fw = new FileWriter(OUTPUT_FILE);
	    PrintWriter pw = new PrintWriter(fw);
	    ScoreDoc[] hits = {};
	    Directory indexDirectory = FSDirectory.open(directoryPath);
	    IndexReader reader = DirectoryReader.open(indexDirectory);

		for (DocumentQuery docQuery: queryList) {
			MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] {"headline", "body"}, analyzer, getBoosts());
			BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
			parser.setDefaultOperator(Operator.OR);

			Query titleQuery = parser.parse(QueryParser.escape(docQuery.getQueryTitle()));
			Query descriptionQuery = parser.parse(QueryParser.escape(docQuery.getDescription()));
			String narrative = docQuery.getActualNarrative();
//			String negativeNarrative = docQuery.getActualNegativeNarrative();
			Query narrativeQuery = null;
//			Query negativeNarrativeQuery = null;
			if(narrative!=null) {
				narrativeQuery = parser.parse(QueryParser.escape(narrative));
			}
//			if(negativeNarrative!=null) {
//				negativeNarrativeQuery = parser.parse(QueryParser.escape(negativeNarrative));
//			}

			if(narrativeQuery!=null) {
				booleanQuery.add(new BoostQuery(narrativeQuery, 1.9f), BooleanClause.Occur.SHOULD);
			}
//			if(negativeNarrativeQuery!=null) {
//				booleanQuery.add(new BoostQuery(negativeNarrativeQuery, 0.01f), BooleanClause.Occur.SHOULD);
//			}
			booleanQuery.add(new BoostQuery(titleQuery, 4.2f), BooleanClause.Occur.SHOULD);
			booleanQuery.add(new BoostQuery(descriptionQuery, 2.5f), BooleanClause.Occur.SHOULD);
			Query mainQuery = booleanQuery.build();
			Query expandedQuery = getExpandedQuery(mainQuery, this.indexSearcher, analyzer, reader, hits);

			ScoreDoc[] scoreDocs = this.indexSearcher.search(expandedQuery, MAX_RESULTS).scoreDocs;
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
		boosts.put("headline", 0.65f);
		boosts.put("body", 0.35f);
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
	
	/**
	 * Method to get expanded query from the base query
	 * @param searcher
	 * @param analyzer
	 * @param queryContents
	 * @param hits
	 * @param reader
	 * @return Query
	 * @throws Exception
	 */
	private Query getExpandedQuery(Query query, IndexSearcher indexSearcher, Analyzer analyzer, IndexReader indexReader, ScoreDoc[] hits) throws Exception {
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		builder.add(query, BooleanClause.Occur.SHOULD);
		TopDocs topDocs = indexSearcher.search(query, 4);

		for (ScoreDoc score : topDocs.scoreDocs) {
			Document hitDoc = indexReader.document(score.doc);
			String fieldText = hitDoc.getField("body").stringValue();
			String[] similarFields = { "body", "headline" };
			MoreLikeThisQuery similarBodyQuery = new MoreLikeThisQuery(fieldText, similarFields, analyzer,
					"body");
			Query expandedQuery = similarBodyQuery.rewrite(indexReader);
			builder.add(expandedQuery, BooleanClause.Occur.SHOULD);
//			if(hitDoc.getField("headline") != null) {
//				String fieldHeadlineText = hitDoc.getField("headline").stringValue();
//				MoreLikeThisQuery similarHeadlineQuery = new MoreLikeThisQuery(fieldHeadlineText, similarFields, analyzer,
//						"body");
//				Query expandedHeadlineQuery = similarHeadlineQuery.rewrite(reader);
//				builder.add(expandedHeadlineQuery, BooleanClause.Occur.SHOULD);
//			}
		}
		return builder.build();
	}
		
	
}
