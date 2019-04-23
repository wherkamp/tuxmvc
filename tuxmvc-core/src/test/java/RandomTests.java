import java.util.Objects;

public class    RandomTests {
    public static void main(String[] args) {
        System.out.println(Objects.hash(1l, "bob"));
        System.out.println(Objects.hash(1l,"gob"));
        System.out.println(new Long(1).hashCode());
    }
}
