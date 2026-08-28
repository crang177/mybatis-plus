package cd.cc.service.Impl;

import cd.cc.domain.po.User;
import cd.cc.domain.query.UserQuery;
import cd.cc.domain.vo.UserVO;
import cd.cc.mapper.UserMapper;
import cd.cc.service.IUserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public void deductBalanceById(Long id, Integer amount) {
        User user = getById(id); // 使用service 的方法（MyBatis-Plus）
        if (user == null || user.getStatus() == 2){
            throw new RuntimeException("用户状态异常");
        }
        if (user.getBalance() < amount){
            throw  new RuntimeException("用户余额不足");
        }

        int remainBalance = user.getBalance() - amount;
        this.lambdaUpdate()
                        .set(User::getBalance,remainBalance)
                        .set(remainBalance==0 , User::getStatus,2)
                        .eq(User::getId,id)
                        .eq(User::getBalance,user.getBalance())
                        .update();



    }

}
