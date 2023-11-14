package com.example.maritimeapp.service.scheduler;

import com.example.maritimeapp.service.CertificateService;
import com.example.maritimeapp.service.DocumentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ShipCertificatesAndUserDocumentsExpiryScheduler {

    private final DocumentService documentService;
    private final CertificateService certificateService;

    public ShipCertificatesAndUserDocumentsExpiryScheduler(DocumentService documentService, CertificateService certificateService) {
        this.documentService = documentService;
        this.certificateService = certificateService;
    }

    @Scheduled(cron = "* * */12 * * *")
    public void cleanHistory(){
        System.out.println("Triggered scheduler at "+ LocalDateTime.now());

        documentService.setNewStatusIfExpiredDocument();
        certificateService.setNewStatusIfExpiredCertificate();

    }

}
