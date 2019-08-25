package com.concrete.desafio.domain.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class UserService extends AbstractVerticle {

    public static final String USER_SERVICE_MESSAGE = "user_service_op";
    public static final String ACTION_CREATE_USER = "create_user";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.eventBus().consumer(USER_SERVICE_MESSAGE, this::onMessage);
        startPromise.complete();
    }

    private void onMessage(Message<JsonObject> message){
        if (message.headers().contains("action"))
            switch (message.headers().get("action")){
                case ACTION_CREATE_USER:
                    System.out.printf("Message received: \n%s\n", message.body());
                    message.reply(String.format("Message received: \n%s\n", message.body()));
                    break;
            }
        else
            message.fail(1, "Any action header specified");

    }
}
