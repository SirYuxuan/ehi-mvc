package com.yuxuan66.ehi.support.mvc;

import com.yuxuan66.ehi.support.mvc.annotation.Action;

import java.lang.reflect.Method;

/**
 * 请求包装类
 * @author Sir丶雨轩
 * @date 2019/1/18 23:26
 */
public class MappingHandler {
    private Object controller;
    private Method method;
    private Action.ActionType[] actionType;

    public MappingHandler(Object controller, Method method, Action.ActionType[] actionType) {
        this.controller = controller;
        this.method = method;
        this.actionType= actionType;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Action.ActionType[] getActionType() {
        return actionType;
    }

    public void setActionType(Action.ActionType[] actionType) {
        this.actionType = actionType;
    }
}
