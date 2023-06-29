import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, 항해99!");

        XlsxParser xlsxParser = new XlsxParser();
        List<Object[]> data = xlsxParser.XlsxRead();
        System.out.println(Arrays.toString(data.get(10)));
    }
}
