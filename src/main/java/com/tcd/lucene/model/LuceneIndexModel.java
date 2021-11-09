package com.tcd.lucene.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import java.io.IOException;
import java.util.List;

public class LuceneIndexModel {

    public static void indexFBIS(List<FBISDocument> fbisDocuments, List<Document> luceneDocuments) throws IOException{
        for(FBISDocument fbisDocument : fbisDocuments){
            Document doc = new Document();
            doc.add(new StringField("documentId", fbisDocument.getDocNo(), Field.Store.YES));
            doc.add(new TextField("body", fbisDocument.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }
    }

    public static void indexFR94(List<FR94Document> fr94Documents, List<Document> luceneDocuments) throws IOException {
        for(FR94Document fr94Document : fr94Documents){
            Document doc = new Document();
            doc.add(new StringField("documentId", fr94Document.getDocno(), Field.Store.YES));
            doc.add(new TextField("body", fr94Document.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }
    }

    public static void indexFT(List<FTDocument> ftDocuments, List<Document> luceneDocuments) throws IOException {
        for(FTDocument ftDocument : ftDocuments){
            Document doc = new Document();
            doc.add(new StringField("documentId", ftDocument.getDocno(), Field.Store.YES));
            doc.add(new TextField("body", ftDocument.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }
    }

    public static void indexLATimes(List<LATimesDocument> laTimesDocuments, List<Document> luceneDocuments) throws IOException{
        for(LATimesDocument laTimesDocument : laTimesDocuments){
            Document doc = new Document();
            doc.add(new StringField("documentId", laTimesDocument.getDocNo(), Field.Store.YES));
            doc.add(new TextField("body", laTimesDocument.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }

    }

}
