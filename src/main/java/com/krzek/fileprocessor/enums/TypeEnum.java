package com.krzek.fileprocessor.enums;

import java.util.stream.Stream;

public enum TypeEnum {
    SALESMAN("001"),
    CLIENT("002"),
    SALE("003");

    private final String code;

    TypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValid(String string) {
        return Stream.of(values()).filter(x -> x.code.equalsIgnoreCase(string)).findAny().orElse(null) != null;
    }

    public static TypeEnum getValue(String code) {
        return Stream.of(values()).filter(e -> e.code.equals(code)).findFirst()
                .orElse(null);
    }
}
