package com.augusto.test.spring.version;

public class Version implements Comparable<Version> {
    public static final String MAX_VERSION = "99.99";

    private final int major;
    private final int minor;

    public Version(String version) {
        String tokens[] = version.split("\\.");

        if (tokens.length != 2) {
            throw new IllegalArgumentException("Invalid version " + version + ". The version must have major and minor number.");
        }

        major = Integer.parseInt(tokens[0]);
        minor = Integer.parseInt(tokens[1]);
    }

    @Override
    public int compareTo(Version other) {
        if (this.major > other.major) {
            return 1;
        } else if (this.major < other.major) {
            return -1;
        } else if (this.minor > other.minor) {
            return 1;
        } else if (this.minor < other.minor) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return major + minor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return "v" + major + "." + minor;
    }
}
