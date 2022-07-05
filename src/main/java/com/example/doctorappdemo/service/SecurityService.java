package com.example.doctorappdemo.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String email, String password);
}
