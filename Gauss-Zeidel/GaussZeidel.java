import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;

public class GaussZeidel {

    private double [][] a; //исходный массив коэффициентов
    private int n, m; // его размер
    private double accuracy; // точность

    private double [] sum; // массив сумм
    public int [] best; // лучшая перестановка

    private int iter = 10, check_last_iter = 4; //заданные значения для решения с контролем

    public void init(String s) throws FileNotFoundException {  //инициализация
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[ \t]+");
        String str = scan.nextLine();
        String [] sn = pat.split(str);
        n = Integer.parseInt(sn[0]);
        m = n+1;
        accuracy = Double.parseDouble(sn[1]);
        create(n, m);
        for (int i = 0; i < n; i++)
        {
            str = scan.nextLine();
            sn = pat.split(str);
            for( int j = 0; j < m; j++)
                a[i][j] = Double.parseDouble(sn[j]);
        }
        scan.close();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m - 1; j++) {
                sum[i] += Math.abs(a[i][j]);
            }
        }
    }

    private void create(int k, int l){   //создание массива
        int i;
        a = new double[k][];
        for(i = 0; i < k; i++)
            a[i] = new double[l];
        best = new int[k];
        for(i = 0; i < k; i++) {
            best[i] = i;
        }
        sum = new double[k];
        for(i = 0; i < k; i++)
            sum[i] = 0;
    }

    public void print() {   //вывод
        System.out.println();
        int i, j;
        for(i = 0; i < a.length; i++)
        {
            for(j = 0; j < a[0].length; j++)
                System.out.printf("%15.6E", a[i][j]);
            System.out.println();
        }
    }


    public int make_best_cond() { // приведение к лучшей перестановке
        if(check_null(best)) { // проверка диагонали на нули
            int condition = find_best(); // находим перестановку
            make_best(); //делаем ее
            return condition;
        }
        if(DUS(best)) //проверка на дус
            return 2;
        else
            return 1;
    }

    private int find_best() { // ищет лучшую перестановку
        int condition = 0;
        boolean found = false;
        int [] p = new int[n]; //массив с перестановками

        for(int i = 0; i < n; i++) { //заполняем
            p[i] = i;
        }
        while(reverse(p)) {
            if (DUS(p)) { // проверяем на дус
                for (int i = 0; i < p.length; i++) {
                    best[i] = p[i];
                }
                return 2; // дус выполняется
            }
            if (!found && !check_null(p)) { //сохраняем перестановку
                found = true;
                for (int i = 0; i < p.length; i++) {
                    best[i] = p[i];
                }
                condition = 1; //есть ненулевая перестановка

            }
        }
        return condition;
    }

    private boolean reverse(int [] p) { // перебирает от возрастания к убыванию
        int i = n - 2;
        while (i != -1 && p[i] >= p[i + 1])
            i--;
        if (i == -1) // убывание?
            return false;
        int k = n - 1;
        while (p[i] >= p[k])
            k--;
        swap(p, i, k);
        int l = i + 1, r = n - 1; // оставшуюся часть сортируем
        while (l < r)
            swap(p, l++, r--);
        return true;
    }

    private void swap(int [] t, int first, int second) { // меняет местами куски массива
        int temp = t[first];
        t[first] = t[second];
        t[second] = temp;
    }

    private void make_best() { // переставляет матрицу в лучшую перестановку
        double [][] temp = new double [n][];
        int i;
        for(i = 0; i < n; i++) {
            temp[best[i]] = a[i];
            best[i] = i; // лучшую перестановку применили => массив с лучшей перестановкой приводим к исх. виду
        }
        a = temp;
    }

    private boolean DUS(int [] var) { // проверка дус
        int i;
        int check = 0; // проверка наличия строки с равенством суммы и числа
        for(i = 0; i < n; i++) {
            if(sum[i] > 2 * Math.abs(a[i][var[i]]))
                return false;
            else {
                if(sum[i] == 2 * Math.abs(a[i][var[i]])) {
                    if(check != n - 1) {
                        check++;
                    }
                    else
                        return false;
                }
            }
        }
        return true;
    }

    public double [] solve (int condition) { //решение системы
        double [] solution = new double [n]; // массив решений
        for(int i = 0; i < n; i++) { // начальное приближение - 0
            solution[i] = 0;
        }

        if(condition == 2) {
            solution = no_control(solution); //  решаем без контроля
        }
        else {
            solution = control(solution); // решаем с контролем
        }
        return solution;
    }

    private double [] control(double [] solution) { //решение с контролем
        int i;
        double t, h;
        for(i = 0; i < iter - check_last_iter - 1; i++) {
        }
        t = iteration(solution);

        for(i = 0; i < check_last_iter; i++) {
            h = iteration(solution);
            if(t < h) {
                return null;
            }
            t = h;
        }
        return no_control(solution);
    }

    private double [] no_control(double [] solution) { //без контроля

        double delta = 0;
        do{
            delta = iteration(solution);
        } while(delta >= accuracy);

        return solution;
    }

    private double iteration(double [] solution) { //одна итерация
        double delta = 0, t;

        for(int j = 0; j < n; j++) {

            t = solution[j];
            solution[j] = a[j][m - 1]; //приравниваем переменной значение свободного члена

            for(int k = 0; k < j; k++) {
                solution[j] -= a[j][k] * solution[k];
            }
            for(int k = j + 1; k < m - 1; k++) {
                solution[j] -= a[j][k] * solution[k];
            }

            solution[j] /= a[j][j];
            t = Math.abs(t - solution[j]); //находим разность с предыдущим значением
            if(t > delta)
                delta = t;
        }
        return delta;  //возвращает разницу
    }

    private boolean check_null(int [] temp) { //проверка диагонали на отсутствие нулей
        for(int i = 0; i < n; i++) {
            if(a[temp[i]][i] == 0) {
                return true;
            }
        }
        return false;
    }

    public void print_m(double [] p) { // выводим массив
        for(int i = 0; i < p.length; i++) {
            System.out.printf("x%d: %15.6e \n", (i + 1), p[i]);
        }
    }
}
