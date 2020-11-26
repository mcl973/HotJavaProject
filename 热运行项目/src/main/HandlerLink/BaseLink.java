package 热运行项目.HandlerLink;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 链表汇总
 */
public abstract class BaseLink{
    /**
     * 存放所有链表的地方,可以通过类.class作为key来拿去相应的handlers
     */
    public static volatile Map<Class,Handlers> handlersMap = new HashMap<>();

    public BaseLink(){

    }
    public BaseLink(Class currentClass,Handlers handlers){
        handlersMap.put(currentClass,handlers);
//        new ExcuteTThread().start();
    }

    public abstract void addHandler(Object object);

    public abstract void removeHandler(Object object);

    public abstract void clearHandler();

    public abstract void setHandlers(Handlers handlers);

}
