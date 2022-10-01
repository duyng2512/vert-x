package org.dng.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.api.AssetsApi;
import org.dng.http.api.ExceptionApi;

@Slf4j
public class HttpVerticles extends AbstractVerticle {
     
     @Override
     public void start(Promise<Void> startPromise) throws Exception {
	// Router Object
	final Router router = Router.router(vertx);
	
	// Attach to router multiple endpoint
	AssetsApi.attach(router);
	ExceptionApi.attach(router);
	
	
	log.info("Init HTTP Server...");
	// Multiple instance will have the same config
	vertx.createHttpServer()
	     .requestHandler(router)
	     .exceptionHandler(err -> log.error("Get Error", err))
	     .listen(8888,
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