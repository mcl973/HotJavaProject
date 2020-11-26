## 这是一个热运行的项目
在程序运行起来后可以动态的添加后续的程序。
主要是通过扫描某个文件夹中是否有新的文件来判断是否有新的代码被添加到整个项目中。
内部启动了一个线程去处理扫描的过程。
如下：
'''
  public void run() {
        while(true) {
            String var1 = this.searchType;
            byte var2 = -1;
            switch(var1.hashCode()) {
            case 3254818:
                if (var1.equals("java")) {
                    var2 = 0;
                }
                break;
            case 94742904:
                if (var1.equals("class")) {
                    var2 = 1;
                }
            }

            switch(var2) {
            case 0:
                ArrayList<Object> file = this.searchFileJavaFile.findFile(this.path);
                if (this.handlers == null) {
                    this.handlers = new Handlers();
                }

                this.handlers.setList(file);
                break;
            case 1:
                ArrayList<Object> file1 = this.searchFileClassFile.findFile(this.path);
                if (this.handlers == null) {
                    this.handlers = new Handlers();
                }

                this.handlers.setList(file1);
            }

            if (this.baseLink != null) {
                this.baseLink.setHandlers(this.handlers);
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }
    }
'''
通过这个可以动态的感知到是否有新的文件的添加。
项目提供了一个父类BaseLink，提供使用者去继承，它可以通过这个来动态的添加BaseLink的实现类在BaseLink中的Map中，相当于一个监听器模式了。
'''
public abstract class BaseLink {
    public static volatile Map<Class, Handlers> handlersMap = new HashMap();

    public BaseLink() {
    }

    public BaseLink(Class currentClass, Handlers handlers) {
        handlersMap.put(currentClass, handlers);
    }

    public abstract void addHandler(Object var1);

    public abstract void removeHandler(Object var1);

    public abstract void clearHandler();

    public abstract void setHandlers(Handlers var1);
}
'''
本文提供了一个例子，以供参考
'''
public class Example {
    private static ArrayList<Integer> list = new ArrayList();
    private static volatile ArrayList<Object> list1;

    public Example() {
    }

    public void test(String filedir, String suffix) {
        AddOperationHandler addOperationHandler = new AddOperationHandler();
        SearchInLoop searchInLoop = new SearchInLoop(filedir, suffix, addOperationHandler);
        (new Thread(searchInLoop)).start();
        (new Thread(new Runnable() {
            public void run() {
                while(true) {
                    Map handlersMap = BaseLink.handlersMap;

                    while(!handlersMap.containsKey(AddOperationHandler.class)) {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException var17) {
                            var17.printStackTrace();
                        }
                    }

                    Handlers handlers = (Handlers)handlersMap.get(AddOperationHandler.class);

                    while(handlers.getList() == null) {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException var16) {
                            var16.printStackTrace();
                        }
                    }

                    while(handlers.getList().size() == 0) {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException var15) {
                            var15.printStackTrace();
                        }
                    }

                    ArrayList<String> list2 = new ArrayList();

                    for(int i = 0; i < 10; ++i) {
                        list2.add(i + "");
                    }

                    Iterator var18 = handlers.getList().iterator();

                    while(var18.hasNext()) {
                        Object o = var18.next();
                        Class<?> aClass = o.getClass();
                        Method[] declaredMethods = aClass.getDeclaredMethods();
                        Method[] var8 = declaredMethods;
                        int var9 = declaredMethods.length;

                        for(int var10 = 0; var10 < var9; ++var10) {
                            Method declaredMethod = var8[var10];

                            try {
                                declaredMethod.invoke(o, list2);
                            } catch (InvocationTargetException | IllegalAccessException var14) {
                                var14.printStackTrace();
                            }
                        }
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException var13) {
                        var13.printStackTrace();
                    }

                    System.out.println("////////////////////////////////////////////////////////");
                }
            }
        })).start();
    }
}
'''
其实核心有两个，一个是javac的编译，一个是自定义类加载器
javac编译如下：
'''
public static void compileJavaFile(File file) {
        if (file.exists()) {
            Runtime runtime = Runtime.getRuntime();
            String[] command = new String[]{"javac ", file.getAbsolutePath(), "-d", FilePathDefine.compileTargetPath};

            try {
                runtime.exec(command);
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

    }
'''
自定义类加载器为：
'''
  public class FileToClass extends ClassLoader {
    private String path;
    private byte[] returncontext = new byte[1048576];

    public FileToClass() {
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(this.getPath());

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] b = new byte[4096];
            boolean var6 = false;

            int n;
            while((n = fileInputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }

            this.returncontext = outputStream.toByteArray();
            return this.defineClass(name, this.returncontext, 0, this.returncontext.length);
        } catch (Exception var7) {
            var7.printStackTrace();
            return super.findClass(name);
        }
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
'''
都是很简单的组件，我只是使用了代码将其组装了起来而已。
运行起来的效果看起来还是不错的。
'''
public static void main(String[] args) {
    Example example = new Example();
    example.test("C:\\Users\\mcl\\Desktop\\test","java");
}
'''
其中C:\\Users\\mcl\\Desktop\\test是我自己的存放数据的一个文件，在这个文件里我总共存放了四个文件。
用来测试。
一开始是四个文件，都是可以运行的，然后我将这四个文件都拿出文件夹，此时下面的输出界面没有输出了。
然后我将这四个文件挨个的添加到文件夹中，现象出现了，每添加一个文件都会有相应的输出。
由此可以看到代码已经能够正常的运行了。

但是还是有不足的地方的，就是每一次的javac的编译其实都是调用的底层的javac的命令，每一次开启这个命令其实都是需要额外的开启一个京城的，所以如果额外开启的代码文件数量太多的话那么
将会导致性能问题。所以后续考虑的问题将是如何提升javac编译的问题。
