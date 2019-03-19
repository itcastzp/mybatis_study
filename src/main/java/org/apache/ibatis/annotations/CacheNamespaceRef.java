/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation that reference a cache.
 * 引用缓存的注释。
 * <p>
 * If you use this annotation, should be specified either {@link #value()} or {@link #name()} attribute.
 * 如果使用此批注，则应指定{@link #value（）}或{@link #name（）}属性。
 * </p>
 * @author Clinton Begin
 * @author Kazuki Shimizu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CacheNamespaceRef {

  /**
   * A namespace type to reference a cache (the namespace name become a FQCN of specified type).
   * 用于引用缓存的命名空间类型（命名空间名称成为指定类型Full_Qualified_Class_Name）。
   */
  Class<?> value() default void.class;

  /**
   * A namespace name to reference a cache.
   * @since 3.4.2
   */
  String name() default "";
}
