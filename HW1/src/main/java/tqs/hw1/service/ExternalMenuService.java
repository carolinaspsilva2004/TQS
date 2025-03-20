package tqs.hw1.service;

import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalMenuService {
    private final MealRepository mealRepository;

    public void fetchAndSaveMealsForRestaurant(Restaurant restaurant) {
        try {
            Document doc = Jsoup.connect(restaurant.getExternalMenuUrl()).get();
            for (Element element : doc.select(".menu-container .day")) {
                String dateStr = element.select(".day-title").text(); // parse date if needed
                String mealDesc = element.select(".menu-item").text();
                Meal meal = new Meal(null, mealDesc, LocalDate.now(), restaurant); // replace with correct date
                mealRepository.save(meal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}