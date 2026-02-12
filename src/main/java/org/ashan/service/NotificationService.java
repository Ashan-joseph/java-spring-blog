package org.ashan.service;

public interface NotificationService {
    boolean sendMail(String email,String subject,String name);
}
