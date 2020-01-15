import com.wym.dao.CustomerDao;
import com.wym.dao.LikeManDao;
import com.wym.domain.Customer;
import com.wym.domain.LikeMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
    /**
     * 通过保存的案例，我们可以发现在设置了双向关系之后，会发送两条insert语句，
     * 一条多余的update语句，那我们的解决是思路很简单，就是一的一方放弃维护权
     *
     * 一般是主表放弃维护权
     *
     * @OneToMany(mappedBy = "customer")//多一方的属性名
     *
     */

    /**
     * 配置级联删除
     * 开发中最好不要使用
     */

    @Test
    public void testDelete(){

        /*Customer customer = new Customer();
        customer.setId(1);*/
        customerDao.delete(1);
    }

}
