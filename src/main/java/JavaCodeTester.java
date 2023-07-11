import javax.tools.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JavaCodeTester {
    public static void main(String[] args) {
        String studentCode = "public class Solution {\n" +
                "    public static int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "}";

        String testCasesFile = "test_cases.txt";
        try {
            // 수강생 코드 파일로 저장
            String fileName = "Solution.java";
            saveCodeToFile(studentCode, fileName);

            // 코드 컴파일
            boolean compilationSuccess = compileCode(fileName);
            if (compilationSuccess) {
                // 테스트케이스 끌어오기
                List<String> testCases = loadTestCasesFromFile(testCasesFile);

                // Execute the compiled code against each test case
                for (String testCase : testCases) {
                    executeTestCase(testCase);
                }
            } else {
                System.out.println("Compilation error!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveCodeToFile(String code, String fileName) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.write(code);
        }
    }

    private static boolean compileCode(String fileName) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector, null, null, compilationUnits);

        boolean success = task.call();
        if (!success) {
            for (Diagnostic<?> diagnostic : diagnosticCollector.getDiagnostics()) {
                System.out.println(diagnostic.getMessage(null));
            }
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    private static List<String> loadTestCasesFromFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    private static void executeTestCase(String testCase) throws IOException {
        String[] command = {"java", "Solution"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(testCase);
            writer.newLine();
            writer.flush();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String output = reader.readLine();
            System.out.println("Test Case: " + testCase);
            System.out.println("Output: " + output);
            System.out.println();
        }
    }
}