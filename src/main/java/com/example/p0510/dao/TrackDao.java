package com.example.p0510.dao;

import com.example.p0510.data.Track;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDao {
    private final DataSource ds;

    public TrackDao(DataSource ds) {
        this.ds = ds;
    }

    public List<Track> findAll() {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from \"Track\"");) {

            return trackListFromStatement(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Track> findByAlbum(int albumId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("select * from \"Track\" where \"AlbumId\" = ?")) {
            ps.setInt(1, albumId);

            return trackListFromStatement(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Track findById(int trackId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement("select * from \"Track\" where \"TrackId\" = ?")) {
            ps.setInt(1, trackId);

            try(ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return trackFromResult(resultSet);
                } else return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Track track) {
        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement(
                "insert into \"Track\" values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )) {
            ps.setInt(1, idAutoIncrement());
            ps.setString(2, track.getName());
            ps.setInt(3, track.getAlbumId());
            ps.setInt(4, track.getMediaTypeId());
            ps.setInt(5, track.getGenreId());
            ps.setString(6, track.getComposer());
            ps.setInt(7, track.getMilliseconds());
            ps.setInt(8, track.getBytes());
            ps.setDouble(9, track.getUnitPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Track track) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "update \"Track\" set \"Name\" = ?, \"AlbumId\" = ?, \"MediaTypeId\" = ?, \"GenreId\" = ?, \"Composer\" = ?, \"Milliseconds\" = ?, \"Bytes\" = ?, \"UnitPrice\" = ? where \"TrackId\" = ?"
             )) {
            ps.setString(1, track.getName());
            ps.setInt(2, track.getAlbumId());
            ps.setInt(3, track.getMediaTypeId());
            ps.setInt(4, track.getGenreId());
            ps.setString(5, track.getComposer());
            ps.setInt(6, track.getMilliseconds());
            ps.setInt(7, track.getBytes());
            ps.setDouble(8, track.getUnitPrice());
            ps.setInt(9, track.getTrackId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int trackId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "delete from \"Track\" where \"TrackId\" = ?"
                     )) {
            ps.setInt(1, trackId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int idAutoIncrement() {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "select max(\"TrackId\") Id\n from \"Track\"")) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("Id");
                return ++id;
            } else return -1; // todo broken
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<Track> trackListFromStatement(PreparedStatement ps) {
        try (ResultSet resultSet = ps.executeQuery()) {

            List<Track> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(trackFromResult(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Track trackFromResult(ResultSet resultSet) {
        try {
            int trackId = resultSet.getInt("TrackId");
            String name = resultSet.getString("Name");
            int albumId = resultSet.getInt("AlbumId");
            int mediaTypeId = resultSet.getInt("MediaTypeId");
            int genreId = resultSet.getInt("GenreId");
            String composer = resultSet.getString("Composer");
            int milliseconds = resultSet.getInt("Milliseconds");
            int bytes = resultSet.getInt("Bytes");
            double unitPrice = resultSet.getDouble("UnitPrice");

            return new Track(trackId, name, albumId, mediaTypeId, genreId,
                    composer, milliseconds, bytes, unitPrice);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
