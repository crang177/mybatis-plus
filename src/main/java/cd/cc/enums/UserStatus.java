package cd.cc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(1,"正常"),
    FREEZE(2,"冻结");

    // 保存到数据库时，使用的属性
    @EnumValue // 枚举中，存入数据库的是哪个值( 1 , 2)的注解
    private Integer code ;

    // 显示到前端时候显示的属性值
    @JsonValue// 枚举返回前端的是什么值（"正常"，"冻结" 等）
    private String message;


    UserStatus(Integer code, String message) {
        this.code = code ;
        this.message = message;
    }
}
