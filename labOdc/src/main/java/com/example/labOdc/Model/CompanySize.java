package com.example.labOdc.Model;

public enum CompanySize {
    ONE_TO_10("1-10"),
    ELEVEN_TO_50("11-50"),
    FIFTYONE_TO_200("51-200"),
    TWOZEROONE_TO_500("201-500"),
    FIVE_HUNDRED_PLUS("500+");

    private final String value;

    CompanySize(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
