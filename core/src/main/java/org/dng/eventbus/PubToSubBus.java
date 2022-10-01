package org.dng.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PubToSubBus {
     public static final String PUB_SUB_ADDRESS = "PUB_SUB_ADDRESS";
     
     public static void main(String[] args) {
	Vertx vert = Vertx.vertx();
          vert.deployVerticle(new PubVerticles());
          for (int i = 0; i < 3; i++) {
               vert.deployVerticle(new SubVerticles(i));
          }
     }
     
     static class PubVerticles extends AbstractVerticle {
     
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               startPromise.complete();
               vertx.setPeriodic(2000L, action -> {
                    vertx.eventBus().publish(PUB_SUB_ADDRESS, "Sending to all sub");
               });
          }
     }
     
     static class SubVerticles extends AbstractVerticle {
          Integer id;
     
          public SubVerticles(Integer id) {
               this.id = id;
          }
     
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               startPromise.complete();
               vertx.eventBus().<String>consumer(PUB_SUB_ADDRESS, stringMessage -> {
                    System.out.println("Receive " + id + " : "+ stringMessage.body().toUpperCase());
               });
          }
     }
}
