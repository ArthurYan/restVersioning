package com.augusto.test.spring.version;

public class VersionRange {

    private Version from;

    public VersionRange(String from) {
        this.from = new Version(from);
    }

    public Version getFrom() {
        return from;
    }

}
