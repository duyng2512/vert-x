package org.dng.vertices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ScalingVertices extends AbstractVerticle {


    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("ScalingVertices Vertices Deploy current on thread " + Thread.currentThread().getName());
        System.out.println("Current Config " + config().getMap());
        startPromise.complete();
    }
}
