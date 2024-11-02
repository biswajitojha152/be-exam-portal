package com.examportal.helper;

public class EmailTemplateBuilder {
    public static String generateRegisterEmail(String firstName, String username, String password){

        return "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; font-family: Arial, sans-serif;\">\n" +
                "        <div style=\"background-color: #f2f2f2; padding: 20px; text-align: center;\">\n" +
                "            <h1 style=\"font-size: 24px; font-weight: bold;\">Exam Portal</h1>\n" +
                "        </div>\n" +
                "        <div style=\"padding: 20px;\">\n" +
                "            <h2 style=\"font-size: 20px; font-weight: bold;\">Congratulations, "+firstName+"!</h2>\n" +
                "            <p>Your registration has been successful.</p>\n" +
                "            <p><strong>Login Credentials:</strong></p>\n" +
                "            <p><strong>Username:</strong> "+username+"</p>\n" +
                "            <p><strong>Password:</strong> "+password+"</p>\n" +
                "            <p>Please keep your login credentials confidential.</p>\n" +
                "        </div>\n" +
                "        <div style=\"background-color: #f2f2f2; padding: 20px; text-align: center;\">\n" +
                "            <p>&copy; <a href=\"[Exam Portal URL]\" target=\"_blank\">Exam Portal</a>. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>";
    }
}
