package 热运行项目.SearchTargetFile.Util;

import java.io.File;
import java.io.IOException;

public class FileDistributeAndCompile {

    /**
     * 编译java文件
     * @param file
     */
    public static void compileJavaFile(File file){
        if (file.exists()) {
            Runtime runtime = Runtime.getRuntime();
            String[] command = {"javac ", file.getAbsolutePath(), "-d", FilePathDefine.compileTargetPath};
            try {
                runtime.exec(command);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
