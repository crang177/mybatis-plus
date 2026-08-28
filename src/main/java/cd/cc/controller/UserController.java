package cd.cc.controller;


import cd.cc.domain.dto.UserFormDTO;
import cd.cc.domain.po.User;
import cd.cc.domain.query.UserQuery;
import cd.cc.domain.vo.UserVO;
import cd.cc.service.IUserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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



    @ApiOperation("根据id扣减余额")
    @PutMapping("/{id}/deduction/{amount}")
    public void updateBalanceById(@PathVariable Long id , @PathVariable Integer amount){
        userService.deductBalanceById(id,amount);
    }


    @ApiOperation("根据条件查询用户列表")
    @PostMapping("/list")
    public List<UserVO> queryList(@RequestBody UserQuery userQuery){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String username = userQuery.getName();
        Integer status = userQuery.getStatus();
        Integer minBalance = userQuery.getMinBalance();
        Integer maxBalance = userQuery.getMaxBalance();


        // hutool 工具类 ：StrUtil.isNotBlank(username)   不等于null且不为空字符串
//        queryWrapper
//                // 成立的条件,sql中的字段,查询的关键字 。  第一个值为 true 才当前设置这个条件到 where 中去
//                .like(StrUtil.isNotBlank(username),User::getUsername,username) // 第一个参数的式子成立才加入sql语句中
//                .eq(status!=null,User::getStatus,status)
//                .gt(minBalance!=null,User::getBalance,minBalance)
//                .le(maxBalance!=null,User::getBalance,maxBalance);
//        List<User> users = userService.list(queryWrapper);

//        等价于
        List<User> users = userService.lambdaQuery()
                .like(StrUtil.isNotBlank(username), User::getUsername, username) // 第一个参数的式子成立才加入sql语句中
                .eq(status != null, User::getStatus, status)
                .gt(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();

        return BeanUtil.copyToList(users, UserVO.class);


    }




}

