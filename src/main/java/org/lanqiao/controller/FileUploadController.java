package org.lanqiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.HttpConstraintElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: WDS
 * @Date: 2018/12/28 19:19
 * @Description:
 */
@Controller
public class FileUploadController {
    //多文件上传
    @RequestMapping("/uploads.do")
    public void uploads(MultipartFile[] photo,HttpSession session) throws IOException {
        for(MultipartFile ph : photo){
            if(!ph.isEmpty()){
                String fileName = ph.getOriginalFilename();
                String path =   session.getServletContext().getRealPath("/imgs");
                File dir = new File(path);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File file = new File(dir,fileName);
                ph.transferTo(file);
            }
        }
    }


    //单个文件上传
    @RequestMapping("/upload.do")
    public void upload(MultipartFile photo,HttpSession session) throws IOException {
        if(!photo.isEmpty()){
            String fileName = photo.getOriginalFilename();
            String path =   session.getServletContext().getRealPath("/imgs");
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(dir,fileName);
            photo.transferTo(file);
        }
    }

    //异步请求上传文件
    @RequestMapping("/ajaxUpload.do")
    public String ajaxUpload(MultipartFile[] photo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        for(MultipartFile ph : photo){
            if(!ph.isEmpty()){
                String fileName = ph.getOriginalFilename();
                System.out.println(fileName);
                String path =   request.getSession().getServletContext().getRealPath("/imgs");

                File dir = new File(path);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File file = new File(dir,fileName);
                try {
                    ph.transferTo(file);
                } catch (IOException e) {
                    System.out.println("文件["+ fileName +"]上传失败");
                    e.printStackTrace();
                    out.print("上传失败");
                    out.flush();
                    return null;
                }
            }
        }
        out.print("上传成功");
        out.flush();
        return null;
    }



}
