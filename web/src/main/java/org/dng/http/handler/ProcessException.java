package org.dng.http.handler;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessException implements Handler<RoutingContext> {
     @Override
     public void handle(RoutingContext context) {
	log.error("Get Exception", context.failure());
	context.response()
	     .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
	     .end(JsonObject.of("message", context.failure().getMessage()).toBuffer());
     }
}
