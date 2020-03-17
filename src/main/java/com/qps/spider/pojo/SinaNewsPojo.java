package com.qps.spider.pojo;

public class SinaNewsPojo {
	private String urlStr;
	private String urlTitle;
	private String urlDate;
	private String urlTime;
	
	public SinaNewsPojo(String urlStr, String urlTitle, String urlDate, String urlTime) {
		super();
		this.urlStr = urlStr;
		this.urlTitle = urlTitle;
		this.urlDate = urlDate;
		this.urlTime = urlTime;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	public String getUrlDate() {
		return urlDate;
	}
	public void setUrlDate(String urlDate) {
		this.urlDate = urlDate;
	}
	public String getUrlTime() {
		return urlTime;
	}
	public void setUrlTime(String urlTime) {
		this.urlTime = urlTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlStr == null) ? 0 : urlStr.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SinaNewsPojo other = (SinaNewsPojo) obj;
		if (urlStr == null) {
			if (other.urlStr != null)
				return false;
		} else if (!urlStr.equals(other.urlStr))
			return false;
		return true;
	}
}
