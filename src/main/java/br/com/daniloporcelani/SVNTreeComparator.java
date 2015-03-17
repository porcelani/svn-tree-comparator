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
			for(Commit c : source)
				if(t.getMessage().equals(c.getMessage())) {
					commitsOnlyOnSource.remove(c);
					break;
				}
		
		return new SVNTreeComparatorResult(commitsOnlyOnSource);
	}

}
