package br.com.daniloporcelani;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "log")
@XmlAccessorType(XmlAccessType.FIELD)
public class SVNLog {

	@XmlElement(name = "logentry")
	private List<Commit> commits;

	public List<Commit> getCommits() {
		return commits;
	}

	public void setCommits(List<Commit> commits) {
		this.commits = commits;
	}
}
