package za.co.blacklemon.tv;

import java.io.Serializable;
import java.util.List;

public class Series implements Serializable {

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    private String id;
    private String title;
    private String imdb_id;
    private String start;
    private String end;
    private List<Episode> ep;
    private String minSeason;
    private String maxSeason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public List<Episode> getEp() {
        return ep;
    }

    public void setEp(List<Episode> ep) {
        this.ep = ep;
    }

    public String getMinSeason() {
        if (ep != null && ep.size() > 0) {
            return ep.get(0).getSeason();
        }
        return "00";
    }

    public String getMaxSeason() {
        if (ep != null && ep.size() > 0) {
            return ep.get(ep.size() - 1).getSeason();
        }
        return "00";
    }

}
