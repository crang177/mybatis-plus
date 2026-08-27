package cd.cc.mapper;


import cd.cc.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 继承 BaseMapper。基于反射获取实体类信息作为数据库表信息
 */
public interface UserMapper extends BaseMapper<User> {

}
