/**
 * Copyright 2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aerogear.kafka.impl;

import org.aerogear.kafka.ExtendedKafkaProducer;
import org.aerogear.kafka.SimpleKafkaProducer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class InjectedKafkaProducer<K, V> extends org.apache.kafka.clients.producer.KafkaProducer implements SimpleKafkaProducer<K, V>, ExtendedKafkaProducer<K, V> {

    public InjectedKafkaProducer(final Map<String, Object> configs, final Serializer<K> keySerializer, final Serializer<V> valSerializer) {
        super(configs, keySerializer, valSerializer);
    }


    public Future<RecordMetadata> send(final String topic, final V payload) {
        return this.send(new ProducerRecord(topic, payload));
    }

    public Future<RecordMetadata> send(final String topic, final V payload, final Callback callback) {
        return this.send(new ProducerRecord(topic, payload), callback);
    }

    public Future<RecordMetadata> send(final String topic, final K key, final V payload) {
        return this.send(new ProducerRecord(topic, key, payload));
    }

    public Future<RecordMetadata> send(final String topic, final K key, final V payload, final Callback callback) {
        return this.send(new ProducerRecord(topic, key, payload), callback);
    }

    public void closeProducer() {
        super.close();
    }

    @Override
    public Future<RecordMetadata> send(final String topic, final V payload, final Map<String, byte[]> headers) {
        final List<Header> headersList = headers.entrySet().stream().map(entry -> new RecordHeader(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        RecordHeaders recordHeaders = new RecordHeaders(headersList);
        return this.send(new ProducerRecord(topic, null, null, null, payload, recordHeaders));
    }

    @Override
    public Future<RecordMetadata> send(final String topic, final V payload, final Map<String, byte[]> headers, final Callback callback) {
        final List<Header> headersList = headers.entrySet().stream().map(entry -> new RecordHeader(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        RecordHeaders recordHeaders = new RecordHeaders(headersList);
        return this.send(new ProducerRecord(topic, null, null, null, payload, recordHeaders), callback);

    }

    @Override
    public Future<RecordMetadata> send(final String topic, final K key, final V payload, final Map<String, byte[]> headers) {
        final List<Header> headersList = headers.entrySet().stream().map(entry -> new RecordHeader(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        RecordHeaders recordHeaders = new RecordHeaders(headersList);
        return this.send(new ProducerRecord(topic, null, null, key, payload, recordHeaders));

    }

    @Override
    public Future<RecordMetadata> send(final String topic, final K key, final V payload, final Map<String, byte[]> headers, final Callback callback) {
        final List<Header> headersList = headers.entrySet().stream().map(entry -> new RecordHeader(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        RecordHeaders recordHeaders = new RecordHeaders(headersList);
        return this.send(new ProducerRecord(topic, null, null, key, payload, recordHeaders), callback);

    }
}