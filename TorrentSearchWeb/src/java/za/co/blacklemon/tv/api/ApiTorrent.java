package za.co.blacklemon.tv.api;

import java.io.Serializable;

public class ApiTorrent implements Serializable {
    private int id;
    private String title;
    private String value;
    private String url;
    private ApiLanguage lang;
    private ApiQuality quality;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ApiLanguage getLang() {
        return lang;
    }

    public void setLang(ApiLanguage lang) {
        this.lang = lang;
    }

    public ApiQuality getQuality() {
        return quality;
    }

    public void setQuality(ApiQuality quality) {
        this.quality = quality;
    }
}
