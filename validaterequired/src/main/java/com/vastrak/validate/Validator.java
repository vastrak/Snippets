package com.vastrak.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 
 * 
 * @author Christian
 *
 */

public class Validator {
	
	/**
	 * Validate that any field, of any class, annotated as Required is not null
	 * 
	 * @param objectToValidate 
	 * @return true if all Required's fields aren't null
	 * @throws RequiredFieldException if the field it's annotated as Required
	 * @throws IllegalAccessException 
	 */
	public static boolean validateNotNulls(Object objectToValidate)
			throws RequiredFieldException, IllegalAccessException {

		// getDeclaredFields: returns an array of Field objects reflecting all the
		// fields declared by the class or interface represented by this Class object.
		// This includes public, protected, default (package) access,
		// and private fields, but excludes inherited fields.

		Field[] declaredFields = objectToValidate.getClass().getDeclaredFields();

		// A Field provides information about, and dynamic access to, a single field of
		// a class or an interface. The reflected field may be a class (static) field
		// or an instance field.
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(Required.class);
			if (annotation != null) {
				Required required = (Required) annotation;
				if (required.value()) {
					// Can not access a member of class com.vastrak.model.Employee
					// with modifiers "private"
					// then setAccessible true!
					field.setAccessible(true);
					// the next one can throw IllegalAccessException
					if (field.get(objectToValidate) == null) {
						throw new RequiredFieldException("The field " + field.getName() + " of "
								+ objectToValidate.getClass() + " can't be null");
					}
				}
			}
		}
		return true;
	}

}
