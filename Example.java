package 热运行项目.Examples;

import 热运行项目.HandlerLink.BaseLink;
import 热运行项目.HandlerLink.Handlers;
import 热运行项目.SearchTargetFile.SearchInLoop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class Example {
    private static ArrayList<Integer> list = new ArrayList<>();
    private static volatile ArrayList<Object> list1;

    /**
     *  new SearchInLoop("C:\\Users\\mcl\\Desktop\\test","java",addOperationHandler);
     */
    public void test(String filedir,String suffix){
        AddOperationHandler addOperationHandler = new AddOperationHandler();
        SearchInLoop searchInLoop = new SearchInLoop(filedir,suffix,addOperationHandler);
        //开启
        new Thread(searchInLoop).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    /**
                     * 具体的运行的代码
                     */
                    Map<Class, Handlers> handlersMap = BaseLink.handlersMap;
                    while (!handlersMap.containsKey(AddOperationHandler.class)){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Handlers handlers = handlersMap.get(AddOperationHandler.class);
                    list1 = handlers.getList();
                    while (handlers.getList() == null) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (handlers.getList().size() == 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                            即时获取数据
//                            list = handlers.getList();
                    }
                    ArrayList<String> list2 = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        list2.add(i + "");
                    }
                    for (Object o : handlers.getList()) {
                        Class<?> aClass = o.getClass();
                        Method[] declaredMethods = aClass.getDeclaredMethods();
                        for (Method declaredMethod : declaredMethods) {
                            try {
                                declaredMethod.invoke(o, list2);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("////////////////////////////////////////////////////////");
                }
            }
        }).start();
    }
}
