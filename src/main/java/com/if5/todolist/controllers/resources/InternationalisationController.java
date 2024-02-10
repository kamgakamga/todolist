package com.if5.todolist.controllers.resources;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class InternationalisationController {

	@GetMapping("lang/{lang}")
	public void changeLocal(@PathVariable("lang") String lang , HttpServletRequest request) {
		
		Locale currentLocal =  request.getLocale();
		String countryCode = currentLocal.getCountry();
		String countryName = currentLocal.getDisplayCountry();
		
		System.out.println("countryCode = "+countryCode+" countryName= "+countryName);
		
		String langCode = currentLocal.getLanguage();
		String langName = currentLocal.getDisplayLanguage();
		
		System.out.println("LangCode = "+langCode+" LangName= "+langName);
	}
}
