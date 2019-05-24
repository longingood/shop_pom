package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/20 21:38
 */
@Controller
@RequestMapping(value="resController")
public class resController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String, String> uploadImg(MultipartFile file){
        //获取上传的图片的名称
        String fname = file.getOriginalFilename();

        //获取上传图片的大小
        long fileSize = file.getSize();

        //获取图片的后缀
        int index = fname.lastIndexOf(".");
        String suffix = fname.substring(index + 1);

        Map<String,String> map = new HashMap<>();


        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    fileSize,
                    suffix,
                    null
            );

            map.put("code", "0000");
            map.put("filepath", storePath.getFullPath());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("code","0001");
        return map ;
    }

}
