package de.pascalwagler;

import static org.junit.jupiter.api.Assertions.fail;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PandocUtil {

    /**
     * Compiles the given test file and returns the generated LaTeX output.
     */
    public static String runPandoc(String testFile, String[] pandocCommand,
            Path workingDir) {

        String absolutePath = Paths.get("src/test/resources/").toAbsolutePath().toString();

        pandocCommand[1] = absolutePath + "/" + testFile + ".md";
        pandocCommand[3] = workingDir.toAbsolutePath() + "/"
                + testFile + "-actual.tex";
        String destinationFile = pandocCommand[3];

        try {
            executePandoc(pandocCommand, workingDir);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return Files.readString(Paths.get(destinationFile));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes pandoc command to generate a LaTeX file.
     */
    private static void executePandoc(String[] pandocCommand, Path workingDir)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(pandocCommand);
        processBuilder.directory(workingDir.toFile());
        Process process = processBuilder.start();

        process.waitFor();

        if (process.exitValue() != 0) {
            System.out.println("Exit value: " + process.exitValue());
            System.out.println(IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8));
            System.out.println(IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));
            fail();
        }
    }
}
