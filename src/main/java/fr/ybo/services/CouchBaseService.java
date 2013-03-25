/*
 * Copyright 2013- Yan Bonnel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ybo.services;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;

public enum CouchBaseService {
    INSTANCE;

    private final ObjectMapper mapper;
    private final CouchbaseClient client;

    private CouchBaseService() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            client = new CouchbaseClient(newArrayList(new URI("http://127.0.0.1:8091/pools")), "default", "");
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public void stopCouchbaseClient() {
        client.shutdown(10, TimeUnit.SECONDS);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public CouchbaseClient getClient() {
        return client;
    }
}
