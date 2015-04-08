package br.com.daniloporcelani;

import java.util.ArrayList;
import java.util.List;

public class SVNTreeComparator {

	private final List<Commit> source;

	public SVNTreeComparator(List<Commit> source) {
		this.source = source;
	}

	public SVNTreeComparatorResult compare(List<Commit> target) {
		List<Commit> commitsOnlyOnSource = new ArrayList<Commit>(source);
		
		for(Commit c : source)
			if(c.getAuthor().equals("builder"))
				commitsOnlyOnSource.remove(c);
		
		for(Commit t : target)
			for(Commit c : commitsOnlyOnSource)
				if(t.getMessage().equals(c.getMessage())) {
					commitsOnlyOnSource.remove(c);
					break;
				}

		for(Commit t : target)
			for(Commit c : commitsOnlyOnSource) {
				if(hasSameText(t,c) && pathEquals(t,c)){
					commitsOnlyOnSource.remove(c);
					break;
				}
			}
		
		return new SVNTreeComparatorResult(commitsOnlyOnSource);
	}

	private boolean hasSameText(Commit t, Commit c) {
		String messageT = t.getMessage();
		String messageC = c.getMessage();
		
		int indexOf = messageT.indexOf('[');
		int indexOf2 = messageT.indexOf(']');
		int indexOf3 = messageC.indexOf('[');
		int indexOf4 = messageC.indexOf(']');
		
		if(indexOf == -1 || indexOf2 == -1 || indexOf3 == -1 || indexOf4 == -1)
			return false;
		
		String textT = messageT.substring(indexOf,	indexOf2);
		String textC = messageC.substring(indexOf3,	indexOf4);
		return textT.equals(textC);
	}

	private boolean pathEquals(Commit t, Commit c) {
		List<String> pathsC = new ArrayList<String>();
		List<String> pathsT = new ArrayList<String>();
		
		for (Path pathC : c.getPaths()) 
			pathsC.add(pathC.getFileName());
		
		for (Path pathT : t.getPaths()) 
			pathsT.add(pathT.getFileName());
		
				
		return pathsT.containsAll(pathsC) && pathsT.size() == pathsC.size() ;
	}

}
