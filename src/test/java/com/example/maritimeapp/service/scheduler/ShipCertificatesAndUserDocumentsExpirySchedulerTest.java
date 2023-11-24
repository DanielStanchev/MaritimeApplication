package com.example.maritimeapp.service.scheduler;

import com.example.maritimeapp.service.CertificateService;
import com.example.maritimeapp.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
class ShipCertificatesAndUserDocumentsExpirySchedulerTest {

        @Mock
        private DocumentService documentService;

        @Mock
        private CertificateService certificateService;

        @InjectMocks
        private ShipCertificatesAndUserDocumentsExpiryScheduler scheduler;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            scheduler = new ShipCertificatesAndUserDocumentsExpiryScheduler(documentService, certificateService);
        }

        @Test
        void testSetNewStatusesIfExpired() {
            scheduler.setNewStatusesIfExpired();
            verify(documentService, times(1)).expireDocuments();
            verify(certificateService, times(1)).setNewStatusIfExpiredCertificate();
        }
    }
