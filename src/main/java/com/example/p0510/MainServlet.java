package com.example.p0510;

import com.example.p0510.beans.AlbumBean;
import com.example.p0510.beans.ArtistBean;
import com.example.p0510.dao.AlbumDao;
import com.example.p0510.dao.ArtistDao;
import com.example.p0510.data.Album;
import com.example.p0510.data.Artist;

import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "mainServlet", value = {
        "/show_artists", "/delete_artist" , "/add_artist",
        "/edit_artist", "/update_artist", "/show_albums_of"})
public class MainServlet extends HttpServlet {

    @Resource(name = "jdbc/chinook")
    private DataSource ds;

    private ArtistDao artistDao;
    private AlbumDao albumDao;

    public void init() {
        artistDao = new ArtistDao(ds);
        albumDao = new AlbumDao(ds);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/show_artists")) {
            showAll(request, response);
        } else if (uri.endsWith("/delete_artist")) {
            deleteArtist(request, response);
        } else if (uri.endsWith("/add_artist")) {
            addArtist(request, response);
        } else if (uri.endsWith("/edit_artist")) {
            editArtist(request, response);
        } else if (uri.endsWith("/update_artist")) {
            updateArtist(request, response);
        } else if (uri.endsWith("/show_albums_of")) {
            showAlbumsByArtist(request, response);
        }
    }

    private void showAlbumsByArtist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int artistId = Integer.parseInt(request.getParameter("id"));
        Artist artist = artistDao.findById(artistId);
        List<Album> albums = albumDao.findByArtistId(artistId);
        AlbumBean albumBean = new AlbumBean(artist, albums);
        request.setAttribute("albumBean", albumBean);
        request.getRequestDispatcher("/albums_by_artist.jsp").forward(request, response);
    }

    private void updateArtist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        artistDao.update(new Artist(id, name));
        response.sendRedirect("show_artists");
    }

    private void editArtist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Artist artist = artistDao.findById(id);
        if (artist == null) {
            request.setAttribute("artist_id", id);
            request.getRequestDispatcher("/artist_not_found.jsp").forward(request, response);
        } else {
            request.setAttribute("artist", artist);
            request.getRequestDispatcher("/edit_artist.jsp").forward(request, response);
        }
    }

    private void addArtist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        artistDao.add(request.getParameter("artistname"));
        response.sendRedirect("show_artists");
    }

    private void deleteArtist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        artistDao.delete(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("show_artists");
    }

    private void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Artist> artists = artistDao.findAll();
        request.setAttribute("artistBean", new ArtistBean(artists));
        request.getRequestDispatcher("/artists.jsp").forward(request, response);
    }

    public void destroy() {
    }
}