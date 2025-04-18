package com.example.appxemphim.Request;

public class RepassRequest {
    private String email;
    private String newpass;

    public RepassRequest(String email, String newpass) {
        this.email = email;
        this.newpass = newpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
}
