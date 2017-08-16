package za.co.blacklemon.tv.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Series {

    private String id;
    private String title;
    private int startSeason;
    private int startEpisode;
    private List<Episode> episodes;
    
    private Series() {}

    public Series(String id, String title, int startEpisode, int startSeason) {
        this.id = id;
        this.title = title;
        this.startSeason = startSeason;
        this.startEpisode = startEpisode;
    }
    
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

    public int getStartSeason() {
        return startSeason;
    }

    public void setStartSeason(int startSeason) {
        this.startSeason = startSeason;
    }

    public int getStartEpisode() {
        return startEpisode;
    }

    public void setStartEpisode(int startEpisode) {
        this.startEpisode = startEpisode;
    }
    
    public static Series fromApiSeries(za.co.blacklemon.tv.api.ApiSeries series) {
        Series s = new Series();
        s.setId(series.getId());
        s.setTitle(series.getTitle());
        
        s.setEpisodes(new ArrayList<>());
        series.getEp().forEach(e -> {
            Episode ep = new Episode();
            ep.setDone("N");
            ep.setEpisode(Integer.valueOf(e.getEp()));
            ep.setSeason(Integer.valueOf(e.getSeason()));
            
            s.getEpisodes().add(ep);
        });
        
        return s;
    }
    
    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
    
    public boolean mustInclude(int season, int episode) {
        if(season < startSeason)
            return false;
        if(season == startSeason && episode < startEpisode)
            return false;
        return true;
    }
    
    public boolean contains(int season, int episode) {
        if(episodes == null || episodes.isEmpty())
            return false;
        for(Episode e : episodes) {
            if(e.getSeason() == season && e.getEpisode() == episode)
                return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("{id: %s, title: '%s' }", id, title);
    }

    public void addEpisode(String id, int season, int episode) {
        if(mustInclude(season, episode) && !contains(season, episode)) {
            Episode e = new Episode(id, season, episode);
            if(episodes == null)
                episodes = new ArrayList<>();
            episodes.add(e);
        }
    }

    public Iterable<Episode> getUnQueued() {
        return this.getEpisodes().stream()
                .filter(e -> e.isDone().equals("N"))
                .collect(Collectors.toList());
    }
}
