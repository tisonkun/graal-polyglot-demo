package io.tison.polyglot.ruby;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public enum Ruby {
    ;

    private static final Context context;

    private static String newline(String source) {
        return source + "\n";
    }

    static {
        context = Context.newBuilder("ruby")
                .allowAllAccess(true)
                .option("ruby.single_threaded", "false")
                .build();
        context.eval("ruby", newline("require 'git'"));
    }

    public static Commits generateGitInfo(
            String repoPath,
            String since) {
        final Value commits = context.eval("ruby", newline("") +
                newline("g = Git.open('" + repoPath + "')") +
                newline("g.log('" + Integer.MAX_VALUE + "')" +
                        ".since('" + since + "')" +
                        ".collect { |c| [c.author.email, c.date.strftime(\"%Y-%m\")] }"));

        final long commitsArraySize = commits.getArraySize();

        final Commits ret = new Commits(commitsArraySize);

        for (int i = 0; i < commitsArraySize; i++) {
            ret.email[i] = commits.getArrayElement(i).getArrayElement(0).asString();
            ret.date[i] = commits.getArrayElement(i).getArrayElement(1).asString();
        }

        return ret;
    }
}
