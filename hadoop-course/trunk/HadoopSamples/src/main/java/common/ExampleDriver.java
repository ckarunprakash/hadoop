package common;

import mr.wordcount.StartsWithCountJob;

import org.apache.hadoop.util.ProgramDriver;

/**
 * A description of an example program based on its class and a human-readable
 * description.
 */
public class ExampleDriver {

	public static void main(String argv[]) {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("countJob", StartsWithCountJob.class,
					"An example of a job that counds number of tokens for each start letter");
			exitCode = pgd.driver(argv);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.exit(exitCode);
	}
}
