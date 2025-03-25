package tqs.hw1.model;

import jakarta.persistence.*;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String externalMenuUrl;

    public Restaurant() {
    }

    public Restaurant(String name, String location, String externalMenuUrl) {
        this.name = name;
        this.location = location;
        this.externalMenuUrl = externalMenuUrl;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getExternalMenuUrl() {
        return externalMenuUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExternalMenuUrl(String externalMenuUrl) {
        this.externalMenuUrl = externalMenuUrl;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", externalMenuUrl='" + externalMenuUrl + '\'' +
                '}';
    }

}