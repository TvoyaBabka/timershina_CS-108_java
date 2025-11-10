import java.util.Random;

public class PotionFactory {
    private static final Random random = new Random();

    public static Potion createRandomPotion() {
        int type = random.nextInt(4);

        switch (type) {
            case 0:
                return new Potion("Зелье силы", +5, 0, 20, "Обычное");
            case 1:
                return new Potion("Зелье разума", 0, +5, 20, "Обычное");
            case 2:
                return new Potion("Эликсир мудрости", +3, +3, 25, "Редкое");
            default:
                return new Potion("Зелье яда", -10, -5, 10, "Проклятое");
        }
    }
}
