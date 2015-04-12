package com.apps.parser;

import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class ReadNASA
{
	
	IndexWriter writer;
	
	public static void main(String[] args)
	{
		try
		{
			URL url = new URL("https://data.ny.gov/data.json");

			NASAIndexer indexer = new NASAIndexer();

			try (InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is))
			{
				indexer.indexData(rdr);

				System.out.println("Test Search");
				NASASearchEngine se = new  NASASearchEngine("");
				TopDocs topDocs = se.performSearch("crime energy", 10);

				System.out.println("Results found: " + topDocs.totalHits);
				ScoreDoc[] hits = topDocs.scoreDocs;
				for (int i = 0; i < hits.length; i++)
				{
					Document searchDoc = se.getDocument(hits[i].doc);
					System.out.println(searchDoc.get("title") + ":" + searchDoc.get("description") + " " + searchDoc.get("url") + " (" + hits[i].score + ")");

				}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
