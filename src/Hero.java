
public class Hero {
    private String name;
    private int strength;
    private int intelligence;
    private int level;
    private int experience;

    public Hero(String name, int strength, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.intelligence = intelligence;
        this.level = 1;
        this.experience = 0;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength < 0) {
            this.strength = 0;
            System.out.println(name + " погиб. Сила упала ниже 0 ");
        } else if (strength > 100) {
            this.strength = 100;
        } else {
            this.strength = strength;
        }
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        if (intelligence < 0) {
            this.intelligence = 0;
            System.out.println(name + " погиб. Интеллект упал ниже 0");
        } else if (intelligence > 100) {
            this.intelligence = 100;
        } else {
            this.intelligence = intelligence;
        }
    }

    public void addExperience(int exp) {
        experience += exp;
        if (experience >= 100) {
            level++;
            experience -= 100;
            System.out.println(name + " повысил уровень! Теперь уровень " + level);
        }
    }

    public void showStats() {
        System.out.println(name + ": сила = " + strength + ", интеллект = " + intelligence +
                ", уровень = " + level + ", опыт = " + experience);
    }
}
