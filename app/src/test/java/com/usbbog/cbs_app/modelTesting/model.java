package com.usbbog.cbs_app.modelTesting;

public class model {

    public class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters y Setters
    }

    public class LoginResponse {
        private Integer statusCode;
        private String message;
        private User user;

        // Getters y Setters
    }


}
