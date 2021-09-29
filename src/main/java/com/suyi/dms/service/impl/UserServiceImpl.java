package com.suyi.dms.service.impl;

import com.suyi.dms.pojo.User;
import com.suyi.dms.dao.UserMapper;
import com.suyi.dms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyi
 * @since 2021-04-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
