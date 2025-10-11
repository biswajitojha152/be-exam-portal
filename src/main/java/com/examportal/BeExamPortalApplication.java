package com.examportal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;


@SpringBootApplication
public class BeExamPortalApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BeExamPortalApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(ManagementFactory.getThreadMXBean().getThreadCount() + " : Number of Threads");
		Thread.getAllStackTraces().keySet().forEach(thread ->
				System.out.println(thread.getName() + " (Daemon: " + thread.isDaemon() + ")"));
	}
}
