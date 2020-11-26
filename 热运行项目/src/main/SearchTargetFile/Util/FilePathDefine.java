package 热运行项目.SearchTargetFile.Util;

import java.io.File;
import java.util.ArrayList;

public class FilePathDefine {

    //获取项目路径
    private static  String projectPath = System.getProperty("user.dir");
    //编译的文件放在这里
    public static String compileTargetPath = projectPath+"\\extern\\";
    static {
        File file = new File(compileTargetPath);
        if (!file.exists()) {
            final boolean mkdir = file.mkdir();
        }
    }
//    存放匹配到的文件的容器
    private ArrayList<File> files = new ArrayList<>();
//    默认的文件名后缀
    @Deprecated
    private String defaultSuffix = "txt";

    /**
     * 得到项目路径
     * @return 到项目路径
     */
    public String getProjectPath() {
        return projectPath;
    }

    /**
     *  默认的格式是txt结尾格式的。
     * @param path  需要查找的路径
     * @return void
     */
    public void findFile(String path){
        File file = new File(path);
        if (file.exists()){
            if (file.isDirectory()){
                findFile(path+"\\"+file.getName());
            }else {
                if (file.getName().contains(".")&&defaultSuffix.equals(file.getName().split(".")[1]))
                    files.add(file);
            }
        }
    }

    /**
     *  可以自定义的后缀的查找方式，一般是java
     * @param path  路径
     * @param suffix   查找的后缀
     * @return
     */
    public void findFileBySuffix(String path,String suffix){
        File file = new File(path);
        if (file.exists()){
            if (file.isDirectory()){
                String[] list = file.list();
                assert list != null;
                for (String s : list) {
                    findFileBySuffix(path+"\\"+s,suffix);
                }
            }else {
//                System.out.println(file.getName());
                if (file.getName().contains(".")&&suffix.equals(file.getName().split("\\.")[1]))
                    files.add(file);
            }
        }
    }


    public ArrayList<File> getFiles() {
        return files;
    }
    /**
     * 将文件路径转换到全限定名
     * @param filePath  文件路径
     * @return
     */
    public String filePathToFullyQualifiedName(String filePath){
        StringBuilder FullyQualifiedName = new StringBuilder();
        char[] chars = filePath.toCharArray();
        int start = 0;
        if (chars[0] == '\\') {
            start = 1;
        }
        for (int i = start; i < chars.length; i++) {
            if (chars[i] == '\\')
                FullyQualifiedName.append(".");
            else FullyQualifiedName.append(chars[i]);
        }
        return FullyQualifiedName.toString();
    }

    /**
     * 等待指定的文件被创建
     * @param path  被创建的文件的路径
     * @return  如果创建成功返回创建的文件，如果不成功一直循环。
     */
    public File waitFileExist(String path){
        File file = new File(path);
        while (!file.exists()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
