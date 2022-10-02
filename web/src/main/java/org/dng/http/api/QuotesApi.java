package org.dng.http.api;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class QuotesApi {
     
     @Builder
     @AllArgsConstructor
     @Getter @Setter
     static class Quote {
	String price;
	String asset;
	String volume;
     }
     
     public static void attach(Router router){
	// Get Asset
	router.get("/quotes/:asset").handler(context -> {
	     String asset = context.pathParam("asset");
	     
	     Quote resBody = Quote.builder()
			.volume("102201")
			.asset(asset)
			.price("51020")
			.build();
	     context.response()
		.end(JsonObject.mapFrom(resBody).toBuffer());
	});
     }
     
}
