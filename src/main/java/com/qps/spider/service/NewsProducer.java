package com.qps.spider.service;

import com.qps.spider.pojo.SinaNewsPojo;
import com.qps.utils.MySynQueue;

public class NewsProducer implements Runnable {
	private String urlString;
	private String encoding;
	private MySynQueue<SinaNewsPojo> queue;
	
	public NewsProducer(String urlString, String encoding, MySynQueue<SinaNewsPojo> queue) {
		super();
		this.urlString = urlString;
		this.encoding = encoding;
		this.queue = queue;
	}

	@Override
	public void run() {
		SinaNewsSpider.crawler(urlString, encoding, queue);
	}

}
