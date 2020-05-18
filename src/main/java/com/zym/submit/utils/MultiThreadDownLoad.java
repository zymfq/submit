package com.zym.submit.utils;

/**
 * 实现多线程下载的功能
 *
 * read():
 * 1.从读取流读取的是一个一个字节
 *
 * 2.返回的是字节的(0-255)内的字节值
 *
 * 3.读一个下次就自动到下一个,如果碰到-1说明没有值了.
 *
 * read(byte[] bytes)
 * 1.从读取流读取一定数量的字节,如果比如文件总共是102个字节
 *
 * 2.我们定义的数组长度是10,那么默认前面10次都是读取10个长度
 *
 * 3.最后一次不够十个,那么读取的是2个
 *
 * 4.这十一次,每次都是放入10个长度的数组.
 * ————————————————
 * 版权声明：本文为CSDN博主「Mr_Flying」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/nzfxx/article/details/51802017
 * @author zym
 * @date 2020-05-18-14:14
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.*;

class DownLoadThread extends Thread {

    private long startIndex;
    private long endIndex;
    private String downLoadUrl;
    private String filePaths;

    public DownLoadThread(long startIndex, long endIndex, String downLoadUrl, String threadName,String filePaths) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.downLoadUrl = downLoadUrl;
        this.filePaths = filePaths;
    }


    @Override
    public void run() {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(downLoadUrl).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            //设置请求头,告知服务器从指定的位置返回数据
            connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

            int responseCode = connection.getResponseCode();

            System.out.println(Thread.currentThread().getName());
            System.out.println("服务器返回的响应码：" + responseCode);

            if (responseCode == 206) {

                //获取要下载文件的流
                InputStream is = connection.getInputStream();

                //创建文件输出位置
                RandomAccessFile AccessFile = new RandomAccessFile(filePaths, "rw");
                //指定文件写入的开始位置
                AccessFile.seek(startIndex);

                //输入字节流in按照byte数组缓冲区每1024个字节循环一次进行read操作，直到读到-1这个整数
                byte[] bytes = new byte[1024];
                int len = -1;
                int count = 0;

                while ((len = is.read(bytes)) != -1) {

                    AccessFile.write(bytes, 0, len);

                    count += bytes.length;


                    System.out.println(getName() + "的下载进度:" + count);
                }

                System.out.println(getName() + "下载完成！！！");

                AccessFile.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}


public class MultiThreadDownLoad {

    private static int mThreadCount = 8;

    public static void main(String[] args) throws UnsupportedEncodingException {

        //URLEncoder.encode进行解码   用replace 替换 "+"变成"%20"   这两个都是空格，但是在不同规则下解析不同  而原本的+号则被转为%2b
        String url =
                "http://127.0.0.1:8081/static/image/" +
                        URLEncoder.encode("“护眼睛灵”—创新创业 - 副本.docx", "UTF-8").
                                replace("+", "%20");


        //得到文件名
        String wordName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        //反解码
        wordName = URLDecoder.decode(wordName, "UTF-8");
        //2.得到文件后缀名
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        //3.得到文件绝对路径
        String pathName = null;
        if (suffix.equals("doc")) {
            pathName = "F://Test//binfaxiazai/" + wordName + ".doc";
        } else {
            pathName = "F://Test//binfaxiazai/" + wordName + ".docx";
        }


        try {
            //获取要进行下载文件的长度
            URL mUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            //使文件不进行压缩传输
            connection.setRequestProperty("Accept-Encoding", "identity");

            long contentLength = connection.getContentLengthLong();
            System.out.println("要下载文件的长度:" + contentLength);

            //根据文件的长度创建一个空的文件
            RandomAccessFile randomAccessFile = new RandomAccessFile(pathName, "rw");

            //提前设置长度进行占位
            randomAccessFile.setLength(contentLength);

            randomAccessFile.close();

            // 计算每个子线程要进行下载的文件块的数据的起始位置和结束位置
            for (int i = 0; i < mThreadCount; i++) {
                long startIndex = i * (contentLength / (mThreadCount - 1));

                Long endIndex = (i + 1) * (contentLength / (mThreadCount - 1)) - 1;

                //最后一个线程下载最后剩下的文件块
                if (mThreadCount - 1 == i) {
                    endIndex = contentLength - 1;
                }

                //创建线程进行分块下载
                DownLoadThread downLoadThread = new DownLoadThread(startIndex, endIndex, url,
                        "线程:" + i,pathName);
                //downLoadThread.setName("线程：" + i);
                System.out.println(downLoadThread.currentThread().getName());
                downLoadThread.start();

                System.out.println("第" + i + "线程:" + "起始位置:" + startIndex + " 结束位置:" + endIndex);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


}

