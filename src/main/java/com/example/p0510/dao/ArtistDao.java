package com.example.p0510.dao;

import com.example.p0510.data.Artist;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistDao {

    private final DataSource ds;

    public ArtistDao(DataSource ds) {
        this.ds = ds;
    }

    public List<Artist> findAll() {
        try (Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from \"Artist\"");
            ResultSet resultSet = ps.executeQuery()) {
            List<Artist> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("ArtistId");
                String name = resultSet.getString("Name");
                result.add(new Artist(id, name));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from \"Artist\" where \"ArtistId\" = ?")
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(String name) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "insert into \"Artist\" values (?, ?)")) {
            ps.setInt(1, idAutoIncrement());
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Artist artist) {
        try (Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement("update \"Artist\" set \"Name\" = ? where \"ArtistId\" = ?")
        ) {
            ps.setString(1, artist.getName());
            ps.setInt(2, artist.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Artist findById(int id) {
        try (Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from \"Artist\" where \"ArtistId\" = ?")
        ) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                return new Artist(id, name);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int idAutoIncrement() {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "select max(\"ArtistId\") Id\n from \"Artist\"")) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("Id");
                return ++id;
            } else return -1; // todo broken
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
