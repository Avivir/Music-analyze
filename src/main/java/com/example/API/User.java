package com.example.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String country;
    private boolean isUserActive;

    public String getName() {
        return name;
    }

    public boolean getIsUserActive() {return  isUserActive;}
    public void setIsUserActive(boolean isUserActive) {this.isUserActive = isUserActive;}

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
