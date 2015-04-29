package br.com.daniloporcelani;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	private String svnUrl;
	private String user;
	private String password;
	private SVNProperty target;
	private List<SVNProperty> sources = new ArrayList<SVNProperty>();

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		try {
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			
			svnUrl = prop.getProperty("svnUrl");
			user = prop.getProperty("svnUser");
			password = prop.getProperty("svnPassword");
			
			target = newSVNProperty(prop.getProperty("target"));
			
			for(String source : prop.getProperty("sources").split(";"))
				sources.add(newSVNProperty(source));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String xml = comando(target.getUrl(), target.getStartDate(), target.getEnd());
		SVNLog svnLogTarget = MarshallUtils.unmarshall(SVNLog.class, xml);
		
		SVNTreeComparator svnTreeComparator = new SVNTreeComparator(svnLogTarget.getCommits());
		
		List<Commit> sourceCommits = new ArrayList<Commit>();
		for(SVNProperty p : sources) {
			xml = comando(p.getUrl(), p.getStartDate(), p.getEnd());
			SVNLog svnLogSource = MarshallUtils.unmarshall(SVNLog.class, xml);
			
			sourceCommits.addAll(svnLogSource.getCommits());
		}
		
		svnTreeComparator.compare(sourceCommits);
	}

	private SVNProperty newSVNProperty(String property) {
		SVNProperty p = new SVNProperty();
		p.setUrl(property.split(",")[0]);
		p.setStartDate(property.split(",")[1]);
		p.setEnd(property.split(",")[2]);
		
		return p;
	}

	public String comando(String branch, String data_inicio, String data_fim) {
		String comandoPadrao = "svn log %s/%s -v -r {%s}:%s --username %s --password %s --xml";
		return String.format(comandoPadrao, svnUrl, branch, data_inicio,
				chaves(data_fim), user, password);
	}

	public String chaves(String data) {
		if (data.equals("HEAD"))
			return data;
		else
			return "{" + data + "}";
	}

	public String executaComando(String comando) {
		final Runtime r = Runtime.getRuntime();

		try {
			Process p = r.exec(comando);

			Scanner scanner = new Scanner(p.getInputStream(), "ISO-8859-1");
			return scanner.useDelimiter("$$").next();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
