package com.example.ecs.logging.java.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.StringMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

@Log4j2
@RestController
public class TestController {

    @Autowired
    private ObjectMapper objectMapper;

    private ThreadMXBean thread = (com.sun.management.ThreadMXBean)
            ManagementFactory.getThreadMXBean();

    @GetMapping("ok")
    public void ok() {
        log.info("ok");
    }

    @GetMapping("test1")
    public String test1() throws JsonProcessingException {

        // 1. StringMapMessage
        log.info(new StringMapMessage()
                .with("message", "Hello World!")
                .with("chottoshitaMetric", 5));

        return "ok";
    }

    @GetMapping("test2")
    public String test2() throws JsonProcessingException {

        // 2. ObjectMessage
        log.info(new SampleMetricsBean("hoge", "fuga", thread.getThreadCount(), thread.getDaemonThreadCount(), thread.getPeakThreadCount()));

        return "ok";
    }

    @GetMapping("test3")
    public String test3() throws JsonProcessingException {

        // 2. ObjectMessage
        log.info(new SampleMetricsBean("hoge", "fuga", thread.getThreadCount(), thread.getDaemonThreadCount(), thread.getPeakThreadCount()));
        log.info(new ThreadMetricsBean(thread));

        return "ok";
    }

    @GetMapping("exception")
    public void exception() {
        throw new RuntimeException("テスト用の例外を発生させます");
    }

    @AllArgsConstructor
    @Value
    private static class SampleMetricsBean {
        private String hogeName;
        private String fugaName;
        private int xxxValue;
        private int yyyValue;
        private int zzzValue;
    }

    @Value
    private static class ThreadMetricsBean {
        private int threadCount;
        private int daemonThreadCount;
        private int peakThreadCount;

        public ThreadMetricsBean(ThreadMXBean thread) {
            threadCount = thread.getThreadCount();
            daemonThreadCount = thread.getDaemonThreadCount();
            peakThreadCount = thread.getPeakThreadCount();
        }
    }
}
