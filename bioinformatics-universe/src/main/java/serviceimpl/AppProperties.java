package serviceimpl;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("serviceimpl")
public class AppProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}