package org.dng.vertices;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public class HttpVertices extends AbstractVerticle {

    static final int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // Deploy Child Vertices
        vertx.deployVerticle(new SupportVertices());
        // Deploy multiple instance
        vertx.deployVerticle(ScalingVertices.class.getName(),
		        new DeploymentOptions()
			   .setInstances(NUM_PROCESSORS)
			   .setConfig(new JsonObject()
			        .put("id", UUID.randomUUID().toString())
			        .put("name", ScalingVertices.class.getSimpleName())));
        // Multiple instance will have the same config
        vertx.createHttpServer().requestHandler(req -> {
	  req.response()
		.putHeader("content-type", "text/plain")
		.end("Hello from Vert.x!");
        }).listen(8888, http -> {
	  if (http.succeeded()) {
	      startPromise.complete();
	      System.out.println("HTTP server started on port 8888");
	  } else {
	      startPromise.fail(http.cause());
	  }
        });
    }
}
