package com.epam.utility.email;

import com.epam.listener.ContextListener;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailController {

    private static final Logger LOGGER = LogManager.getLogger(EmailController.class);
    private static final long PERIOD = ChronoUnit.DAYS.getDuration().getSeconds();
    private static final long NEXT_DAY_MORNING = ChronoUnit.HOURS.getDuration().getSeconds() * 9;
    private ScheduledExecutorService scheduledExecutorService;

    public void startScheduler() {
        LOGGER.info("Start EmailScheduler");
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //scheduledExecutorService.scheduleAtFixedRate(EmailSender::emailProcessTest, 10, 10, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(EmailSender::emailProcess,
                10, PERIOD, TimeUnit.SECONDS);
        /*scheduledExecutorService.scheduleAtFixedRate(EmailSender::emailProcess,
                getInitialDelayToEndOfTheDay() + NEXT_DAY_MORNING, PERIOD, TimeUnit.SECONDS);*/
    }

    private long getInitialDelayToEndOfTheDay() {
        ZonedDateTime tomorrowMidnight = ZonedDateTime.now(ZoneOffset.ofHours(2)).plusDays(1).truncatedTo(ChronoUnit.DAYS);
        return ChronoUnit.SECONDS.between(ZonedDateTime.now(ZoneOffset.ofHours(2)), tomorrowMidnight);
    }

    public void shutdownScheduler() {
        LOGGER.info("Shutdown EmailScheduler");
        scheduledExecutorService.shutdown();
    }
}
