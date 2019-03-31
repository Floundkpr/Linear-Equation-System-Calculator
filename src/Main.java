import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        m: do {
            try {
                StringBuilder protocol = new StringBuilder();
                boolean f = false;
                do {
                    System.out.println("Выберите метод: \n1.Метод Зейделя\n2.Метод хорд\n0.Выход");
                    switch (scanner.nextInt()) {
                        case 1:
                            f = false;
                            protocol.append(Methods.SeidelMethod());
                            break;
                        case 2:
                            f = false;
                            protocol.append(Methods.ChordMethod());
                            break;
                        case 0:
                            f = false;
                            break m;
                        default:
                            f = true;
                    }
                } while (f);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (scanner.nextInt() != 0);
    }
}
