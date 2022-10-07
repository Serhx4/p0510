package com.example.p0510.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Track {
    private int trackId;
    private String name;
    private int albumId;
    private int mediaTypeId;
    private int genreId;
    private String composer;
    private int milliseconds;
    private int bytes;
    private double unitPrice;
}
