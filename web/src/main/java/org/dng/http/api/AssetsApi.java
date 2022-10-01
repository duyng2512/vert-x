package org.dng.http.api;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AssetsApi {
     
     @Builder
     @AllArgsConstructor
     @Getter @Setter
     static class Asset {
	String name;
	int price;
     }
     
     public static void attach(Router router){
	// Get Asset
	router.get("/assets").handler(context -> {
	     JsonArray response = new JsonArray();
	     response.add(new Asset("FLC", 1000))
		   .add(new Asset("GAS", 2000));
	     context.response()
		.end(response.toBuffer());
	});
     }
     
}
