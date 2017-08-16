package za.co.blacklemon.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import za.co.blacklemon.rest.TvService;
import za.co.blacklemon.tv.Series;
 
@ManagedBean
@ViewScoped
public class TorrentSearch implements Serializable {
    private String searchText;
    private List<Series> series;
    private Series selectedSeries;
    
    public void searchButtonAction(ActionEvent actionEvent) {
        search();
    }
    
    public void onRowSelect(SelectEvent event) {
        String id = ((Series) event.getObject()).getId();
        getDetails(id);
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    private void search() {
        series = TvService.getInstance().search(searchText);
        addMessage(String.format("%d series returned", series.size()));
    }

    public Series getSelectedSeries() {
        return selectedSeries;
    }

    public void setSelectedSeries(Series selectedSeries) {
        this.selectedSeries = selectedSeries;
    }

    private void getDetails(String id) {
        selectedSeries = TvService.getInstance().getSeries(id);
    }

}
