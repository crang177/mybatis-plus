package cd.cc.service.Impl;

import cd.cc.domain.po.User;
import cd.cc.mapper.UserMapper;
import cd.cc.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

}
