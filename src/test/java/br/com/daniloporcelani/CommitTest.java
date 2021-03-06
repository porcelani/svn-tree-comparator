package br.com.daniloporcelani;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class CommitTest {

	String expected =
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
			+ "<logentry revision=\"1\">\n"
			+ "    <author>someone</author>\n"
			+ "    <date>2014-12-29T16:07:09.712879Z</date>\n"
			+ "    <msg>first commit</msg>\n"
			+ "    <paths>\n"
			+ "        <path text-mods=\"true\" prop-mods=\"false\" kind=\"file\" action=\"M\">/Class1.java</path>\n"
			+ "        <path text-mods=\"true\" prop-mods=\"false\" kind=\"file\" action=\"M\">/Class2.java</path>\n"
			+ "    </paths>\n"
			+ "</logentry>\n";

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
		
		Commit subject = new Commit();
		subject.setRevision("1");
		subject.setAuthor("someone");
		subject.setDate("2014-12-29T16:07:09.712879Z");
		subject.setMessage("first commit");
		subject.setPaths(Arrays.asList(path1, path2));
		
		String out = MarshallUtils.marshall(Commit.class, subject);
		assertThat(out, equalTo(expected));
	}
	
	@Test
	public void unmarshall() {
		Commit logEntry = MarshallUtils.unmarshall(Commit.class, expected);
		
		assertThat(logEntry.getRevision(), equalTo("1"));
		assertThat(logEntry.getAuthor(), equalTo("someone"));
		assertThat(logEntry.getDate(), equalTo("2014-12-29T16:07:09.712879Z"));
		assertThat(logEntry.getMessage(), equalTo("first commit"));
		assertThat(logEntry.getPaths().size(), equalTo(2));
		assertThat(logEntry.getPaths().get(0).getPath(), equalTo("/Class1.java"));
		assertThat(logEntry.getPaths().get(1).getPath(), equalTo("/Class2.java"));
	}
}
