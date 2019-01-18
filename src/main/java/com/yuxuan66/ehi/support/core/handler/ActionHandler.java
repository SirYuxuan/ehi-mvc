package com.yuxuan66.ehi.support.core.handler;

import cn.hutool.core.util.StrUtil;
import com.yuxuan66.ehi.support.core.AppContext;
import com.yuxuan66.ehi.support.mvc.MappingHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Sir丶雨轩
 * @date 2019/1/17 11:16
 */
@Slf4j
public class ActionHandler extends AbstractHandler {

    private AppContext appContext = AppContext.getInstance();

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        Map<String, MappingHandler> mappingHandlerMap = appContext.getMappingHandlerMap();
        log.debug("静态资源处理Handler无法处理,转至本Handler");
        if(mappingHandlerMap.get(s) == null){
            //TODO 如果静态资源无法处理,请求Handler也无法处理,则转入 404处理,对外提供接口,可自定义404处理方式
            return;
        }

        log.debug(StrUtil.format("请求路径->{} 请求方式->{}",s,httpServletRequest.getMethod()));
        httpServletResponse.getWriter().print("共计:" + mappingHandlerMap.size() + "个链接映射");
        request.setHandled(true);

    }
}
