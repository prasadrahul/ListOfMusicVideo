package com.example.adi.recyclerviewmusic.model;

public class DataVideo {
    String videoURL;
    String trackCensoredName;
    String artistName;
    String artworkUrl30;


    public DataVideo(String videoURL, String trackCensoredName, String artistName,

                     String artworkUrl30) {
        this.videoURL = videoURL;
        this.trackCensoredName = trackCensoredName;
        this.artistName = artistName;
        this.artworkUrl30 = artworkUrl30;
    }

    public String getVideoURL() {
        return videoURL;
    }


    public String getTrackCensoredName() {
        return trackCensoredName;
    }


    public String getArtistName() {
        return artistName;
    }


    public String getArtworkUrl30() {
        return artworkUrl30;
    }


}
