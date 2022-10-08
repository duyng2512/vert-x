package org.dng.http.api;

import io.vertx.ext.web.Router;
import org.dng.http.handler.GetQuote;

public class QuotesApi {
     public static void attach(Router router){
	router.get("/quotes/:asset").handler(new GetQuote());
     }
     private QuotesApi(){}
}
