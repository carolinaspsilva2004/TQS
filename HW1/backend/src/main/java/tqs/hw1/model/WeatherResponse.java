package tqs.hw1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    
    private List<Day> days;
    private currentConditions currentConditions;
    private List<Alert> alerts;
   
    public WeatherResponse() {
    }

    public WeatherResponse(List<Day> days, currentConditions currentConditions, List<Alert> alerts) {
        this.days = days;
        this.currentConditions = currentConditions;
        this.alerts = alerts;
    }

    
    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }


    public currentConditions getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrent(currentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }


    @Override
    public String toString() {
        return "WeatherResponse{" +
                "days=" + days +
                ", currentConditions=" + currentConditions +
                ", alerts=" + alerts +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Day {
        private String datetime;
        private String temp;
        private String conditions;
        private List<Hours> hours;
       
        public Day() {
        }

        public Day(String datetime, String temp, String conditions, List<Hours> hours) {
            this.datetime = datetime;
            this.temp = temp;
            this.conditions = conditions;
            this.hours = hours;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public List<Hours> getHours() {
            return hours;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Hours {
            private String datetime;
            private String temp;
            private String conditions;
    
            public Hours() {
            }
    
            public Hours(String datetime, String temp, String conditions) {
                this.datetime = datetime;
                this.temp = temp;
                this.conditions = conditions;
            }
    
            public String getDatetime() {
                return datetime;
            }
    
            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }
    
            public String getTemp() {
                return temp;
            }
    
            public void setTemp(String temp) {
                this.temp = temp;
            }
    
            public String getConditions() {
                return conditions;
            }
    
            public void setConditions(String conditions) {
                this.conditions = conditions;
            }
    
            @Override
            public String toString() {
                return "Hour{" +
                        "datetime='" + datetime + '\'' +
                        ", temp='" + temp + '\'' +
                        ", conditions='" + conditions + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Day{" +
                    "datetime='" + datetime + '\'' +
                    ", temp='" + temp + '\'' +
                    ", conditions='" + conditions + '\'' +
                    ", hours=" + hours +
                    '}';
        }
        
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class currentConditions {
        private String temp;
        private String conditions;
        private String humidity;

        public currentConditions() {
        }
        

        public currentConditions(String temp, String conditions, String humidity) {
            this.temp = temp;
            this.conditions = conditions;
            this.humidity = humidity;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return "Current{" +
                    "temperature='" + temp + '\'' +
                    ", conditions='" + conditions + '\'' +
                    ", humidity='" + humidity + '\'' +
                    '}';
        }

        
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Alert {
        private String description;
        private String severity;
        private String area;

        public Alert() {
        }

        public Alert(String description, String severity, String area) {
            this.description = description;
            this.severity = severity;
            this.area = area;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        @Override
        public String toString() {
            return "Alert{" +
                    "description='" + description + '\'' +
                    ", severity='" + severity + '\'' +
                    ", area='" + area + '\'' +
                    '}';
        }
        
    }

    }
