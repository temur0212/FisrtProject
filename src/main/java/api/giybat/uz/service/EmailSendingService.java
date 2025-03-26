package api.giybat.uz.service;

import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.SmsType;
import api.giybat.uz.exps.AppBadExseption;
import api.giybat.uz.util.JwtUtil;
import api.giybat.uz.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.util.concurrent.CompletableFuture;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String domain;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired SmsHistoryService smsHistoryService;




    public void sendRegistrationEmail(String email, Long profileId, AppLanguage lang) {
        String subject = "Complete Registration";
        String link = String.format("%s/api/auth/registration/email-verification/%s/%s", domain, lang, JwtUtil.encode(profileId));
        String text = """
        <html>
        <body style="font-family: Arial, sans-serif; text-align: center; margin: 0; padding: 20px; background-color: #f7f7f7;">
            <div style="max-width: 600px; margin: 0 auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                <div style="background-color: #4CAF50; color: white; padding: 20px; font-size: 20px;">
                    Complete Your Registration
                </div>
                <div style="padding: 20px; color: #333;">
                    <p>Hello,</p>
                    <p>Thank you for registering. Please click the button below to complete your registration:</p>
                    <a href="%s" style="display: inline-block; margin: 20px 0; padding: 10px 20px; font-size: 16px; color: white; background-color: #4CAF50; text-decoration: none; border-radius: 5px;">Complete Registration</a>
                    <p style="margin-top: 20px;">Best regards,<br/>The Team</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(link, link);

        CompletableFuture.runAsync(() -> sendEmail(email, subject, text));
    }

    public void resetPasswordEmail(String username , AppLanguage lang){
        String subject = "Complete Reset Password";
        // Generate code
        String code = RandomUtil.getRandomValue();
        String text = "<html>" +
                "<body style='font-family: Arial, sans-serif; text-align: center; padding: 20px;'>" +
                "<h2 style='color: #2c3e50;'>Tasdiqlash kodi</h2>" +
                "<p style='font-size: 18px; color: #34495e;'>Parolni tiklash uchun quyidagi kodni kiriting:</p>" +
                "<h1 style='background: #f4f4f4; display: inline-block; padding: 10px 20px; border-radius: 5px;'>" + code + "</h1>" +
                "<p style='color: #7f8c8d;'>Agar bu so‘rovni siz bajarmagan bo‘lsangiz, ushbu xabarni e’tiborsiz qoldiring.</p>" +
                "</body>" +
                "</html>";

        checkAndSendEmail(username,subject,text,code,SmsType.FORGOT_PASSWORD);

    }

    public void UpdateUsernameEmail(String username , AppLanguage lang){
        String subject = "Complete Update Username";
        // Generate code
        String code = RandomUtil.getRandomValue();
        String text = "<html>" +
                "<body style='font-family: Arial, sans-serif; text-align: center; padding: 20px;'>" +
                "<h2 style='color: #2c3e50;'>Tasdiqlash kodi</h2>" +
                "<p style='font-size: 18px; color: #34495e;'>Usernameni o'zgartirish  uchun quyidagi kodni kiriting:</p>" +
                "<h1 style='background: #f4f4f4; display: inline-block; padding: 10px 20px; border-radius: 5px;'>" + code + "</h1>" +
                "<p style='color: #7f8c8d;'>Agar bu so‘rovni siz bajarmagan bo‘lsangiz, ushbu xabarni e’tiborsiz qoldiring.</p>" +
                "</body>" +
                "</html>";

        checkAndSendEmail(username,subject,text,code,SmsType.CHANGE_PHONE);

    }

    private void checkAndSendEmail(String username, String subject, String body,String code,SmsType smsType) {

        // check
        Long count = smsHistoryService.getSmsCount(username);
        Long smsLimit = 3l;
        if (smsLimit <=count)
        {
            System.out.println("Sms limit reached. Email : "+username);
            throw new AppBadExseption("Sms limit reached");
        }
        //send
        CompletableFuture.runAsync(() -> sendEmail(username, subject, body));
        // save
        smsHistoryService.saveSmsHistory(username,subject,code,smsType );
    }

    private void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAccount);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // "true" to enable HTML
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

}
