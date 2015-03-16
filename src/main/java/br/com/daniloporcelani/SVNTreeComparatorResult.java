package br.com.daniloporcelani;

import java.util.ArrayList;
import java.util.List;

public class SVNTreeComparatorResult {

	private List<LogEntry> commits = new ArrayList<LogEntry>();

	public boolean areEquals() {
		return commits.isEmpty();
	}

	public List<LogEntry> commitsOnlyOnSource() {
		return commits;
	}

	public void add(LogEntry entry) {
		commits.add(entry);
	}

}
