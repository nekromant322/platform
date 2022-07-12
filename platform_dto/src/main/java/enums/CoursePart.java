package enums;

public enum CoursePart {
    CORE("CORE"),
    WEB("WEB"),
    SPRING("Spring");

    private final String name;

    CoursePart(String coursePartName){
        name = coursePartName;
    }

    public String getName() {
        return name;
    }


}
