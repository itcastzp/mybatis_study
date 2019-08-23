/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.cache;

import org.apache.ibatis.cache.decorators.*;
import org.apache.ibatis.cache.impl.PerpetualCache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class BaseCacheTest {
    /*
     *
     * 应该为各种缓存类型演示相等和哈希代码
     * */
    @Test
    void shouldDemonstrateEqualsAndHashCodeForVariousCacheTypes() {
        PerpetualCache cache = new PerpetualCache("test_cache");
        assertTrue(cache.equals(cache));
        assertTrue(cache.equals(new SynchronizedCache(cache)));
        assertTrue(cache.equals(new SerializedCache(cache)));
        assertTrue(cache.equals(new LoggingCache(cache)));
        assertTrue(cache.equals(new ScheduledCache(cache)));

        assertEquals(cache.hashCode(), new SynchronizedCache(cache).hashCode());
        assertEquals(cache.hashCode(), new SerializedCache(cache).hashCode());
        assertEquals(cache.hashCode(), new LoggingCache(cache).hashCode());
        assertEquals(cache.hashCode(), new ScheduledCache(cache).hashCode());

        Set<Cache> caches = new HashSet<>();
        caches.add(cache);
        caches.add(new SynchronizedCache(cache));
        caches.add(new SerializedCache(cache));
        caches.add(new LoggingCache(cache));
        caches.add(new ScheduledCache(cache));
        assertEquals(1, caches.size());
    }

    @Test
    void shouldEqualsFromEachWrapper() {

        PerpetualCache cache = new PerpetualCache("test_cache");
        SynchronizedCache synchronizedCache = new SynchronizedCache(cache);
        SerializedCache serializedCache = new SerializedCache(cache);
        LoggingCache loggingCache = new LoggingCache(cache);
        ScheduledCache scheduledCache = new ScheduledCache(cache);
        LruCache lruCache = new LruCache(cache);


        /*assertTrue(cache.equals(cache));
        assertTrue(cache.equals(synchronizedCache));
        assertTrue(cache.equals(serializedCache));
        assertTrue(cache.equals(loggingCache));
        assertTrue(cache.equals(scheduledCache));
        */
        assertTrue(cache.equals(new LoggingCache(loggingCache)));

        assertTrue(cache.equals(new ScheduledCache(loggingCache)));
        assertTrue(cache.equals(new LruCache(loggingCache)));
    }

}
