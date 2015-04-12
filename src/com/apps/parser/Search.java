package com.apps.parser;

import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Search
{

	public ArrayList<SearchResult> search(String searchTerm, String pathInfo)
	{
		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		System.out.println("performSearch");
		NASASearchEngine se;
		try
		{
			se = new NASASearchEngine(pathInfo);

			TopDocs topDocs = se.performSearch(searchTerm, 100);

			System.out.println("Results found: " + topDocs.totalHits);
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (int i = 0; i < hits.length; i++)
			{
				Document searchDoc = se.getDocument(hits[i].doc);
				SearchResult result = new SearchResult();
				result.setTitle(searchDoc.get("title"));
				result.setDescription(searchDoc.get("description"));
				result.setUrl(searchDoc.get("url"));
				results.add(result);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return results;

	}

}
