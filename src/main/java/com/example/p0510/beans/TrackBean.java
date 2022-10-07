package com.example.p0510.beans;

import com.example.p0510.data.Album;
import com.example.p0510.data.Genre;
import com.example.p0510.data.Track;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrackBean {
    private Album album;
    private List<Track> tracks;
    private List<Genre> genres;
}
