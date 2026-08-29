package mapper;

import cd.cc.domain.po.User;
import cd.cc.mapper.UserMapper;

import cd.cc.service.IUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import cd.cc.MpApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = MpApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService iUserService;

    @Test
    void testInsert() {
        User user = new User();
//        user.setId(6L);
        user.setUsername("aaa");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(2092972589905784833L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(2092972589905784833L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }


    @Test
    void testPage(){
        // 创建分页对象
        Page<User> page = new Page<>();
        // 分页查询
        Page<User> userPage = iUserService.page(page);

        System.out.println("总页数 = " + userPage.getPages());
        System.out.println("总记录数 = " + userPage.getTotal());
        // 记录的user列表
        for (User user : userPage.getRecords()) {
            System.out.println(user);
        }

    }
}