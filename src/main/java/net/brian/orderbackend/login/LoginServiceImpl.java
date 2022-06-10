package net.brian.orderbackend.login;

import java.util.concurrent.CompletableFuture;

public class LoginServiceImpl implements LoginService {

    public LoginServiceImpl(){

    }


    @Override
    public CompletableFuture<Boolean> attemptLogin(String userID, String password) {
        return CompletableFuture.supplyAsync(()->true);
    }

}
