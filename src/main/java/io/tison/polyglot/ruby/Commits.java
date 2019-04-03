package io.tison.polyglot.ruby;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.function.Function;

@SuppressWarnings("WeakerAccess")
public class Commits {

    public final ColumnProxy email;
    public final ColumnProxy date;

    public Commits(Info[] infos) {
        this.email = new ColumnProxy(infos, x -> x.email);
        this.date = new ColumnProxy(infos, x -> x.date);
    }

    public static class Info {
        public final String email;
        public final String date;

        public Info(String email, String date) {
            this.email = email;
            this.date = date;
        }
    }

    public static class ColumnProxy implements ProxyArray {
        public final Info[] infos;
        public final Function<Info, Object> extractor;

        public ColumnProxy(Info[] infos, Function<Info, Object> extractor) {
            this.infos = infos;
            this.extractor = extractor;
        }

        @Override
        public Object get(long index) {
            return extractor.apply(infos[(int)index]);
        }

        @Override
        public void set(long index, Value value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getSize() {
            return infos.length;
        }
    }

}
