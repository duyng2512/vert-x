package org.dng.http.api;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.handler.CreateWatchList;
import org.dng.http.handler.DeleteArrWatchList;
import org.dng.http.handler.DeleteSingleWatchList;
import org.dng.http.handler.GetWatchList;
import org.dng.http.object.WatchStock;

import java.util.*;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

@Slf4j
public class WatchListApi {
     
     static public final Map<String, Set<WatchStock>> WatchListStore = new HashMap<>();
     
     public static void attach(Router router){
	get(router);
	post(router);
	deleteSingle(router);
	deleteMultiple(router);
     }
     
     private static void get(Router router) {
	router.get("/watch/:accountID")
	     .handler(new GetWatchList());
     }
     
     private static void post(Router router) {
	router.post("/watch/:accountID")
	     .consumes(APPLICATION_JSON)
	     .handler(BodyHandler.create())
	     .handler(new CreateWatchList());
     }
     
     private static void deleteSingle(Router router) {
	router.delete("/watch/:accountID/:stockName")
	     .handler(BodyHandler.create())
	     .handler(new DeleteSingleWatchList());
     }
     
     private static void deleteMultiple(Router router) {
	router.delete("/watch/:accountID")
	     .handler(BodyHandler.create())
	     .handler(new DeleteArrWatchList());
     }
     
}
