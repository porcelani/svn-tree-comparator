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
	
	@Override
	public String toString() {
		return String.format("%s - %s - %s", revision, author, message);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
	    if(!(obj instanceof LogEntry))
	    	return false;
	    
		LogEntry another = (LogEntry) obj;
		
		return revision.equals(another.revision) &&
				author.equals(another.author) &&
				date.equals(another.date) &&
				message.equals(another.message);
	}
	
	@Override
	public int hashCode() {
		return revision.hashCode() *
				author.hashCode() *
				date.hashCode() *
				message.hashCode();
	}
}
