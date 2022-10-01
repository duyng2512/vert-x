package org.dng.vertices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class EventLoopVertices extends AbstractVerticle {
     
     @Override
     public void start(Promise<Void> startPromise) throws Exception {
	startPromise.complete();
	// Thread.sleep(5000);
	// Never block an event loop
     }
}
