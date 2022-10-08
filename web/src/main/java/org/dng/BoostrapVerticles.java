package org.dng;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import lombok.extern.slf4j.Slf4j;
import org.dng.http.HttpVerticles;

@Slf4j
public class BoostrapVerticles extends AbstractVerticle {
     
     @Override
     public void start() throws Exception {
	DeploymentOptions options = new DeploymentOptions();
	options.setInstances(Runtime.getRuntime().availableProcessors());
	
	
	vertx.deployVerticle(HttpVerticles.class.getName(), options)
	     .onSuccess(s -> log.info("Deployed verticles with id {}", s))
	     .onFailure(s -> log.error("Fail to deploy verticles", s));
	
     }
}