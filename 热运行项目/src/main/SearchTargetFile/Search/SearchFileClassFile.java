package 热运行项目.SearchTargetFile.Search;

import 热运行项目.SearchTargetFile.Util.GetImplements;

import java.io.File;
import java.util.ArrayList;

/**
 * 默认查找后缀参数为class
 */
public class SearchFileClassFile implements GetImplements {

    @Override
    public ArrayList<Object> findFile() {
        return findFile(fpd.getProjectPath());
    }

    @Override
    public ArrayList<Object> findFile(String path) {
        fpd.findFileBySuffix(fpd.getProjectPath(),"class");
        ArrayList<File> files = fpd.getFiles();
        ArrayList<Object> interfacesArrayList = new ArrayList<>();
        for (File file : files) {
            try {
                ftc.setPath(file.getAbsolutePath());
                Class<?> aClass = ftc.findClass(file.getName().split("\\.")[0]);
                interfacesArrayList.add( aClass.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
        }
        return interfacesArrayList;
    }

}
