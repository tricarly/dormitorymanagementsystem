package com.suyi.dms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suyi.dms.pojo.Dormitory;
import com.suyi.dms.pojo.Student;
import com.suyi.dms.service.DormitoryService;
import com.suyi.dms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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
@RequestMapping("/dms/dormitory")
public class DormitoryController {

    @Autowired
    DormitoryService dormitoryService;

    @Autowired
    StudentService studentService;

    //查询全部
    @RequestMapping("/list")
    @ResponseBody
    public Map list(Dormitory dormitory){
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        wrapper.eq(dormitory.getDno()!=null,"dno",dormitory.getDno())
                .eq(dormitory.getBuilding()!=null,"building",dormitory.getBuilding())
                .eq(StringUtils.isNotBlank(dormitory.getState()),"subject",dormitory.getState());
        List<Dormitory> list = dormitoryService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",list.size());
        map.put("msg","宿舍信息");
        map.put("page",true);
        map.put("data",list);
        return map;
    }

    //添加
    @RequestMapping("/add")
    @ResponseBody
    public Map add(Dormitory dormitory){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",dormitory.getSno()).last("LIMIT 1");
        Student student = studentService.getOne(wrapper);
        dormitory.setSname(student.getSname());
        dormitory.setPhone(student.getPhone());
        boolean save = dormitoryService.save(dormitory);
        HashMap<Object, Object> map = new HashMap<>();
        if (save){
            map.put("code",1);
            map.put("msg","新增成功！");
        }else {
            map.put("code",0);
            map.put("msg","新增失败！");
        }
        return map;
    }

    //跳转修改页面
    @RequestMapping("/toEdit")
    public String toEdit(String id, Model model){
        Dormitory dormitory = dormitoryService.getById(id);
        model.addAttribute("dormitory",dormitory);
        return "page/dormitory/edit";
    }

    //修改
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(Dormitory dormitory){
        System.out.println(dormitory);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",dormitory.getSno()).last("LIMIT 1");
        Student student = studentService.getOne(wrapper);
        dormitory.setSname(student.getSname());
        dormitory.setPhone(student.getPhone());
        boolean update = dormitoryService.updateById(dormitory);
        HashMap<Object, Object> map = new HashMap<>();
        if (update){
            map.put("code",1);
            map.put("msg","更新成功！");
        }else {
            map.put("code",0);
            map.put("msg","更新失败！");
        }
        return map;
    }

    //删除
    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(String ids){
        System.out.println(ids);
        HashMap<Object, Object> map = new HashMap<>();
        List<String> idList = Arrays.asList(ids.split(","));
        boolean remove = dormitoryService.removeByIds(idList);
        if (remove){
            map.put("code",1);
            map.put("msg","删除成功！");
        }else {
            map.put("code",0);
            map.put("msg","删除失败！");
        }
        return map;
    }

}

