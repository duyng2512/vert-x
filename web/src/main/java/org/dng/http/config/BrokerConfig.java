package org.dng.http.config;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
@ToString
public class BrokerConfig {

     int serverPort;
     String instanceName;
     String version;
     
     public static BrokerConfig from (JsonObject config) {
	return BrokerConfig.builder()
	     .serverPort(config.getInteger(ConfigLoader.SERVER_PORT))
	     .instanceName(config.getString(ConfigLoader.INSTANCE_NAME))
	     .version(config.getString("version"))
	     .build();
     }
}
