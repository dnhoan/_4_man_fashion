package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.DataMailDTO;

import javax.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException;
}
