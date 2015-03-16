package br.com.daniloporcelani;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "logentry")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class LogEntry {

	@XmlAttribute
	private String revision;
	
	private String author;
	private String date;
	
	@XmlElement(name = "msg")
	private String message;

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public String getRevision() {
		return revision;
	}

	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

}
