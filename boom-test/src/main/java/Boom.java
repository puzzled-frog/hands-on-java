public class Boom {

    public static void a() {
        b();
    }

    public static void b() {
        boom();
    }

    public static void boom() {
        throw new RuntimeException("boom");
    }

    public static void main(String[] args) {
        a();
    }

}
