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

        if (pandocLocation == null) {
            pandocLocation = executeCommand(locatePandocCommand, new File(".").toPath()).trim();
        }
        String absolutePath = Paths.get("src/test/resources/").toAbsolutePath().toString();

        renderTestFileCommand[0] = pandocLocation;
        renderTestFileCommand[1] = absolutePath + "/" + testFile + ".md";
        renderTestFileCommand[3] = workingDir.toAbsolutePath() + "/"
                + testFile + "-actual.tex";
        renderTestFileCommand[9] = absolutePath + "/eisvogel.tex";
        String destinationFile = renderTestFileCommand[3];

        executeCommand(renderTestFileCommand, workingDir);


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
