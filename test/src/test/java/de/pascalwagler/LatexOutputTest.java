package de.pascalwagler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class LatexOutputTest {

    private static final String[] pandocCommand = {"pandoc", "<document.md>", "-o", "<actual.tex>",
            "--from", "markdown", "--to", "latex", "--template", "eisvogel.latex"};

    private static final File testFolder = new File("./src/test/resources/");

    @AfterEach
    void afterEach() {
        File[] toBeDeleted = testFolder.listFiles((dir, name) -> name.contains("-actual.tex"));

        for (File file : toBeDeleted) {
            file.delete();
        }
    }

    @Test
    void footerCenter() {

        String expectedPart = "\\cfoot[Footer Center]{Footer Center}";

        String actual = getActual("footer-center");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerDefault() {

        String expectedPart = "\\fancypagestyle{eisvogel-header-footer}{\n"
                + "  \\fancyhead{}\n"
                + "  \\fancyfoot{}\n"
                + "  \\lhead[2019-12-20]{Test Footer Default}\n"
                + "  \\chead[]{}\n"
                + "  \\rhead[Test Footer Default]{2019-12-20}\n"
                + "  \\lfoot[\\thepage]{Author}\n"
                + "  \\cfoot[]{}\n"
                + "  \\rfoot[Author]{\\thepage}\n"
                + "  \\renewcommand{\\headrulewidth}{0.4pt}\n"
                + "  \\renewcommand{\\footrulewidth}{0.4pt}\n"
                + "}\n"
                + "\\pagestyle{eisvogel-header-footer}";

        String actual = getActual("footer-default");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerLeft() {

        String expectedPart = "\\lfoot[\\thepage]{Footer Left}";

        String actual = getActual("footer-left");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerRight() {

        String expectedPart = "\\rfoot[Author]{Footer Right}";

        String actual = getActual("footer-right");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerCenter() {

        String expectedPart = "\\chead[Header Center]{Header Center}";

        String actual = getActual("header-center");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerDefault() {

        String expectedPart = "\\fancypagestyle{eisvogel-header-footer}{\n"
                + "  \\fancyhead{}\n"
                + "  \\fancyfoot{}\n"
                + "  \\lhead[2019-12-20]{Test Header Default}\n"
                + "  \\chead[]{}\n"
                + "  \\rhead[Test Header Default]{2019-12-20}\n"
                + "  \\lfoot[\\thepage]{Author}\n"
                + "  \\cfoot[]{}\n"
                + "  \\rfoot[Author]{\\thepage}\n"
                + "  \\renewcommand{\\headrulewidth}{0.4pt}\n"
                + "  \\renewcommand{\\footrulewidth}{0.4pt}\n"
                + "}\n"
                + "\\pagestyle{eisvogel-header-footer}";

        String actual = getActual("header-default");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerLeft() {

        String expectedPart = "\\lhead[2019-12-20]{Header Left}";

        String actual = getActual("header-left");

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerRight() {

        String expectedPart = "\\rhead[Test Header Right]{Header Right}";

        String actual = getActual("header-right");

        assertThat(actual, containsString(expectedPart));
    }

    private String getActual(String testFile) {

        pandocCommand[1] = testFile + ".md";
        pandocCommand[3] = testFile + "-actual.tex";

        try {
            executePandocCommandFile();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return Files.readString(Paths.get("./src/test/resources/"
                    + testFile + "-actual.tex"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    @ParameterizedTest(name = "{1}")
    @MethodSource("provideTestFolders")
    void latexOutput(String testFolder, String folderName)
            throws IOException, InterruptedException {

        // Arrange
        String expected = new String(Files.readAllBytes(Paths.get(testFolder + "/expected.tex")),
                StandardCharsets.UTF_8);

        // Act
        executePandocCommandFile(new File(testFolder + "/command.sh"));
        String actual = new String(Files.readAllBytes(Paths.get(testFolder + "/actual.tex")),
                StandardCharsets.UTF_8);

        // Assert
        assertEquals(expected, actual);

        // Cleanup
        cleanupGeneratedFiles(new File(testFolder));
    }*/

    /*private static Stream<Arguments> provideTestFolders() {

        File dir = new File("./src/test/resources");
        File[] filesList = dir.listFiles();

        if (filesList == null) {
            throw new RuntimeException("Could not read files.");
        }

        return Stream.of(filesList)
                .filter(File::isDirectory)
                .sorted(Comparator.comparing(File::getName))
                .map(folder -> Arguments.of(folder.getAbsolutePath(), folder.getName()));
    }*/

    /**
     * Executes the given testCommandFile (the pandoc command to generate the LaTeX file).
     */
    private void executePandocCommandFile()
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(pandocCommand);
        processBuilder.directory(new File("./src/test/resources/"));
        Process process = processBuilder.start();

        process.waitFor();

        if(process.exitValue() != 0) {
            System.out.println("Exit value: " + process.exitValue());
            System.out.println(IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8));
            System.out.println(IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));
            fail();
        }
    }

    /**
     * Deletes all files containing the string "actual" from the testFolder.
     */
    private void cleanupGeneratedFiles(File testFolder) {
        File[] toBeDeleted = testFolder.listFiles((dir, name) -> name.contains("actual"));

        for (File file : toBeDeleted) {
            file.delete();
        }
    }
}
