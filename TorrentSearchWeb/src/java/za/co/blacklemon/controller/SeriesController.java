package za.co.blacklemon.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import za.co.blacklemon.SeriesDownloader;
import za.co.blacklemon.tv.dao.Series;

@ManagedBean
@RequestScoped
public class SeriesController extends ControllerBase {
private List<Series> series;
    
    @PostConstruct
    public void init() {
        processSeries();
    }
    
    private void processSeries() {
        setSeries(SeriesDownloader.getInstance().downloadSeries());
        super.addMessage(String.format("%d series returned", series.size()));
    }
    
    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
