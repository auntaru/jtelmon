/*
 * http://www.sivalabs.in/2011/04/springmvc3-hibernate-crud-sample.html
 * http://www.javacodegeeks.com/2011/04/spring-mvc3-hibernate-crud-sample.html
 * 
 */

package com.sivalabs.contacts;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author SivaLabs
 *
 */
@Component("contactFormValidator")
public class ContactFormValidator implements Validator
{
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz)
	{
		return Contact.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","required.name", "Name is required.");
	}

}
