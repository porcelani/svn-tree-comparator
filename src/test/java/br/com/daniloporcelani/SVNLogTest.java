package br.com.daniloporcelani;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class SVNLogTest {

	String expected =
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
			+ "<log>\n"
			+ "    <logentry revision=\"1\">\n"
			+ "        <author>someone</author>\n"
			+ "        <date>2014-12-29T16:07:09.712879Z</date>\n"
			+ "        <msg>first commit</msg>\n"
			+ "        <paths>\n"
			+ "            <path text-mods=\"true\" prop-mods=\"false\" kind=\"file\" action=\"M\">/Class1.java</path>\n"
			+ "            <path text-mods=\"true\" prop-mods=\"false\" kind=\"file\" action=\"M\">/Class2.java</path>\n"
			+ "        </paths>\n"
			+ "    </logentry>\n"
			+ "</log>\n";

	@Test
	public void marshall() {
		Path path1 = new Path();
		path1.setAction("M");
		path1.setKind("file");
		path1.setPropMods("false");
		path1.setTextMods("true");
		path1.setPath("/Class1.java");

		Path path2 = new Path();
		path2.setAction("M");
		path2.setKind("file");
		path2.setPropMods("false");
		path2.setTextMods("true");
		path2.setPath("/Class2.java");
		
		Commit commit = new Commit();
		commit.setRevision("1");
		commit.setAuthor("someone");
		commit.setDate("2014-12-29T16:07:09.712879Z");
		commit.setMessage("first commit");
		commit.setPaths(Arrays.asList(path1, path2));
		
		SVNLog log = new SVNLog();
		log.setCommits(Arrays.asList(commit));
		
		String out = MarshallUtils.marshall(SVNLog.class, log);
		assertThat(out, equalTo(expected));
	}
	
	@Test
	public void unmarshall() {
		SVNLog log = MarshallUtils.unmarshall(SVNLog.class, expected);
		assertThat(log.getCommits().size(), equalTo(1));
		
		Commit logEntry = log.getCommits().get(0);
		assertThat(logEntry.getRevision(), equalTo("1"));
		assertThat(logEntry.getAuthor(), equalTo("someone"));
		assertThat(logEntry.getDate(), equalTo("2014-12-29T16:07:09.712879Z"));
		assertThat(logEntry.getMessage(), equalTo("first commit"));
		assertThat(logEntry.getPaths().size(), equalTo(2));
		assertThat(logEntry.getPaths().get(0).getPath(), equalTo("/Class1.java"));
		assertThat(logEntry.getPaths().get(1).getPath(), equalTo("/Class2.java"));
	}
}
