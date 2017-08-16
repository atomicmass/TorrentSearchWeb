package za.co.blacklemon.tv.api;

import java.io.Serializable;
import java.util.List;

public class ApiSeriesListResult implements Serializable {
    private int count;
    private List<ApiSeries> serials;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ApiSeries> getSerials() {
        return serials;
    }

    public void setSerials(List<ApiSeries> serials) {
        this.serials = serials;
    }
}
