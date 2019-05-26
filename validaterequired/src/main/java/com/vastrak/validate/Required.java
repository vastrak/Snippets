package com.vastrak.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Christian
 *
 */
@Retention(RetentionPolicy.RUNTIME) // When the annotation is checked? 
@Target(ElementType.FIELD)  // Where the annotation applies: classes and / or fields
public @interface Required {
	public boolean value() default true; // value by default is true, value=true 
}

