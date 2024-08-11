package com.examportal.helper;

public class FileValidator {
    public static boolean isImageType(String contentType){
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
