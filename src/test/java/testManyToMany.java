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

        role.getUsers().add(user);

        user.getRoles().add(role);

        roleDao.save(role);
        userDao.save(user);

    }

}
