package br.com.daniloporcelani;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class LogEntryTest {

	String expected =
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
			+ "<logentry revision=\"1\">\n"
			+ "    <author>someone</author>\n"
			+ "    <date>2014-12-29T16:07:09.712879Z</date>\n"
			+ "    <msg>first commit</msg>\n"
			+ "</logentry>\n";

	@Test
	public void marshall() {
		Commit subject = new Commit();
		subject.setRevision("1");
		subject.setAuthor("someone");
		subject.setDate("2014-12-29T16:07:09.712879Z");
		subject.setMessage("first commit");
		
		String out = marshall(Commit.class, subject);
		assertThat(out, equalTo(expected));
	}
	
	@Test
	public void unmarshall() {
		Commit logEntry = unmarshall(Commit.class, expected);
		
		assertThat(logEntry.getRevision(), equalTo("1"));
		assertThat(logEntry.getAuthor(), equalTo("someone"));
		assertThat(logEntry.getDate(), equalTo("2014-12-29T16:07:09.712879Z"));
		assertThat(logEntry.getMessage(), equalTo("first commit"));
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T unmarshall(Class<T> clazz, String xml) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String marshall(Class<?> clazz, Object in) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter out = new StringWriter();
			marshaller.marshal(in, out);
			return out.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
