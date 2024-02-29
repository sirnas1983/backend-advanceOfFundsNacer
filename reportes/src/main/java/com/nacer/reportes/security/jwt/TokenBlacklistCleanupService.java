package com.nacer.reportes.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Service
public class TokenBlacklistCleanupService {

    @Autowired
    TokenBlacklistService tokenBlacklistService;
    // Scheduled executor service
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Constructor
    public TokenBlacklistCleanupService() {
        // Schedule the cleanup task to run every hour
        scheduler.scheduleAtFixedRate(this::cleanExpiredTokens, 0, 1, TimeUnit.DAYS);
    }

    // Cleanup task
    private void cleanExpiredTokens() {
        // Get expired tokens from your blacklist service
        tokenBlacklistService.cleanExpiredTokens();
    }

    // Shutdown method (call this when your application shuts down)
    public void shutdown() {
        scheduler.shutdown();
    }
}
