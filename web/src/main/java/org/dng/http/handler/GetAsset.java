package org.dng.http.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import org.dng.http.object.Asset;

public class GetAsset implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
          JsonArray response = new JsonArray();
          response.add(new Asset("FLC", 1000))
               .add(new Asset("GAS", 2000))
               .add(new Asset("POM", 20100));
          context.response().end(response.toBuffer());
     }
}
