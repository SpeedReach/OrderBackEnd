package net.brian.orderbackend.foods;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Food {

    private static final List<Food> foods = new ArrayList<>();

    final int price;
    final String id;

    public Food(String id, int price){
        this.id = id;
        this.price = price;
        foods.add(this);
    }

    public static List<Food> getFoods(){
        return new ArrayList<>(foods);
    }

    public static Optional<Food> get(String id){
        for (Food food : foods) {
            if(food.id.equalsIgnoreCase(id)){
                return Optional.of(food);
            }
        }
        return Optional.empty();
    }

    public int getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

}
