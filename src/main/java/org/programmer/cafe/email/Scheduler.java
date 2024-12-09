package org.programmer.cafe.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job updateStatusJob;

    @Scheduled(cron = "0 0 2 * * ?")

    public void scheduled() {
        try {
            JobParameters params = new JobParametersBuilder().addLong("timestamp",
                System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(updateStatusJob, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


