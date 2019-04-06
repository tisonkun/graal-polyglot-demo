package io.tison.polyglot;

import io.tison.polyglot.r.R;
import io.tison.polyglot.ruby.Ruby;
import io.tison.polyglot.ruby.Commits;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class GitPlot {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("r", "repo", true, "");
        options.addOption("s", "since", true, "");
        options.addOption("o", "output", true, "");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String repo = cmd.getOptionValue("repo");
        String since = cmd.hasOption("since") ? cmd.getOptionValue("since") : "2019-01-01";
        String output = cmd.getOptionValue("output");

        Commits commits = Ruby.generateGitInfo(repo, since);

        R.ggplot(commits, output);

        System.exit(0);
    }
}
