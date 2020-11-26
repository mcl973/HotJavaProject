package 热运行项目.Examples;

import 热运行项目.HandlerLink.BaseLink;
import 热运行项目.HandlerLink.Handlers;

import java.util.ArrayList;

public class AddOperationHandler extends BaseLink {
    public AddOperationHandler(){

    }

    @Override
    public void addHandler(Object o) {
        handlersMap.get(AddOperationHandler.class).getList().add(o);
    }

    @Override
    public void removeHandler(Object o) {
        ArrayList<Object> list =
                super.handlersMap.get(AddOperationHandler.class).getList();
        list.remove(o);
    }

    @Override
    public void clearHandler() {
        if (handlersMap.containsKey(AddOperationHandler.class))
            handlersMap.get(AddOperationHandler.class).getList().clear();
    }

    @Override
    public void setHandlers(Handlers handlers) {
            handlersMap.put(AddOperationHandler.class, handlers);
    }
}
