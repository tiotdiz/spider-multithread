package com.qps.spider.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qps.spider.pojo.SinaNewsPojo;
import com.qps.utils.IOUtil;
import com.qps.utils.MySynQueue;
import com.qps.utils.RegExUtil;
import com.qps.utils.WebUtil;

public class SinaNewsSpider {

	public static void multiThreadCrawler() {
		MySynQueue<SinaNewsPojo> queue = new MySynQueue<SinaNewsPojo>();
		// list page
		String urlString = "http://roll.news.sina.com.cn/news/gnxw/gdxw1/index.shtml";
		String encoding = "utf-8";
		int page = 1;
		for (int i = 1; i <= page; i++) {
			if (i >= 2) {
				urlString = "http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_" + i + ".shtml";
			}
			Thread crawlerNewsList = new Thread(new NewsProducer(urlString, encoding, queue));
			crawlerNewsList.start();
//			crawler(urlString, encoding);
		}

		// detail page
		//FileLock FileChannel
		Thread crawlerDetail = new Thread(new NewsConsumer(queue));
		crawlerDetail.start();
	}

	public static void crawler(String urlStr, String encoding, MySynQueue<SinaNewsPojo> queue) {
		String ulRegex = "<ul class=\"list_009\">[\\s\\S]*?</ul>";
		String ulInput = WebUtil.urlGetString(urlStr, encoding);
		String ulResult = RegExUtil.ulMatch(ulRegex, ulInput);
		String liRegex = "<li>[\\s\\S]*?</li>";
		List<String> listLi = RegExUtil.liMatch(liRegex, ulResult);
		for (String str : listLi) {
			String indexRegex = "<li><a href=\"([\\S]*?)\" target=\"_blank\">([\\s\\S]*?)</a><span>\\(([\\S]*?) ([\\S]*?)\\)</span></li>";
			String liUrl = RegExUtil.indexMatch(indexRegex, str, 1);
			String liTitle = RegExUtil.indexMatch(indexRegex, str, 2);
			String liDate = RegExUtil.indexMatch(indexRegex, str, 3);
			String liTime = RegExUtil.indexMatch(indexRegex, str, 4);
			queue.offer(new SinaNewsPojo(liUrl, liTitle, liDate, liTime));
		}
	}

	public static void crawler() {
		String urlString = "http://roll.news.sina.com.cn/news/gnxw/gdxw1/index.shtml";
		String encoding = "gb2312";
		String ulRegex = "<ul class=\"list_009\">[\\s\\S]*?</ul>";
		String ulInput = WebUtil.urlGetString(urlString, encoding);
		String ulResult = RegExUtil.ulMatch(ulRegex, ulInput);
		String liRegex = "<li>[\\s\\S]*?</li>";
		List<String> listLi = RegExUtil.liMatch(liRegex, ulResult);
		int count = 0;
		for (String str : listLi) {
			if (count > 3) {
				break;
			}
			String indexRegex = "<li><a href=\"([\\S]*?)\" target=\"_blank\">([\\s\\S]*?)</a><span>\\(([\\S]*?) ([\\S]*?)\\)</span></li>";
			String liUrl = RegExUtil.indexMatch(indexRegex, str, 1);
			String liTitle = RegExUtil.indexMatch(indexRegex, str, 2);
			String liDate = RegExUtil.indexMatch(indexRegex, str, 3);
			String liTime = RegExUtil.indexMatch(indexRegex, str, 4);

			detalProcesser(liUrl, liDate, liTitle, liTime);

			count++;
		}
	}

	public static void detalProcesser(String liUrl, String liDate, String liTitle, String liTime) {

		byte[] b = detailCrawler(liUrl);

//		String dateRegex = "(20)([\\S]*)";
//		Pattern p = Pattern.compile(dateRegex);
//		Matcher m = p.matcher(liDate);
//		String date = null;
//		while(m.find()) {
//			date = m.group(2);
//		}
//		String baseDir = "E:" + "spider_db" + File.separator + date + File.separator;

		String baseDir = "E:" + File.separator + "spider_db" + liDate + File.separator;

		File baseDirObj = new File(baseDir);
		if (!baseDirObj.exists()) {
			baseDirObj.mkdirs();
		}

		String dataPath = baseDir + "spider_data.dat";
		String indexPath = baseDir + "spider_index.txt";

		File dataFile = new File(dataPath);
		long offset = dataFile.length();
		int byteNum = b.length;
		StringBuffer sb = new StringBuffer();
		char splitChar = '\u0001';
		sb.append(liUrl).append(splitChar).append(liTitle).append(splitChar).append(liDate).append(splitChar)
				.append(liTime).append(splitChar).append(offset).append(splitChar).append(byteNum);
		IOUtil.writeDataFile(b, dataPath);
		IOUtil.writeIndexFile(sb.toString(), indexPath);
	}

	public static byte[] detailCrawler(String urlString) {
		// String urlString =
		// "String ulInput = WebUtil.urlGetString(urlString, encoding);";
		// String encoding = "utf-8";
		return WebUtil.urlGetByteArray(urlString);
	}
}
