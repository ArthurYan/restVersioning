package com.augusto.test.spring.version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersionHolder {
    private static Map<String, List<Version>> API_VERSIONS = new HashMap<>();

    public static boolean match(String patterns, String version, String from) {
        List<Version> versions = API_VERSIONS.get(patterns);

        Version requestVersion = new Version(version);
        Version fromVersion = new Version(from);

        if (requestVersion.compareTo(fromVersion) < 0) {
            return false;
        } else {
            int fromIndex = versions.indexOf(fromVersion);

            if (fromIndex == versions.size() - 1) {
                return true;
            }

            Version nextVersion = versions.get(fromIndex + 1);
            if (requestVersion.compareTo(nextVersion) < 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void fillApiVersions(String patterns, String from) {

        List<Version> versions;
        if (!API_VERSIONS.containsKey(patterns)) {
            versions = new ArrayList<>();
            API_VERSIONS.put(patterns, versions);
        } else {
            versions = API_VERSIONS.get(patterns);
        }
        Version fromVersion = new Version(from);
        versions.add(fromVersion);

        versions.sort(Version::compareTo);
    }
}
