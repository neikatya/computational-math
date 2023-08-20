import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) {
        GaussZeidel gz = new GaussZeidel();
        try
        {

            gz.init("C:\\Users\\Екатерина\\IdeaProjects\\лаба_1__2курс\\Gauss.txt");  //инициализация
            gz.print();  //вывод
            System.out.print("-------------------------------");

            int k = gz.make_best_cond(); // ищем лучшую перестановку

            gz.print();
            System.out.print("-------------------------------");
            System.out.println();

            if(k == 0) { // если не получилось избавиться от 0 на диагонали
                System.out.println("Нет решений");
            }
            else { // если получилось избавиться - решаем
                double [] answers = gz.solve(k);

                if(answers == null) {
                    System.out.println("Решений нет");
                }
                else {
                    System.out.println("Ответ:");
                    gz.print_m(answers);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("FILE NOT FOUND!!!");
        }

    }

}
