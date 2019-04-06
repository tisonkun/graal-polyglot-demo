package io.tison.polyglot.ruby;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.List;

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
        Value commits = context.eval("ruby", newline("") +
                newline("g = Git.open('" + repoPath + "')") +
                newline("g.log('" + Integer.MAX_VALUE + "')" +
                        ".since('" + since + "')" +
                        ".collect { |c| [c.author.email, c.date.strftime(\"%Y-%m\")] }"));

        List<Commits.Info> infos = new ArrayList<>();
        long commitsArraySize = commits.getArraySize();

        for (int i = 0; i < commitsArraySize; i++) {
            infos.add(
                    new Commits.Info(
                            commits.getArrayElement(i).getArrayElement(0).asString(),
                            commits.getArrayElement(i).getArrayElement(1).asString()
                    )
            );
        }

        return new Commits(infos.toArray(new Commits.Info[0]));
    }
}
