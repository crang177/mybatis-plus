package service;

import cd.cc.MpApplication;
import cd.cc.domain.po.User;
import cd.cc.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = MpApplication.class)
public class UserServiceTest {

    @Autowired
    private IUserService userService;


    /**
     * 批量新增大量数据。
     * 使用普通 for
     * 耗时最差
     */
    @Test
    public void testInsertBatch_01(){
        Long start = System.currentTimeMillis();
        for (int i = 1; i < 100000; i++) {
            userService.save(buildUser(i));
        }

        Long end = System.currentTimeMillis();
        System.out.println("耗时： " + (end - start) + " ms" );

    }

    /**
     * 批量新增大量数据。
     * 使用普通 IService 的 批量插入方法
     * 本质还是 普通for
     * 耗时适中
     */
    @Test
    public void testInsertBatch_02(){
        Long start = System.currentTimeMillis();
        // 一次插入一千条数据
        List<User> list = new ArrayList<>(1000);
        for (int i = 1; i < 100000; i++) {
            list.add(buildUser(i));
            if (i % 1000 == 0 ){
                // 本质还是一条一条的插入，不是使用的是 values (),(),() 的形式
                userService.saveBatch(list);
                list.clear(); // 清空列表，只是空列表，不是null
            }

        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时： " + (end - start) + " ms" );
    }


    /**
     * 批量新增大量数据。
     * 使用 IService 的 批量插入方法。与第二种方式的代码一模一样，只是在配置文件新增了值
     * 开启配置 ：   在数据库的配置文件中 设置 rewriteBatchedStatements=true
     * 耗时最短
     */
    @Test
    public void testInsertBatch_03(){
        Long start = System.currentTimeMillis();
        // 一次插入一千条数据
        List<User> list = new ArrayList<>(1000);
        for (int i = 1; i < 100000; i++) {
            list.add(buildUser(i));
            if (i % 1000 == 0 ){
                // 设置了rewriteBatchedStatements=true，就是使用的是 values (),(),() 的形式
                userService.saveBatch(list);
                list.clear(); // 清空列表，只是空列表，不是null
            }

        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时： " + (end - start) + " ms" );
    }


    private User buildUser(int count){
        User user = new User();
        user.setUsername("user_" + count);
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }

}
