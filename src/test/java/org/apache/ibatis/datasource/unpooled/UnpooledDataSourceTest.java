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
package org.apache.ibatis.datasource.unpooled;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class UnpooledDataSourceTest {


  @Test
  public void  will_not_work() throws ClassNotFoundException, SQLException, MalformedURLException {
    URL u = new URL("jar:file:/path/to/pgjdbc2.jar!/");
    String classname = "org.postgresql.Driver";
    URLClassLoader ucl = new URLClassLoader(new URL[] { u });
    Class.forName(classname, true, ucl);
    DriverManager.getConnection("jdbc:postgresql://host/db", "user", "pw");
    // That will throw SQLException: No suitable driver
  }
  @Test
  public void will_work() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    URL u = new URL("jar:file:/path/to/pgjdbc2.jar!/");
    String classname = "org.postgresql.Driver";
    URLClassLoader ucl = new URLClassLoader(new URL[] { u });
    Driver d = (Driver)Class.forName(classname, true, ucl).newInstance();
    DriverManager.registerDriver(new DriverShim(d));
   DriverManager.getConnection("jdbc:postgresql://host/db", "user", "pw");
    // Success!
  }



  @Test
  void shouldNotRegisterTheSameDriverMultipleTimes() throws Exception {
    // https://code.google.com/p/mybatis/issues/detail?id=430
    UnpooledDataSource dataSource = null;
    dataSource = new UnpooledDataSource("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:multipledrivers", "sa", "");
    dataSource.getConnection().close();
    int before = countRegisteredDrivers();
    dataSource = new UnpooledDataSource("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:multipledrivers", "sa", "");
    dataSource.getConnection().close();
    assertEquals(before, countRegisteredDrivers());
  }

  @Disabled("Requires MySQL server and a driver.")
  @Test
  void shouldRegisterDynamicallyLoadedDriver() throws Exception {
    int before = countRegisteredDrivers();
    ClassLoader driverClassLoader = null;
    UnpooledDataSource dataSource = null;
    driverClassLoader = new URLClassLoader(new URL[] { new URL("jar:file:/PATH_TO/mysql-connector-java-5.1.25.jar!/") });
    dataSource = new UnpooledDataSource(driverClassLoader, "com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/test", "root", "");
    dataSource.getConnection().close();
    assertEquals(before + 1, countRegisteredDrivers());
    driverClassLoader = new URLClassLoader(new URL[] { new URL("jar:file:/PATH_TO/mysql-connector-java-5.1.25.jar!/") });
    dataSource = new UnpooledDataSource(driverClassLoader, "com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/test", "root", "");
    dataSource.getConnection().close();
    assertEquals(before + 1, countRegisteredDrivers());
  }

  int countRegisteredDrivers() {
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    int count = 0;
    while (drivers.hasMoreElements()) {
      drivers.nextElement();
      count++;
    }
    return count;
  }

  private class DriverShim implements Driver {
    private Driver proxy;
    public DriverShim(Driver d) {
      this.proxy = d;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
      return proxy.connect(url, info);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
      return this.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      return this.getPropertyInfo(url, info);
    }

    @Override
    public int getMajorVersion() {
      return this.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
      return this.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
      return this.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return this.getParentLogger();
    }

  }
}
