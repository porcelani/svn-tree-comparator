package br.com.daniloporcelani;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SVNTreeComparatorTest {

	@Test
	public void compareEqualTrees() {
		LogEntry logEntry1 = newLogEntry("1", "author 1", "date 1", "message 1");
		LogEntry logEntry2 = newLogEntry("2", "author 2", "date 2", "message 2");
		LogEntry logEntry3 = newLogEntry("3", "author 3", "date 3", "message 3");

		LogEntry logEntry4 = newLogEntry("1", "author 1", "date 1", "message 1");
		LogEntry logEntry5 = newLogEntry("2", "author 2", "date 2", "message 2");
		LogEntry logEntry6 = newLogEntry("3", "author 3", "date 3", "message 3");
		
		List<LogEntry> source = Arrays.asList(logEntry1, logEntry2, logEntry3);
		List<LogEntry> target = Arrays.asList(logEntry4, logEntry5, logEntry6);
		
		SVNTreeComparator comparator = new SVNTreeComparator(source);
		SVNTreeComparatorResult result = comparator.compare(target);
		
		assertThat(result.areEquals(), is(true));
		assertThat(result.commitsOnlyOnSource().isEmpty(), is(true));
	}
	
	@Test
	public void compareDifferentTrees() {
		LogEntry logEntry1 = newLogEntry("1", "author 1", "date 1", "message 1");
		LogEntry logEntry2 = newLogEntry("2", "author 2", "date 2", "message 2");
		LogEntry logEntry3 = newLogEntry("3", "author 3", "date 3", "message 3");

		LogEntry logEntry4 = newLogEntry("1", "author 1", "date 1", "message 1");
		LogEntry logEntry5 = newLogEntry("2", "author 2", "date 2", "message 2");
		LogEntry logEntry6 = newLogEntry("4", "author 4", "date 4", "message 4");
		
		List<LogEntry> source = Arrays.asList(logEntry1, logEntry2, logEntry3);
		List<LogEntry> target = Arrays.asList(logEntry4, logEntry5, logEntry6);
		
		SVNTreeComparator comparator = new SVNTreeComparator(source);
		SVNTreeComparatorResult result = comparator.compare(target);
		
		assertThat(result.areEquals(), is(false));
		assertThat(result.commitsOnlyOnSource().size(), equalTo(1));
		assertThat(result.commitsOnlyOnSource().get(0), equalTo(logEntry3));
	}

	private LogEntry newLogEntry(String revision, String author, String date, String message) {
		LogEntry logEntry = new LogEntry();
		logEntry.setRevision(revision);
		logEntry.setAuthor(author);
		logEntry.setDate(date);
		logEntry.setMessage(message);
		return logEntry;
	}
}
