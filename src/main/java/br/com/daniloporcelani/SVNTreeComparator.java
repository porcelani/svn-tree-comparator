package br.com.daniloporcelani;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SVNTreeComparator {

	public static SVNTreeComparatorResult compare(List<Commit> old, List<Commit> _new) {
		List<Commit> commitsOnlyOnSource = new ArrayList<Commit>(old);

		for (Commit c : old)
			if (c.getAuthor().equals("builder"))
				commitsOnlyOnSource.remove(c);

		
		for (Commit t : _new)
			for (Commit c : commitsOnlyOnSource)
				if (t.getMessage().equals(c.getMessage())) {
					commitsOnlyOnSource.remove(c);
					break;
				}

		
		for (Commit t : _new)
			for (Commit c : commitsOnlyOnSource) 
				if (hasSameTag(t, c) && pathEquals(t, c)) {
					commitsOnlyOnSource.remove(c);
					break;
				}
			
		
		for (Commit t : _new){
			List<Commit> listTarget = new ArrayList<Commit>();
			List<Commit> listSource = new ArrayList<Commit>();
			
			List<Path> pathTarget = new ArrayList<Path>();
			List<Path> pathSource = new ArrayList<Path>();

			for (Commit commit : _new) 
				if(hasSameTag(t,commit))
					listTarget.add(commit);
			
			for (Commit commitSource : commitsOnlyOnSource) 
				if(hasSameTag(t,commitSource))
					listSource.add(commitSource);
			
			
			for (Commit commit : listTarget) 
				pathTarget.addAll(commit.getPaths());
						
			for (Commit commit : listSource) 
				pathSource.addAll(commit.getPaths());
				
			if(pathEquals(pathTarget,pathSource))
				commitsOnlyOnSource.removeAll(listSource);
		}	
				

		return new SVNTreeComparatorResult(commitsOnlyOnSource);
	}

	private static boolean hasSameTag(Commit t, Commit c) {
		String tagT=getTag(t);
		String tagC=getTag(c);

		return tagT == null || tagC == null ? false : tagT.equals(tagC);
	}

	private static String getTag(Commit t) {
		String message = t.getMessage();
		
		String oneOrMoreCaracter = "\\S+";
		String oneOrMoreNumber = "\\d+";
		Matcher matcher = Pattern.compile("\\["+oneOrMoreCaracter+"-"+oneOrMoreNumber+"\\]").matcher(message);
		
		if(!matcher.find())
			return null;
	
		return matcher.group();
	}

	private static boolean pathEquals(Commit t, Commit c) {
		List<String> pathsC = new ArrayList<String>();
		List<String> pathsT = new ArrayList<String>();

		for (Path pathC : c.getPaths())
			pathsC.add(pathC.getFileName());

		for (Path pathT : t.getPaths())
			pathsT.add(pathT.getFileName());

		return pathsT.containsAll(pathsC) && pathsT.size() == pathsC.size();
	}

	private static boolean pathEquals(List<Path> t, List<Path> c) {
		List<String> pathsC = new ArrayList<String>();
		List<String> pathsT = new ArrayList<String>();
		
		for (Path pathC : c)
			pathsC.add(pathC.getFileName());
		
		for (Path pathT : t)
			pathsT.add(pathT.getFileName());
		
		return pathsT.containsAll(pathsC) && pathsT.size() == pathsC.size();
	}
}
