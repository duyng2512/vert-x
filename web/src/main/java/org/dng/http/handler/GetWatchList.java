package org.dng.http.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import static org.dng.http.api.WatchListApi.WatchListStore;

public class GetWatchList implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
	String accountID = context.pathParam("accountID");
	JsonArray resBody = JsonArray.of(WatchListStore.get(accountID));
	context.response()
	     .end(resBody.toBuffer());
     }
}
