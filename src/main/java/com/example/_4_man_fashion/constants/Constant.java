package com.example._4_man_fashion.constants;

public final class Constant {

    public static final class Api {

        public static class Path {
            public static final String PREFIX = "/api";

            public static final String PUBLIC = PREFIX + "/public";

            public static final String ADMIN = PREFIX + "/admin";
            public static final String USER = PREFIX + "/user";
            public static final String EMPLOYEE = PREFIX + "/employee";

            public static final String AUTH = PREFIX + "/auth";
            public static final String ACCOUNT =  "/account";
            public static final String ON_BOARDING = PUBLIC + "/on-boarding";

            public static class Auth {
                public static final String LOGIN = "/login";
                public static final String REFRESH_TOKEN = "/refresh-token";
                public static final String CHECK_PHONE_NUMBER = "/check-phone-number";
                public static final String OTP = "/otp";
                public static final String OTP_REQUEST = OTP + "/request";
                public static final String OTP_VERIFY = OTP + "/verify";
            }

            public static class Account {
                public static final String REGISTER = "/register";
                public static final String CHANGE_PASSWORD = "/change-password";
                public static final String RESET_PASSWORD = "/reset-password";
                public static final String RESET_PASSWORD_INIT = RESET_PASSWORD + "/init";
                public static final String RESET_PASSWORD_FINISH = RESET_PASSWORD + "/finish";
            }

            public static class Admin {
                public static final String AUTH = ADMIN + "/auth";
            }
        }
    }

    public static final class Status {
        public static final Integer ACTIVE = 1;
        public static final Integer INACTIVE = 0;
        public static final Integer ALL = -1;
    }

    public static final class OrderStatus {
        public static final Integer DRAFT = 0;
        public static final Integer PENDING = 1;
        public static final Integer CONFIRMED = 2;
        public static final Integer PACKAGING = 3;
        public static final Integer DELIVERING = 4;
        public static final Integer COMPLETE = 5;
        public static final Integer EXCHANGE = 6;
        public static final Integer CANCEL_ORDER = 7;
    }

    public static final class Role {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String EMPLOYEE = "ROLE_EMPLOYEE";
        public static final String USER = "ROLE_USER";
    }

    public static final class OTP {
        public static final Long EXPIRED_TIME = 5 * 60 * 1000L;
    }

    public static final class Regex {
        public static final String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        public static final String PHONE_NUMBER = "(0[3|5|7|8|9])+([0-9]{8})\\b";
    }

}
