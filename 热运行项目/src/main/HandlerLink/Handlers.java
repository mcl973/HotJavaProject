package 热运行项目.HandlerLink;

import java.util.ArrayList;

public class Handlers {
    /**
     * 这里不使用volatile的原因是代码中会将list置空，而不是修改其指向的数据。
     */
    public  volatile ArrayList<Object> list;

    public ArrayList<Object> getList() {
        return list;
    }

    public void setList(ArrayList<Object> list) {
        this.list = null;
        this.list = list;
    }
}
