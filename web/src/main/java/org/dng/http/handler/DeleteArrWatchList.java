package org.dng.http.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import static org.dng.http.api.WatchListApi.WatchListStore;

@Slf4j
public class DeleteArrWatchList implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
	String accountID = context.pathParam("accountID");
	JsonArray delStockList = context.getBodyAsJsonArray();
	handleDeleteMultipleStock(context, accountID, delStockList);
     }
     
     private static void handleDeleteMultipleStock(RoutingContext context, String accountID, JsonArray listStock) {
	if (!WatchListStore.containsKey(accountID)) {
	     context.response()
		.setStatusCode(500)
		.end(JsonObject.of("message", accountID + " not found").toBuffer());
	} else {
	     boolean isRemoved = WatchListStore.get(accountID)
		.removeIf(watchStock -> listStock.getList().contains(watchStock.name));
	     if (isRemoved)
		context.response()
		     .setStatusCode(202)
		     .end();
	     else
		context.response()
		     .setStatusCode(400)
		     .end(JsonObject.of("message", " stock not found").toBuffer());
	}
	log.info("Watch list of user {} update to {}", accountID, WatchListStore.get(accountID));
     }
}
