package com.wen.servcie;

public interface MailService {
    boolean sendSimpleMail(String to, String subject, String content);

}
