package 热运行项目.SearchTargetFile.Util;

import java.util.ArrayList;

public interface GetImplements {
    public FilePathDefine fpd = new FilePathDefine();
    public FileToClass ftc = new FileToClass();
    /**
     * 查找文件，文件的默认格式由不同的实现方式实现，不提供外部的更爱后缀的参数。
     * @return
     */
    public ArrayList<Object> findFile();

    /**
     * 查找文件，文件的格式即后缀方式由参数提供。
     * @param path
     * @return
     */
    public  ArrayList<Object> findFile(String path);
}
