package com.example.p0510.beans;

import com.example.p0510.data.Album;
import com.example.p0510.data.Genre;
import com.example.p0510.data.MediaType;
import com.example.p0510.data.Track;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EditTrackBean {
    private Track track;
    private List<Genre> genres;
    private List<MediaType> types;
    private List<Album> albums;
    private String title;
    private String action;
}
