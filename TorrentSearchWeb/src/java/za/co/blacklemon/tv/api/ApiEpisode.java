package za.co.blacklemon.tv.api;

import java.io.Serializable;
import java.util.List;

public class ApiEpisode implements Serializable {

    private String id;
    private String season;
    private String ep;
    private String title;
    private List<ApiTorrent> torrent;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<ApiTorrent> getTorrent() {
        return torrent;
    }

    public void setTorrent(List<ApiTorrent> torrent) {
        this.torrent = torrent;
    }
    
    public int getEpAsInt() {
        return Integer.parseInt(ep);
    }
    
    public int getSeasonAsInt() {
        return Integer.parseInt(season);
    }
    
    public ApiTorrent getBestQuality() {
        if(torrent.isEmpty()) return null;
        
        torrent.sort((t1, t2) -> t1.getQuality().compareTo(t2.getQuality()));
        return torrent.get(0);
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
