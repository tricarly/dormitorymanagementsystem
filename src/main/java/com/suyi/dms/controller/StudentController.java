package com.suyi.dms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suyi.dms.pojo.Dormitory;
import com.suyi.dms.pojo.Student;
import com.suyi.dms.service.StudentService;
import com.suyi.dms.service.impl.FaceServiceImpl;
import com.suyi.dms.utils.Base64Utils;
import com.suyi.dms.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
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
@RequestMapping("/dms/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    FaceServiceImpl faceService;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Student student){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(student.getSno()),"sno",student.getSno())
                .like(StringUtils.isNotBlank(student.getSname()),"sname",student.getSname())
                .eq(StringUtils.isNotBlank(student.getSex()),"sex",student.getSex())
                .eq(StringUtils.isNotBlank(student.getSubject()),"subject",student.getSubject());
        List<Student> list = studentService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",list.size());
        map.put("msg","学生信息");
        map.put("page",true);
        map.put("data",list);
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(Student student, MultipartFile file) throws IOException {
        System.out.println(student);
        System.out.println(file.getSize());
        BASE64Encoder base64Encoder =new BASE64Encoder();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(file.getOriginalFilename()+","+ base64Encoder.encode(file.getBytes()));
        faceService.registface(stringBuffer,student.getSno());
        HashMap<Object, Object> map = new HashMap<>();
        String picture = new UploadFileUtils().uploadFile(file);
        student.setPicture(picture);
        boolean save = studentService.save(student);
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
        Student student = studentService.getById(id);
        model.addAttribute("student",student);
        return "page/student/edit";
    }

    //修改
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(Student student,MultipartFile file) throws IOException {
        String picture = new UploadFileUtils().uploadFile(file);
        student.setPicture(picture);
        HashMap<Object, Object> map = new HashMap<>();
        boolean update = studentService.updateById(student);
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
        boolean remove = studentService.removeByIds(idList);
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

