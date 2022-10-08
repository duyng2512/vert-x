package org.dng.http.api;

import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.handler.ProcessException;

@Slf4j
public class ExceptionApi {
     private ExceptionApi(){}
     public static void attach(Router router){
          router.route().failureHandler(new ProcessException());
     }
     
}
