import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TopoGigiDBTest {
    private TopoGigiDB gigi;

    public TopoGigiDBTest(){
        this.gigi = new TopoGigiDB();
    }

    @Test
    public void testSet() {
        gigi.set("pippo:1", "salvatore");
        gigi.set("pippo:2", "ciro");
        gigi.set("pippo:3", "crocefisso");
        List<String> r = gigi.getMatches("pippo:.+");
        Assertions.assertTrue(r.contains("salvatore"));
        Assertions.assertTrue(r.contains("ciro"));
        Assertions.assertTrue(r.contains("crocefisso"));
    }
}
