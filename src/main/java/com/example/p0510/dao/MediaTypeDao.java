package com.example.p0510.dao;

import com.example.p0510.data.MediaType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaTypeDao {

    private final DataSource ds;

    public MediaTypeDao(DataSource ds) {
        this.ds = ds;
    }

    public List<MediaType> findAll() {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from \"MediaType\"");) {

            return mediaListFromStatement(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MediaType findMediaType(int mediaTypeId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps
                     = connection.prepareStatement("select * from \"MediaType\" where \"MediaTypeId\" = ?")) {

            ps.setInt(1, mediaTypeId);

            try(ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return mediaFromResult(resultSet);
                } else return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<MediaType> mediaListFromStatement(PreparedStatement ps) {
        try (ResultSet resultSet = ps.executeQuery()) {

            List<MediaType> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(mediaFromResult(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private MediaType mediaFromResult(ResultSet resultSet) {
        try {
            int mediaTypeId = resultSet.getInt("MediaTypeId");
            String name = resultSet.getString("Name");

            return new MediaType(mediaTypeId, name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
