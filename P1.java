import java.util.Scanner;

public class P1 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("Введите ваше имя: ");
        String name = console.nextLine();

        System.out.print("Введите ваш возраст: ");
        int age = console.nextInt();

        System.out.print("Есть ли у вас лицензия на меч? (true/false): ");
        boolean license = console.nextBoolean();

        System.out.print("Есть ли у вас броня? (true/false): ");
        boolean armor = console.nextBoolean();

        System.out.print("Введите уровень вашей силы (1-100): ");
        int power = console.nextInt();

        if ((age > 18 && license) || armor) {
            System.out.println(name + ", вы допущены к бою с драконом!");
        } else {
            System.out.println(name + ", вы слишком слабы и не можете участвовать в бою!");
            return;
        }



        if (power < 30) {
            System.out.println("Вы проиграли дракону!");
        } else if (power <= 60) {
            System.out.println("Вы сражались достойно, но дракон улетел!");
        } else {
            System.out.println("Поздравляем! Вы победили дракона!");
        }

        int point = (age / 2 + power + (armor ? 20 : 0) + (license ? 10 : 0));

        String title;

        if (point < 50) {
            title = "Новичок";
        } else if (point < 100) {
            title = "Рыцарь";
        } else {
            title = "Легендарный герой";
        }

        System.out.println("Ваши очки героя: " + point);
        System.out.println("Ваш титул: " + title);

        console.close();
    }
}
