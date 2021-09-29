package com.suyi.dms.service.impl;

import com.suyi.dms.pojo.Application;
import com.suyi.dms.dao.ApplicationMapper;
import com.suyi.dms.service.ApplicationService;
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
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
