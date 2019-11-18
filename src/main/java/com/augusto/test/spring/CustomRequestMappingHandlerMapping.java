package com.augusto.test.spring;

import com.augusto.test.spring.version.VersionHolder;
import com.augusto.test.spring.version.VersionedResource;
import com.augusto.test.spring.version.VersionedResourceRequestCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
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
        return createCondition(methodAnnotation);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo requestMappingInfo = super.getMappingForMethod(method, handlerType);

        String patterns = requestMappingInfo.getPatternsCondition().getPatterns().iterator().next();
        //TODO
        // 应该处理所有的patterns

        if (requestMappingInfo.getCustomCondition() instanceof VersionedResourceRequestCondition) {

            ((VersionedResourceRequestCondition) requestMappingInfo.getCustomCondition()).patterns = patterns;
            VersionHolder.fillApiVersions(patterns,
                ((VersionedResourceRequestCondition) requestMappingInfo.getCustomCondition()).from);

        }
        return requestMappingInfo;
    }

    private RequestCondition<?> createCondition(VersionedResource versionMapping) {
        if (versionMapping != null) {
            return new VersionedResourceRequestCondition(versionMapping.from());
        }

        return null;
    }

}