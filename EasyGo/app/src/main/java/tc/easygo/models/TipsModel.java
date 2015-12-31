package tc.easygo.models;

import java.io.Serializable;

/**
 * Created by ikrom on 12/29/2015.
 */

public class TipsModel implements Serializable {
    private String id;
    private String deskripsi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
