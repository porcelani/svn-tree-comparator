package br.com.daniloporcelani;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallUtils {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<T> clazz, String xml) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(SVNLog.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			T unmarshal = (T) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));

			return unmarshal;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String marshall(Class<?> clazz, Object in) {
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
	}}