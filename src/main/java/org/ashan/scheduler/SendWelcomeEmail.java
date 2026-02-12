package org.ashan.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashan.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendWelcomeEmail {

    private final UserService userService;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void sendUserWelcomeEmail(){

        userService.sendUserWelcomeEmail();

    }
}
