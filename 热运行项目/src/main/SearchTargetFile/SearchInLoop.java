package 热运行项目.SearchTargetFile;

import 热运行项目.HandlerLink.BaseLink;
import 热运行项目.HandlerLink.Handlers;
import 热运行项目.SearchTargetFile.Search.SearchFileClassFile;
import 热运行项目.SearchTargetFile.Search.SearchFileJavaFile;

import java.util.ArrayList;

/**
 * 基本程序，可以复写，或是重新编写
 * 这里只是简单的使用了判断文件是否存在来添加相应的元素的。
 */
public class SearchInLoop implements Runnable{
    private String path;
    private String searchType = "java";
    private SearchFileJavaFile searchFileJavaFile = new SearchFileJavaFile();
    private SearchFileClassFile searchFileClassFile = new SearchFileClassFile();
    private Handlers handlers = null;
    private BaseLink baseLink = null;
    public SearchInLoop(String path){
        this.path = path;
    }
    public SearchInLoop(String path,String searchType){
        this.path = path;
        this.searchType = searchType;
    }
    public SearchInLoop(String path, String searchType, BaseLink baseLink){
        this.path = path;
        this.searchType = searchType;
        this.baseLink = baseLink;
    }

    /**
     * 死循环，不断地查找本地有无新的功能
     */
    @Override
    public void run() {
        while (true) {
            switch (searchType) {
                case "java":
                    ArrayList<Object> file = searchFileJavaFile.findFile(this.path);
                    if (handlers == null)
                        handlers = new Handlers();
                    handlers.setList(file);
                    break;
                case "class":
                    ArrayList<Object> file1 = searchFileClassFile.findFile(this.path);
                    if (handlers == null)
                        handlers = new Handlers();
                    handlers.setList(file1);
            }
            if (this.baseLink != null) {
                this.baseLink.setHandlers(handlers);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
