package cd.cc.service;

import cd.cc.domain.po.User;
import cd.cc.domain.query.UserQuery;
import cd.cc.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUserService extends IService<User>  {
    // 根据用户id 扣减余额
    void deductBalanceById(Long id, Integer amount);



}
