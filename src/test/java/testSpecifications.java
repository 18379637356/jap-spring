import com.wym.dao.CustomerDao;
import com.wym.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 使用Spring Data JPA 中的JpaSpecificationExecutor
 * 接口来添加动态查询
 * //根据条件查询一个对象
 *  	T findOne(Specification<T> spec);
 *    	//根据条件查询集合
 *  	List<T> findAll(Specification<T> spec);
 *    	//根据条件分页查询
 *  	Page<T> findAll(Specification<T> spec, Pageable pageable);
 *    	//排序查询查询
 *  	List<T> findAll(Specification<T> spec, Sort sort);
 *    	//统计查询
 *  	long count(Specification<T> spec);
 * }
 */
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
