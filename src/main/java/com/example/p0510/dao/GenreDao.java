package com.example.p0510.dao;

import com.example.p0510.data.Genre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {
    private final DataSource ds;

    public GenreDao(DataSource ds) {
        this.ds = ds;
    }

    public List<Genre> findAll() {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from \"Genre\"");) {

            return genreListFromStatement(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Genre findById(int genreId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps
                     = connection.prepareStatement("select * from \"Genre\" where \"GenreId\" = ?")) {

            ps.setInt(1, genreId);
            try(ResultSet resultSet = ps.executeQuery()) {
                if(resultSet.next()) {
                    return genreFromResult(resultSet);
                } else return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Genre> genreListFromStatement(PreparedStatement ps) {
        try (ResultSet resultSet = ps.executeQuery()) {

            List<Genre> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(genreFromResult(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Genre genreFromResult(ResultSet resultSet) {
        try {
            int genreId = resultSet.getInt("GenreId");
            String name = resultSet.getString("Name");

            return new Genre(genreId, name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
