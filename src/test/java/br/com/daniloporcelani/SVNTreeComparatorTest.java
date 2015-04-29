package br.com.daniloporcelani;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SVNTreeComparatorTest {

	@Test
	public void compareEqualTrees() {
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[PROJ-2] message 2", asList(myClass1()));
		Commit logEntry3 = newLogEntry("3", "author 3", "date 3", "[PROJ-3] message 3", asList(myClass1()));

		Commit logEntry4 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry5 = newLogEntry("2", "author 2", "date 2", "[PROJ-2] message 2", asList(myClass1()));
		Commit logEntry6 = newLogEntry("3", "author 3", "date 3", "[PROJ-3] message 3", asList(myClass1()));
		
		List<Commit> source = Arrays.asList(logEntry1, logEntry2, logEntry3);
		List<Commit> target = Arrays.asList(logEntry4, logEntry5, logEntry6);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(source, target);
		
		assertThat(result.areEquals(), is(true));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(true));
	}
	
	@Test
	public void compareDifferentTrees() {
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[PROJ-2] message 2", asList(myClass1()));
		Commit logEntry3 = newLogEntry("3", "author 3", "date 3", "[PROJ-3] message 3", asList(myClass1()));

		Commit logEntry4 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry5 = newLogEntry("2", "author 2", "date 2", "[PROJ-2] message 2", asList(myClass1()));
		Commit logEntry6 = newLogEntry("4", "author 4", "date 4", "[PROJ-4] message 4", asList(myClass1()));
		
		List<Commit> source = Arrays.asList(logEntry1, logEntry2, logEntry3);
		List<Commit> target = Arrays.asList(logEntry4, logEntry5, logEntry6);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(target, source);
		
		assertThat(result.areEquals(), is(false));
		assertThat(result.commitsOnlyOnSource().size(), equalTo(1));
		assertThat(result.commitsOnlyOnSource().get(0), equalTo(logEntry3));
	}

	@Test
	public void treesWithSameMessageAreEquals() {
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));

		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[PROJ-1] message 1", asList(myClass1()));
		
		List<Commit> source = Arrays.asList(logEntry1);
		List<Commit> target = Arrays.asList(logEntry2);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(source, target);;
		
		assertThat(result.areEquals(), is(true));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(true));
	}

	@Test
	public void treesWithSameMessageButDifferentQuantityOfCommitsAreNotEquals() {
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[PROJ-1] message 1", asList(myClass2()));
		Commit logEntry3 = newLogEntry("3", "author 3", "date 3", "[PROJ-1] message 1", asList(myClass1()));
		
		List<Commit> source = Arrays.asList(logEntry1, logEntry3);
		
		List<Commit> target = Arrays.asList(logEntry2);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(target, source);
		
		assertThat(result.areEquals(), is(false));
		assertThat(result.commitsOnlyOnSource().size(), equalTo(1));
		assertThat(result.commitsOnlyOnSource().get(0), equalTo(logEntry3));
	}

	@Test
	public void treesWithSameMessageButDifferentQuantityOfCommitsOnTargetAreEquals() {
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[PROJ-1] message 1", asList(myClass1()));
		Commit logEntry3 = newLogEntry("3", "author 3", "date 3", "[PROJ-1] message 1", asList(myClass1()));
		
		List<Commit> source = Arrays.asList(logEntry1);
		List<Commit> target = Arrays.asList(logEntry2, logEntry3);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(source, target);
		
		assertThat(result.areEquals(), is(true));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(true));
	}
	
	@Test
	public void differentMessagesButWithSameTagAndSamePath() {

		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] Message 1", asList(myClass1()));
		
		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[PROJ-1] Message 2", asList(myClass1B()));
		
		List<Commit> source = Arrays.asList(logEntry1);
		List<Commit> target = Arrays.asList(logEntry2);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(source, target);
		
		assertThat(result.areEquals(), is(true));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(true));
	}
	
	@Test
	public void differentMessagesButWithSameTagAndSamePathWithTwoCommits() {		
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[PROJ-1] Message 1", asList(myClass1()));
		Commit logEntry2 = newLogEntry("2", "author 1", "date 2", "[PROJ-1] Message 1", asList(myClass2()));
		
		Commit logEntry3 = newLogEntry("3", "author 2", "date 3", "[PROJ-1] Message 2", asList(myClass1B(),myClass2B()));
		
		List<Commit> source = Arrays.asList(logEntry1,logEntry2);
		List<Commit> target = Arrays.asList(logEntry3);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(source, target);
		
		assertThat(result.areEquals(), is(true));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(true));
	}
	
	@Test
	public void anyTagDifferentTagDifferentMessages() {
		Commit logEntry1 = newLogEntry("1", "author 1", "date 1", "[anyTag][PROJ-1] message 1", asList(myClass1()));

		Commit logEntry2 = newLogEntry("2", "author 2", "date 2", "[anyTag][PROJ-2] message 2", asList(myClass1()));
		
		List<Commit> source = Arrays.asList(logEntry1);
		List<Commit> target = Arrays.asList(logEntry2);
		
		SVNTreeComparatorResult result = SVNTreeComparator.compare(source, target);
		
		assertThat(result.areEquals(), is(false));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(false));
	}

	
	private Commit newLogEntry(String revision, String author, String date, String message) {
		Commit logEntry = new Commit();
		logEntry.setRevision(revision);
		logEntry.setAuthor(author);
		logEntry.setDate(date);
		logEntry.setMessage(message);
		return logEntry;
	}
	
	private Commit newLogEntry(String revision, String author, String date, String message, List<Path> paths) {
		Commit logEntry = newLogEntry( revision, author,date, message);
		logEntry.setPaths(paths);
		return logEntry;
	}

	private Path myClass1() {
		Path classe1 = new Path();
		classe1.setAction("M");
		classe1.setKind("");
		classe1.setPath("/path/to/class/MyClass.java");
		classe1.setPropMods("");
		classe1.setTextMods("");
		return classe1;
	}

	private Path myClass1B() {
		Path classe1 = new Path();
		classe1.setAction("M");
		classe1.setKind("");
		classe1.setPath("/path/to/class/MyClass.java");
		classe1.setPropMods("");
		classe1.setTextMods("");
		return classe1;
	}
	
	private Path myClass2() {
		Path classe1 = new Path();
		classe1.setAction("M");
		classe1.setKind("");
		classe1.setPath("/path/to/class/MySecundClass.java");
		classe1.setPropMods("");
		classe1.setTextMods("");
		return classe1;
	}

	private Path myClass2B() {
		Path classe1 = new Path();
		classe1.setAction("M");
		classe1.setKind("");
		classe1.setPath("/path/to/class/MySecundClass.java");
		classe1.setPropMods("");
		classe1.setTextMods("");
		return classe1;
	}
}
