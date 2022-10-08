package org.dng.http.api;

import io.vertx.ext.web.Router;
import org.dng.http.handler.GetAsset;

public class AssetsApi {
     public static void attach(Router router){
	// Get Asset
	router.get("/assets").handler(new GetAsset());
     }
     private AssetsApi(){};
}
