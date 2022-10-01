package org.dng.vertices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class SupportVertices extends AbstractVerticle {

    static class SupportTraceVertices extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            System.out.println("Support Tracing Deploy !");
            startPromise.complete();
        }
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("Support Vertices Deploy !");
        vertx.deployVerticle(new SupportTraceVertices());
        startPromise.complete();
    }
}
