package org.dng.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PointToPointBus {
     public static final String POINT_ADDRESS = "POINT.POINT.ADDRESS";
     
     public static void main(String[] args) {
	Vertx vert = Vertx.vertx();
          vert.deployVerticle(new SenderPointVerticles());
          vert.deployVerticle(new ReceiverPointVerticles());
     }
     
     static class SenderPointVerticles extends AbstractVerticle {
     
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               startPromise.complete();
               vertx.setPeriodic(1000L, id -> {
                    vertx.eventBus().send(POINT_ADDRESS, "Message " + id);
               });
          }
     }
     
     static class ReceiverPointVerticles extends AbstractVerticle {
          
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               startPromise.complete();
               vertx.eventBus().<String>consumer(POINT_ADDRESS, stringMessage -> {
                    System.out.println("Receive :" + stringMessage.body().toUpperCase());
               });
          }
     }
}
