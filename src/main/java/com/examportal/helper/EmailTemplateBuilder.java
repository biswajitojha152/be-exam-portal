package com.examportal.helper;

public class EmailTemplateBuilder {
    public static String generateRegisterEmail(String firstName, String username, String password){

        return "<article style=\"font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; font-size: 0.875rem;\">\n" +
                "        Congratulations "+firstName+"!, you have successfully registered in the exam portal. Kindly use below credentials for login.\n" +
                "        <p>Username: <b>"+username+"</b></p>\n" +
                "        <p>Password: <b>"+password+"</b></p>\n" +
                "    </article>";
    }
}
