package org.dng.codec;

import io.vertx.core.*;
import lombok.Builder;
import lombok.ToString;

public class PingPongVerticles {
     
     @Builder
     @ToString
     static class Ping {
          String message;
          Boolean enabled;
     }
     @Builder
     @ToString
     static class Pong {
          String message;
          Boolean ack;
     }
     public static final String PING_PONG_ADDRESS = "PING.PONG.ADDRESS";
     
     public static void main(String[] args) {
	Vertx vert = Vertx.vertx();
          vert.deployVerticle(new PingVerticles(), loggingError());
          vert.deployVerticle(new PongVerticles(), loggingError());
     }
     
     private static Handler<AsyncResult<String>> loggingError() {
          return ar -> {
               if (ar.failed()) {
                    System.out.println("Err " + ar);
               }
          };
     }
     
     static class PingVerticles extends AbstractVerticle {
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               Ping ping = Ping.builder().enabled(true).message("ping !").build();
               System.out.println("Sending " + ping );
     
               vertx.eventBus().registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
               vertx.eventBus().<Pong>request(PING_PONG_ADDRESS, ping, (res) -> {
                    if (res.failed()){
                         System.err.println("Error " + res.cause().getMessage());
                    }
                    System.out.println("Receive " + res.result().body().toString());
               });
               startPromise.complete();
          }
     }
     
     static class PongVerticles extends AbstractVerticle {
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               Pong pong = Pong.builder().ack(true).message("Pong").build();
     
               vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
               vertx.eventBus().<Ping>consumer(PING_PONG_ADDRESS, message -> {
                    System.out.println("Receive :" + message.body());
                    message.reply(pong);
               }).exceptionHandler(error -> {
                    System.err.println("Err " + error.getMessage());
               });
               startPromise.complete();
          }
     }
}
