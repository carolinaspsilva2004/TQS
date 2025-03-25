package tqs.hw1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WeatherResponse {
    private List<Day> days;
    private List<Event> events;
    private Current current;
    private List<Alert> alerts;
    private List<Hour> hours;

    public WeatherResponse() {
    }

    public WeatherResponse(List<Day> days, List<Event> events, Current current, List<Alert> alerts, List<Hour> hours) {
        this.days = days;
        this.events = events;
        this.current = current;
        this.alerts = alerts;
        this.hours = hours;
    }

    public WeatherResponse(List<Event> events, Current current, List<Hour> hours) {
        this.events = events;
        this.current = current;
        this.hours = hours;
    }

    public WeatherResponse(List<Day> days, List<Event> events, Current current) {
        this.days = days;
        this.events = events;
        this.current = current;
    }

    public WeatherResponse(List<Day> days, List<Event> events, Current current, List<Alert> alerts) {
        this.days = days;
        this.events = events;
        this.current = current;
        this.alerts = alerts;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "days=" + days +
                ", events=" + events +
                ", current=" + current +
                ", alerts=" + alerts +
                ", hours=" + hours +
                '}';
    }

    public static class Day {
        private String datetime;
        private String temp;
        private String conditions;

        public Day() {
        }

        public Day(String datetime, String temp, String conditions) {
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
            return "Day{" +
                    "datetime='" + datetime + '\'' +
                    ", temp='" + temp + '\'' +
                    ", conditions='" + conditions + '\'' +
                    '}';
        }
        
        // Getters and Setters
    }

    public static class Event {
        private String datetime;
        private String description;

        public Event() {
        }   

        public Event(String datetime, String description) {
            this.datetime = datetime;
            this.description = description;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "datetime='" + datetime + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public static class Current {
        private String temperature;
        private String conditions;
        private String humidity;

        public Current() {
        }

        public Current(String temperature, String conditions, String humidity) {
            this.temperature = temperature;
            this.conditions = conditions;
            this.humidity = humidity;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
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
                    "temperature='" + temperature + '\'' +
                    ", conditions='" + conditions + '\'' +
                    ", humidity='" + humidity + '\'' +
                    '}';
        }

        
    }

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

    public static class Hour {
        private String datetime;
        private String temp;
        private String conditions;

        public Hour() {
        }

        public Hour(String datetime, String temp, String conditions) {
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
}
