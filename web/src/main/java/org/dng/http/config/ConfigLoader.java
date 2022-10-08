package org.dng.http.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ConfigLoader {
     
     public static final String SERVER_PORT = "SERVER_PORT";
     public static final String INSTANCE_NAME = "INSTANCE_NAME";
     public static final String APPLICATION_YAML = "application.yaml";
     static List<String> EXPOSED_ENV_VARIABLE = Arrays.asList(SERVER_PORT, INSTANCE_NAME);
     
     public static Future<BrokerConfig> load(Vertx vertx) {
	var jsonArray = new JsonArray();
	EXPOSED_ENV_VARIABLE.forEach(jsonArray::add);
	log.info("Fetch Key configurations {}", jsonArray.encode());
	
	var envStore = new ConfigStoreOptions();
	envStore.setType("env");
	envStore.setConfig(new JsonObject().put("keys", jsonArray));
 
	var sysStore = new ConfigStoreOptions();
	sysStore.setType("sys");
	sysStore.setConfig(new JsonObject().put("cache", false));
 
	var yamlStore = new ConfigStoreOptions();
	yamlStore.setType("file");
	yamlStore.setFormat("yaml");
	yamlStore.setConfig(new JsonObject().put("path", APPLICATION_YAML));
	
	var configOption = new ConfigRetrieverOptions();
	configOption.addStore(envStore)
	     	.addStore(sysStore)
	     	.addStore(yamlStore);
	
	return ConfigRetriever.create(vertx, configOption)
	     		.getConfig()
	     		.map(BrokerConfig::from);
     };
}
