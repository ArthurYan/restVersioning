package com.augusto.test.spring.version;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class VersionHolder {
    private static ConcurrentHashMap<String, List<Version>> API_VERSIONS = new ConcurrentHashMap<>();

    public static void fillApiVersions(VersionedResource methodAnnotation,
                                       RequestMapping requestMapping) {
        String url = requestMapping.value()[0];//TODO 需要支持多URL声明，以及param、header等
        List<Version> versions;
        if (!API_VERSIONS.contains(url)) {
            versions = new ArrayList<>();
            API_VERSIONS.put(url, versions);
        } else {
            versions = API_VERSIONS.get(url);
        }
        versions.add(new Version(methodAnnotation.from()));
    }

    public static boolean match(String version, Version from) {
        return true;
    }
}
