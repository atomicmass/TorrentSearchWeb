package za.co.blacklemon.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import za.co.blacklemon.json.JsonMapper;
import za.co.blacklemon.tv.Series;
import za.co.blacklemon.tv.SeriesListResult;
import za.co.blacklemon.tv.SeriesResult;

public class TvService {
    private static TvService _instance;
    private TvService() {}
    
    public static TvService getInstance() {
        if(_instance == null)
            _instance = new TvService();
        
        return _instance;
    }
    
    public List<Series> search(String searchText) {
        String json = TvRest.getInstance().get(TvRest.SEARCH, searchText);
        
        JsonMapper<SeriesListResult> map = new JsonMapper<>(new TypeReference<SeriesListResult>() {});
        SeriesListResult res = map.mapJason(json);
        return res.getSerials();
    }
    
    public Series getSeries(String id) {
        String json = TvRest.getInstance().get(TvRest.GET_SERIES, id);
        
        JsonMapper<SeriesResult> map = new JsonMapper<>(new TypeReference<SeriesResult>() {});
        SeriesResult res = map.mapJason(json);
        return res.getSerial();
    }
}
