package nl.codefusion.comsat.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class Poller {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ContactService contactService;

    @PostConstruct
    public void init() {
        start();
    }

    public void start() {
        final Runnable task = contactService::updateUserStatuses;
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
