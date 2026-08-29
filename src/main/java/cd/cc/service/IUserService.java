package cd.cc.service;

import cd.cc.domain.po.User;
import cd.cc.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUserService extends IService<User>  {
    // 根据用户id 扣减余额
    void deductBalanceById(Long id, Integer amount);


    // 根据id 查询用户及其地址
    UserVO getUserAndAddressById(Long id);

    // 根据用户 id 批量查询，及其地址
    List<UserVO> getUserAndAddressByIds(List<Long> ids);
}