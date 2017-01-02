package com.wolf.springmvc.action;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by wolf on 17/1/2.
 */
@Controller
public class UploadAction {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file) {
        try {
            StringBuilder sb = new StringBuilder(100);
            sb.append("/Users/wolf/tmp/uploads/").append(file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(new File(sb.toString()), file.getBytes());
            return HttpStatus.OK.getReasonPhrase();
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
    }
}
