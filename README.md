# JPA 和 Spring Data JPA

## 原生Jpa的概念和操作

### 什么是原生Jpa

JPA的全称是Java Persistence API， 即Java 持久化API，是SUN公司推出的一套基于ORM的规范，内部是由一系列的接口和抽象类构成。

JPA通过JDK 5.0注解描述对象－关系表的映射关系，并将运行期的实体对象持久化到数据库中。

### Jpa 的入门

#### 一,创建Maven 工程导入Pom.xml坐标

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>Jpa-test</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.hibernate.version>5.0.7.Final</project.hibernate.version>
    </properties>
    <dependencies>
        <!-- junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <!-- hibernate对jpa的支持包 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>${project.hibernate.version}</version>
        </dependency>

        <!-- c3p0 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-c3p0</artifactId>
            <version>${project.hibernate.version}</version>
        </dependency>

        <!-- log日志 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <!-- Mysql and MariaDB -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.6</version>
        </dependency>
    </dependencies>

</project>
```

#### 二，在META-INF 中创建名为"Persistence.xml"资源配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
    http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
             version="2.0">
    <!--配置持久化单元
		name：持久化单元名称
		transaction-type：事务类型
		 	RESOURCE_LOCAL：本地事务管理 我们当前是为本地操作
		 	JTA：分布式事务管理 -->
    <persistence-unit name="myJpa" transaction-type="RESOURCE_LOCAL">
        <!--配置JPA规范 服务提供商-->
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <properties>
            <!--配置数据库-->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
            <!-- 数据库地址 -->
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/customer" />
            <!-- 数据库用户名 -->
            <property name="javax.persistence.jdbc.user" value="root" />
            <!-- 数据库密码 -->
            <property name="javax.persistence.jdbc.password" value="1024" />
			<!-- 因为hibernate 提供了JPA的实现也可以配置Hibernate 中属性-->
            <property name="hibernate.show_sql" value="true" /> <!-- 显示sql -->
            <property name="hibernate.format_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="update" /><!--创建生成策略 -->
        </properties>
    </persistence-unit>
</persistence>
```

log4j配置文件

```xml
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# MyBatis logging configuration...
log4j.logger.org.mybatis.example.BlogMapper=TRACE
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

#### 三,配置实体类属性

在实体类中要配置实体类属性和表中对应的属性,这里我以我创建的客户表为例

   **@Entity**

​      作用：指定当前类是实体类。

​    **@Table**

​      作用：指定实体类和表之间的对应关系。

​      属性：

​       name：指定数据库表的名称

​    **@Id**

​      作用：指定当前字段是主键。

​    **@GeneratedValue**

​      作用：指定主键的生成方式。。

​      属性：

​       **strategy ：指定主键生成策略。**

​    **@Column**

​      作用：指定实体类属性和数据库表之间的对应关系

​      属性：

​       **name：指定数据库表的列名称。**

​       unique：是否唯一 

​       nullable：是否可以为空 

​       inserttable：是否可以插入 

​       updateable：是否可以更新 

​       columnDefinition: 定义建表时创建此列的DDL 

​       secondaryTable: 从表名。如果此列不建在主表上（默认建在主表），该属性定义该列所在从表的名字搭建开发环境[重点]  *这个不清楚

```java
package com.wym.Entity;

import javax.persistence.*;

@Entity
@Table(name = "customer_table")
public class Customer {
    @Id//声明当前私有主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键生成策略
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "tell")
    private Integer tell;
    @Column(name = "addr")
    private String addr;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getTell() {
        return tell;
    }

    public void setTell(Integer tell) {
        this.tell = tell;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tell=" + tell +
                ", addr='" + addr + '\'' +
                '}';
    }
}

```

#### 四，配置Jpa 的管理器

```java
     //获取配置文件得到Factory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        //创建实体类管理对象
        EntityManager em = factory.createEntityManager();
        //获取事务对象
        EntityTransaction transaction = em.getTransaction();
        //开启事务
        transaction.begin();
        Customer customer=new Customer();
        customer.setName("廖伟");
        customer.setAddr("井冈山");
        em.persist(customer);//添加数据
        //提交事务
        transaction.commit();
        //释放资源
        em.close();
        factory.close();
```

#### 五，优化Jpa 的创建过程 抽取一个工具类

```java
package com.wym.Entity.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Jpa工具类
 */
public class JpaUtil {
    // JPA的实体管理器工厂：相当于Hibernate的SessionFactory
    private static EntityManagerFactory factory=null;
    // 使用静态代码块赋值
    static{
      factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 提供一个方法管理对象
     */
    public static EntityManager getFactory(){
        return  factory.createEntityManager();
    }

}
```

### Jpa的CRUD 操作

```java

    /**
     * EntityManager:提供了crud 的方法
     * getTransaction : 获取事务对象
     * 	persist ： 保存操作
     * 	merge ： 更新操作
     * 	remove ： 删除操作
     * 	find/getReference ： 根据id查询
     */
```

**上面我们创建了JpaUtils下面我们使用这个JpaUtils 工具类**

#### 一  ,保存操作persist

```java
 @Test
    public  void  testUtil(){
        EntityManager me = JpaUtil.getFactory();
        EntityTransaction transaction = me.getTransaction();
        transaction.begin();
        try {
            Customer customer = new Customer();
            customer.setName("wengyanmin");
            customer.setSex("男");
            customer.setAddr("江西");
            customer.setTell(19312312);

            me.persist(customer);//保存
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        me.close();
    }
```

#### 二，更新数据merge

```java
 @Test
    public void testMerge(){
        EntityManager me = JpaUtil.getFactory();
        EntityTransaction transaction = me.getTransaction();
        transaction.begin();
        try {
            //查询前要先查询出对象
            Customer customer = me.find(Customer.class, 1l);
            customer.setAddr("深圳");
            //修改
            me.merge(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        me.close();
    }
```

#### 三，删除数据remove

```java
    @Test
    public void testDelete(){
        EntityManager me = JpaUtil.getFactory();
        EntityTransaction transaction = me.getTransaction();
        transaction.begin();
        try {
            //查询前要先查询出对象
            Customer customer = me.find(Customer.class, 1l);
            //删除
            me.remove(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        me.close();
    }
```

#### 四，查询find 和 getReference

```java
   /**
     * 查询 根据id
     * find :直接获取对象本体
     * getReference：延迟加载
     */
@Test
    public void testFind(){
        EntityManager me = JpaUtil.getFactory();
        EntityTransaction transaction = me.getTransaction();
        transaction.begin();
        try {
            //查询前要先查询出对象
            //Customer customer = me.find(Customer.class, 1l);
            Customer c1 = me.find(Customer.class, 1L);
            Customer c2 = me.find(Customer.class, 1L);
            System.out.println(c1==c2);
            //返回true 有缓存
            //getReference：延迟加载
            Customer reference = me.getReference(Customer.class, 1l);
            //删除
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        me.close();
    }
```

### Jpa的复杂查询

 jpa提供了一套复杂查询的 方式，就是使用JPQl,下面使用JPQL 来进行复杂的查询，JPQL 的语法是使用实体类中属性来进行语句的书写

#### 一， 查询所有

```java
@Test
    public  void test1(){
        EntityManager manager = JpaUtil.getFactory();
        EntityTransaction tx = manager.getTransaction();

        tx.begin();
        String jql="from  Customer ";
        Query query = manager.createQuery(jql);
        List resultList = query.getResultList();
        for (Object cus:resultList
             ) {
            System.out.println(((Customer)cus));
        }
        tx.commit();

        manager.close();
    }
```

#### 二，分页查询

```java
 @Test
    public  void testLimit(){
        EntityManager manager = JpaUtil.getFactory();
        EntityTransaction tx = manager.getTransaction();

        tx.begin();
        String jql="from  Customer ";
        Query query = manager.createQuery(jql);
        //起始索引
        query.setFirstResult(1);
        //每页显示条数
        query.setMaxResults(2);

        List resultList = query.getResultList();
        for (Object cus:resultList
        ) {
            System.out.println(((Customer)cus));
        }
        tx.commit();
        manager.close();
    }
```

#### 三，条件查询

```java
模糊查询:
 @Test
    public void testFindCondition(){
        EntityManager manager = JpaUtil.getFactory();
        EntityTransaction tx = manager.getTransaction();

        tx.begin();
        String jql="from Customer where  name like ?";
        Query query = manager.createQuery(jql);
        //站位符
        query.setParameter(1, "weng%");

        //返回对象
        Object singleResult = query.getSingleResult();
        System.out.println(singleResult);
        tx.commit();
        manager.close();
    }
 /**
     * 下面的不写了：
     * 
     * 排序查询：
     *  jql: from Customer order by id desc
     *  统计查询：
     *  jql
     *  select count(id) from Customer
     */
```

## Spring Data Jpa 的概念和操作

### 什么是spring Data jpa 

Spring Data JPA 是 Spring 基于 ORM 框架、JPA 规范的基础上封装的一套JPA应用框架，可使开发者用极简的代码即可实现对数据库的访问和操作。它提供了包括增删改查等在内的常用功能，且易于扩展！学习并使用 Spring Data JPA 可以极大提高开发效率！

###  Spring Data Jpa 的入门

#### 一，引入SpringData Jpa 的坐标

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>jap-spring</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <spring.version>4.2.4.RELEASE</spring.version>
        <hibernate.version>5.0.7.Final</hibernate.version>
        <slf4j.version>1.6.6</slf4j.version>
        <log4j.version>1.2.12</log4j.version>
        <c3p0.version>0.9.1.2</c3p0.version>
        <mysql.version>5.1.6</mysql.version>
    </properties>

    <dependencies>
        <!-- junit单元测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.9</version>
            <scope>test</scope>
        </dependency>

        <!-- spring beg -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.6.8</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- spring end -->

        <!-- hibernate beg -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>${hibernate.version}</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>${hibernate.version}</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>5.2.1.Final</version>
        </dependency>
        <!-- hibernate end -->

        <!-- c3p0 beg -->
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>${c3p0.version}</version>
        </dependency>
        <!-- c3p0 end -->

        <!-- log end -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <!-- log end -->


        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>1.9.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>4.2.4.RELEASE</version>
        </dependency>

        <!-- el beg 使用spring data jpa 必须引入 -->
        <dependency>
            <groupId>javax.el</groupId>
            <artifactId>javax.el-api</artifactId>
            <version>2.2.4</version>
        </dependency>

        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.el</artifactId>
            <version>2.2.4</version>
        </dependency>

    </dependencies>
</project>
```

#### 二，创建ApplicationContext.xml 配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:jdbc="http://www.springframework.org/schema/jdbc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">
    <!--spring 和配置spring data jpa -->
    <!--扫描包-->
    <context:component-scan base-package="com.wym"></context:component-scan>
    <!--配置entityManagerFactory对象交给spring 容器-->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <!--引入dataSoucre-->
        <property name="dataSource" ref="dataSource"/>
        <!--配置扫描实体类的包-->
        <property name="packagesToScan" value="com.wym.domain"></property>
        <!--jpa 的实现厂家-->
        <property name="persistenceProvider">
            <bean class="org.hibernate.jpa.HibernatePersistenceProvider"/>
        </property>
        <!--jpa 的提供商家-->
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <!--配置是否自动创建数据库表 -->
                <property name="generateDdl" value="false"></property>
                <property name="database" value="MYSQL"></property>
                <!--方言 支持的特有语法 这里我使用的Mysql数据库-->
                <property name="databasePlatform" value="org.hibernate.dialect.MySQLDialect"></property>
                <property name="showSql" value="true"></property>
            </bean>
        </property>
        <!--jpa 的方言：高级的特性-->
        <property name="jpaDialect">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaDialect"></bean>
        </property>

        <!--注入jpa 的基本配置信息
            加载jap 的基本信息和jpa 实现方式 （hibernate） 的配置信息
            hibernate.hbm2ddl.auto :自动创建数据库表
        -->
        <property name="jpaProperties">
            <props>
                <!--一加载就重新创建表-->
                <prop key="hibernate.hbm2ddl.auto">update</prop>
            </props>
        </property>

    </bean>
    <!--c3p0数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="user" value="root"></property>
        <property name="password" value="1024"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/customer"></property>
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
    </bean>
    <!--整合spring dataJpa-->
    <!--提供dao层的包位置-->
    <jpa:repositories base-package="com.wym.dao" transaction-manager-ref="transactionManager"
                      entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>
    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"></property>
    </bean>
</beans>

```

#### 三，配置 实体类的属性和数据库进行对应

这里我以Customer为例

```java
package com.wym.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户表
 * 实体类
 * 一的一方
 */
@Entity//表示这个类是实体类
@Table(name="customer_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "addr")
    private String addr;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "tell")
    private Integer tell;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getTell() {
        return tell;
    }

    public void setTell(Integer tell) {
        this.tell = tell;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", addr='" + addr + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tell=" + tell +
                ", likemans=" + likemans +
                '}';
    }
}

```

#### 四，配置Dao 接口

创建一个接口类继承 **JpaRepository**，和**JpaSpecificationExecutor** 获得 CRUD 方法和复杂查询方法

下面以CustomerDao 为例：

```java
package com.wym.dao;

import com.wym.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Jpa实现 Dao 接口
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 */
public interface CustomerDao  extends JpaRepository<Customer,Integer> , JpaSpecificationExecutor<Customer>{
   
}

```

#### 五，测试Spring Data Jpa 

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")//自己的配置文件的类路径
public class testJpa {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 保存客户信息
     * save() 若有id 属性 则为更新 否则为 保存
     * 更新 ：会先清空一遍数据
     */
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setName("爬虫");
        customerDao.save(customer);
    }
}
```

### Spring Data Jpa 进行查询

#### 一，简单的CRUD  查询使用JpaRepository中的方法

因为CustomerDao继承了JpaRepository 接口所以也获得了Repository 中的方法

```java
 /**
     * 保存客户信息
     * save() 若有id 属性 则为更新 否则为 保存
     * 更新 ：会先清空一遍数据
     */
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setName("爬虫");
        customerDao.save(customer);
    }

    /**
     * 删除
     */
    @Test
    public void  testDelete(){
        customerDao.delete(1);

    }
    /**
     * 根据id 查询
     */
    @Test
    public  void testFindById(){
        customerDao.findOne(2);
    }
    @Test
    public  void  testUpdateById(){
        customerDao.
    }
```

#### 二，使用JPQL 查询

##### Dao的配置

在Dao 的接口创建接口方法 中接口方法上配置注解@Query

```java
 @Query(value = "from  Customer ")
    public List<Customer> findAllCustomer();

    @Query(value = "from Customer  where  name =?1")
    public Customer findCustomer(String custName);
    /**
     * 更新
     * 要加这个标志
     */
    @Query(value = "update  Customer  set  name= ?  where id =?")
    @Modifying
    public void UpdateByCustomer(String name,int id);
```

##### 测试方法

```java
/**
 * 使用JPQL 方式查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class testJPQL {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 测试jpql的更新和删除操作
     *  * springDataJpa中使用jpql完成 更新/删除操作
     *         * 需要手动添加事务的支持
     *         * 默认会执行结束之后，回滚事务
     *   @Rollback : 设置是否自动回滚
     *          false | true
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateCustomer(){
       customerDao.UpdateByCustomer("翁艳敏", 1);
    }
}
```

#### 三，使用SQL 查询

在@Query注解中有一个nativeQuery=true 打开

```java
 /**
     * 使用sql
     * nativeQuery = true
 */
    @Query(value = "select  * from customer_table" ,nativeQuery = true)
    public Customer SqlFindAll();
```

使用方法和JPQL一样

#### 四，方法命名规则查询

顾名思义，方法命名规则查询就是根据方法的名字，就能创建查询。只需要按照Spring Data JPA提供的方法命名规则定义方法的名称，就可以完成查询工作。Spring Data JPA在程序执行的时候会根据方法名称进行解析，并自动生成查询语句进行查询

例如：

```java
    //方法命名方式查询（根据客户名称查询客户）
    public Customer findByCustName(String custName);
```

|                   |                                           |                                                              |      |      |
| :---------------: | :---------------------------------------: | :----------------------------------------------------------: | ---- | ---- |
|    **Keyword**    |                **Sample**                 |                           **JPQL**                           |      |      |
|        And        |        findByLastnameAndFirstname         |        …  where x.lastname = ?1 and x.firstname = ?2         |      |      |
|        Or         |         findByLastnameOrFirstname         |         …  where x.lastname = ?1 or x.firstname = ?2         |      |      |
|     Is,Equals     | findByFirstnameIs,  findByFirstnameEquals |                  …  where x.firstname = ?1                   |      |      |
|      Between      |          findByStartDateBetween           |            …  where x.startDate between ?1 and ?2            |      |      |
|     LessThan      |             findByAgeLessThan             |                     …  where x.age < ?1                      |      |      |
|   LessThanEqual   |          findByAgeLessThanEqual           |                     …  where x.age ⇐ ?1                      |      |      |
|    GreaterThan    |           findByAgeGreaterThan            |                     …  where x.age > ?1                      |      |      |
| GreaterThanEqual  |         findByAgeGreaterThanEqual         |                     …  where x.age >= ?1                     |      |      |
|       After       |           findByStartDateAfter            |                  …  where x.startDate > ?1                   |      |      |
|      Before       |           findByStartDateBefore           |                  …  where x.startDate < ?1                   |      |      |
|      IsNull       |              findByAgeIsNull              |                    …  where x.age is null                    |      |      |
| IsNotNull,NotNull |           findByAge(Is)NotNull            |                   …  where x.age not null                    |      |      |
|       Like        |            findByFirstnameLike            |                 …  where x.firstname like ?1                 |      |      |
|      NotLike      |          findByFirstnameNotLike           |               … where  x.firstname not like ?1               |      |      |
|   StartingWith    |        findByFirstnameStartingWith        | …  where x.firstname like ?1 (parameter bound with appended %) |      |      |
|    EndingWith     |         findByFirstnameEndingWith         | …  where x.firstname like ?1 (parameter bound with prepended %) |      |      |
|    Containing     |         findByFirstnameContaining         | …  where x.firstname like ?1 (parameter bound wrapped in %)  |      |      |
|      OrderBy      |       findByAgeOrderByLastnameDesc        |         …  where x.age = ?1 order by x.lastname desc         |      |      |
|        Not        |             findByLastnameNot             |                  …  where x.lastname <> ?1                   |      |      |
|        In         |       findByAgeIn(Collection ages)        |                     …  where x.age in ?1                     |      |      |
|       NotIn       |      findByAgeNotIn(Collection age)       |                   …  where x.age not in ?1                   |      |      |
|       TRUE        |            findByActiveTrue()             |                   …  where x.active = true                   |      |      |
|       FALSE       |            findByActiveFalse()            |                  …  where x.active = false                   |      |      |
|    IgnoreCase     |         findByFirstnameIgnoreCase         |            …  where UPPER(x.firstame) = UPPER(?1)            |      |      |

 具体的关键字，使用方法和生产成SQL参考上面，具体使用方式参考JPQL 

#### 五，使用Specifications动态查询

有时我们在查询某个实体的时候，给定的条件是不固定的，这时就需要动态构建相应的查询语句，在Spring Data JPA中可以通过JpaSpecificationExecutor接口查询。相比JPQL,其优势是类型安全,更加的面向对象。

下面是JpaSpecificationExecutor中定义的方法

```java
public interface JpaSpecificationExecutor<T> {
   	//根据条件查询一个对象
 	T findOne(Specification<T> spec);	
   	//根据条件查询集合
 	List<T> findAll(Specification<T> spec);
   	//根据条件分页查询
 	Page<T> findAll(Specification<T> spec, Pageable pageable);
   	//排序查询查询
 	List<T> findAll(Specification<T> spec, Sort sort);
   	//统计查询
 	long count(Specification<T> spec);
}
```

对于JpaSpecificationExecutor，这个接口基本是围绕着**Specification接口**来定义的。我们可以简单的理解为，**Specification构造的就是查询条件**。

```java
 //构造查询条件
    /**
    *	root	：Root接口，代表查询的根对象，可以通过root获取实体中的属性
    *	query	：代表一个顶层查询对象，用来自定义查询
    *	cb		：用来构建查询，此对象里有很多条件方法
    **/
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
```

##### 使用Specifications 来动态查询的例子

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class testSpecifications {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 使用JpaSpecificationExecutor
     * 模糊查询
     */
    @Test
    public  void  testSpecifications(){
        
        //使用匿名内部类的方式，创建一个Specifications的实现类，并实现toPredicate 方法

        Specification<Customer> spec = new Specification<Customer>() {
            //构造查询条件
            /**
             *	root	：Root接口，代表查询的根对象，可以通过root获取实体中的属性
             *	query	：代表一个顶层查询对象，用来自定义查询
             *	cb		：用来构建查询，此对象里有很多条件方法
             **/
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //cd ：构造条件
                //root 从实体Customer 对象中按属性来查询
                Predicate predicate = cb.like(root.get("name").as(String.class), "翁%");
                return predicate;
            }
        };
        Customer test = customerDao.findOne(spec);
        System.out.println(test);
    }

    /**
     * 分页查询
     */
    @Test
    public  void testPage(){
        Specification<Customer> scp = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate like = criteriaBuilder.like(root.get("name").as(String.class), "翁%");
                return like;
            }
        };
        /**
         * 构造分页参数
         *  Pageable :接口
         *  PageRequest实现了Pageable接口，调用构造方法的形式构造
         * 		第一个参数：页码（从0开始）
         * 		第二个参数：每页查询条数
         */
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<Customer> all = customerDao.findAll(scp,pageRequest);
//        all.getTotalPages();//获取总页数
//        all.getTotalElements(); //获取总记录数
//        all.getContent();//获取列表数据
        System.out.println(all.getContent());
    }
}
```

#####  Specifications 方法对应关系

|          方法名称           |      Sql对应关系      |
| :-------------------------: | :-------------------: |
|            equle            |    filed =  value     |
|     gt（greaterThan ）      |    filed  > value     |
|       lt（lessThan ）       |    filed  < value     |
| ge（greaterThanOrEqualTo ） |    filed  >= value    |
|  le（ lessThanOrEqualTo）   |    filed  <= value    |
|          notEqule           |    filed  != value    |
|            like             |   filed  like value   |
|           notLike           | filed  not like value |

### Spring Data Jpa 多表的关联

在数据库中多表关系有三种，分别是**一对一**，**一对多**，**多对多**，其中使用最多的是一对多和多对多，而一对一这则很少，具体步骤则为：

**第一步：首先确定两张表之间的关系如果关系确定错了，后面做的所有操作就都不可能正确。**

**第二步：在数据库中实现两张表的关系**

**第三步：在实体类中描述出两个实体的关系**

**第四步：配置出实体类和数据库表的关系映射**

#### Spring Data Jpa中的一对多

在一对多关系中，我们**习惯把一的一方称之为主表**，把**多的一方称之为从表**。在数据库中建立一对多的关系，需要使用**数据库的外键约束**。

这里我使用客户和联系人的关系

​	一个客户有多个联系人，而一个联系人则有一个客户

##### 一，配置这两张表的实体类关系

一的一方配置多的一方 集合    （客户表）

```java
/**
 * 客户表
 * 实体类
 * 一的一方
 */
@Entity
@Table(name="customer_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "addr")
    private String addr;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "tell")
    private Integer tell;
    /**
     * 配置一对多
     */
    //建立一对多的关系映射
	@OneToMany(targetEntity = LikeMan.class)//指定多的多方的类的字节码
    //用于定义主键字段和外键字段的对应关系。
    @JoinColumn(name = "lik_cust_id",referencedColumnName = "id")
    //name:指定外键字段的名称 ,referencedColumnName:指定引用主表的主键字段名称
    private Set<LikeMan>  likemans=new HashSet<>();
    public Set<LikeMan> getLikemans() {
        return likemans;
    } 
    get&set 方法省略
}
```

多的一方配置一的一方的对象 （联系人表）

```java
package com.wym.domain;

import javax.persistence.*;

/**
 * 联系人表
 * 多的一方
 */
@Entity
@Table(name = "linkman_table")
public class LikeMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lid")
    private Integer lid ;
    @Column(name="lname")
    private String lname;
    @Column(name="lphone")
    private String lphone;

    /*配置多对一方*/
    @ManyToOne(targetEntity = Customer.class)//指定一的一方的字节码属性
    @JoinColumn(name = "lik_cust_id",referencedColumnName = "id")//referencedColumnName:指定引用主表的主键字段名称
    private Customer customer;//用它的主键，对应联系人表中的外键
    get和set 方法省略
}

```

##### 二, 测试一对多的关系

测试前要配置 他们的Dao接口 ，继承Jpa的接口 ，我上面有相关的讲述

```java
/**
 * 测试一对多
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class testOneToMany {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LikeManDao likeManDao;

    @Test
    @Transactional//开启事务
    @Rollback(false)//设置为不回滚
    public  void  testSave(){
        Customer customer=new Customer();
        customer.setName("翁艳萍");

        LikeMan l=new LikeMan();
        l.setLname("翁艳萍的员工");
        //建立关系
        customer.getLikemans().add(l);
        l.setCustomer(customer);

        customerDao.save(customer);
        likeManDao.save(l);
    }
}

```

这样就完成了Jpa 一对多的配置

##### 三，优化一对多的关系

   通过保存的案例，我们可以发现在设置了双向关系之后，会发送两条insert语句，一条多余的update语句，那我们的解决是思路很简单，就是一的一方放弃维护权一般是主表放弃维护权**@OneToMany(mappedBy = "customer")**，并配置了级联属性(在实际开发中，级联删除请慎用)，和立即加载属性
```java
@Entity
@Table(name="customer_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "addr")
    private String addr;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "tell")
    private Integer tell;
    
    /**
     * 配置一对多
     */
    /**
     * cascade:配置级联操作
     * 		CascadeType.MERGE	级联更新
     * 		CascadeType.PERSIST	级联保存：
     * 		CascadeType.REFRESH 级联刷新：
     * 		CascadeType.REMOVE	级联删除：
     * 		CascadeType.ALL		包含所有
     */
   /* @OneToMany(targetEntity = LikeMan.class)
    @JoinColumn(name = "lik_cust_id",referencedColumnName = "id")*/
   //fetch =FetchType.EAGER 立即加载
   @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch =FetchType.EAGER)//配置属性名
    private Set<LikeMan>  likemans=new HashSet<>();
    
    get和set省略
}

```

##### 四，注解的属性说明

```java'
@OneToMany:
   	作用：建立一对多的关系映射
    属性：
    	targetEntityClass：指定多的多方的类的字节码
    	mappedBy：指定从表实体类中引用主表对象的名称。
    	cascade：指定要使用的级联操作
    	fetch：指定是否采用延迟加载
    	orphanRemoval：是否使用孤儿删除

@ManyToOne
    作用：建立多对一的关系
    属性：
    	targetEntityClass：指定一的一方实体类字节码
    	cascade：指定要使用的级联操作
    	fetch：指定是否采用延迟加载
    	optional：关联是否可选。如果设置为false，则必须始终存在非空关系。

@JoinColumn
     作用：用于定义主键字段和外键字段的对应关系。
     属性：
    	name：指定外键字段的名称
    	referencedColumnName：指定引用主表的主键字段名称
    	unique：是否唯一。默认值不唯一
    	nullable：是否允许为空。默认值允许。
    	insertable：是否允许插入。默认值允许。
    	updatable：是否允许更新。默认值允许。
    	columnDefinition：列的定义信息。
```

#### Spring Data Jpa 中的多对多

  在数据库中多对多的关系一般的解决方法是创建一张中间表 来管理 多对多的关系

下面我使用 **角色**和 **用户**的关系来表示 多对多的关系

##### 一，配置用户表和角色表的实体关系 

 **用户表**默认放弃了关联维护权

```java
package com.wym.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 */
@Table(name = "sys_role")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Integer rid;

    @Column(name = "roldName")
    private String roldName;

    @Column(name = "roldMemo")
    private String roldMemo;

    /*配置多对多
     * mappedBy = "users" 对方的属性名
     *放弃维护权
     * */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
    
}

```

**角色表**来维护多表的关系

```java
package com.wym.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 */
@Table(name = "sys_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;

    @Column(name = "userCode")
    private String userCode;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userPwd")
    private String userPwd;

    @Column(name = "userState")
    private String userState;

    @ManyToMany
    @JoinTable(name = "user_role_rel",//中間表類型
            //中间表user_role_rel字段关联sys_role表的主键字段role_id
            //中间表的外键字段关联当前实体类所对应表的主键字段
            joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "uid")},
            //中间表user_role_rel的字段关联sys_user表的主键user_id
            //中间表的外键字段关联对方表的主键字段
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "rid")}
    )
    private Set<Role> roles = new HashSet<>();
}

```

##### 二，测试多对多的关系

```java
import com.wym.dao.RoleDao;
import com.wym.dao.UserDao;
import com.wym.domain.Role;
import com.wym.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试多对多
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class testManyToMany {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    @Rollback(false)
    public  void  testSave(){
        Role role=new Role();
        role.setRoldName("翁艳敏");
        role.setRoldMemo("weqwe");

        User user =new User();
        user.setUserName("weng");
        user.setUserCode("100");
		//建立关联
        role.getUsers().add(user);

        user.getRoles().add(role);

        roleDao.save(role);
        userDao.save(user);

    }
}
```

##### 三，注解的属性说明

```java
@ManyToMany
	作用：用于映射多对多关系
	属性：
		cascade：配置级联操作。
		fetch：配置是否采用延迟加载。
    	targetEntity：配置目标的实体类。映射多对多的时候不用写。

@JoinTable
    作用：针对中间表的配置
    属性：
    	nam：配置中间表的名称
    	joinColumns：中间表的外键字段关联当前实体类所对应表的主键字段			  			
    	inverseJoinColumn：中间表的外键字段关联对方表的主键字段
    	
@JoinColumn
    作用：用于定义主键字段和外键字段的对应关系。
    属性：
    	name：指定外键字段的名称
    	referencedColumnName：指定引用主表的主键字段名称
    	unique：是否唯一。默认值不唯一
    	nullable：是否允许为空。默认值允许。
    	insertable：是否允许插入。默认值允许。
    	updatable：是否允许更新。默认值允许。
    	columnDefinition：列的定义信息。
```

#### Spring Data Jpa 的多表查询

有了上面的多表关系与之而来的就是多表查询了，而Spring Data Jpa 中有这对象导航查询，从而不使用SQL 而是对象的方式来处理查询

```java
/**
 * 测试对象导航查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
class testObjectQuery {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LikeManDao likeManDao;

    /**
     * 测试关系对象导航（查询一个对象的时候，通过此对象查询所有的关联对象）
     */
    @Test
    @Transactional// 解决在java代码中的no session问题
    public void testQuery1() {
        //查询客户为一的客户
        Customer one = customerDao.getOne(1);
        //查询客户下的所有联系人
        Set<LikeMan> likemans = one.getLikemans();
        for (LikeMan likeMan : likemans) {
            System.out.println(likeMan);
        }
    }
	
	
    /**
     * 使用Specification的多表查询
     */
    @Test
    public void testFind() {
        Specification<LikeMan> spec = new Specification<LikeMan>() {
            @Override
            public Predicate toPredicate(Root<LikeMan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //创建的过程中，第一个参数为关联对象的属性名称，第二个参数为连接查询的方式（left，inner，right）
                //JoinType.LEFT : 左外连接,JoinType.INNER：内连接,JoinType.RIGHT：右外连接
                Join<LikeMan, Customer> customer = root.join("customer", JoinType.INNER);
                return criteriaBuilder.like(customer.get("name").as(String.class), "weng%");
            }
        };
        List<LikeMan> all = likeManDao.findAll(spec);
        for (LikeMan likeMan :
                all) {
            System.out.println(likeMan);
        }
    }
}

```

​                                                                                                                                                                          2010-1-15

​																																					                           weng@								


