package com.example.appxemphim.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String email;
    private String ma;

    public String getEmail() {
        return email;
    }

    public String getMa() {
        return ma;
    }
}
