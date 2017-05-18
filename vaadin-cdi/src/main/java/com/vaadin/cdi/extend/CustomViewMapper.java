package com.vaadin.cdi.extend;

import com.vaadin.cdi.ViewScoped;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alexander.Castro on 18/05/2017.
 */
@ViewMapper
@Stereotype
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@ViewScoped
public @interface CustomViewMapper {
}
