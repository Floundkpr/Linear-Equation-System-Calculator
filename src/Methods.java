import java.io.FileReader;
import java.util.Scanner;

class Methods {
    private static Scanner scanner = new Scanner(System.in);

    static StringBuilder SeidelMethod() {

            StringBuilder protocol = new StringBuilder();

            try {

                int n;
                double eps;
                double[] b;
                double[][] A;


                boolean f;
                do {
                    System.out.println("Выберите действие: \n1.Ввести вручную\n2.Считать с файла\n0.Выход");
                    switch (scanner.nextInt()) {
                        case 1:
                            f = false;
                            protocol.append("Пользователь выбрал ввод вручную.").append('\n');
                            System.out.println("Введите количество неизвестных");
                            n = scanner.nextInt();
                            protocol.append("Количество неизвестных = ").append(n).append("\n");

                            System.out.println("Введите точность");
                            eps = scanner.nextDouble();
                            protocol.append("Точность = ").append(eps).append("\n");

                            A = new double[n][n];
                            b = new double[n];


                            System.out.println("Введите матрицу А");
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < n; j++) {
                                    A[i][j] = scanner.nextDouble();
                                }
                            }

                            protocol.append("Введена матрица А").append("\n");

                            System.out.println("Введите матрица свободных членов");
                            for (int i = 0; i < n; i++) {
                                b[i] = scanner.nextDouble();
                            }

                            protocol.append("Введена матрица свободных членов").append("\n");
                            break;

                        case 2:
                            f = false;
                            protocol.append("Пользователь выбрал считывание из файла.").append('\n');

                            FileReader fr = new FileReader("./Data/Seidel.txt");
                            Scanner scan = new Scanner(fr);

                            String[] buf = scan.nextLine().split(" ");

                            n = Integer.parseInt(buf[buf.length - 1]);
                            protocol.append("Количество неизвестных = ").append(n).append("\n");

                            A = new double[n][n];
                            b = new double[n];

                            scan.nextLine();

                            for (int i = 0; i < n; i++) {
                                buf = scan.nextLine().split(" ");
                                for (int j = 0; j < n; j++) {
                                    A[i][j] = Double.parseDouble(buf[j]);
                                }
                            }
                            protocol.append("Считана матрица А").append("\n");

                            scan.nextLine();

                            buf = scan.nextLine().split(" ");
                            for (int i = 0; i < n; i++) {
                                b[i] = Double.parseDouble(buf[i]);
                            }
                            protocol.append("Считана матрица свободных членов").append("\n");

                            buf = scan.nextLine().split(" ");
                            eps = Double.parseDouble(buf[buf.length-1]);
                            protocol.append("Точность = ").append(eps).append("\n");

                            break;
                        case 0:
                            protocol.append("Пользователь закрыл программу.").append('\n');
                            return protocol;
                        default:
                            n = 0;
                            A = new double[0][0];
                            b = new double[0];
                            eps = 0;
                            f = true;
                    }
                } while (f);

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (Math.abs(A[i][i]) < Math.abs(A[i][j])) {
                            System.out.println("Система не сходима");
                            protocol.append("Система не сходима, возвращение в меню").append("\n");
                            return protocol;
                        }
                    }
                }

                double[] xNew = new double[n];
                double[] xOld = new double[n];

                do {
                    System.arraycopy(xNew, 0, xOld, 0, n);

                    for (int i = 0; i < n; i++) {
                        xNew[i] = 0;
                        for (int j = 0; j < n; j++) {
                            if (i != j) xNew[i] += A[i][j] * xNew[j];
                        }
                        xNew[i] = (b[i] - xNew[i]) / A[i][i];
                    }

                    double maxDiff = -1;
                    for (int i = 0; i < n; i++) {
                        if (Math.abs(xNew[i] - xOld[i]) > maxDiff) maxDiff = Math.abs(xNew[i] - xOld[i]);
                    }
                    if (maxDiff < eps) break;

                } while (true);

                protocol.append("Система решена").append("\n");

                for (int i = 0; i < n; i++) {
                    System.out.printf("x[%d] = %.4f     ", i + 1, xNew[i]);
                    protocol.append("x[").append(i + 1).append("] = ").append(xNew[i]).append("; ");
                }
                System.out.println();
                System.out.println();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return protocol.append("\n");
    }

    static StringBuilder ChordMethod() {
        StringBuilder protocol = new StringBuilder();

        System.out.println("Введите степень старшего члена");
        int pow = scanner.nextInt();
        protocol.append("Степень старшего члена = ").append(pow).append('\n');

        double[] ar = new double[pow + 1];
        for (int i = 0; i < ar.length; i++) {
            if (i == pow) {
                System.out.println("Введите свободный член");
            }
            else {
                System.out.println("Введите коэфф при X^" + (pow - i));
            }
            ar[i] = scanner.nextDouble();
        }
        protocol.append("Введено уравнение ");
        for (int i = 0; i < pow; i++) {
            protocol.append("(").append(ar[i]).append("*x^").append(pow-i).append(")").append("+");
            System.out.print("(" + ar[i] + "*x^" + (pow-i) + ")+");
        }
        System.out.println("("+ar[pow]+") = 0");
        protocol.append("(").append(ar[pow]).append(") = 0").append("\n");

        System.out.println("Введите отрезок на котором находится корень");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        if (a > b) {
            System.out.println("Введен неверный формат");
            return protocol.append("Отрезок введен неверно").append("\n");
        }

        System.out.println("Введите точность");
        double eps = scanner.nextDouble();
        protocol.append("Точность = ").append(eps).append("\n");

        double fa = 0;
        double fb = 0;

        for (int i = pow, j = 0; j < pow; i--, j++) {
            fa += ar[j] * Math.pow(a, i);
            fb += ar[j] * Math.pow(b, i);
        }
        fa += ar[pow];
        fb += ar[pow];
        if (Math.signum(fa) == Math.signum(fb)) {
            System.out.println("В отрезке [" + a + ", " + b + "] нет корней.");
            return protocol.append("В данном отрезке нет корней.").append('\n');
        }

        double SDFA = 0;
        for (int i = pow; i > 1; i--) {
            SDFA += ar[i] * i * (i - 1) * Math.pow(a, i - 2);
        }

        double t = a;
        do {
            double ft = 0;
            for (int i = pow; i > 1; i--) {
                ft += ar[i] * i * (i - 1) * Math.pow(t, i - 2);
            }
            if (Math.signum(ft) != Math.signum(SDFA)) {
                System.out.println("В отрезке [" + a + ", " + b + "] более одного корня");
                return protocol.append("В данном отрезке более одного корня.").append('\n');
            }
            t += 0.1;
        } while (t <= b);

        double xi;
        if (Math.signum(SDFA) == Math.signum(fa)){
            double xiOld;
            xi = b;

            do {
                xiOld = xi;
                double fxi = 0;
                for (int i = pow, j = 0; j < pow; i--, j++) {
                    fxi += ar[j] * Math.pow(xi, i);
                }
                fxi += ar[pow];
                xi = xi - (fxi / (fxi - fa)) * (xi - a);
            } while (Math.abs(xi - xiOld) > eps);
        }
        else {
            double xiOld;
            xi = a;

            do {
                xiOld = xi;
                double fxi = 0;
                for (int i = pow, j = 0; j < pow; i--, j++) {
                    fxi += ar[j] * Math.pow(a, i);
                }
                fxi += ar[pow];
                xi = xi - (fxi / (fb - fxi)) * (b - xi);
            } while (Math.abs(xi - xiOld) > eps);
        }

        System.out.println("X = " + xi);

        return protocol.append("X = ").append(xi).append('\n');
    }
}
