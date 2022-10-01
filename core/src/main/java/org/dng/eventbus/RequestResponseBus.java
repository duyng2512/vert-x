package org.dng.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public class RequestResponseBus {
     public static final String REQ_RES_ADDRESS = "REQ.RES.ADDRESS";
     
     public static void main(String[] args) {
	Vertx vert = Vertx.vertx();
          vert.deployVerticle(new RequestVerticle());
          vert.deployVerticle(new ResponseVerticle());
     }
     
     static class RequestVerticle extends AbstractVerticle {
          
     
          @Override
          public void start(Promise<Void> startPromise) throws Exception {
               startPromise.complete();
               System.out.println("Sending to " + REQ_RES_ADDRESS);
               vertx.eventBus().<String>request(REQ_RES_ADDRESS, "Request Message", reply -> {
                    System.out.println("Receive reply " + reply.result().body().toUpperCase());
               } );
          }
     }
     
     static class ResponseVerticle extends AbstractVerticle {
          @Override
          public void start(Promise<Void> startPromise) {
               startPromise.complete();
               vertx.eventBus().<String>consumer(REQ_RES_ADDRESS, message -> {
                    System.out.println("Receive message " + message.body().toUpperCase());
                    message.reply("Ack");
               });
          }
     }
}
