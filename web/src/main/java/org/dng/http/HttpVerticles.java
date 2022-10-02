package org.dng.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.api.AssetsApi;
import org.dng.http.api.ExceptionApi;
import org.dng.http.api.QuotesApi;
import org.dng.http.api.WatchListApi;

@Slf4j
public class HttpVerticles extends AbstractVerticle {
     
     public static final int PORT = 8888;
     
     @Override
     public void start(Promise<Void> startPromise) throws Exception {
	// Router Object
	final Router router = Router.router(vertx);
	
	// Attach to router multiple endpoint
	AssetsApi.attach(router);
	ExceptionApi.attach(router);
	QuotesApi.attach(router);
	WatchListApi.attach(router);
	
	log.info("Init HTTP Server...");
	// Multiple instance will have the same config
	vertx.createHttpServer()
	     .requestHandler(router)
	     .exceptionHandler(err -> log.error("Get Error", err))
	     .listen(PORT,
		http -> {
		     if (http.succeeded()) {
			startPromise.complete();
			System.out.println("HTTP server started on port 8888");
		     } else {
			startPromise.fail(http.cause());
		     }
		}
	);
     }
}