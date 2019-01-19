package com.yuxuan66.ehi.support.core.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.yuxuan66.ehi.support.core.AppContext;
import com.yuxuan66.ehi.support.mvc.MappingHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        request.setHandled(true);
        MappingHandler mappingHandler = getMappirngHandlerByUrl(s, httpServletRequest.getMethod());
        if (mappingHandler == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        appContext.setRequest(httpServletRequest);
        appContext.setResponse(httpServletResponse);
        Method requestMethod = mappingHandler.getMethod();
        Object controller = mappingHandler.getController();
        List<Object> params = new ArrayList<>();
        for (Parameter param : requestMethod.getParameters()) {
            params.add(ConverterRegistry.getInstance().convert(param.getType(), request.getParameter(param.getName()),""));
        }

        try {
            requestMethod.invoke(controller, params.toArray());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        log.debug(StrUtil.format("请求路径->{} 请求方式->{}", s, httpServletRequest.getMethod()));

    }


    public MappingHandler getMappirngHandlerByUrl(String url, String method) {
        Map<String, MappingHandler> mappingHandlerMap = appContext.getMappingHandlerMap();
        for (String key : mappingHandlerMap.keySet()) {
            String[] keys = key.split(".#00#.");
            JSONArray actionType = JSONUtil.parseArray(keys[1]);
            if (url.equals(keys[0].startsWith("/") ? keys[0] : "/" + keys[0]) && actionType.contains(method)) {
                return mappingHandlerMap.get(key);
            }
        }
        return null;
    }
}
