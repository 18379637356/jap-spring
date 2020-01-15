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
 * @author wym
 */
public interface CustomerDao  extends JpaRepository<Customer,Integer> , JpaSpecificationExecutor<Customer>{
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
    /**
     * 使用sql
     * nativeQuery = true
     */
    @Query(value = "select  * from customer_table" ,nativeQuery = true)
    public Customer SqlFindAll();
    /**
     * 方法命名规则查询
     * 按照Spring Data JPA 定义的规则，查询方法以findBy开头，涉及条件查询时，
     * 条件的属性用条件关键字连接，要注意的是：条件属性首字母需大写。
     * 框架在进行方法名解析时，会先把方法名多余的前缀截取掉，然后对剩下部分进行解析
     */
    public Customer findByAddr(String addr);

}
