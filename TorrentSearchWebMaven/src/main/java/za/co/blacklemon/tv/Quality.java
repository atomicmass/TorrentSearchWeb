package za.co.blacklemon.tv;

import java.io.Serializable;

public class Quality implements Serializable {
    private int id;
    private int quality_group_id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuality_group_id() {
        return quality_group_id;
    }

    public void setQuality_group_id(int quality_group_id) {
        this.quality_group_id = quality_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
