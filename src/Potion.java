public class Potion {
    private String name;
    private int strengthChange;
    private int intelligenceChange;
    private int expReward;
    private String rarity;

    public Potion(String name, int strengthChange, int intelligenceChange, int expReward, String rarity) {
        this.name = name;
        this.strengthChange = strengthChange;
        this.intelligenceChange = intelligenceChange;
        this.expReward = expReward;
        this.rarity = rarity;
    }

    public void apply(Hero hero) {
        System.out.println("\nПрименяем " + name + " (" + rarity + ")...");

        hero.setStrength(hero.getStrength() + strengthChange);
        hero.setIntelligence(hero.getIntelligence() + intelligenceChange);
        hero.addExperience(expReward);

        System.out.println("Новые характеристики героя:");
        hero.showStats();
    }

    public String getName() {
        return name;
    }
}
