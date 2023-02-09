package com.project.sparta.user.entity;

public enum UserGradeEnum {

    MOUNTAIN_CHILDREN(Authority.CHILDREN),
    MOUNTAIN_MANIA(Authority.MANIA),
    MOUNTAIN_GOD(Authority.GOD);

    private final String authority;

    UserGradeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CHILDREN = "ROLE_CHILDREN";
        public static final String MANIA = "ROLE_MANIA";
        public static final String GOD = "ROLE_GOD";
    }

}
