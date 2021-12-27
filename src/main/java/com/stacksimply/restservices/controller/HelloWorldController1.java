package com.stacksimply.restservices.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldController1 {

	@Autowired
	ResourceBundleMessageSource messageSource;
	
	@GetMapping("/helloi18n")
	public String getMessagesInI18NFormat(@RequestHeader(name="Accept-Language",required = false) String locale) {
		return messageSource.getMessage("label.hello",null, new Locale(locale));
	}
	
	@GetMapping("/helloi18n2")
	public String getMessagesInI18NFormat2() {
		return messageSource.getMessage("label.hello",null,LocaleContextHolder.getLocale());
	}
}
