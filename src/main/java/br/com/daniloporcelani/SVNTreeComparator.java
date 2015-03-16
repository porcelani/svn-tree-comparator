package br.com.daniloporcelani;

import java.util.List;

public class SVNTreeComparator {

	private List<LogEntry> subject;

	public SVNTreeComparator(List<LogEntry> tree) {
		subject = tree;
	}

	public SVNTreeComparatorResult compare(List<LogEntry> target) {
		SVNTreeComparatorResult result = new SVNTreeComparatorResult();
		
		for(LogEntry entry : subject)
			if(!target.contains(entry))
				result.add(entry);
		
		return result;
	}

}
