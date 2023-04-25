package com.example.navigationdrawersample;

public class LoginData
{
    public static boolean USER_ALREADY_LOGIN = false;

    public static boolean isUserAlreadyLogin() {
        return USER_ALREADY_LOGIN;
    }

    public static void setUserAlreadyLogin(boolean userAlreadyLogin) {
        USER_ALREADY_LOGIN = userAlreadyLogin;
    }
}
