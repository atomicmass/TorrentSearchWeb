package za.co.blacklemon.tv.dao;

import java.io.Serializable;
import java.util.Objects;

public class Episode implements Serializable, Comparable {

    private String id;
    private int season;
    private int episode;
    private String done;
    
    public Episode() {}
    
    public Episode(String id, int season, int episode) {
        this(id, season, episode, "N");
    }

    public Episode(String id, int season, int episode, String done) {
        this.id = id;
        this.season = season;
        this.episode = episode;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String isDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Episode)) {
            return false;
        }
        Episode eo = (Episode) o;
        return this.getSeason() == eo.getSeason() && this.getEpisode() == eo.getEpisode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + this.season;
        hash = 79 * hash + this.episode;
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Episode))
            return -1;
        Episode ot = (Episode)o;
        if(this.getSeason() == ot.getSeason() && this.getEpisode() == ot.getSeason())
            return 0;
        return -1;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
