package com.concrete.desafio.application;

import com.concrete.desafio.application.web.MainVerticle;
import io.vertx.core.Vertx;

public class Application {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(MainVerticle.class.getName());
    }
}
