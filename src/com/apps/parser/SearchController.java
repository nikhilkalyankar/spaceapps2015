package com.apps.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SearchController
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String home(@ModelAttribute("form") Form form, Locale locale, Model model, BindingResult result, HttpServletRequest request)
	{
		logger.info("Welcome home! The client locale is {}.", locale);

		Search text = new Search();
		String searchText = (String) form.getSearch();
//		System.out.println(request.getAttribute("searchText"));
		String pathInfo = request.getSession().getServletContext().getRealPath("");
		ArrayList<SearchResult> results = text.search(searchText, pathInfo);

		Map<String, ArrayList<SearchResult>> attMap = new HashMap<String, ArrayList<SearchResult>>();
		attMap.put("results", results);
		model.addAllAttributes(attMap);

		return "search";
	}

}
