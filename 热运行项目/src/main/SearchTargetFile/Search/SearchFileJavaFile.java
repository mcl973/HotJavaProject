package 热运行项目.SearchTargetFile.Search;

import 热运行项目.SearchTargetFile.Util.FilePathDefine;
import 热运行项目.SearchTargetFile.Util.GetImplements;
import 热运行项目.SearchTargetFile.Util.FileDistributeAndCompile;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 默认查找后缀参数为java
 */
public class SearchFileJavaFile implements GetImplements {
    private static HashMap<String,Object> loadset = new HashMap<>();
    @Override
    public ArrayList<Object> findFile() {
        return findFile(fpd.getProjectPath());
    }

    @Override
    public ArrayList<Object> findFile(String path) {
        fpd.getFiles().clear();
        fpd.findFileBySuffix(path,"java");
        ArrayList<File> files = fpd.getFiles();
        ArrayList<Object> interfacesArrayList = new ArrayList<>();
        for (File file : files) {
            if (!loadset.containsKey(file.getAbsolutePath())) {
                FileDistributeAndCompile.compileJavaFile(file);
                String targetFilePath = FilePathDefine.compileTargetPath + file.getName().split("\\.")[0] + ".class";
                File file1 = fpd.waitFileExist(targetFilePath);
                String absolutePath = file1.getAbsolutePath();
                try {
                    ftc.setPath(absolutePath);
                    Class<?> aClass = ftc.findClass(file.getName().split("\\.")[0]);
                    Object o = aClass.newInstance();
                    interfacesArrayList.add(o);
                    loadset.put(file.getAbsolutePath(), o);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }
            }else{
                interfacesArrayList.add(loadset.get(file.getAbsolutePath()));
            }
        }
        files = null;
        return interfacesArrayList;
    }
}
