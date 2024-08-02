package com.arthur.picpay.service;

import com.arthur.picpay.entity.Transfer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final com.arthur.picpay.client.NotificationClient notificationClient;
    private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(Transfer transfer) {
        try {
            logger.info("Sending notification...");

            var resp = notificationClient.sendNotification(transfer);
            if (resp.getStatusCode().isError()) {
                logger.error("Error while sending notification.");
            }

        } catch (Exception e) {
            logger.error("Error while sending notification.", e);
        }
    }

}
