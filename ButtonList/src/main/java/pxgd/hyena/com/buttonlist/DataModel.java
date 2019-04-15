package pxgd.hyena.com.buttonlist;

/**
 * Created by juan.brusco on 22-May-17.
 */

public class DataModel {

    String name;
    String description;
    String file;

    public DataModel(String name, String description, String file) {
        this.name = name;
        this.description = description;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
