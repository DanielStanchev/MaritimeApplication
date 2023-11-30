package com.example.maritimeapp.service.scheduler;

import com.example.maritimeapp.service.CertificateService;
import com.example.maritimeapp.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ShipCertificatesAndUserDocumentsExpiryScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ShipCertificatesAndUserDocumentsExpiryScheduler.class);

    private final DocumentService documentService;
    private final CertificateService certificateService;

    public ShipCertificatesAndUserDocumentsExpiryScheduler(DocumentService documentService, CertificateService certificateService) {
        this.documentService = documentService;
        this.certificateService = certificateService;
    }

    @Scheduled(cron = "* * */12 * * *")
    public void setNewStatusesIfExpired(){
        logger.info(String.format("Triggered scheduler at %s",LocalDateTime.now()));

        documentService.checkIfDocumentExpiredAndChangeStatus();
        certificateService.checkIfCertificateExpiredAndChangeStatus();
    }

}
