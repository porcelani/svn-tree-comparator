package br.com.daniloporcelani;

import java.util.ArrayList;
import java.util.List;

public class SVNTreeComparatorResult {

	private List<Commit> commits = new ArrayList<Commit>();

	public SVNTreeComparatorResult(List<Commit> commits) {
		this.commits = commits;
	}

	public boolean areEquals() {
		return commits.isEmpty();
	}

	public List<Commit> commitsOnlyOnSource() {
		return commits;
	}
}
