package za.co.blacklemon.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.List;
import za.co.blacklemon.json.JsonMapper;
import za.co.blacklemon.tv.api.ApiEpisode;
import za.co.blacklemon.tv.api.ApiEpisodeResult;
import za.co.blacklemon.tv.api.ApiSeries;
import za.co.blacklemon.tv.api.ApiSeriesListResult;
import za.co.blacklemon.tv.api.ApiSeriesResult;

public class TvService {
    private static TvService _instance;
    private TvService() {}
    
    public static TvService getInstance() {
        if(_instance == null)
            _instance = new TvService();
        
        return _instance;
    }
    
    public List<ApiSeries> search(String searchText) {
        String json = TvRest.getInstance().get(TvRest.SEARCH, searchText);
        
        JsonMapper<ApiSeriesListResult> map = new JsonMapper<>(new TypeReference<ApiSeriesListResult>() {});
        ApiSeriesListResult res = map.mapJason(json);
        List<ApiSeries> series = res.getSerials();
        Collections.sort(series, (s1, s2) -> s1.getTitle().compareTo(s2.getTitle()));

        return series;
    }
    
    public ApiSeries getSeries(String id) {
        String json = TvRest.getInstance().get(TvRest.GET_SERIES, id);
        
        JsonMapper<ApiSeriesResult> map = new JsonMapper<>(new TypeReference<ApiSeriesResult>() {});
        ApiSeriesResult res = map.mapJason(json);
        return res.getSerial();
    }
    
    public ApiEpisode getEpisode(String id) {
        String json = TvRest.getInstance().get(TvRest.GET_SPISODE, id);
        
        JsonMapper<ApiEpisodeResult> map = new JsonMapper<>(new TypeReference<ApiEpisodeResult>() {});
        ApiEpisodeResult res = map.mapJason(json);
        return res.getEp();
    }
}
