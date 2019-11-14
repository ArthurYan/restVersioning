package com.augusto.test.spring;

import com.augusto.test.spring.version.VersionHolder;
import com.augusto.test.spring.version.VersionedResource;
import com.augusto.test.spring.version.VersionedResourceRequestCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        VersionedResource typeAnnotation = AnnotationUtils.findAnnotation(handlerType, VersionedResource.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        VersionedResource methodAnnotation = AnnotationUtils.findAnnotation(method, VersionedResource.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        VersionHolder.fillApiVersions(methodAnnotation, requestMapping);

        return createCondition(methodAnnotation);
    }

    private RequestCondition<?> createCondition(VersionedResource versionMapping) {
        if (versionMapping != null) {
            return new VersionedResourceRequestCondition(versionMapping.media(), versionMapping.from(), versionMapping.to());
        }

        return null;
    }

}