package com.wu.leetcode.leetcode.November;

/**
 * @author wuxuyang
 * @date 2020/12/8 10:32
 */
import com.alibaba.fastjson.JSON;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileList {
    private String dir_name="\\\\192.168.6.180\\测试部\\新建文件夹1";
//    private String list_name=null;
//    private BufferedWriter out = null;
    List<String> ver=null;

    public FileList(String dir_name) throws IOException{
        this.dir_name=dir_name;    //文件夹地址
//        this.list_name=list_name;    //保存文件列表的文件地址
        List<String> ver =new ArrayList<>();    //用做堆栈
    }

    public void getList() throws Exception{
//        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(list_name, true)));    //以追加的方式写入到指定的文件

//        out.close();
    }

    public static void main(String[] args) throws Exception {
        FileList fileList=new FileList("\\\\192.168.6.180\\测试部\\新建文件夹1");
        List<String> ver=new ArrayList<>();
        ver.add("\\\\192.168.6.180\\测试部\\新建文件夹1");
        while(ver.size()>0){
            File[] files = new File(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);
            int id=1;
            doFile(files,id);
        }
//        System.out.println(JSON.toJSON(ver));
    }

    private static void doFile(File[] files, int id) {
        int len=files.length;
        for(int i=0;i<len;i++){
            String tmp=files[i].getAbsolutePath();
            if(files[i].isDirectory()){
//                System.out.println("读取到了第"+i+"个，文件名称为"+name);
                File[] fil = new File(tmp).listFiles();
                doFile(fil,id);
            }else {
                String name = files[i].getName().substring(0,files[i].getName().lastIndexOf("."));
                System.out.println("文件名称"+name);
            }//如果是目录，则加入队列。以便进行后续处理
        }
    }
}