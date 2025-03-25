package tqs.hw1.dto;


public class RestaurantDTO {
    private Long id;
    private String name;
    private String externalMenuUrl;

    public RestaurantDTO() {
    }

    public RestaurantDTO(Long id, String name, String externalMenuUrl) {
        this.id = id;
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
        return "RestaurantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", externalMenuUrl='" + externalMenuUrl + '\'' +
                '}';
    }
    
}