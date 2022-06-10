package net.brian.orderbackend.objects;

import com.google.gson.Gson;
import net.brian.orderbackend.foods.Food;

import java.util.HashMap;
import java.util.UUID;

public class Order {

    public static final Gson gson = new Gson();

    final String customer_id,email;
    final UUID order_id;
    final HashMap<String,Integer> food = new HashMap<>();
    int price;

    public Order(String customer_id,String email){
        this.customer_id = customer_id;
        order_id = UUID.randomUUID();
        this.email = email;
    }

    public void addFood(Food food,int amount){
        this.food.compute(food.getId(),(k,v)-> ((v==null) ? 0:v) +amount);
        price += food.getPrice()*amount;
    }

    @Override
    public String toString(){
        return gson.toJson(this);
    }

    public UUID getOrder_id() {
        return order_id;
    }

}
