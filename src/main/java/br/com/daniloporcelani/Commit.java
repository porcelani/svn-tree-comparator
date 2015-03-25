package br.com.daniloporcelani;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "logentry")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Commit {

	@XmlAttribute
	private String revision;
	
	private String author;
	private String date;
	
	@XmlElement(name = "msg")
	private String message;
	
	@XmlElement(name = "path")
	@XmlElementWrapper(name = "paths")
	private List<Path> paths;

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
	
	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}

	public List<Path> getPaths() {
		return paths;
	}

	@Override
	public String toString() {
		return String.format("%s - %s - %s", revision, author, message);
	}
}
