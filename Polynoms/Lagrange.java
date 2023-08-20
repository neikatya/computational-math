public class Lagrange {
    private Polynoms function;

    public Lagrange(Setka dots) {    //конструктор

        function = new Polynoms();
        int i, j;

        double с;

        Polynoms p, s = new Polynoms(1, 1);

        s.summa(new Polynoms (0, 1));   //добавление свободного члена

        s.change((-1) * dots.x[1]);

        p = new Polynoms();
        p.summa(s);


        с = dots.y[0] / (dots.x[0] - dots.x[1]);

        for(j = 2; j < dots.y.length; j++) {

            с /= (dots.x[0] - dots.x[j]);
            s.change((-1) * dots.x[j]);

            p.multiply(s);
        }

        p.multiply_number(с);
        function.summa(p);

        for(i = 1; i < dots.y.length; i++) {

            p = new Polynoms();

            s.change((-1) * dots.x[0]);

            с = dots.y[i] / (dots.x[i] - dots.x[0]);
            p.summa(s);

            for(j = 1; j < i; j++) {
                с /= (dots.x[i] - dots.x[j]);
                s.change((-1) * dots.x[j]);
                p.multiply(s);
            }

            for(j = i + 1; j < dots.y.length; j++) {
                с /= (dots.x[i] - dots.x[j]);
                s.change((-1) * dots.x[j]);
                p.multiply(s);
            }

            p.multiply_number(с);
            function.summa(p);
        }
    }

    public double tochka(double x) {
        return function.tochka(x);
    }

    public void print() {
        function.print();
    }
}
