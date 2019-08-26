package com.concrete.desafio.application.web;

import com.concrete.desafio.application.controller.UserController;
import com.concrete.desafio.domain.access_register.service.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> promise) throws Exception {
        vertx.deployVerticle(UserService.class.getName());

        /*
        * Refatoramentos:
        *   Usar logger para registrar os eventos da aplicacao
        *   obter a configuracao de porta apartir das variaveis do ambiente
        */

        int serverPortNumber = 8888;
        Router router = Router.router(vertx);
        router.route()
                .handler(BodyHandler.create());
        this.route(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(serverPortNumber, ar ->{
                    if (ar.succeeded())
                        promise.complete();
                    else
                        promise.fail(ar.cause());
                });
    }

    private void route(Router router){
        new UserController().route(router);
    }
}
