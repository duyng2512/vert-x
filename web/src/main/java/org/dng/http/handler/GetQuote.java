package org.dng.http.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.dng.http.object.Quote;

public class GetQuote implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
	String asset = context.pathParam("asset");
 
	Quote resBody = Quote.builder()
	     .volume("102201")
	     .asset(asset)
	     .price("51020")
	     .build();
	context.response()
	     .end(JsonObject.mapFrom(resBody).toBuffer());
     }
}
