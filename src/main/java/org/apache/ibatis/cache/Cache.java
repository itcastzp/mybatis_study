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

import java.util.concurrent.locks.ReadWriteLock;

/**
 * SPI for cache providers.
 * SPI缓存生产者
 * <p>
 * One instance of cache will be created for each namespace.
 * 每个命名空间将创建一个缓存实例
 * <p>
 * The cache implementation must have a constructor that receives the cache id as an String parameter.
 * 缓存实现必须具有一个构造函数，该构造函数将缓存ID作为String参数接收。
 * <p>
 * MyBatis will pass the namespace as id to the constructor.
 * MyBatis将命名空间作为id传递给构造函数。如下所示
 * <pre>
 * public MyCache(final String id) {
 *  if (id == null) {
 *    throw new IllegalArgumentException("Cache instances require an ID");
 *  }
 *  this.id = id;
 *  initialize();
 * }
 * </pre>
 *
 * @author Clinton Begin
 */

public interface Cache {




    /**
     * 此缓存的标识符
     *
     * @return The identifier of this cache
     */
    String getId();

    /**
     * @param key   Can be any object but usually it is a {@link CacheKey}
     * @param value The result of a select.
     */
    void putObject(Object key, Object value);

    /**
     * @param key The key
     * @return The object stored in the cache.
     */
    Object getObject(Object key);

    /**
     * As of 3.3.0 this method is only called during a rollback
     * for any previous value that was missing in the cache.
     * This lets any blocking cache to release the lock that
     * may have previously put on the key.
     * A blocking cache puts a lock when a value is null
     * and releases it when the value is back again.
     * This way other threads will wait for the value to be
     * available instead of hitting the database.
     * <p>从3.3.0开始，只有在回滚
     * *期间才会调用此方法，针对缓存中缺失的任何先前缓存过的值。
     * *这让任何阻塞缓存释放
     * *之前可能已经存放的CacheKey了。
     * *阻塞缓存在值为null时置位锁定
     * *在值再次返回时释放它。
     * *这样，其他线程将等待值
     * *可用而不是命中数据库。
     * </p>
     *
     * @param key The key
     * @return Not used
     */
    Object removeObject(Object key);

    /**
     * Clears this cache instance.
     */
    void clear();

    /**
     * Optional. This method is not called by the core.
     *
     * @return The number of elements stored in the cache (not its capacity).
     */
    int getSize();

    /**
     * Optional. As of 3.2.6 this method is no longer called by the core.
     * <p>
     * Any locking needed by the cache must be provided internally by the cache provider.
     *
     * @return A ReadWriteLock
     */
    ReadWriteLock getReadWriteLock();

}
