package za.co.blacklemon.tv.database;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.blacklemon.tv.api.ApiSeries;
import za.co.blacklemon.tv.dao.Episode;
import za.co.blacklemon.tv.dao.Series;

public class SeriesDb {

    private static SeriesDb _instance;
    private static final String SERVER = "MYSQL";

    private SeriesDb() {
    }

    private Connection getConnection() {
        String con = SERVER.equals("MYSQL") ? "jdbc:mysql://localhost:3306/Torrent?autoReconnect=true&useSSL=false" : "jdbc:sqlserver://localhost:1433;databaseName=Torrent";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(con, "torrent", "torrent");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static SeriesDb getInstance() {
        if (_instance == null) {
            _instance = new SeriesDb();
        }
        return _instance;
    }

    public void insert(ApiSeries apiSeries, int startSeason, int startEpisode) {
        Series s = Series.fromApiSeries(apiSeries);
        s.setStartEpisode(startEpisode);
        s.setStartEpisode(startEpisode);

        String sql = "insert into Series(seriesId, title, startSeason, startEpisode) values(?, ?, ?, ?)";

        try (Connection con = getConnection();
                PreparedStatement stm = con.prepareStatement(sql);) {
            stm.setString(1, apiSeries.getId());
            stm.setString(2, apiSeries.getTitle());
            stm.setInt(3, startSeason);
            stm.setInt(4, startEpisode);

            stm.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Series series) {
        updateSeries(series);
        series.getEpisodes().forEach(e -> saveEpisode(series.getId(), e));
    }

    public List<Series> getAllSeries() {
        String sql = "select seriesId, title, startSeason, startEpisode from Series";

        List<Series> series = new ArrayList<>();

        try (
                Connection con = getConnection();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sql);) {
            while (rs.next()) {

                String id = rs.getString("seriesId");
                String title = rs.getString("title");
                int startSeason = rs.getInt("startSeason");
                int startEpisode = rs.getInt("startEpisode");

                Series s = new Series(id, title, startEpisode, startSeason);
                s.setEpisodes(getEpisodes(s.getId()));
                series.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }

        return series;
    }

    public List<Episode> getEpisodes(String seriesId) {
        String sql = "select episodeId, season, episode, done from Episode where seriesId = ?";
        List<Episode> episodes = new ArrayList<>();

        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setString(1, seriesId);
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                String id = rs.getString("episodeId");
                int season = rs.getInt("season");
                int episode = rs.getInt("episode");
                String done = rs.getString("done");
                
                Episode e = new Episode(id, season, episode, done);
                episodes.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return episodes;
    }

    private void updateSeries(Series series) {
        String sql = "update Series set title = ?, startSeason = ?, startEpisode = ? where seriesId = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setString(1, series.getTitle());
            statement.setInt(2, series.getStartSeason());
            statement.setInt(3, series.getStartEpisode());
            statement.setString(4, series.getId());
            
            statement.executeUpdate();
        }
        catch(SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEpisodes(String id) {
        String sql = "delete from Episode where seriesId = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setString(1, id);
            statement.executeUpdate();
        }
        catch(SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveEpisode(String seriesId, Episode e) {
        deleteEpisode(e.getId());
        String sql = "insert into Episode(episodeId, seriesId, season, episode, done) values(?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql)) {
            System.out.println(String.format("Insert Episode: %s", e.getId()));
            statement.setString(1, e.getId());
            statement.setString(2, seriesId);
            statement.setInt(3, e.getSeason());
            statement.setInt(4, e.getEpisode());
            statement.setString(5, e.isDone());
            
            statement.executeUpdate();
        }
        catch(SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteEpisode(String id) {
        String sql = "delete from Episode where episodeId = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setString(1, id);
            statement.executeUpdate();
        }
        catch(SQLException ex) {
            Logger.getLogger(SeriesDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
