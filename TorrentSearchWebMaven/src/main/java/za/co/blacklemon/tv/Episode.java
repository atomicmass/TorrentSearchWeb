package za.co.blacklemon.tv;

import java.io.Serializable;
import java.util.List;

public class Episode implements Serializable {
    private int id;
    private String season;
    private String ep;
    private String title;
    private List<Torrent> torrent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Torrent> getTorrent() {
        return torrent;
    }

    public void setTorrent(List<Torrent> torrent) {
        this.torrent = torrent;
    }
}
