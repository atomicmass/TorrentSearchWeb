package za.co.blacklemon.tv;

import java.io.Serializable;
import java.util.List;

public class SeriesListResult implements Serializable {
    private int count;
    private List<Series> serials;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Series> getSerials() {
        return serials;
    }

    public void setSerials(List<Series> serials) {
        this.serials = serials;
    }
}
