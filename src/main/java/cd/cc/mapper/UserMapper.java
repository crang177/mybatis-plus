package cd.cc.mapper;


import cd.cc.domain.po.User;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 继承 BaseMapper。基于反射获取实体类信息作为数据库表信息
 */
public interface UserMapper extends BaseMapper<User> {


    // 拼接的 wrapper 的param 必须设置为 ew
    void updateBalanceByWrapper(@Param("amount") int amount,@Param("ew") LambdaUpdateWrapper<User> updateWrapper);
}
