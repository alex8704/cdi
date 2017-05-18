package com.vaadin.cdi.extend;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.cdi.ViewScoped;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.apache.deltaspike.core.util.AnnotationUtils;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;
import javax.enterprise.inject.spi.Bean;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * View Mapping Provider base class
 *
 * Various implementations can exist (default DefaultViewMappingProvider, others with
 * {@link Alternative}) and the active implementation can be selected in
 * beans.xml .
 */
public abstract class ViewMappingProvider {
    protected static List<Class<? extends Annotation>> viewMapperAnnotations;
    protected static String viewMapperSimpleNames;
    static {
        viewMapperAnnotations = new FastClasspathScanner()
                .matchClassesWithAnnotation(ViewMapper.class, clazz -> {})
                .scan().getNamesOfAnnotationsWithMetaAnnotation(ViewMapper.class)
                .parallelStream().map(ViewMappingProvider::loadClass)
                .collect(Collectors.toList());
    }

    static{
        StringBuilder namesBuilder = new StringBuilder("[@").append(CDIView.class.getSimpleName());
        viewMapperAnnotations.forEach(annotClazz -> {
            if(annotClazz.getAnnotation(ViewScoped.class) == null && annotClazz.getAnnotation(UIScoped.class) == null)
                throw new RuntimeException("Custom view mapping provider target Annotation "+annotClazz+" must be " +
                        "annotated as at least one of ["+ViewScoped.class. getName()+", "+UIScoped.class. getName()+"] " +
                        "to be compatible with cdi capabilities.");
            if(annotClazz.getAnnotation(Stereotype.class) == null)
                throw new RuntimeException("Custom view mapping provider target Annotation "+annotClazz+" must be " +
                        "annotated as "+Stereotype.class.getName()+" to be compatible with cdi capabilities.");
            namesBuilder.append(", @").append(annotClazz.getSimpleName());
        });
        viewMapperSimpleNames = namesBuilder.append("]").toString();
    }

    protected static Class<? extends Annotation> loadClass(String className){
        try{
            return (Class<? extends Annotation>) Annotation.class.forName(className);
        }catch(ClassNotFoundException ex){
            throw new RuntimeException("Cannot load custom view mapper annotation @"+className);
        }
    }

    public static boolean isAnnotatedAsCDIView(Class candidateClass){
        if(candidateClass.isAnnotationPresent(CDIView.class))
            return true;
        for(Class<? extends Annotation> annot : viewMapperAnnotations)
            if(candidateClass.isAnnotationPresent(annot))
                return true;
        return false;
    }

    public static String getViewMapperSimpleNames(){
        return viewMapperSimpleNames;
    }

    public abstract String resolveViewMapping(String uriFragment);
    public abstract String resolveViewMapping(Class<?> viewClazz);
    public abstract boolean isInCurrentUI(Class<?> viewClass);

    public static void main(String[] args) {
        System.out.println(viewMapperAnnotations);
    }
}
