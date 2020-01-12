package de.pascalwagler.eisvogeltests;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PandocUtil {

    private static String[] locatePandocCommand = {"bash", "-c", "command -v pandoc"};

    private static final String[] renderTestFileCommand = {
            "<pandoc-command>", "<document.md>",
            "--out", "<actual.tex>",
            "--from", "markdown",
            "--to", "latex",
            "--template", "<eisvogel-template>"};

    private static String pandocLocation;

    /**
     * Compiles the given test file with pandoc and returns the generated LaTeX output.
     */
    public static String runPandoc(String testFile, Path workingDir)
            throws IOException, InterruptedException {
        return runPandoc(testFile, renderTestFileCommand, workingDir);
    }

    /**
     * Compiles the given test file with pandoc and returns the generated LaTeX output.
     */
    public static String runPandoc(String testFile, String[] commandArray, Path workingDir)
            throws IOException, InterruptedException {

        if (pandocLocation == null) {
            pandocLocation = executeCommand(locatePandocCommand, new File(".").toPath()).trim();
        }
        String absolutePath = Paths.get("src/test/resources/").toAbsolutePath().toString();

        commandArray[0] = pandocLocation;
        commandArray[1] = absolutePath + "/" + testFile + ".md";
        commandArray[3] = workingDir.toAbsolutePath() + "/"
                + testFile + "-actual.tex";
        commandArray[9] = absolutePath + "/eisvogel.tex";
        String destinationFile = commandArray[3];

        executeCommand(commandArray, workingDir);


        return Files.readString(Paths.get(destinationFile));
    }

    private static String executeCommand(String[] command, Path workingDir)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.directory(workingDir.toFile());
        Process process = processBuilder.start();

        process.waitFor();

        String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        if (process.exitValue() != 0) {
            System.out.println("Exit value: " + process.exitValue());
            System.out.println(output);
            System.out.println(IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));
        }
        return output;
    }
}
