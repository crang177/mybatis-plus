package cd.cc.domain.vo;

import cd.cc.domain.po.UserInfo;
import cd.cc.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户VO实体")
public class UserVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详细信息")
    private UserInfo info;

    @ApiModelProperty("使用状态（1正常 2冻结）")
    private UserStatus status;

    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty("地址")
    private List<AddressVO> addresses;
}
