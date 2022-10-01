import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
@Slf4j
public class FuturePromiseTest {
     
     @Test
     public void promiseSuccess(Vertx vertx, VertxTestContext context) {
	Promise<String> promise = Promise.promise();
	log.info("Start");
	vertx.setTimer(10L, (id) ->{
	     promise.complete("Done");
	     log.info("Done");
	     context.completeNow();
	});
	log.info("End");
	
	/*
	     [INFO] FuturePromiseTest: Start
	     [INFO] FuturePromiseTest: End
	     [INFO] FuturePromiseTest: Done
	 */
     }
     
     @Test
     public void promiseFailure(Vertx vertx, VertxTestContext context) {
	Promise<String> promise = Promise.promise();
	log.info("Start");
	vertx.setTimer(10L, (id) ->{
	     promise.fail(new RuntimeException("Error"));
	     log.info("Failed");
	     context.completeNow();
	});
	log.info("End");
     }
     
     @Test
     public void futureSuccess(Vertx vertx, VertxTestContext context) {
	Promise<String> promise = Promise.promise();
	log.info("Start");
	vertx.setTimer(10L, (id) ->{
	     promise.complete("Done");
	     log.info("Done");
	     context.completeNow();
	});
	log.info("End");
	Future<String> future = promise.future();
	future.onSuccess(res -> {
	     log.info("Success {}", res);
	});
     }
     
     @Test
     public void futureFail(Vertx vertx, VertxTestContext context) {
	Promise<String> promise = Promise.promise();
	log.info("Start");
	vertx.setTimer(10L, (id) ->{
	     promise.fail("Fail");
	     log.info("Done");
	     context.completeNow();
	});
	log.info("End");
	Future<String> future = promise.future();
	future.onFailure(res -> {
	     log.error("Fail", res);
	});
     }
     
     @Test
     public void futureMap(Vertx vertx, VertxTestContext context) {
	Promise<String> promise = Promise.promise();
	
	log.info("Start Promise");
	vertx.setTimer(10, (id) ->{
	     promise.complete("Done");
	     log.info("Done Promise");
	     context.completeNow();
	});
	log.info("End Promise");
	
	Future<String> future = promise.future();
	future.map( asString -> new JsonObject().put("result", asString))
	     .onSuccess(res -> log.info("Success {}", res))
	     .andThen(res -> log.info("After Future Done {}", res));
     }
     
     @Test
     public void futureCoordination(Vertx vertx, VertxTestContext context) {
	vertx.createHttpServer()
	     .requestHandler(serverRequest -> log.info("Incoming Request {}", serverRequest))
	     .listen(10)
	     .compose(httpServer -> {
		log.info("Additional Info");
		return Future.succeededFuture(httpServer);
	     })
	     .compose(httpServer -> {
		log.info("More Info");
		return Future.succeededFuture(httpServer);
	     })
	     .onFailure(context::failNow)
	     .onSuccess(httpServer -> {
		log.info("HTTP Server Start on {}", httpServer.actualPort());
		context.completeNow();
	     });
     }
}
