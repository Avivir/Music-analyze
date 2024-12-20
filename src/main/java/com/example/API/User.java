package com.example.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String country;
    private boolean isUserActive;
    private List<String> mostListenedMusic = new ArrayList<>();
    private List<String> listOfArtist = new ArrayList<>();
    private List<String> numberOfPlaycounts = new ArrayList<>();

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

    public List<String> getMostListenedMusic(){
        return mostListenedMusic;
    }

    public void setMostListenedMusic(String mostListenedMusic) {
        this.mostListenedMusic.add(mostListenedMusic);
    }

    public List<String> getListOfArtist() {
        return listOfArtist;
    }

    public void setListOfArtist(String numberOfCounts) {
        this.listOfArtist.add(numberOfCounts);
    }

    public List<String> getNumberOfPlaycounts() {
        return numberOfPlaycounts;
    }

    public void setNumberOfPlaycounts(String numberOfPlaycounts) {
        this.numberOfPlaycounts.add(numberOfPlaycounts);
    }

}
