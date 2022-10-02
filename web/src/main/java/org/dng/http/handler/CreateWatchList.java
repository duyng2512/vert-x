package org.dng.http.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.object.WatchStock;

import java.util.HashSet;
import java.util.Set;

import static org.dng.http.api.WatchListApi.WatchListStore;

@Slf4j
public class CreateWatchList implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
	String accountID = context.pathParam("accountID");
	WatchStock watchStock = WatchStock.createWatchStock(context.getBodyAsJson());
	
	if (WatchListStore.containsKey(accountID)) WatchListStore.get(accountID).add(watchStock);
	else WatchListStore.put(accountID, new HashSet<>(Set.of(watchStock)));
	
	log.info("Watch list of user {} update to {}", accountID, WatchListStore.get(accountID));
	context.response()
		.setStatusCode(201)
		.end();
     }
}
