package com.example.p0510.dao;

import com.example.p0510.data.Album;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDao {

    private final DataSource ds;

    public AlbumDao(DataSource ds) {
        this.ds = ds;
    }

    public List<Album> findAll() {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from album")
        ) {
            ResultSet resultSet = ps.executeQuery();
            List<Album> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("AlbumId");
                String title = resultSet.getString("Title");
                int artistId = resultSet.getInt("ArtistId");
                result.add(new Album(id, title, artistId));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Album> findByArtistId(int artistId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from album where ArtistId = ?")
        ) {
            ps.setInt(1, artistId);
            ResultSet resultSet = ps.executeQuery();
            List<Album> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("AlbumId");
                String title = resultSet.getString("Title");
                result.add(new Album(id, title, artistId));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
