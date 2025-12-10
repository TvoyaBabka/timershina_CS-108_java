package characters;
import strategies.MeleeAttack; // Используем Melee, т.к. стратегии RageAttack нет
public class Rogue extends Character {
    public Rogue(String name) { super(name, 105, new MeleeAttack()); }
    @Override
    public String getStats() { return "Разбойник [HP: " + getHealth() + "]"; }
}