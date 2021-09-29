package com.suyi.dms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suyi.dms.pojo.Dormitory;
import com.suyi.dms.pojo.Student;
import com.suyi.dms.pojo.Unbackform;
import com.suyi.dms.service.StudentService;
import com.suyi.dms.service.UnbackformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyi
 * @since 2021-04-10
 */
@Controller
@RequestMapping("/dms/unbackform")
public class UnbackformController {

    @Autowired
    UnbackformService unbackformService;

    @Autowired
    StudentService studentService;

    //查询全部
    @RequestMapping("/list")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String list(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("count",unbackformService.list().size());
        jsonObject.put("msg","签到信息");
        jsonObject.put("page",true);
        jsonObject.put("data",unbackformService.list());
        return jsonObject.toJSONString();
    }

    //添加
    @RequestMapping("/add")
    @ResponseBody
    public Map add(){
        List<Student> studentList = studentService.list();
        for (Student student : studentList) {
            Unbackform unbackform = new Unbackform();
            unbackform.setSno(student.getSno());
            unbackform.setSname(student.getSname());
            unbackform.setDepartment(student.getDepartment());
            unbackform.setRecordingtime(new Date());
            unbackformService.save(unbackform);
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code",1);
        map.put("msg","重置成功！");
        return map;
    }

}

