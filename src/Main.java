import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            StringBuilder protocol = new StringBuilder();
            protocol.append("Программа запущена.").append('\n');

            m: do {
                //Выполнение самих методов

                boolean f;
                do {
                    System.out.println("Выберите метод: \n1.Метод Зейделя\n2.Метод хорд\n0.Выход");
                    switch (scanner.nextInt()) {
                        case 1:
                            f = false;
                            protocol.append("Пользователь выбрал решение методом Зейделя.").append('\n');
                            protocol.append(Methods.SeidelMethod());
                            break;
                        case 2:
                            f = false;
                            protocol.append("Пользователь выбрал решение методом хорд.").append('\n');
                            protocol.append(Methods.ChordMethod());
                            break;
                        case 0:
                            protocol.append("Пользователь закрыл программу.").append('\n');
                            break m;
                        default:
                            f = true;
                    }
                } while (f);
            } while (true);

            protocol.append("Программа завершила свою работу.").append('\n');

            //Запись в протокол
            FileWriter writer = new FileWriter("./Data/Protocol.txt", false);
            writer.append(protocol);
            writer.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
