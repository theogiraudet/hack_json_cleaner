package fr.istic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    private final ArtistData artistData;

    public Data(@JsonProperty(value = "fields", required = true) ArtistData artistData) {
        super();
        this.artistData = artistData;
    }

    public ArtistData getArtistData() {
        return artistData;
    }

}

