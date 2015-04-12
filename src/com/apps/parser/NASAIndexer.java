package com.apps.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 
 * @author Nikhil
 * 
 */
public class NASAIndexer
{

	/**
	 * 
	 */
	private IndexWriter indexWriter = null;

	/**
	 * 
	 * @param create
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getIndexWriter(boolean create) throws IOException
	{
		if (indexWriter == null)
		{
			Directory indexDir = FSDirectory.open(Paths.get("index-directory"));
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void closeIndexWriter() throws IOException
	{
		if (indexWriter != null)
		{
			indexWriter.close();
		}
	}

	/**
	 * 
	 * @param rdr
	 * @throws IOException
	 */
	public void indexData(JsonReader rdr) throws IOException
	{

		indexWriter = getIndexWriter(true);
		String title = "";
		String description = "";
		String arrayString = "";
		String downloadURL = "";
		String data = "";
		JsonObject obj = rdr.readObject();
		JsonArray results = obj.getJsonArray("dataset");
		for (JsonObject result : results.getValuesAs(JsonObject.class))
		{
			indexWriter = getIndexWriter(false);
			Document doc = new Document();

			JsonArray jArray = result.getJsonArray("keyword");
			title = result.getString("title");
			description = result.getString("description");

			doc.add(new StringField("title", title, Field.Store.YES));
			doc.add(new StringField("description", description, Field.Store.YES));

			JsonArray distObj = result.getJsonArray("distribution");
			if (distObj != null)
			{
				int count = 0;
				Iterator<JsonValue> itr = distObj.listIterator();
				while (itr.hasNext())
				{
					if ((distObj.getJsonObject(count).getString("mediaType")).equals("application/json"))
					{
						downloadURL = distObj.getJsonObject(count).getString("downloadURL");
						doc.add(new StringField("url", downloadURL, Field.Store.YES));
					}
					count++;
					itr.next();
				}
			}

			if (jArray != null)
			{
				Iterator<JsonValue> itr = jArray.listIterator();
				while (itr.hasNext())
				{
					arrayString = arrayString + " " + itr.next();
				}
			}
			String fullSearchableText = title + " " + description + " " + arrayString + " " + downloadURL + " " + data;
			doc.add(new TextField("fullText", fullSearchableText, Field.Store.NO));
			indexWriter.addDocument(doc);

			arrayString = "";
		}
		closeIndexWriter();

	}

}
