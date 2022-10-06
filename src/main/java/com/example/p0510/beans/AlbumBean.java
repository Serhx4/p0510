package com.example.p0510.beans;

import com.example.p0510.data.Album;
import com.example.p0510.data.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumBean {
    private Artist artist;
    private List<Album> albums;
}
