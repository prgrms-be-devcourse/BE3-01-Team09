package org.programmer.cafe.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

        //@Scheduled(cron = "0 0 24 * * ?")
    @Scheduled( cron = "*/10 * * * * ?")
    public void scheduled() {

        }
}
