package za.co.blacklemon.tv.api;

import java.io.Serializable;

public class ApiEpisodeResult implements Serializable {

    public ApiEpisode getEp() {
        return ep;
    }

    public void setEp(ApiEpisode ep) {
        this.ep = ep;
    }
    private ApiEpisode ep;
    
}
