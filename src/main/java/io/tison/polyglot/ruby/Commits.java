package io.tison.polyglot.ruby;


@SuppressWarnings("WeakerAccess")
public class Commits {
    public final String[] email;
    public final String[] date;

    public Commits(long size) {
        this.date = new String[(int) size];
        this.email = new String[(int) size];
    }
}
