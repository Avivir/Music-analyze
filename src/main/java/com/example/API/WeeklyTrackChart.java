package com.example.API;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyTrackChart {

    @JsonProperty("weeklytrackchart")
    private TrackChart trackChart;

    public TrackChart getTrackChart() {
        return trackChart;
    }

    public void setTrackChart(TrackChart trackChart) {
        this.trackChart = trackChart;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackChart {
        private List<Track> track = new ArrayList<>();

        public List<Track> getTrack() {
            return track;
        }

        public void setTrack(List<Track> track) {
            this.track = track;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Track {
        private String name;
        private Artist artist;
        private List<Image> image = new ArrayList<>();
        private String mbid;
        private String url;

        @JsonProperty("@attr")
        private Attr attr;

        private String playcount;

        @Override
        public String toString() {
            return "Track{name='" + name + "', artist=" + getArtist().getText() + ", playcount='" + playcount + "'}";
        }

        // Gettery i settery
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Artist getArtist() {
            return artist;
        }

        public void setArtist(Artist artist) {
            this.artist = artist;
        }

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
        }

        public String getMbid() {
            return mbid;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Attr getAttr() {
            return attr;
        }

        public void setAttr(Attr attr) {
            this.attr = attr;
        }

        public String getPlaycount() {
            return playcount;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artist {
        @JsonProperty("#text")
        private String text;

        private String mbid;

        // Gettery i settery
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMbid() {
            return mbid;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        private String size;

        @JsonProperty("#text")
        private String text;

        // Gettery i settery
        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Attr {
        private String rank;

        // Getter i setter
        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }
}
