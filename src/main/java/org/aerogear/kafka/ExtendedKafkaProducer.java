package org.aerogear.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.Future;

public interface ExtendedKafkaProducer<K, V>  extends SimpleKafkaProducer<K, V> {

    Future<RecordMetadata> send(String topic, V payload, Map<String, byte[]> headers);
    Future<RecordMetadata> send(String topic, V payload, Map<String, byte[]> headers, Callback callback);
    Future<RecordMetadata> send(String topic, K key, V payload, Map<String, byte[]> headers);
    Future<RecordMetadata> send(String topic, K key, V payload, Map<String, byte[]> headers, Callback callback);
}
