public class Newton {  //ньютон
    Polynoms function;

    public Newton(Setka dots) {  //конструктор

        function = new Polynoms();

        Polynoms s, p1, p2;
        p1 = new Polynoms();
        p2 = new Polynoms();

        int i, j, size = dots.y.length;
        double [] last_d = new double [size], d = new double [size], temp, c = new double [size];

        for(i = 0; i < size; i++) {
            last_d[i] = dots.y[i];
        }

        c[0] = last_d[0];

        for(i = size - 1; i > 0; i--) {
            for(j = 0; j < i; j++) {
                d[j] = (last_d[j] - last_d[j + 1]) / (dots.x[j] - dots.x[j + size - i]);
            }

            temp = last_d;
            last_d = d;
            d = temp;

            c[size - i] = last_d[0];
        }


        s = new Polynoms(0,c[0]);
        function.summa(s);
        s.summa(new Polynoms(1, 1));

        for(i = 1; i < size; i++) {

            s.change(-dots.x[i - 1]);

            p1.multiply(s);
            p2.copy(p1);
            p2.multiply_number(c[i]);

            function.summa(p2);
        }

    }

    public double tochka(double x) {
        return function.tochka(x);
    }

    public void print() {
        function.print();
    }
}
