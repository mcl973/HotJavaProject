package 热运行项目.SearchTargetFile.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileToClass extends ClassLoader{
    private String path;
    private byte[] returncontext = new byte[1024*1024];
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(getPath());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //每次读取的大小是4kb
            byte[] b = new byte[4 * 1024];
            int n = 0;
            while ((n = fileInputStream.read(b)) != -1) {
//                下个将数据读入到字节数组中，再将字节数组输出到字节输出流中
                outputStream.write(b, 0, n);
            }
            //将Dog.class类读取到byte数组里
            returncontext = outputStream.toByteArray();
            //调用defineClass 将byte 加载成class

            return this.defineClass(name, returncontext, 0, returncontext.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
