package com.concrete.desafio.application.controller;

import com.concrete.desafio.application.web.payloads.UserCreation;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import static com.concrete.desafio.domain.access_register.service.UserService.ACTION_CREATE_USER;
import static com.concrete.desafio.domain.access_register.service.UserService.USER_SERVICE_MESSAGE;

public class UserController implements RestController {

    public void createUser(RoutingContext rc){
        UserCreation newUser = Json.decodeValue(rc.getBodyAsString(), UserCreation.class);

        //TODO send a message on the event bus to the service
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("action", ACTION_CREATE_USER);

        rc.vertx()
                .eventBus()
                .request(USER_SERVICE_MESSAGE, Json.encode(newUser), options, reply -> {
                    if (reply.succeeded()){
                        rc.response()
                                .putHeader("content-type", "application/json")
                                .end(reply.result().body().toString());
                    } else {
                        rc.response()
                                .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                                .end(Json.encode(reply.cause()));
                    }
                });
    }

    @Override
    public void route(Router router) {
        router.get("/").handler(rc -> {
            rc.response()
                    .putHeader("content-type", "text/plain")
                    .end("server's waiting for connections...");
        });

        router.post("/usuario").handler(rc -> {
            this.createUser(rc);
        });
    }
}
