package com.vaadin.cdi.extend;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.internal.AnnotationUtil;
import com.vaadin.cdi.internal.Conventions;
import com.vaadin.ui.UI;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander.Castro on 17/05/2017.
 */
@Default
public class DefaultViewMappingProvider extends ViewMappingProvider implements Serializable {
    @Inject
    private BeanManager beanManager;

    @Override
    public String resolveViewMapping(String uriFragment) {
        String viewName = uriFragment;
        if (viewName.startsWith("!")) {
            viewName = viewName.substring(1);
        }

        for (String name : AnnotationUtil.getCDIViewMappings(beanManager)) {
            if (viewName.equals(name) || (viewName.startsWith(name + "/"))) {
                return name;
            }
        }

        return null;
    }

    @Override
    public boolean isInCurrentUI(Class<?> viewClass) {
        CDIView viewAnnotation = viewClass.getAnnotation(CDIView.class);

        if (viewAnnotation != null) {
            List<Class<? extends UI>> uiClasses = Arrays.asList(viewAnnotation.uis());
            if(uiClasses.contains(UI.class))
                return true;
            Class<? extends UI> currentUI = UI.getCurrent().getClass();
            for (Class<? extends UI> uiClass : uiClasses) {
                if (uiClass.isAssignableFrom(currentUI)) {
                   return true;
                }
            }
        }
        return false;
    }

    @Override
    public String resolveViewMapping(Class<?> viewClazz) {
        return Conventions.deriveMappingForView(viewClazz);
    }
}
