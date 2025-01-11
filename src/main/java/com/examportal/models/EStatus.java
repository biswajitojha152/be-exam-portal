package com.examportal.models;

public enum EStatus {
    PASSED("Passed"),
    FAILED("Failed");

    private final String roleValue;

    EStatus(String value) {
        this.roleValue = value;
    }

    public String getRoleValue() {
        return roleValue;
    }
}
