package pr4;

public class Item {
    private String name;
    private String type;
    private int value;
    private int weight;
    private String rarity;

    public Item(String name, String type, int value, int weight, String rarity) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.weight = weight;
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "[" + rarity + "] " + name + " (тип: " + type + ", ценность: " + value + ", вес: " + weight + ")";
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
}
