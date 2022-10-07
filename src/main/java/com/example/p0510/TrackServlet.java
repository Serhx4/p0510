package com.example.p0510;

import com.example.p0510.beans.EditTrackBean;
import com.example.p0510.beans.TrackBean;
import com.example.p0510.dao.AlbumDao;
import com.example.p0510.dao.GenreDao;
import com.example.p0510.dao.MediaTypeDao;
import com.example.p0510.dao.TrackDao;
import com.example.p0510.data.Album;
import com.example.p0510.data.Genre;
import com.example.p0510.data.MediaType;
import com.example.p0510.data.Track;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "trackServlet", value = { "/show_tracks_of", "/edit_track", "/update_track",
        "/add_track", "/create_track", "/delete_track"
})
public class TrackServlet extends HttpServlet {

    @Resource(name = "jdbc/chinook")
    private DataSource ds;
    private TrackDao trackDao;
    private AlbumDao albumDao;
    private GenreDao genreDao;
    private MediaTypeDao mediaTypeDao;

    @Override
    public void init() throws ServletException {
        trackDao = new TrackDao(ds);
        albumDao = new AlbumDao(ds);
        genreDao = new GenreDao(ds);
        mediaTypeDao = new MediaTypeDao(ds);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/show_tracks_of")) {
            showTracksByAlbum(request, response);
        } else if (uri.endsWith("/edit_track")) {
            editTrack(request, response);
        } else if (uri.endsWith("/update_track")) {
            updateTrack(request, response);
        } else if (uri.endsWith("/add_track")) {
            addTrack(request, response);
        } else if (uri.endsWith("/create_track")) {
            createTrack(request, response);
        } else if (uri.endsWith("/delete_track")) {
            deleteTrack(request, response);
        }
    }

    private void deleteTrack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int trackId = Integer.parseInt(request.getParameter("trackId"));
        int albumId = Integer.parseInt(request.getParameter("albumId"));
        trackDao.delete(trackId);
        response.sendRedirect("show_tracks_of?id=" + albumId);
    }

    private void createTrack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int trackId = Integer.parseInt(request.getParameter("trackId"));
        String name = request.getParameter("trackName");
        int albumId = Integer.parseInt(request.getParameter("album"));
        int mediaTypeId = Integer.parseInt(request.getParameter("mediaType"));
        int genreId = Integer.parseInt(request.getParameter("genre"));
        String composer = request.getParameter("composer");
        int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
        int bytes = Integer.parseInt(request.getParameter("bytes"));
        double unitPrice = Double.parseDouble(request.getParameter("price"));

        trackDao.add(new Track(trackId,name,albumId,mediaTypeId,genreId,composer,milliseconds,bytes,unitPrice));

        response.sendRedirect("show_tracks_of?id=" + albumId);
    }

    private void addTrack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int albumId = Integer.parseInt(request.getParameter("albumId"));

        Track track = new Track(-1, "",albumId,1,1,"",0,0,0);
        List<Genre> genres = genreDao.findAll();
        List<MediaType> types = mediaTypeDao.findAll();
        List<Album> albums = albumDao.findAll();
        EditTrackBean editTrackBean =
                new EditTrackBean(track, genres, types, albums, "Create new Track", "create_track");
        request.setAttribute("editTrackBean", editTrackBean);
        request.getRequestDispatcher("/edit_track.jsp").forward(request,response);
    }

    private void updateTrack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int trackId = Integer.parseInt(request.getParameter("trackId"));
        String name = request.getParameter("trackName");
        int albumId = Integer.parseInt(request.getParameter("album"));
        int mediaTypeId = Integer.parseInt(request.getParameter("mediaType"));
        int genreId = Integer.parseInt(request.getParameter("genre"));
        String composer = request.getParameter("composer");
        int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
        int bytes = Integer.parseInt(request.getParameter("bytes"));
        double unitPrice = Double.parseDouble(request.getParameter("price"));

        trackDao.update(new Track(trackId,name,albumId,mediaTypeId,genreId,composer,milliseconds,bytes,unitPrice));

        response.sendRedirect("show_tracks_of?id=" + albumId);
    }

    private void editTrack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int trackId = Integer.parseInt(request.getParameter("id"));
        Track track = trackDao.findById(trackId);
        List<Genre> genres = genreDao.findAll();
        List<MediaType> types = mediaTypeDao.findAll();
        List<Album> albums = albumDao.findAll();
        EditTrackBean editTrackBean =
                new EditTrackBean(track,genres, types,albums, "Update", "update_track");
        request.setAttribute("editTrackBean", editTrackBean);
        request.getRequestDispatcher("/edit_track.jsp").forward(request,response);
    }

    private void showTracksByAlbum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int albumId = Integer.parseInt(request.getParameter("id"));
        Album album = albumDao.findById(albumId);
        List<Track> tracks = trackDao.findByAlbum(albumId);
        List<Genre> genres = genreDao.findAll();
        TrackBean trackBean = new TrackBean(album, tracks, genres);
        request.setAttribute("trackBean", trackBean);
        request.getRequestDispatcher("/tracks_by_album.jsp").forward(request,response);
    }
}
