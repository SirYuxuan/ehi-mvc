package com.yuxuan66.demo.web;

import com.yuxuan66.ehi.support.core.AppContext;
import com.yuxuan66.ehi.support.mvc.annotation.*;

import java.io.IOException;

/**
 * @author Sir丶雨轩
 * @date 2019/1/17 11:49
 */
@Action
@Path("user/")
public class UserController {

    @Get
    public void get(String id) throws IOException {
        AppContext.getInstance().getResponse().getOutputStream().print("用户ID:" + id);
        System.out.println("执行完毕");
    }
    @Post
    public void add(){

    }
    @Post("/update")
    public void update(){

    }
    @Del
    public void del(){

    }



}
