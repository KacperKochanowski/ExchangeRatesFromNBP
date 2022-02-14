package nbpApi;

public enum Table {
    TABLE_A("A"), //public static final Table TABLE_A = new Table("A")
    TABLE_B("B"),
    TABLE_C("C");

    private final String name;

    Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
