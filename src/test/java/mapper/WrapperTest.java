package mapper;

import cd.cc.MpApplication;
import cd.cc.domain.po.User;
import cd.cc.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MpApplication.class)
//@RequiredArgsConstructor// 自动生成包含所有 final 字段的构造器
public class WrapperTest {
//    private final UserMapper userMapper;

    @Autowired
    private  UserMapper userMapper;


    @Test
    void testQueryWrapper01(){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

//        使用 QueryWrapper  设置查询条件. 只能写查询条件
        queryWrapper
                .select("id","username","info","balance") //要查询的字段
                .like("username","o") // 模糊查询，默认会有%拼接
                .ge("balance",200); // 存款大于等于

        List<User> users = userMapper.selectList(queryWrapper); // 使用 QueryWrapper 查询
        System.out.println("users = " + users);


        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username","jack");
        User user = new User();
        user.setBalance(10);
        // 由于更新策略为非空，所以为空的字段不会修改
        int updateCount = userMapper.update(user,queryWrapper1);
    }


    /**
     * 存在 使用表中的字段名 进行 balance = balance - 2 这种操作 ， QueryWrapper无法这样子使用
     */
    @Test
    void testUpdateWrapper(){

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .setSql("balance = balance - 100")  // 自定义 sql 语句 ，为update的set 后面的部分
                .in("id",List.of(1L,2L,3L));   // where 下的条件

        userMapper.update(null,updateWrapper);
    }






    // 更常使用：LambdaQueryWrapper和LambdaUpdateWrapper
    @Test
    void testLambdaQueryWrapper(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .select(User::getId,User::getUsername,User::getInfo,User::getBalance)
                .like(User::getUsername,"o")
                .ge(User::getBalance,5000)
                .orderByDesc(User::getBalance)  // 根据薪水降序
                .last("limit 5") ;// 自定义sql

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);


        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,"jack");
        User user = new User();
        user.setBalance(3000);
        // 由于更新策略为非空，所以为空的字段不会修改
        int updateCount = userMapper.update(user,wrapper);
    }



    @Test
    void testLambdaUpdateWrapper(){
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .setSql("balance = balance - 100")  // 自定义 sql 语句 ，为update的set 后面的部分
                .in(User::getId,List.of(1L,2L,3L));   // where 下的条件

        userMapper.update(null,updateWrapper);
    }




    // 自定义 SQL 拼接。写的sql语句应该在mapper层
    @Test
    void testLambdaUpdateWrapper02(){
        List<Long> ids = List.of(1L, 2L, 3L);
        int amount = 300;

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(User::getId,ids);   // where 下的条件
        userMapper.updateBalanceByWrapper(amount,updateWrapper);
    }


}
