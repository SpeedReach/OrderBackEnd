import net.brian.orderbackend.foods.Food;
import net.brian.orderbackend.kafka.OrderProducer;
import net.brian.orderbackend.login.LoginService;
import net.brian.orderbackend.login.LoginServiceImpl;
import net.brian.orderbackend.ClientInstance;

public class Main {

    public static void main(String[] args){


        new Food("hamburger",20);
        new Food("cola",5);
        new Food("french_fries",15);
        LoginService loginService = new LoginServiceImpl();
        OrderProducer orderProducer = new OrderProducer();
        new ClientInstance(loginService,orderProducer);
    }

}
