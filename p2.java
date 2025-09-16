import java.util.random.RandomGenerator;
import java.util.Scanner;

public class p2 {
    public static void main(String[] args) {

        RandomGenerator rng = RandomGenerator.getDefault();
        Scanner sc = new Scanner(System.in);

        int[] hero = {100, 0, 1, 15};

        for (int round = 1; round <= 3; round++) {

            int monsterHP = 15 + round * 10;
            int monsterAttack = 5 + round * 3;
            int[] monster = {monsterHP, monsterAttack};

            System.out.println("Раунд " + round + ": появляется монстр! HP: " + monsterHP + ", Урон: " + monsterAttack);

            boolean escaped = false;

            while (monster[0] > 0 && hero[0] > 0) {
                System.out.println("Герой: HP=" + hero[0] + ", XP=" + hero[1] + ", LVL=" + hero[2]);
                System.out.println("Действия: 1 - атаковать, 2 - лечиться, 3 - бежать");
                System.out.print("Выберите действие: ");
                int d = sc.nextInt();

                switch (d) {
                    case 1:
                        int damageHero = hero[3] + rng.nextInt(0, 6);
                        monster[0] -= damageHero;
                        System.out.println("Герой атакует и наносит " + damageHero + " урона!");

                        if (monster[0] > 0) {
                            int damageMonster = monster[1] + rng.nextInt(0, 4);
                            hero[0] -= damageMonster;
                            System.out.println("Монстр атакует и наносит " + damageMonster + " урона!");
                        }
                        break;

                    case 2:
                        int healHero = rng.nextInt(10, 31);
                        hero[0] += healHero;
                        System.out.println("Герой лечится и восстанавливает " + healHero + " HP.");

                        if (monster[0] > 0) {
                            int damageMonster = monster[1] + rng.nextInt(0, 4);
                            hero[0] -= damageMonster;
                            System.out.println("Монстр атакует и наносит " + damageMonster + " урона!");
                        }
                        break;

                    case 3:
                        if (rng.nextBoolean()) {
                            System.out.println("Герой убегает от монстра!");
                            escaped = true;
                            monster[0] = 0;
                        } else {
                            System.out.println("Побег не удался!");
                            int damageMonster = monster[1] + rng.nextInt(0, 4);
                            hero[0] -= damageMonster;
                            System.out.println("Монстр атакует и наносит " + damageMonster + " урона!");
                        }
                        break;

                    default:
                        System.out.println("Неизвестная команда!");
                        break;
                }
            }


            if (hero[0] <= 0) {
                System.out.println("Герой пал в бою...");
                break;
            } else if (!escaped) {
                System.out.println("Монстр побеждён! Герой получает +10 XP.");
                hero[1] += 10;
            } else {
                System.out.println("Герой продолжает путь без победы.");
            }
        }

        System.out.println("Конец приключения! Герой достиг уровня " + hero[2] + " с " + hero[1] + " XP и " + hero[0] + " HP");
    }
}
