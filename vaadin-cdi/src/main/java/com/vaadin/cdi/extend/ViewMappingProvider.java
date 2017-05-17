package com.vaadin.cdi.extend;

import com.vaadin.cdi.CDIView;
import org.apache.deltaspike.core.util.AnnotationUtils;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.Bean;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * View Mapping Provider interface
 *
 * Various implementations can exist (default DefaultViewMappingProvider, others with
 * {@link Alternative}) and the active implementation can be selected in
 * beans.xml .
 */
public interface ViewMappingProvider {
    public Class<? extends Annotation> getViewAnnotationType();
    public String resolveViewMapping(String uriFragment);
    public String resolveViewMapping(Class<?> viewClazz);
    public boolean isInCurrentUI(Class<?> viewClass);
}
