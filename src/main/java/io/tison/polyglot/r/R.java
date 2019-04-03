package io.tison.polyglot.r;

import io.tison.polyglot.ruby.Commits;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public enum  R {
    ;

    private static final Context context;
    private static final Value ggplot;

    private static String newline(String source) {
        return source + "\n";
    }

    static {
        context = Context.newBuilder("R").allowAllAccess(true).build();

        context.eval("R", newline("library(dplyr)"));
        context.eval("R", newline("library('ggplot2')"));

        ggplot = context.eval("R", newline("") +
                newline("function(table, output) { ") +
                newline("  table <- as.data.frame(table)") +
//                newline("  print(table); cat('----------\n')") +
                newline("  email <- count(table, email)") +
                newline("  email <- top_n(email, 10)") +
                newline("  table <- rename(count(table, email, date), commits=n)") +
//                newline("  print(table); cat('----------\n')") +
                newline("  table <- merge(table, email, by='email')") +
//                newline("  print(table); cat('----------\n')") +
                newline("  ggplot(table, aes(date, commits, group=email, colour=email)) + geom_line(aes(colour=email)) + geom_point()") +
                newline("  ggsave(output)") +
                newline("}"));
    }

    public static void ggplot(Commits container, String output) {
        ggplot.execute(container, output);
    }
}
