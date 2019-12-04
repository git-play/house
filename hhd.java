package com.zking.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        try {
            subject.login(token);
            HttpSession session = req.getSession();
            session.setAttribute("username",username);
            req.getRequestDispatcher("/main.jsp").forward(req,resp);

        } catch (AuthenticationException e) {
            HttpSession session = req.getSession();
            session.setAttribute("message","账户或者密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
            e.printStackTrace();
        }


    }
}
