import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        XlsxParser xlsxParser = new XlsxParser();
        List<Object[]> data = xlsxParser.XlsxRead();

        System.out.println(Arrays.toString(data.get(1)));
//        System.out.println(Arrays.toString(data.get(1)));

    }
}
