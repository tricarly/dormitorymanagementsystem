package com.suyi.dms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suyi.dms.pojo.Backform;
import com.suyi.dms.pojo.Student;
import com.suyi.dms.pojo.Unbackform;
import com.suyi.dms.service.BackformService;
import com.suyi.dms.service.StudentService;
import com.suyi.dms.service.UnbackformService;
import com.suyi.dms.service.impl.FaceServiceImpl;
import com.suyi.dms.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
@RequestMapping("/dms/backform")
@SessionAttributes(value = "userinf")
public class BackformController {

    @Autowired
    FaceServiceImpl faceService=null;

    @Autowired
    BackformService backformService;

    @Autowired
    StudentService studentService;

    @Autowired
    UnbackformService unbackformService;

    //查询全部
    @RequestMapping("/list")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String list(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("count",backformService.list().size());
        jsonObject.put("msg","签到信息");
        jsonObject.put("page",true);
        jsonObject.put("data",backformService.list());
        return jsonObject.toJSONString();
    }

    @RequestMapping("/search")
    @ResponseBody
    public String searchface(@RequestBody @RequestParam(name = "imagebast64") StringBuffer imagebast64, Model model, HttpServletRequest request) throws IOException {
        Map<String, Object> searchface = faceService.searchface(imagebast64);
        if(searchface==null||searchface.get("user_id")==null){
            String flag="fail";
            String s = GsonUtils.toJson(flag);
            return s;
        }
        String user_id = searchface.get("user_id").toString();
        String score=searchface.get("score").toString().substring(0,2);
        int i = Integer.parseInt(score);
        if(i>80){
            model.addAttribute("userinf",user_id);
            HttpSession session = request.getSession();
            session.setAttribute("userinf",user_id);
            System.out.println("存入session");
        }
        System.out.println("--------------------------------------------------------------------"+searchface);
        String s = GsonUtils.toJson(searchface);
        return s;
    }

    @RequestMapping("/success")
    public String Faceloginsucceed(HttpSession session){
        System.out.println(1222222);
        String sno = (String) session.getAttribute("userinf");
        QueryWrapper<Student> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("sno",sno).last("LIMIT 1");
        Student student = studentService.getOne(wrapper1);
        QueryWrapper<Backform> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("sno",sno);
        backformService.remove(wrapper2);
        Backform backform = new Backform();
        backform.setSno(sno);
        backform.setSname(student.getSname());
        backform.setDepartment(student.getDepartment());
        backformService.save(backform);
        QueryWrapper<Unbackform> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("sno",sno);
        unbackformService.remove(wrapper3);
        return "page/backform/list";
    }

}

