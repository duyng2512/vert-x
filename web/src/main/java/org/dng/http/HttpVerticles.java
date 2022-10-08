package org.dng.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.api.AssetsApi;
import org.dng.http.api.ExceptionApi;
import org.dng.http.api.QuotesApi;
import org.dng.http.api.WatchListApi;
import org.dng.http.config.BrokerConfig;
import org.dng.http.config.ConfigLoader;

@Slf4j
public class HttpVerticles extends AbstractVerticle {
     
     public static final int PORT = 8888;
     
     @Override
     public void start(Promise<Void> startPromise) {
	// Load configuration
	ConfigLoader.load(vertx)
	     .onFailure(startPromise::fail)
	     .onSuccess(config -> log.info("Load config successfully {}", config))
	     .andThen(ar -> initHttpServer(startPromise, ar.result()));
     }
     
     private void initHttpServer(Promise<Void> startPromise, BrokerConfig config) {
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
	     .listen(config.getServerPort(),
		http -> {
		     if (http.succeeded()) {
			startPromise.complete();
			log.info("HTTP server instance {} started on port {} version {}",
			     config.getInstanceName(),
			     config.getServerPort(),
			     config.getVersion());
		     } else {
			startPromise.fail(http.cause());
		     }
		}
	);
     }
}