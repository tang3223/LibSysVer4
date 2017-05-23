package com.gcit.libsys;

import java.util.Locale;

import org.springframework.ui.Model;

//@Controller
public class HomeController {

	/*@RequestMapping(value = "/", method = RequestMethod.GET)*/
	public String home(Locale locale, Model model) {

		return "index";
	}
	
	

}
