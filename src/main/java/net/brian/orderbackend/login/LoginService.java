package net.brian.orderbackend.login;

import java.util.concurrent.CompletableFuture;

public interface LoginService {

    CompletableFuture<Boolean> attemptLogin(String userID,String password);

}

