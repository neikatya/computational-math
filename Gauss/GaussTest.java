import java.io.FileNotFoundException;

public class GaussTest {
    public static void main(String[] args) {
        Gauss test = new Gauss();  //объявление объекта
        try {
            test.init("C:\\Users\\Екатерина\\IdeaProjects\\лаба_1__2курс\\Gauss.txt");  //метод инициализации
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

        test.print();  //вывод на экран

        System.out.print("-------------------------------");
        System.out.println();

        int k = test.getTriangle();             //приведение к треугольному виду

        test.print();    //вывод на экран

        System.out.print("-------------------------------");
        System.out.println();


        if (k == 0) {
            double[] b = test.answers();
            for (int i = 0; i < b.length; i++) {
                System.out.printf("x%d: %15.6e \n", (i + 1), b[i]);
            }
        }
        else test.printX(k);

    }

        /*Объявляется объект
          Метод инициализации
          Вывод на экран
          Метод приведения к треугольному виду ----> ЧИСЛО
          Вывод на экран
          1. Вызов обработчика вырожденности
          2. Вычисление решения + вывод решения*/

}