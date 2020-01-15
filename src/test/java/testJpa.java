import com.wym.dao.CustomerDao;
import com.wym.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
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

}
