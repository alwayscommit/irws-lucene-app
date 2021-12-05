package com.tcd.lucene;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;


import com.tcd.lucene.index.EnglishIndexer;
import com.tcd.lucene.index.Indexer;
import com.tcd.lucene.model.DocumentQuery;
import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.model.FTDocument;
import com.tcd.lucene.model.LATimesDocument;
import com.tcd.lucene.model.LuceneDocumentConverter;
import com.tcd.lucene.parse.FBISParser;
import com.tcd.lucene.parse.FR94Parser;
import com.tcd.lucene.parse.FTParser;
import com.tcd.lucene.parse.LATimesParser;
import com.tcd.lucene.parse.QueryParser;
import com.tcd.lucene.search.LuceneSearcher;
import com.tcd.lucene.util.Utils;

public class LuceneApp {

	private static final String FBIS_PATH = "FBIS_PATH";
	private static final String FR94_PATH = "FR94_PATH";
	private static final String FT_PATH = "FT_PATH";
	private static final String LATIMES_PATH = "LATIMES_PATH";
	private static final String QUERY_FILE_PATH = "QUERY_FILE_PATH";
	private static final String INDEX_DIRECTORY = "../index";

	private static Properties properties = null;

	// private static final String TEST_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\test\\";

	public static void main(String[] args) throws IOException, ParseException {
		try {
			
			properties = Utils.getProperties(args[0]);
			//set the analyzer and similarity
			Indexer indexer = new EnglishIndexer();
//			Similarity[] sims = {new BM25Similarity(0.75f, 0.9f), new LMJelinekMercerSimilarity(new LMSimilarity.DefaultCollectionModel(), 0.5f)};
//			Similarity similarity = new MultiSimilarity(sims);
//			Similarity similarity = new DFRSimilarity(new BasicModelIF(), new AfterEffectL(), new NormalizationH2());
			Similarity[] sims = {new BM25Similarity(0.75f, 0.9f), new LMJelinekMercerSimilarity(new LMSimilarity.DefaultCollectionModel(), 0.5f)};
			Similarity similarity = new MultiSimilarity(sims);
			Path indexDirectory = getPath(indexer.getClass().getSimpleName());
			
			if(!indexExists(indexDirectory)) {
				indexDirectory = createIndexDirectory(indexDirectory);
				
				List<Document> fbisDocs = parseFBIS();
				indexer.processIndex(fbisDocs, indexDirectory);
				fbisDocs.clear();
				
				List<Document> fr94Docs = parseFR94();
				indexer.processIndex(fr94Docs, indexDirectory);
				fr94Docs.clear();
				
				List<Document> ftDocs = parseFT();
				indexer.processIndex(ftDocs, indexDirectory);
				ftDocs.clear();
				
				List<Document> laTimesDocs = parseLATimes();
				indexer.processIndex(laTimesDocs, indexDirectory);
				laTimesDocs.clear();
				
				System.out.println("Indexing done...");
			}

			List<DocumentQuery> queries = new ArrayList<DocumentQuery>();
			List<DocumentQuery> queryList = QueryParser.parse(properties.getProperty(QUERY_FILE_PATH), queries);

			// Search
			LuceneSearcher luceneSearcher = new LuceneSearcher(indexDirectory, similarity);
			luceneSearcher.searchAndGenerateOutput(queryList, indexer.getAnalyzer());

		} catch (IOException | IllegalAccessException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static Path getPath(String analyzerName) {
		File folder = new File(INDEX_DIRECTORY+System.getProperty("file.separator")+analyzerName);
		return Paths.get(folder.getAbsolutePath());
	}

	private static boolean indexExists(Path indexDirectory) {
		File folder = new File(indexDirectory.toString());
		return folder.exists();
	}

	private static List<Document> parseLATimes() throws IOException, IllegalAccessException, URISyntaxException {
		List<LATimesDocument> laTimesDocList = new ArrayList<LATimesDocument>();
		System.out.println("Reading LA Times Documents:: " + properties.getProperty(LATIMES_PATH));
		LATimesParser.parse(properties.getProperty(LATIMES_PATH), laTimesDocList);
		return LuceneDocumentConverter.convertLATimes(laTimesDocList);
	}

	private static List<Document> parseFT() throws IOException, IllegalAccessException, URISyntaxException {
		List<FTDocument> ftDocList = new ArrayList<FTDocument>();
		System.out.println("Reading FT Documents :: " + properties.getProperty(FT_PATH));
		FTParser.parseNestedFolders(new File(properties.getProperty(FT_PATH)).listFiles(), ftDocList);
		return LuceneDocumentConverter.convertFT(ftDocList);
	}
 
	private static List<Document> parseFR94() throws IOException, IllegalAccessException, URISyntaxException {
		List<FR94Document> fr94DocList = new ArrayList<FR94Document>();
		System.out.println("Reading FR94 Documents :: " + properties.getProperty(FR94_PATH));
		FR94Parser.parseNestedFolders(new File(properties.getProperty(FR94_PATH)).listFiles(), fr94DocList);
		return LuceneDocumentConverter.convertFR94(fr94DocList);
	}

	private static List<Document> parseFBIS() throws IllegalAccessException, IOException, URISyntaxException {
		List<FBISDocument> fbisDocList = new ArrayList<FBISDocument>();
		System.out.println("Reading FBIS Documents :: " + properties.getProperty(FBIS_PATH));
		FBISParser.parse(properties.getProperty(FBIS_PATH), fbisDocList);
		return LuceneDocumentConverter.convertFBIS(fbisDocList);
	}
	
	private static Path createIndexDirectory(Path indexDirectory) throws IOException {
		File folder = new File(indexDirectory.toString());
		folder.mkdir();
		return Paths.get(folder.getAbsolutePath());
	}

}
