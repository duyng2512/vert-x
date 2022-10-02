package org.dng;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.HttpVerticles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;


@Slf4j
class MapTest {
     
     @Test
     public void testHashMap() {
          Map<Integer, List<String>> map = new HashMap<>();
          for (int i = 0; i < 2; i++) {
               if (map.containsKey(1000))
                    map.get(1000).add("TEST");
               else
                    map.putIfAbsent(1000, new ArrayList<>(List.of("HELLO")));
          }
          System.out.println(map);
     }
}