package com.zhy.submit.teacher.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class downloadZipUtils {
    public  String downloadZip(String zipBasePath, String zipName, List<String> filePaths, HttpServletResponse response) throws IOException {
        byte [] buffer=new byte[1024];
        //创建压缩文件需要的空的zip包
        String zipFilePath=zipBasePath+File.separator+zipName;
        //压缩文件

        try {
            File zip=new File(zipFilePath);
            if(!zip.exists())
                zip.createNewFile();
            //创建zip文件输出流
            ZipOutputStream zos = new ZipOutputStream((new FileOutputStream(zip)));
            this.zipFile(zipBasePath,zipName, zipFilePath,filePaths,zos);
            zos.close();
            response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(zipName,"UTF-8"));
            //将打包后的文件写到客户端（用缓冲流输出）
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(zipFilePath));
            byte[] buff= new byte[bis.available()];
            bis.read(buff);
            bis.close();
            OutputStream out = response.getOutputStream();
            out.write(buff);//输出数据文件
            out.flush();//释放缓存
            out.close();
        }catch(Exception e){
            e.printStackTrace();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("<div align=\"center\" style=\"font-size: 30px;font-family: serif;color: red;\">系统内部错误，下载未成功，请联系管理员！</div>"
                    + "<div>错误信息："+e.getMessage()+"</div>");
            response.getWriter().flush();
            response.getWriter().close();

        }
        return null;
    }

    public String zipFile(String zipBasePath,String zipName,String zipFilePath,List<String> filePaths,ZipOutputStream zos) throws IOException {
        //循环读取文件路径集合，获取每一个文件的路径
        for(String filePath:filePaths){
            File inputFile=new File(filePath);
            if(inputFile.exists()){
                if(inputFile.isFile()){
                    //创建输入流读取文件
                    BufferedInputStream bis =new BufferedInputStream(new FileInputStream(inputFile));
                    zos.putNextEntry(new ZipEntry(inputFile.getName()));
                    int size =0;
                    byte[] buffer=new byte[1024];
                    while((size=bis.read(buffer))>0){


                        zos.write(buffer,0,size);
                    }
                    zos.closeEntry();
                    bis.close();
                }else{
                    try {
                        File[] files = inputFile.listFiles();
                        List<String> filePathsTem = new ArrayList<String>();
                        for (File fileTem:files) {
                            filePathsTem.add(fileTem.toString());
                        }
                        return zipFile(zipBasePath, zipName, zipFilePath, filePathsTem,zos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
