import java.io.FileReader;
import java.util.Scanner;

class Methods {
    private static Scanner scanner = new Scanner(System.in);

    static StringBuilder SeidelMethod() {

            StringBuilder protocol = new StringBuilder();

            try {

                //объявление переменных заранее для того чтобы они были видны при вычислении
                int n; //количество неизвестных
                double eps; //точность
                double[] b; //массив свободных членов
                double[][] A; //матрица коэффициентов


                boolean f; //переменная для проверки корректного ввода
                do {
                    System.out.println("Выберите действие: \n1.Ввести вручную\n2.Считать с файла\n0.Выход");
                    switch (scanner.nextInt()) {
                        case 1:
                            f = false; //ввод верный
                            protocol.append("Пользователь выбрал ввод вручную.").append('\n'); //запись каждого пункта в протокол
                            System.out.println("Введите количество неизвестных");
                            n = scanner.nextInt(); //считывание количества неизвестных
                            protocol.append("Количество неизвестных = ").append(n).append("\n");

                            System.out.println("Введите точность");
                            eps = scanner.nextDouble(); //считывание точности
                            protocol.append("Точность = ").append(eps).append("\n");

                            A = new double[n][n]; //создание массива коэффициентов n x n
                            b = new double[n]; //создание массива свободных членов


                            System.out.println("Введите матрицу А");
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < n; j++) {
                                    A[i][j] = scanner.nextDouble(); //считывание матрицы
                                }
                            }

                            protocol.append("Введена матрица А").append("\n");// запись в протокол

                            System.out.println("Введите матрица свободных членов");
                            for (int i = 0; i < n; i++) {
                                b[i] = scanner.nextDouble();
                            }

                            protocol.append("Введена матрица свободных членов").append("\n");
                            break;

                        case 2: //в случае считывания из файла
                            f = false;
                            protocol.append("Пользователь выбрал считывание из файла.").append('\n');

                            FileReader fr = new FileReader("./Data/Seidel.txt"); //переменная для считывания
                            Scanner scan = new Scanner(fr); //переменная которая будет считывать

                            String[] buf = scan.nextLine().split(" "); //разделение строки на слова

                            n = Integer.parseInt(buf[buf.length - 1]); //последнее слово в строке это количество неизв. переменных
                            protocol.append("Количество неизвестных = ").append(n).append("\n");

                            A = new double[n][n];//создание массива коэффициентов n x n
                            b = new double[n]; //создание массива свободных членов

                            scan.nextLine(); //пропуск строки

                            for (int i = 0; i < n; i++) {
                                buf = scan.nextLine().split(" "); //считывание строки и разделение чисел
                                for (int j = 0; j < n; j++) {
                                    A[i][j] = Double.parseDouble(buf[j]); //запись чисел в массив
                                }
                            }
                            protocol.append("Считана матрица А").append("\n");

                            scan.nextLine(); //пропуск строки

                            buf = scan.nextLine().split(" ");//считывание строки и разделение чисел
                            for (int i = 0; i < n; i++) {
                                b[i] = Double.parseDouble(buf[i]);//запись чисел в массив
                            }
                            protocol.append("Считана матрица свободных членов").append("\n");

                            buf = scan.nextLine().split(" ");//считывание строки и разделение чисел
                            eps = Double.parseDouble(buf[buf.length-1]);//последнее слово в строе это точность
                            protocol.append("Точность = ").append(eps).append("\n");

                            break;
                        case 0: //выход
                            protocol.append("Пользователь закрыл программу.").append('\n');
                            return protocol;
                        default: //если введен неверный вариант
                            n = 0;
                            A = new double[0][0];
                            b = new double[0];
                            eps = 0;
                            f = true;
                    }
                } while (f);

                //проверка системы на сходимость
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (Math.abs(A[i][i]) < Math.abs(A[i][j])) {//если в строке есть элемент больший по модулю чем элемент на главной диагонали
                            System.out.println("Система не сходима");//то система не сходима
                            protocol.append("Система не сходима, возвращение в меню").append("\n");
                            return protocol;
                        }
                    }
                }

                double[] xNew = new double[n];//массив для вычисления
                double[] xOld = new double[n];//массив для сравнения элементов, для вычисления точности

                do {
                    System.arraycopy(xNew, 0, xOld, 0, n);//копирование массива xNew

                    for (int i = 0; i < n; i++) {//двойной цикл. один для прохода по строкам, а другой для обработки самой строки
                        xNew[i] = 0;
                        for (int j = 0; j < n; j++) {
                            if (i != j) xNew[i] += A[i][j] * xNew[j]; //суммирование элементов строки с коэффициентами
                        }
                        xNew[i] = (b[i] - xNew[i]) / A[i][i]; //деление на коэфф при i-том x. это значение текщего x
                    }

                    double maxDiff = -1; //обнуление разницы
                    for (int i = 0; i < n; i++) {
                        if (Math.abs(xNew[i] - xOld[i]) > maxDiff) maxDiff = Math.abs(xNew[i] - xOld[i]); //поиск максимальной разницы между элементами
                    }
                    if (maxDiff < eps) break; // если достигнута необходимая точность то прервать вычисление

                } while (true);

                protocol.append("Система решена").append("\n");

                for (int i = 0; i < n; i++) {
                    System.out.printf("x[%d] = %.4f     ", i + 1, xNew[i]); //вывод результата на экран
                    protocol.append("x[").append(i + 1).append("] = ").append(xNew[i]).append("; "); //запись результата в протокол
                }
                System.out.println();
                System.out.println();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return protocol.append("\n"); //возврат протокола
    }

    static StringBuilder ChordMethod() {
        StringBuilder protocol = new StringBuilder(); //переменная для записи протокола

        System.out.println("Введите степень старшего члена");
        int pow = scanner.nextInt(); //считывание степени старшего члена. это же количество слагаемых кроме свободного члена
        protocol.append("Степень старшего члена = ").append(pow).append('\n');

        double[] ar = new double[pow + 1]; //создание массива для хранения коэффициентов
        for (int i = 0; i < ar.length; i++) {
            if (i == pow) {
                System.out.println("Введите свободный член");
            }
            else {
                System.out.println("Введите коэфф при X^" + (pow - i));
            }
            ar[i] = scanner.nextDouble(); //считывание коэффициентов
        }
        protocol.append("Введено уравнение ");
        for (int i = 0; i < pow; i++) {
            protocol.append("(").append(ar[i]).append("*x^").append(pow-i).append(")").append("+"); //запись уравнения в протокол
            System.out.print("(" + ar[i] + "*x^" + (pow-i) + ")+"); //вывод уравнения на экран
        }
        System.out.println("("+ar[pow]+") = 0");//продолжение вывода
        protocol.append("(").append(ar[pow]).append(") = 0").append("\n");

        System.out.println("Введите отрезок на котором находится корень");
        double a = scanner.nextDouble();//границы отрезка
        double b = scanner.nextDouble();
        if (a > b) { //если начальная граница больше конечной прекратить работу и выдать сообщение об ошибке
            System.out.println("Введен неверный формат");
            return protocol.append("Отрезок введен неверно").append("\n");
        }

        System.out.println("Введите точность");
        double eps = scanner.nextDouble();//считывание точности
        protocol.append("Точность = ").append(eps).append("\n");

        double fa = 0;
        double fb = 0;

        for (int i = pow, j = 0; j < pow; i--, j++) {
            fa += ar[j] * Math.pow(a, i);
            fb += ar[j] * Math.pow(b, i);
        }
        fa += ar[pow];//функция в точке а
        fb += ar[pow];//функция в точке b
        if (Math.signum(fa) == Math.signum(fb)) { //если значение функции неменяет знак, значит на данном отрезке нет корней
            System.out.println("В отрезке [" + a + ", " + b + "] нет корней.");
            return protocol.append("В данном отрезке нет корней.").append('\n');
        }

        double SDFA = 0;//переменная для хранения второй производной
        for (int i = pow; i > 1; i--) {
            SDFA += ar[i] * i * (i - 1) * Math.pow(a, i - 2);//вычисление второй производной в точке a
        }

        //проверка функции на перегибы
        double t = a;
        do {
            double ft = 0;
            for (int i = pow; i > 1; i--) {
                ft += ar[i] * i * (i - 1) * Math.pow(t, i - 2);//вычисление второй производной в точке между a и b
            }
            if (Math.signum(ft) != Math.signum(SDFA)) {//если знак второй производной в очередной точке отличается от знака второй производной в начале
                System.out.println("В отрезке [" + a + ", " + b + "] более одного корня"); //значит в отрезке более одного корня
                return protocol.append("В данном отрезке более одного корня.").append('\n'); //остановить вычисление и выдать сообщение
            }
            t += 0.1;
        } while (t <= b);//пока точка, в которой проверяется производная не будет равно концу отрезка

        double xi;
        if (Math.signum(SDFA) == Math.signum(fa)){ //если знак второй производной совпадает со знаком функции в начале отрезка
            double xiOld;//переменная для вычисоения точности
            xi = b; //то движущейся точкой будет конец отрезка

            do {//уточнение корня
                xiOld = xi;//запись очередного корня в память
                double fxi = 0;//переменная для хранения функции в точне xi
                for (int i = pow, j = 0; j < pow; i--, j++) {
                    fxi += ar[j] * Math.pow(xi, i);//вычисление функции в точне xi
                }
                fxi += ar[pow];//прибавление свободного члена
                xi = xi - (fxi / (fxi - fa)) * (xi - a); //вычисление корня
            } while (Math.abs(xi - xiOld) > eps);//пока не достигнута необходимая точность
        }
        else {//иначе
            double xiOld;
            xi = a;//движущейся точкой будет начало отрезка

            //такой же цикл что и выше
            do {
                xiOld = xi;
                double fxi = 0;
                for (int i = pow, j = 0; j < pow; i--, j++) {
                    fxi += ar[j] * Math.pow(a, i);
                }
                fxi += ar[pow];
                xi = xi - (fxi / (fb - fxi)) * (b - xi); //отличается формула вычисления корня
            } while (Math.abs(xi - xiOld) > eps);
        }

        System.out.println("X = " + xi); //вывод результата

        return protocol.append("X = ").append(xi).append('\n'); //запись в протокол и возврат
    }
}