package com.example.API;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WeeklyTrackChart {

    @JsonProperty("track")
    private List<Track> track;  // Lista utworów

    @JsonProperty("@attr")
    private Attr attr;  // Dodatkowe atrybuty

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    // Wewnętrzna klasa do mapowania atrybutów @attr
    public static class Attr {
        private String from;
        private String to;
        private String user;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    // Wewnętrzna klasa do mapowania utworów
    public static class Track {
        private String name;
        private String artist;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }
    }
}
