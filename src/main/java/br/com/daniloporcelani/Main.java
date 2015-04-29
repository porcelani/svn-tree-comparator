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
	private SVNProperty old;
	private List<SVNProperty> news = new ArrayList<SVNProperty>();

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
			
			old = newSVNProperty(prop.getProperty("old"));
			
			for(String source : prop.getProperty("new").split(";"))
				news.add(newSVNProperty(source));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String xmlOld = comando(old.getUrl(), old.getStartDate(), old.getEnd());
		SVNLog svnLogOld = MarshallUtils.unmarshall(SVNLog.class, xmlOld);
		
		List<Commit> newCommits = new ArrayList<Commit>();
		for(SVNProperty p : news) {
			String xmlNew = comando(p.getUrl(), p.getStartDate(), p.getEnd());
			SVNLog svnLogSource = MarshallUtils.unmarshall(SVNLog.class, xmlNew);
			
			newCommits.addAll(svnLogSource.getCommits());
		}
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(svnLogOld.getCommits(), newCommits);
		System.out.println(result);
	}

	private SVNProperty newSVNProperty(String property) {
		SVNProperty p = new SVNProperty();
		p.setUrl(property.split(",")[0].trim());
		p.setStartDate(property.split(",")[1].trim());
		p.setEnd(property.split(",")[2].trim());
		
		return p;
	}

	public String comando(String branch, String startDate, String endDate) {
		String comandoPadrao = "svn log %s/%s -v -r %s:%s --username %s --password %s --xml";
		return executaComando(String.format(comandoPadrao, svnUrl, branch, toDate(startDate), toDate(endDate), user, password));
	}

	public String toDate(String date) {
		if (date.equals("HEAD"))
			return date;

		return "{" + date + "}";
	}

	public String executaComando(String comando) {
		final Runtime r = Runtime.getRuntime();

		try {
			Process p = r.exec(comando);

			Scanner scanner = new Scanner(p.getInputStream(), "UTF-8");
			return scanner.useDelimiter("$$").next();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
