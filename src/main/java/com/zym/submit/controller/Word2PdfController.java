package com.zym.submit.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author zym
 * @date 2019-09-16-13:26
 */
@Controller
public class Word2PdfController {

    @Autowired
    private DocumentConverter documentConverter;

    /**
     * 文件转化成pdf预览
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException
     */
    //将上传的Word文档转成PDF,通过输出流相应到页面
    @RequestMapping("/word2PDF")
    @ResponseBody

    public ModelAndView Word2PdfController(HttpServletRequest request, HttpServletResponse response, Map<String,
            Object> map) throws IOException {

        String url = "http://localhost:8081/submit/static/image/朱亚民+2017207321118（自主学习）.doc";
        String wordName = url.substring(url.lastIndexOf("/") + 1);
        String PdfName = url.substring(wordName.lastIndexOf(".") - 1);

        File inputFile = new File("F:/Test/uploadFiles/" + wordName);
        File outputFile = new File("F:/Test/previewPDF/" + PdfName + ".pdf");

        try {
            documentConverter.convert(inputFile).to(outputFile).execute();
            OutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(outputFile);
            IOUtils.copy(in, outputStream);

            //获取PDFurl
            /*String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
            map.put("path", basePath);
            String endPath = basePath + "/static/image/" + PdfName + ".pdf";
            URLEncoder.encode(endPath, "UTF-8");
            System.out.println(endPath);*/

            in.close();
            outputStream.close();
            return null;

        } catch (OfficeException e) {

            e.printStackTrace();
            System.out.println("转换出错");
        }

        return new ModelAndView("success", map);
    }

}
