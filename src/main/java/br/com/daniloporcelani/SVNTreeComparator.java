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

		for (Commit c : source)
			if (c.getAuthor().equals("builder"))
				commitsOnlyOnSource.remove(c);

		
		for (Commit t : target)
			for (Commit c : commitsOnlyOnSource)
				if (t.getMessage().equals(c.getMessage())) {
					commitsOnlyOnSource.remove(c);
					break;
				}

		
		for (Commit t : target)
			for (Commit c : commitsOnlyOnSource) 
				if (hasSameTag(t, c) && pathEquals(t, c)) {
					commitsOnlyOnSource.remove(c);
					break;
				}
			
		
		for (Commit t : target){
			List<Commit> listTarget = new ArrayList<Commit>();
			List<Commit> listSource = new ArrayList<Commit>();
			
			List<Path> pathTarget = new ArrayList<Path>();
			List<Path> pathSource = new ArrayList<Path>();

			for (Commit commit : target) 
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

	private String getTag(Commit t) {
		String message = t.getMessage();
		int indexOf = message.indexOf('[');
		int indexOf2 = message.indexOf(']');
		return message.substring(indexOf + 1, indexOf2);
	}
	
	private boolean hasSameTag(Commit t, Commit c) {
		String messageT = t.getMessage();
		String messageC = c.getMessage();

		int indexOf = messageT.indexOf('[');
		int indexOf2 = messageT.indexOf(']');
		int indexOf3 = messageC.indexOf('[');
		int indexOf4 = messageC.indexOf(']');

		if (indexOf == -1 || indexOf2 == -1 || indexOf3 == -1 || indexOf4 == -1)
			return false;

		String textT = messageT.substring(indexOf, indexOf2);
		String textC = messageC.substring(indexOf3, indexOf4);
		return textT.equals(textC);
	}

	private boolean pathEquals(Commit t, Commit c) {
		List<String> pathsC = new ArrayList<String>();
		List<String> pathsT = new ArrayList<String>();

		for (Path pathC : c.getPaths())
			pathsC.add(pathC.getFileName());

		for (Path pathT : t.getPaths())
			pathsT.add(pathT.getFileName());

		return pathsT.containsAll(pathsC) && pathsT.size() == pathsC.size();
	}

	private boolean pathEquals(List<Path> t, List<Path> c) {
		List<String> pathsC = new ArrayList<String>();
		List<String> pathsT = new ArrayList<String>();
		
		for (Path pathC : c)
			pathsC.add(pathC.getFileName());
		
		for (Path pathT : t)
			pathsT.add(pathT.getFileName());
		
		return pathsT.containsAll(pathsC) && pathsT.size() == pathsC.size();
	}
}
