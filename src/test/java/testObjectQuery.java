import com.wym.dao.CustomerDao;
import com.wym.dao.LikeManDao;
import com.wym.domain.Customer;
import com.wym.domain.LikeMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

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
