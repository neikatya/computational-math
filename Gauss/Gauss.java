import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Gauss {
    double[][] a;
    int n, m;
    double eps;

    private double[][] create(int k, int l) {
        double[][] b = new double[k][];
        int i;
        for (i = 0; i < k; i++)
            b[i] = new double[l];
        return b;
    }

    public void init(String s) throws FileNotFoundException {
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[ \t]+");
        String str = scan.nextLine();
        String[] sn = pat.split(str);
        n = Integer.parseInt(sn[0]);
        m = n + 1;
        eps = Double.parseDouble(sn[1]);
        a = create(n, m);
        int i, j;
        for (i = 0; i < n; i++) {
            str = scan.nextLine();
            sn = pat.split(str);
            for (j = 0; j < n + 1; j++)
                a[i][j] = Double.parseDouble(sn[j]);
        }
        scan.close();
    }

    public void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%15.6e", a[i][j]);
            }
            System.out.println();
        }
    }

    public int getTriangle(){          //приведение матрицы к треугольному виду
        int k;
        for(k = 0; k < n; k++) {
            if (Math.abs(a[k][k]) < eps) {
                int g = findNotZeroRow(k);
                if (g!=-1) {
                    swap(k, g);
                    for (int j = k + 1; j < n; j++) {
                        getZero(k, j);
                    }
                }
                return anotherAnswer(k);
            }
            else for (int j = k + 1; j < n; j++) {
                getZero(k, j);
            }
        }
        return 0;
    }


    private int findNotZeroRow(int i) {        //ищем строку с ненулевым элементом
        int j;
        for (j = i; j < n; j++) {
            if (eps < Math.abs(a[j][i])) {
                return j;
            }
        }
        return -1;
    }

    private void swap( int i, int j){        //меняем строки местами
        double []b=a[i];
        a[i]=a[j];
        a[j]=b;
    }


    public void getZero(int i, int j){       //преобразовываем столбец, чтобы первый элемент стал нулём
        int k;
        double m = a[j][i] / a[i][i];
        a[j][i]=0;    //УБРАЛИ                       //занулять те элементы, которые должны искусственно оказаться нулями
        for (k = i+1; k <= n; k++) { //И ТУТ
            a[j][k] -= a[i][k] * m;
        }
    }

    public double[] answers() {    //вычисляем и заносим ответы в массив
        int i, j;
        double b[] = new double[n];
        for (i = n - 1; i >= 0; i--) {
            for (j = n - 1; j > i; j--) {
                a[i][n] -= a[i][j] * b[j];
            }
            b[i] = a[i][n] / a[i][i];
        }
        return b;
    }

    public int anotherAnswer(int k) {           //случаи, когда ответов несколько или их нет
        if (k != n)
            return 1;
        if (Math.abs(a[k - 1][k]) < eps)
            return 2;
        return 3;
    }

    public void printX(int m){          //Вывод в случае отсутствия конечного решения
        switch (m){
            case 1:{
                System.out.println("Вырождена");
                break;
            }
            case 2:{
                System.out.println("Бесконечное число решений");
                break;
            }
            default:{
                System.out.println("Нет решений");
                break;
            }
        }
    }
}