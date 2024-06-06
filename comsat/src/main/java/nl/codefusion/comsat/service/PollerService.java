package nl.codefusion.comsat.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.engine.KikEngine;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PollerService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final KikEngine kikEngine;

    @PostConstruct
    public void init() {
        start();
    }

    public void start() {
        final Runnable task = kikEngine::updateContactChatStatuses;
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
