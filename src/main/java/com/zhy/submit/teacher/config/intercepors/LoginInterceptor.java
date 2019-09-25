package com.zhy.submit.teacher.config.intercepors;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        HttpSession session=request.getSession();
        String teacherNumber= (String) session.getAttribute("teacherNumber");
        if(teacherNumber==null) {
            System.out.println("您还未登录，请先登录");
            response.sendRedirect(request.getContextPath()+"/teacher/login");

            return false;
        }
        else
            return true;
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
