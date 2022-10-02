package org.dng.http.object;

import io.vertx.core.json.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dng.http.api.WatchListApi;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WatchStock {
     public String name;
     public Boolean enable;
     public static WatchStock createWatchStock(JsonObject json) {
	WatchStock watchStock = new WatchStock();
	watchStock.setEnable(Boolean.valueOf(json.getString("enable")));
	watchStock.setName(json.getString("name"));
	return watchStock;
     }
}
