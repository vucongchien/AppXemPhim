package com.example.appxemphim.Model.DTO;

public class EmailDTO {
    public String getEmail() {
        return email;
    }

    public String getMa() {
        return ma;
    }

    private String email;
    private String ma;

    public EmailDTO(String email, String ma) {
        this.email = email;
        this.ma = ma;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }
}
