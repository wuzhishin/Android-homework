package com.hitsz.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HttpServerLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("This is servlet, Request: Post");

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");
        String result = "";
        boolean isExist = false;
        //核对数据库中是否已有该用户名
        UserDao b = new UserDaoImpl();
        if(!b.findByName(name)){
            System.out.println("Register Succes!");
            isExist = true;
            //写回数据
            b.doAdd(new User(name,pwd));
            b.writeBack();
        }
        if (isExist == true) {// 用户注册成功
            result = "success";
        } else {// 已存在该user
            result = "failed";
        }
        out.write(result);
        out.flush();
        out.close();
        System.out.println(result);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TThis is servlet, Request: GET");

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");
        String result = "";
        boolean isExist = false;
        // 通过客户端返回的用户名跟密码进行登录验证
        UserDao a = new UserDaoImpl();

        if (a.login(name, pwd)) {
            System.out.println("Login Succes!");
            isExist = true;
        }
        if (isExist == true) {// 用户登录验证成功
            result = "success";
        } else {// 用户名或密码错误
            result = "failed";
        }
        out.write(result);
        out.flush();
        out.close();
        System.out.println(result);
    }
}
