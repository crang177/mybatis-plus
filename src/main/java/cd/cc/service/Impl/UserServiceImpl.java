package cd.cc.service.Impl;

import cd.cc.domain.po.Address;
import cd.cc.domain.po.User;
import cd.cc.domain.vo.AddressVO;
import cd.cc.domain.vo.UserVO;
import cd.cc.mapper.UserMapper;
import cd.cc.service.IUserService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @Override
    public UserVO getUserAndAddressById(Long id) {
        User user = getById(id);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();
        userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        return userVO;
    }

    @Override
    public List<UserVO> getUserAndAddressByIds(List<Long> ids) {
        List<User> users = listByIds(ids);
        List<UserVO> userVOList = BeanUtil.copyToList(users, UserVO.class);

        List<Address> addressVOList = Db.lambdaQuery(Address.class)
                .in(Address::getUserId, ids)
                .list();

        Map<Long, List<Address>> addressMap = addressVOList.stream().collect(Collectors.groupingBy(Address::getUserId));

        userVOList.forEach(userVO -> {
            userVO.setAddresses(BeanUtil.copyToList(addressMap.get(userVO.getId()), AddressVO.class));
        });

        return userVOList;
    }

}
