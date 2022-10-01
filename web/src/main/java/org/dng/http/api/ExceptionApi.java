package org.dng.http.api;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionApi {
     
     public static void attach(Router router){
          router.route().failureHandler(context -> {
               log.error("Get Exception", context.failure());
               context.response()
                    .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                    .end(JsonObject.of("message", context.failure().getMessage()).toBuffer());
          });
     }
     
}
