package com.project.sparta.member.entity;

public enum MemberRoleEnum {

    CUSTOMER(Authority.CUSTOMER),  // 사용자
    SELLER(Authority.SELLER), // 판매자
    ADMIN(Authority.ADMIN);  // 관리자

    private final String authority;

    MemberRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String SELLER = "ROLE_SELLER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}
