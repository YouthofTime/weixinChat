package cn.itcast.utils;

import cn.itcast.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerLogin implements HandlerInterceptor {
    // 控制器方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user= (User) request.getSession().getAttribute("user");
        String url = request.getRequestURI();
        if(user==null){
            response.sendRedirect("/index.jsp");
            return false;
        }
        return true;
    }
}
