package tqs.hw1.service;

import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExternalMenuService {
    private final MealRepository mealRepository;

    public ExternalMenuService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public void fetchAndSaveMealsForRestaurant(Restaurant restaurant) {
        try {
            Document doc = Jsoup.connect(restaurant.getExternalMenuUrl()).get();
    
            // Obter o nome do restaurante (a partir do título da página)
            String restaurantName = doc.select("h1").text();
            Restaurant fetchedRestaurant = new Restaurant(restaurantName, restaurant.getExternalMenuUrl());
    
            // Selecionar todos os dias
            List<Element> dias = doc.select("table tbody tr");
    
            for (Element dia : dias) {
                // Obter data
                Element dateElement = dia.selectFirst("td a");
                if (dateElement == null) continue;
    
                String dateText = dateElement.text(); // Ex: Segunda 31/03/2025
                String[] parts = dateText.split(" ");
                if (parts.length < 2) continue;
    
                LocalDate date = LocalDate.parse(parts[1], java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    
                // Obter a lista de refeições
                Element mealsList = dia.selectFirst("td ul");
                if (mealsList == null) continue;
    
                for (Element li : mealsList.select("li")) {
                    String description = li.text().trim();
                    if (!description.isEmpty()) {
                        Meal meal = new Meal(description, date, fetchedRestaurant);
                        mealRepository.save(meal);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
