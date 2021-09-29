package com.suyi.dms.service.impl;

import com.suyi.dms.pojo.Student;
import com.suyi.dms.dao.StudentMapper;
import com.suyi.dms.service.StudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
