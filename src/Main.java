import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            StringBuilder protocol = new StringBuilder(); //переменная для хранения протоколв
            protocol.append("Программа запущена.").append('\n'); //запись в проотокол

            m: do {
                //Выполнение самих методов

                boolean f; //переменная для проверки корректного ввода
                do {
                    System.out.println("Выберите метод: \n1.Метод Зейделя\n2.Метод хорд\n0.Выход");
                    switch (scanner.nextInt()) { //считывание варианта
                        case 1:
                            f = false; //введено корректно
                            protocol.append("Пользователь выбрал решение методом Зейделя.").append('\n'); //запись в протокол выбранного вырианта
                            protocol.append(Methods.SeidelMethod()); //вызов функции метода Зейделя и запись в протокол.
                            break;
                        case 2:
                            f = false;
                            protocol.append("Пользователь выбрал решение методом хорд.").append('\n');
                            protocol.append(Methods.ChordMethod()); //вызов функции метода хорд и запись в протокол
                            break;
                        case 0: //пользователь выбрал выход из программы
                            protocol.append("Пользователь закрыл программу.").append('\n');
                            break m; //прекратить цикл
                        default: //введено значение не из списка
                            f = true; //неверный формат - повторить цикл
                    }
                } while (f);
            } while (true);

            protocol.append("Программа завершила свою работу.").append('\n');

            //Запись в протокол
            FileWriter writer = new FileWriter("./Data/Protocol.txt", false); //переменная для записи в файл
            writer.append(protocol); //запись в файл
            writer.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
