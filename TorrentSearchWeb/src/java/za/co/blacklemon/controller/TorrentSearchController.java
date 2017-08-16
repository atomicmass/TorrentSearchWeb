package za.co.blacklemon.controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import za.co.blacklemon.rest.TvService;
import za.co.blacklemon.tv.api.ApiSeries;
import za.co.blacklemon.tv.database.SeriesDb;
 
@ManagedBean
@ViewScoped
public class TorrentSearchController extends ControllerBase {
    private String searchText;
    private List<ApiSeries> series;
    private ApiSeries selectedSeries;
    private int startSeason;
    private int startEpisode;
    
    public void searchButtonAction(ActionEvent actionEvent) {
        series = TvService.getInstance().search(searchText);
        super.addMessage(String.format("%d series returned", series.size()));
    }
    
    public void saveButtonAction(ActionEvent actionEvent) {
        SeriesDb.getInstance().insert(selectedSeries, startSeason, startEpisode);
    }
    
    public void onRowSelect(SelectEvent event) {
        String id = ((ApiSeries) event.getObject()).getId();
        selectedSeries = TvService.getInstance().getSeries(id);
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<ApiSeries> getSeries() {
        return series;
    }

    public void setSeries(List<ApiSeries> series) {
        this.series = series;
    }

    public ApiSeries getSelectedSeries() {
        return selectedSeries;
    }

    public void setSelectedSeries(ApiSeries selectedSeries) {
        this.selectedSeries = selectedSeries;
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

}
