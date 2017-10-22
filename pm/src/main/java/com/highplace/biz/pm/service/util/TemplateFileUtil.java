package com.highplace.biz.pm.service.util;

import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TemplateFileUtil {

    public static FileInputStream getTemplates(String tempName) throws FileNotFoundException {
        //return new FileInputStream(ResourceUtils.getFile("classpath:excel-templates/" + tempName));
        return new FileInputStream(ResourceUtils.getFile("classpath:" + tempName));
    }
}
