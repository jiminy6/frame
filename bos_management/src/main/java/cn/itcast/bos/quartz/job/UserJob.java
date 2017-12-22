package cn.itcast.bos.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.service.UserService;

public class UserJob implements Job {
	@Autowired
	private UserService UserService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		UserService.updateStatusForExpiredTime();
		System.out.println("任务被调度了");
	}
	
}
