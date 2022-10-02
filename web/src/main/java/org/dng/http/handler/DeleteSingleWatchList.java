package org.dng.http.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import static org.dng.http.api.WatchListApi.WatchListStore;

@Slf4j
public class DeleteSingleWatchList implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
	String accountID = context.pathParam("accountID");
	String delStockName = context.pathParam("stockName");
	handleDeleteSingleStock(context, accountID, delStockName);
     }
     
     private static void handleDeleteSingleStock(RoutingContext context, String accountID, String stockName) {
	if (!WatchListStore.containsKey(accountID)) {
	     context.response()
		.setStatusCode(500)
		.end(JsonObject.of("message", accountID + " not found").toBuffer());
	} else {
	     boolean isRemoved = WatchListStore.get(accountID)
		.removeIf(watchStock -> watchStock.name.equals(stockName));
	     if (isRemoved)
		context.response()
		     .setStatusCode(202)
		     .end();
	     else
		context.response()
		     .setStatusCode(400)
		     .end(JsonObject.of("message", stockName + " not found").toBuffer());
	}
	log.info("Watch list of user {} update to {}", accountID, WatchListStore.get(accountID));
     }
}
