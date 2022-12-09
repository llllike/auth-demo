package com.mv.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    @RequestMapping("/test1/{path}")
    public String test1(@MatrixVariable("name")String name, @MatrixVariable("hobbies")List<String> hobbies, @PathVariable String path){
        StringBuffer stringBuffer=new StringBuffer();
        for (String s:hobbies) {
            stringBuffer.append(s+",");
        }
        return "name="+name+"</br>"+"hobbies"+stringBuffer.toString()+"</br>"+"path="+path;
    }
}
