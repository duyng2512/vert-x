package org.dng;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVertices());
    }
}