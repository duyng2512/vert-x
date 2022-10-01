package org.dng.vertices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;



public class WorkerVertices extends AbstractVerticle {
     
     @Override
     public void start(Promise<Void> startPromise) throws Exception {
	startPromise.complete();
	// Fetch this task to worker thread pool
	// in order to not block the event loop
	vertx.executeBlocking(this::executeBlockingCode, res -> {
	     if (res.succeeded()) {
		System.out.println("Blocking event success: " + res.result());
	     } else {
		System.out.println("Blocking event fail: "  + res.result());
	     }
	});
     }
     
     private void executeBlockingCode(Promise<Object> event) {
	// Here let just count from i to 1_000_000
	System.out.println("Blocking event start on thread " + Thread.currentThread().getName());
	try {
	     Thread.sleep(5000);
	     event.complete("Yeah complete!");
	} catch (InterruptedException e) {
	     System.out.println("Error " + e);
	     event.fail("Error !");
	}
     }
}
