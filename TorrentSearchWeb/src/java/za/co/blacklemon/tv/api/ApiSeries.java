package za.co.blacklemon.tv.api;

import java.io.Serializable;
import java.util.List;

public class ApiSeries implements Serializable {

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
    private List<ApiEpisode> ep;

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

    public List<ApiEpisode> getEp() {
        return ep;
    }

    public void setEp(List<ApiEpisode> ep) {
        this.ep = ep;
    }
    
    public ApiEpisode getEpisode(int season, int episode) {
        return ep.stream().filter(e -> e.getSeasonAsInt() == season && e.getEpAsInt() == episode).findFirst().get();
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
