package org.dng;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.dng.vertices.EventLoopVertices;
import org.dng.vertices.HttpVertices;
import org.dng.vertices.WorkerVertices;

import java.util.concurrent.TimeUnit;

public class MainVerticles extends AbstractVerticle {
    
    @Override
    public void start() throws Exception {
        // Option configuration
        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setMaxEventLoopExecuteTime(500);
        vertxOptions.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);
        vertxOptions.setBlockedThreadCheckInterval(500);
        vertxOptions.setBlockedThreadCheckIntervalUnit(TimeUnit.MILLISECONDS);
    
        // Deploy configuration
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(true);
        deploymentOptions.setWorkerPoolSize(1);
        deploymentOptions.setWorkerPoolName("worker-count-thread");
    
        var vertx = Vertx.vertx(vertxOptions);
        vertx.deployVerticle(new HttpVertices());
        vertx.deployVerticle(new EventLoopVertices());
        vertx.deployVerticle(new WorkerVertices(), deploymentOptions);
    }
}