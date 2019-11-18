package com.augusto.test.spring.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public class VersionedResourceRequestCondition extends AbstractRequestCondition<VersionedResourceRequestCondition> {
    private Logger logger = LoggerFactory.getLogger(VersionedResourceRequestCondition.class);

    public String from;
    public String patterns;
    private Collection content;

    public VersionedResourceRequestCondition(String from) {
        this.from = from;
        content = new ArrayList(1);
        content.add(from);
    }

    @Override
    public VersionedResourceRequestCondition combine(VersionedResourceRequestCondition other) {

        VersionedResourceRequestCondition condition = new VersionedResourceRequestCondition(from);
        condition.patterns = patterns;
        return condition;
    }

    @Override
    public VersionedResourceRequestCondition getMatchingCondition(HttpServletRequest request) {

        String version = request.getHeader("uaweb");
        if (!StringUtils.isEmpty(version)) {
            logger.debug("Version={}", version);

            if (VersionHolder.match(patterns, version, from)) {
                return this;
            }

        }

        logger.debug("Didn't find matching version");
        return null;
    }

    @Override
    public int compareTo(VersionedResourceRequestCondition other, HttpServletRequest request) {
        return 0;
    }

    @Override
    protected Collection<?> getContent() {

        return content;
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }
}
