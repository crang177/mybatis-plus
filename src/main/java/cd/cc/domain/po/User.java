package cd.cc.domain.po;

import cd.cc.enums.UserStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data

@TableName("user") // 映射数据库的表名，默认为类名的小写（满足驼峰命名的转换）
public class User {

    /**
     * 用户id
     */
    //指定表中的主键字段信息.  type主键的增加方式，
//            IdType.AUTO 自增 ； IdType.INPUT 手动输入 ； IdType.ASSIGN_ID 雪花算法，得到一串数值字符串
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    // 指定表中的其他字段信息  ,exist = false当数据库中不需要某个属性时，使用这个忽略
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 详细信息
     */
    private String info;

    /**
     * 使用状态（1正常 2冻结）
     */
    private UserStatus status;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
