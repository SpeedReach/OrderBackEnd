package net.brian.orderbackend;

import net.brian.orderbackend.foods.Food;
import net.brian.orderbackend.kafka.OrderProducer;
import net.brian.orderbackend.login.LoginService;
import net.brian.orderbackend.objects.Order;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientInstance {


    Scanner scanner;
    Console console;
    LoginService loginService;
    OrderProducer orderProducer;

    String user,email;

    public ClientInstance(LoginService loginService, OrderProducer orderProducer){
        try {
            Thread.sleep(1000);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        scanner = new Scanner(System.in);
        console = System.console();
        this.loginService = loginService;
        this.orderProducer = orderProducer;

        enterUsername();
        enterPassword();


        startOrdering();


    }

    private void showFoods(){
        log("======Food======");
        Food.getFoods().forEach(food -> {
            log(food.getId()+" "+food.getPrice()+"$");
        });
        log("================");
    }

    private void startOrdering(){
        showFoods();
        log("\nenter what you want to eat, syntax: \"id amount\"");
        log("enter \"end\" to end ordering");

        Order order = new Order(user,email);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("end")){
            String[] args = input.split(" ");
            if(args.length == 2){
                Food.get(args[0]).ifPresentOrElse(food -> {
                    try {
                        int amount = Integer.parseInt(args[1]);
                        order.addFood(food,amount);
                        log("added "+food.getId() +"x"+amount+" to cart");
                    }catch (NumberFormatException e){
                        log("enter a integer on the second column");
                    }
                },()->{
                    log("can't find food "+args[0]);
                });
            }
            else{
                log("wrong syntax. Syntax: \"id amount\"");
            }
            input = scanner.nextLine();
        }

        log(order.toString());
        log("enter \"confirm\" to send your order，or \"cancel\" to order again");
        input = scanner.nextLine();
        if(input.equalsIgnoreCase("cancel")){
            startOrdering();
        }
        else if(input.equalsIgnoreCase("confirm")){
            orderProducer.sendOrder(order);
            log("order sent successfully，if your email is correct, you will receive a confirm email.");
        }
    }

    private void enterUsername(){
        log("enter your id");
        while (true){
            user = scanner.nextLine();
            if(user.contains(" ")){
                log("id can't contain spaces");
            }
            else {
                break;
            }
        }
    }

    private void enterEmail(){
        log("enter your email");
        while (true){
            email = scanner.nextLine();
            if(email.contains("@")){
                break;
            }
            else{
                log("wrong email syntax");
            }
        }
    }

    private void enterPassword(){
        boolean login = false;
        log("enter your password");
        char[] ch = console.readPassword();
        String password;
        while (!login){
            password = String.valueOf(ch);
            try {
                if(loginService.attemptLogin(user,password).get()){
                    login = true;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }finally {
                if(!login){
                    log("wrong password enter again");
                }
                else{
                    log("login successful");
                }
            }
        }
    }

    private void log(String msg){
        System.out.println(msg);
    }

}
