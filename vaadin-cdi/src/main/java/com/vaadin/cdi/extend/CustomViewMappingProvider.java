package com.vaadin.cdi.extend;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by Alexander.Castro on 30/05/2017.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface CustomViewMappingProvider {
}
