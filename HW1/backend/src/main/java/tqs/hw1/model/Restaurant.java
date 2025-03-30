package tqs.hw1.model;

import jakarta.persistence.*;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String externalMenuUrl;

    public Restaurant() {
    }

    public Restaurant(String name, String externalMenuUrl) {
        this.name = name;
        this.externalMenuUrl = externalMenuUrl;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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


    public void setExternalMenuUrl(String externalMenuUrl) {
        this.externalMenuUrl = externalMenuUrl;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", externalMenuUrl='" + externalMenuUrl + '\'' +
                '}';
    }

}