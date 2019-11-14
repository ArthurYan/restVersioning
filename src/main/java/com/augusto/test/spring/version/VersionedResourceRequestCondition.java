package com.augusto.test.spring.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class VersionedResourceRequestCondition extends AbstractRequestCondition<VersionedResourceRequestCondition> {
    private Logger logger = LoggerFactory.getLogger(VersionedResourceRequestCondition.class);
    private final Set<VersionRange> versions;
    private final String acceptedMediaType;

    public VersionedResourceRequestCondition(String acceptedMediaType, String from, String to) {
        this(acceptedMediaType, versionRange(from, to));
    }

    public VersionedResourceRequestCondition(String acceptedMediaType, Collection<VersionRange> versions) {
        this.acceptedMediaType = acceptedMediaType;
        this.versions = Collections.unmodifiableSet(new HashSet<VersionRange>(versions));
    }

    private static Set<VersionRange> versionRange(String from, String to) {
        HashSet<VersionRange> versionRanges = new HashSet<>();

        if (StringUtils.hasText(from)) {
            String toVersion = (StringUtils.hasText(to) ? to : Version.MAX_VERSION);
            VersionRange versionRange = new VersionRange(from, toVersion);

            versionRanges.add(versionRange);
        }

        return versionRanges;
    }

    @Override
    public VersionedResourceRequestCondition combine(VersionedResourceRequestCondition other) {
        logger.debug("Combining:\n{}\n{}", this, other);
        Set<VersionRange> newVersions = new LinkedHashSet<VersionRange>(this.versions);
        newVersions.addAll(other.versions);
        String newMediaType;
        if (StringUtils.hasText(this.acceptedMediaType) && StringUtils.hasText(other.acceptedMediaType)
            && !this.acceptedMediaType.equals(other.acceptedMediaType)) {
            throw new IllegalArgumentException("Both conditions should have the same media type. " + this.acceptedMediaType + " =!= " + other.acceptedMediaType);
        } else if (StringUtils.hasText(this.acceptedMediaType)) {
            newMediaType = this.acceptedMediaType;
        } else {
            newMediaType = other.acceptedMediaType;
        }
        return new VersionedResourceRequestCondition(newMediaType, newVersions);
    }

    @Override
    public VersionedResourceRequestCondition getMatchingCondition(HttpServletRequest request) {

        String version = request.getHeader("uaweb");
        if (!StringUtils.isEmpty(version)) {
            logger.debug("Version={}", version);
            for (VersionRange versionRange : versions) {
                if (VersionHolder.match(version, versionRange.getFrom())) {
                    return this;
                }
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
        return versions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version={");
        sb.append("media=").append(acceptedMediaType).append(",");
        for (VersionRange range : versions) {
            sb.append(range).append(",");
        }
        sb.append("}");

        return sb.toString();
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }
}
