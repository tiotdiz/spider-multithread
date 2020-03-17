package com.qps.spider.service;

import com.qps.spider.pojo.SinaNewsPojo;
import com.qps.utils.MySynQueue;

public class NewsConsumer implements Runnable {
	private MySynQueue<SinaNewsPojo> queue;

	public NewsConsumer(MySynQueue<SinaNewsPojo> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		Thread ct = Thread.currentThread();
		while(!ct.isInterrupted()) {
			try {
				int size = queue.size();
				if(size > 0) {
					SinaNewsPojo sinaNewsPojo = queue.poll();
					String liUrl = sinaNewsPojo.getUrlStr();
					String liTitle = sinaNewsPojo.getUrlTitle();
					String liDate = sinaNewsPojo.getUrlDate();
					String liTime = sinaNewsPojo.getUrlTime();
					SinaNewsSpider.detalProcesser(liUrl, liTitle, liDate, liTime);
					long sec = 1L;
					Thread.sleep(sec * 1000);
				} else {
					long sec = 3L;
					System.out.println("Null, wait " + sec + "second(s)");
					Thread.sleep(sec * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
