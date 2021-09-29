package com.suyi.dms.service.impl;

import com.suyi.dms.dto.Setingmodel;
import com.suyi.dms.face.BaiduAIFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class FaceServiceImpl {

    @Autowired
    BaiduAIFace faceapi;
    @Autowired
    Setingmodel setingmodel;
    public Map<String,Object> searchface(StringBuffer imagebase64) throws IOException {
        String substring = imagebase64.substring(imagebase64.indexOf(",")+1, imagebase64.length());
        setingmodel.setImgpath(substring);
        setingmodel.setGroupID("su");  //需要在百度智能云上创建人脸识别库
        System.out.println(substring);
        Map map = faceapi.FaceSearch(setingmodel);
        return map;

    }
    public Map<String,Object> registface(StringBuffer stringBuffer,String sno) throws IOException {
        String substring = stringBuffer.substring(stringBuffer.indexOf(",")+1, stringBuffer.length());
        setingmodel.setImgpath(substring);
        setingmodel.setGroupID("su");  //需要在百度智能云上创建人脸识别库
        setingmodel.setUserID(sno);
        Map map = faceapi.FaceRegistration(setingmodel);
        return map;

    }
}
