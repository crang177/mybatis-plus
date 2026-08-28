package cd.cc.controller;


import cd.cc.domain.dto.UserFormDTO;
import cd.cc.domain.po.User;
import cd.cc.domain.vo.UserVO;
import cd.cc.service.IUserService;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Api("用户相关接口")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;


    @ApiOperation("新增用户")
    @PostMapping
    public void addUser(@RequestBody UserFormDTO dto){
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }



    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public  void deleteUser(@PathVariable Long id){
        log.info("根据id删除用户");
        userService.removeById(id);
    }



    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public  UserVO getById(@PathVariable Long id){
        log.info("根据id查询用户");
        User user = userService.getById(id);
        // hutool 包的工具类
        return BeanUtil.copyProperties(user, UserVO.class);
    }


    @ApiOperation("根据id批量查询用户")
    @GetMapping
    public  List<UserVO> getByIds(@RequestParam("ids") List<Long> ids){
        log.info("根据id批量查询用户");
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }

}

