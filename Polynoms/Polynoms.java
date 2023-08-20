public class Polynoms {  //полиномы
    Monom first;
    static double  accuracy = 1e-40;    //точность

    private static class Monom      //моном
    {
        private int stepen;      //степень
        private double koeff;         //коэффициент
        private Monom next;        //указатель

        private Monom(int r, double i, Monom n) {      //конструктор
            stepen = r;
            koeff = i;
            next = n;
        }

        private Monom(Monom a, Monom n) {    //конструктор с копированием
            stepen = a.stepen;
            koeff = a.koeff;
            next = n;
        }

        public void print() {
            System.out.printf("%15.6E", Math.abs(koeff));
            System.out.print("x^" + stepen);
        }
    }

    public Polynoms() { //конструктор для полиномов
        first = null;
    }

    public Polynoms(int r, double i) {  //контруктор для полиномов со значениями
        first = new Monom(r, i, null);
    }


    public void copy(Polynoms a) {            //копирование одного полинома в др.
        Monom m1 = first, m2 = a.first;

        if(m1 == null) {
            summa(a);
            return;
        }

        while(m1.next != null && m2 != null) {
            m1.stepen = m2.stepen;
            m1.koeff = m2.koeff;
            m1 = m1.next;
            m2 = m2.next;
        }

        if(m2 != null) {
            m1.stepen = m2.stepen;
            m1.koeff = m2.koeff;
            m2 = m2.next;
            if(m1.next == null)
                while(m2 != null && m1 != null){
                    m1.next = new Monom(m2, null);
                    m1 = m1.next;
                    m2 = m2.next;
                }
        }
    }

    public void summa (Polynoms a) {           //сложение полиномов

        Monom m0 = null, m1 = first, m2 = a.first;

        if(m2 == null)
            return;

        if(m1 == null) {
            first = new Monom(m2, null);
            m1 = first;
            m2 = m2.next;
            while(m2 != null && m1 != null){
                m1.next = new Monom(m2, null);
                m1 = m1.next;
                m2 = m2.next;
            }
            return;
        }

        if(m1.stepen < m2.stepen) {
            first = new Monom(m2, m1);
            m2 = m2.next;
            m0 = first;
            m1 = m0.next;
        }

        else
        if(m1.stepen > m2.stepen){
            m0 = m1;
            m1 = m1.next;
        }


        while(m1 != null && m2 != null) {

            if(m1.stepen < m2.stepen) {
                m0.next = new Monom(m2, m1);
                m2 = m2.next;
                m1 = m0.next;
            }

            else {

                if(m1.stepen == m2.stepen) { //
                    m1.koeff += m2.koeff;
                    if(Math.abs(m1.koeff) <= accuracy) {
                        if(m0 != null) {
                            m0.next = m1.next;
                            m1 = m1.next;
                        }
                        else {

                        }
                    }
                    m2 = m2.next;
                }
                if(m1 != null) {
                    m0 = m1;
                    m1 = m1.next;
                }
            }
        }

        if(m1 == null) {
            while(m2 != null && m0 != null){
                m0.next = new Monom(m2, null);
                m0 = m0.next;
                m2 = m2.next;
            }
        }
    }


    public void multiply(Polynoms a) {        //умножение полиномов

        Monom m1 = first, m2 = a.first;

        if(m2 == null)
            return;
        if(m1 == null) {
            summa(a);
            return;
        }
        Polynoms sum = new Polynoms(m1.stepen + m2.stepen, m1.koeff * m2.koeff);
        Monom s = sum.first;
        m2 = m2.next;
        int temp_r;

        while(m1 != null) {
            while(m2 != null) {

                temp_r = m1.stepen + m2.stepen;

                if(s.stepen == temp_r) {
                    s.koeff += (m1.koeff * m2.koeff);
                    m2 = m2.next;
                }
                else {
                    if(s.stepen > temp_r && (s.next == null || s.next != null && s.next.stepen < temp_r)) {
                        s.next = new Monom(temp_r, (m1.koeff * m2.koeff), s.next);
                        m2 = m2.next;
                    }
                    s = s.next;
                }
            }

            m1 = m1.next;
            m2 = a.first;
            s = sum.first;
        }
        first = sum.first;
    }

    public void multiply_number(double a) {        //умножение полинома на число
        if(Math.abs(a) < accuracy) {
            first = null;
            return;
        }

        Monom m = first;
        while(m != null) {
            m.koeff *= a;
            m = m.next;
        }
    }

    public double tochka(double n) { //нахождение значения в точке
        if(first == null) {
            return 0;
        }

        Monom m = first;
        double sum = 0;

        int last_stepen = m.stepen, i;

        for(i = last_stepen; i > 0 && m != null; i--) {

            if(m.stepen == i) {
                sum += m.koeff;
                m = m.next;
            }
            sum *= n;
        }
        if (m != null)
            sum += m.koeff;

        for(; i > 0; i++) {
            sum *= n;
        }

        if(Math.abs(sum) < accuracy)
            sum = 0;

        return sum;
    }

    public void change(double n) {    //смена свободного члена
        if(Math.abs(n) < accuracy) {
            first.next = null;
        }
        else if(first.next != null) {
            Monom m = first.next;
            m.koeff = n;
        }
        else {
            first.next = new Monom(0, n, null);
        }
    }

    public void print() {   //вывод
        if(first == null)
            return;

        Monom m = first, n = first.next;

        while(n != null) {

            if(Math.abs(n.koeff) < 0) {
                m.next = n.next;
                if(m.next == null) {
                    break;
                }
            }
            n = n.next;
            m.print();
            m = m.next;
            if (m.koeff < 0 )
                System.out.print("   -");
            else
                System.out.print("   +");

        }
        m.print();
        System.out.println();
    }
}
