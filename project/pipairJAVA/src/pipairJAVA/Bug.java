package pipairJAVA;

import java.util.HashSet;

public class Bug {
    public final String name;
    public final String p1;
    public final int nameHashCode;
    public final int p1HashCode;
    public HashSet<String> locationSet;
    public final int support;
    public final double conf;

    /**
     * Store a bug info except the location of this bug,
     * because bug could exist in multiple locations of the program
     * @param name function name of the bug
     * @param p1 of pair name < p1 ? <name, p1> : <p1, name>
     * @param support count of the support pair
     * @param conf confidence of this bug
     */
    Bug(final String name, final String p1, final int nameHashCode, int p1HashCode,
            final int support, final double conf) {
        this.name = name;
        this.p1 = p1;
        this.nameHashCode = nameHashCode;
        this.p1HashCode = p1HashCode;
        this.support = support;
        this.conf = conf;
        this.locationSet = new HashSet<String>();
    }

    public void addLocation(String nodeName) {
        this.locationSet.add(nodeName);
    }

    public HashSet<String> getLocationSet() {
        return this.locationSet;
    }
}
