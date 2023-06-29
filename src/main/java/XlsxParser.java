import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.All;

import javax.print.DocFlavor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsxParser {
    public List<Object[]> XlsxRead() {
        List<Object[]> data = new ArrayList<>();

        String filePath = "/Users/kimhyeongjun/Desktop/VSCodeProject/java-scoring-automation/src/main/xlsx/답안.xlsx";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<Object> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING -> rowData.add(cell.getStringCellValue());
                        case NUMERIC -> rowData.add(cell.getNumericCellValue());
                        case BOOLEAN -> rowData.add(cell.getBooleanCellValue());
                        case BLANK -> rowData.add(""); // 빈 값을 빈 문자열로 추가
                        default -> rowData.add(""); // 기본적으로 빈 값을 빈 문자열로 추가
                    }
                }
                data.add(rowData.toArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

}
