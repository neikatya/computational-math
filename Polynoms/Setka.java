public class Setka {  //сетка
    double [] x;
    double [] y;

    public Setka(double begin,  double end, int n) {    //задаем а, б и количество точек

        x = new double [n];
        y = new double [n];

        double k = (end - begin) / n;     //шаг

        x[0] = begin;
        x[n - 1] = end;

        for(int i = 1; i < n - 1; i++)
            x[i] = x[i - 1] + k;

        for(int i = 0; i < n; i++) {
            y[i] = function(x[i]);
        }
    }

    public static double function(double n) {     //функция public double function(double n){..........return..}
        double t;
        if (Math.abs(Math.cos(n)) >= Polynoms.accuracy)
            t =Math.cos(n);
        else t = 0;
        return t;
    }
}
