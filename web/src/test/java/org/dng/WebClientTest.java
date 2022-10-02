package org.dng;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jnr.constants.platform.INAddr;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.HttpVerticles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@ExtendWith(VertxExtension.class)
class WebClientTest {
     
     @BeforeEach
     void setUp (Vertx vertx, VertxTestContext context) {
          vertx.deployVerticle(new BoostrapVerticles())
               .onSuccess(s -> log.info("Success Deploy Verticles"))
               .andThen(r -> context.completeNow());
     }
     
     @Test
     void returnAllAssets (Vertx vertx, VertxTestContext context) {
          WebClientOptions options = new WebClientOptions();
          options.setDefaultPort(HttpVerticles.PORT);

          var client = WebClient.create(vertx, options);
          client.get("/assets")
               .send()
               .onComplete(context.succeeding(res -> {
                    var json = res.bodyAsJsonArray();
                    log.info("Res {}", json);
                    
                    Assertions.assertNotNull(json);
                    Assertions.assertEquals("[{\"name\":\"FLC\",\"price\":1000},{\"name\":\"GAS\",\"price\":2000}]", json.encode());
                    Assertions.assertEquals(200, res.statusCode());
                    Assertions.assertEquals(2, res.bodyAsJsonArray().getList().size());
                    
                    context.completeNow();
               }));
     }
     
     @Test
     void returnQuote(Vertx vertx, VertxTestContext context) {
          WebClientOptions options = new WebClientOptions();
          options.setDefaultPort(HttpVerticles.PORT);
          
          WebClient webClient = WebClient.create(vertx, options);
          
          webClient.get("/quotes/AMZ")
               .send()
               .onComplete(context.succeeding(res -> {
                    var json = res.bodyAsJsonObject();
                    Assertions.assertEquals("{\"price\":\"51020\",\"asset\":\"AMZ\",\"volume\":\"102201\"}", json.encode());
                    Assertions.assertEquals(200, res.statusCode());
                    context.completeNow();
               }));
     }
}