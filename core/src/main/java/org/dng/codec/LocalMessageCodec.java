package org.dng.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class LocalMessageCodec<T> implements MessageCodec<T, T> {
     private final String typeName;
     
     public LocalMessageCodec(Class<T> clazz) {
	this.typeName = clazz.getTypeName();
     }
     
     @Override
     public void encodeToWire(Buffer buffer, T t) {
	throw new UnsupportedOperationException();
     }
     
     @Override
     public T decodeFromWire(int i, Buffer buffer) {
	throw new UnsupportedOperationException();
     }
     
     @Override
     public T transform(T t) {
	return t;
     }
     
     @Override
     public String name() {
	return this.typeName;
     }
     
     @Override
     public byte systemCodecID() {
	return -1;
     }
}
