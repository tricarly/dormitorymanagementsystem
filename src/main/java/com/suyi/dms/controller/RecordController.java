package com.suyi.dms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suyi.dms.pojo.Application;
import com.suyi.dms.pojo.Dormitory;
import com.suyi.dms.pojo.Record;
import com.suyi.dms.pojo.Student;
import com.suyi.dms.service.ApplicationService;
import com.suyi.dms.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/dms/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @Autowired
    ApplicationService applicationService;

    //查询全部
    @RequestMapping("/list")
    @ResponseBody
    public Map list(Record record){
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(record.getSno()),"sno",record.getSno())
                .like(StringUtils.isNotBlank(record.getSname()),"sname",record.getSname());
        List<Record> list = recordService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",list.size());
        map.put("msg","记录信息");
        map.put("page",true);
        map.put("data",list);
        return map;
    }

    //跳转修改页面
    @RequestMapping("/toEdit")
    public String toEdit(String id, Model model){
        Record record = recordService.getById(id);
        model.addAttribute("record",record);
        return "page/record/edit";
    }

    //修改
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(Record record){
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("id",record.getId()).last("LIMIT 1");
        Application application = applicationService.getOne(wrapper);
        application.setState(record.getState());
        application.setOpinion(record.getOpinion());
        boolean update = applicationService.updateById(application);
        boolean remove = recordService.removeById(record);
        HashMap<Object, Object> map = new HashMap<>();
        if (update && remove){
            map.put("code",1);
            map.put("msg","操作成功！");
        }else {
            map.put("code",0);
            map.put("msg","操作失败！");
        }
        return map;
    }

}

