package com.suyi.dms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suyi.dms.pojo.Application;
import com.suyi.dms.pojo.Record;
import com.suyi.dms.pojo.Student;
import com.suyi.dms.service.ApplicationService;
import com.suyi.dms.service.RecordService;
import com.suyi.dms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyi
 * @since 2021-04-10
 */
@Controller
@RequestMapping("/dms/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    StudentService studentService;

    @Autowired
    RecordService recordService;

    //查询全部
    @RequestMapping("/list")
    @ResponseBody
    public Map list(Application application){
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(application.getSno()),"sno",application.getSno())
                .like(StringUtils.isNotBlank(application.getSname()),"sname",application.getSname());
        List<Application> list = applicationService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",list.size());
        map.put("msg","申请信息");
        map.put("page",true);
        map.put("data",list);
        return map;
    }

    //跳转修改页面
    @RequestMapping("/detail")
    public String detail(String id, Model model){
        Application application = applicationService.getById(id);
        model.addAttribute("applicationInfo",application);
        return "page/application/detail";
    }

    /*@RequestMapping("/list")
    public Map list(HttpSession session){
        String sname = (String) session.getAttribute("user");
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("sname",sname).last("LIMIT 1");
        Application application = applicationService.getOne(wrapper);
        HashMap<Object, Object> map = new HashMap<>();
        if (application==null){
            map.put("code",0);
            map.put("application",application);
        }
    }*/

    @RequestMapping("/add")
    @ResponseBody
    public Map add(Application application){
        System.out.println(application);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",application.getSno()).last("LIMIT 1");
        Student student = studentService.getOne(wrapper);
        application.setDnobefore(student.getDno());
        application.setSname(student.getSname());
        application.setPhone(student.getPhone());
        application.setState("审核中");
        boolean save = applicationService.save(application);
        Record record = new Record();
        record.setSno(application.getSno());
        record.setSname(application.getSname());
        record.setDnobefore(application.getDnobefore());
        record.setDnoafter(application.getDnoafter());
        record.setReason(application.getReason());
        record.setState(application.getState());
        recordService.save(record);
        HashMap<Object, Object> map = new HashMap<>();
        if (save){
            map.put("code",1);
            map.put("msg","添加成功！");
        }else {
            map.put("code",0);
            map.put("msg","新增失败！");
        }
        return map;
    }

}

