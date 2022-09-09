package me.cell.wewant.scheudle;

import me.cell.wewant.core.Main;
import me.cell.wewant.tools.YDY;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class JobConfig {
    @Autowired
    private JobScheduler jobScheduler;

    @Order(2)
    @EventListener
    public void handleContextRefreshEvent(ApplicationReadyEvent applicationReadyEvent) {
//        jobScheduler.scheduleRecurrently("*/1 * * * *", () -> Main.wewant());
        jobScheduler.scheduleRecurrently(" 0 */8 * * *", () -> Main.wewant());
        jobScheduler.scheduleRecurrently(Cron.daily(7), () -> YDY.checkin());
    }

}
