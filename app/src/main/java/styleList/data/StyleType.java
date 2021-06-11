package styleList.data;

public class StyleType {
    int type;
    String category;

    public StyleType(int type) {
        this.type = type;
    }
    public StyleType(int type,String category) {
        this.type = type; this.category=category;
    }

    public int getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }
}
