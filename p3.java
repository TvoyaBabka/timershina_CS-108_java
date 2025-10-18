import java.util.Scanner;
import java.util.random.RandomGenerator;

public class p3 {

    static char[][] map = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', 'P', '_', '_', '_', '_', '_', '_', 'M', '#'},
            {'#', '_', '#', '#', '_', '#', '#', '_', '_', '#'},
            {'#', '_', '_', '_', '_', '_', '#', '_', '_', '#'},
            {'#', '_', '#', '_', '#', '_', '_', '#', '_', '#'},
            {'#', '_', '#', '_', '_', '_', '_', '#', '_', '#'},
            {'#', '_', '#', '#', '#', '_', '#', '#', '_', '#'},
            {'#', '_', '_', '_', '_', '_', '_', '_', '_', '#'},
            {'#', '_', '_', '#', '#', '_', '#', '_', '_', 'E'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };

    static Scanner sc = new Scanner(System.in);
    static RandomGenerator rng = RandomGenerator.getDefault();

    public static void main(String[] args) {

        int[] hero = {100, 0, 1, 20};
        int[] position = {1, 1};

        boolean running = true;

        while (running) {
            clearConsole();
            printMaze();
            System.out.println("\nHP: " + hero[0] + " | XP: " + hero[1] + " | LVL: " + hero[2]);

            moveMonsters(position, hero);

            System.out.println("Введите движение (W/A/S/D): ");
            char move = sc.next().toUpperCase().charAt(0);

            int newY = position[0];
            int newX = position[1];

            switch (move) {
                case 'W':
                    newY--;
                    break;
                case 'S':
                    newY++;
                    break;
                case 'A':
                    newX--;
                    break;
                case 'D':
                    newX++;
                    break;
                default:
                    continue;
            }

            if (newY < 0 || newY >= map.length || newX < 0 || newX >= map[0].length)
                continue;

            char target = map[newY][newX];

            if (target == '#') {
                hero[0] -= 20;
                if (hero[0] <= 0) {
                    System.out.println("Вы врезались в стену и потеряли все HP. Игра окончена.");
                    break;
                } else {
                    System.out.println("Вы врезались в стену и потеряли 20 HP.");
                    continue;
                }
            }

            if (target == 'E') {
                clearConsole();
                printMaze();
                System.out.println("\nВы нашли выход! Победа!");
                break;
            }

            if (target == 'M') {
                boolean won = battle(hero);
                if (won) {
                    map[newY][newX] = '_';
                }
                continue;
            }

            map[position[0]][position[1]] = '_';
            position[0] = newY;
            position[1] = newX;
            map[position[0]][position[1]] = 'P';
        }

        System.out.println("Игра окончена! HP: " + hero[0] + ", XP: " + hero[1] + ", LVL: " + hero[2]);
    }

    static void moveMonsters(int[] heroPos, int[] hero) {
        char[][] newMap = new char[map.length][map[0].length];

        for (int y = 0; y < map.length; y++)
            System.arraycopy(map[y], 0, newMap[y], 0, map[0].length);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'M') {
                    int newY = y;
                    int newX = x;
                    int dir = rng.nextInt(4);
                    switch (dir) {
                        case 0 -> newY--;
                        case 1 -> newY++;
                        case 2 -> newX--;
                        case 3 -> newX++;
                    }

                    if (newY < 0 || newY >= map.length || newX < 0 || newX >= map[0].length)
                        continue;
                    if (map[newY][newX] == '#' || map[newY][newX] == 'M' || map[newY][newX] == 'P')
                        continue;

                    newMap[newY][newX] = 'M';
                    newMap[y][x] = '_';
                }
            }
        }

        for (int y = 0; y < map.length; y++)
            System.arraycopy(newMap[y], 0, map[y], 0, map[0].length);
    }

    static void printMaze() {
        for (char[] row : map) {
            for (char col : row) {
                switch (col) {
                    case '#' -> System.out.print("🟦");
                    case 'P' -> System.out.print("😋");
                    case 'M' -> System.out.print("👻");
                    case 'E' -> System.out.print("🚪");
                    case '_' -> System.out.print("⬛");
                }
            }
            System.out.println();
        }
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static boolean battle(int[] hero) {
        int monsterHP = 15 + rng.nextInt(6, 16);
        int monsterAttack = 5 + rng.nextInt(0, 6);
        int[] monster = {monsterHP, monsterAttack};

        System.out.println("\nНа вас напал монстр! HP: " + monsterHP + ", Урон: " + monsterAttack);
        boolean escaped = false;

        while (monster[0] > 0 && hero[0] > 0) {
            System.out.println("Герой: HP=" + hero[0] + ", XP=" + hero[1] + ", LVL=" + hero[2]);
            System.out.println("Действия: 1 - атаковать, 2 - лечиться, 3 - бежать");
            System.out.print("Выберите действие: ");
            int d;
            if (sc.hasNextInt()) {
                d = sc.nextInt();
            } else {
                sc.next();
                continue;
            }

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
                        System.out.println("Герой сбежал от монстра!");
                        escaped = true;
                        return false;
                    } else {
                        System.out.println("Побег не удался!");
                        int damageMonster = monster[1] + rng.nextInt(0, 4);
                        hero[0] -= damageMonster;
                        System.out.println("Монстр атакует и наносит " + damageMonster + " урона!");
                    }
                    break;
                default:
                    continue;
            }
        }

        if (hero[0] <= 0) {
            System.out.println("Герой пал в бою. Игра окончена!");
            System.exit(0);
        }

        if (!escaped) {
            System.out.println("Монстр побеждён! Герой получает +10 XP.");
            hero[1] += 10;
            return true;
        }

        return false;
    }
}

