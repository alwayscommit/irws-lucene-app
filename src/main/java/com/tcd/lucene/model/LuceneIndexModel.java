package com.tcd.lucene.model;

import com.tcd.lucene.util.ParsingUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import java.util.List;

public class LuceneIndexModel {

    public static void indexFBIS(List<FBISDocument> fbisDocuments, List<Document> luceneDocuments) throws IllegalAccessException{
        for(FBISDocument fbisDocument : fbisDocuments){
            if(fbisDocument.isEmpty())
                continue;
            Document doc = new Document();
            doc.add(new StringField("documentId", fbisDocument.getDocNo(), Field.Store.YES));
            doc.add(new TextField("body", fbisDocument.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }
    }

    public static void indexFR94(List<FR94Document> fr94Documents, List<Document> luceneDocuments) throws IllegalAccessException {
        for(FR94Document fr94Document : fr94Documents){
            if(fr94Document.isEmpty())
                continue;
            Document doc = new Document();
            doc.add(new StringField("documentId", fr94Document.getDocno(), Field.Store.YES));
            doc.add(new TextField("body", fr94Document.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }
    }

    public static void indexFT(List<FTDocument> ftDocuments, List<Document> luceneDocuments) throws IllegalAccessException {
        for(FTDocument ftDocument : ftDocuments){
            if(ftDocument.isEmpty())
                continue;
            Document doc = new Document();
            doc.add(new StringField("documentId", ftDocument.getDocno(), Field.Store.YES));
            doc.add(new TextField("body", ftDocument.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }
    }

    public static void indexLATimes(List<LATimesDocument> laTimesDocuments, List<Document> luceneDocuments) throws IllegalAccessException {
        for(LATimesDocument laTimesDocument : laTimesDocuments){
            if(laTimesDocument.isEmpty())
                continue;
            Document doc = new Document();
            doc.add(new StringField("documentId", laTimesDocument.getDocNo(), Field.Store.YES));
            doc.add(new TextField("body", laTimesDocument.getText(), Field.Store.YES));
            luceneDocuments.add(doc);
        }

    }

}
