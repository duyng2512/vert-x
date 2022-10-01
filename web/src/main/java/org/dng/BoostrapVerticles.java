package org.dng;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.dng.http.HttpVerticles;

public class BoostrapVerticles extends AbstractVerticle {
     
     @Override
     public void start() throws Exception {
	vertx.deployVerticle(new HttpVerticles());
     }
}