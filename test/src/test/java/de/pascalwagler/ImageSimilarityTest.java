package de.pascalwagler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

class ImageSimilarityTest {

    private static final double THRESHOLD = 0.002;

    @ParameterizedTest(name = "Test {1}")
    @MethodSource("provideImageFiles")
    void imageSimilarity(String testFolder, String folderName)
            throws IOException, InterruptedException {

        // Arrange
        executePandocCommandFile(new File(testFolder + "/command.sh"));
        generatePngImage(new File(testFolder + "/command.sh"));

        BufferedImage expectedImage = ImageIO.read(new File(testFolder + "/expected.png"));
        BufferedImage actualImage = ImageIO.read(new File(testFolder + "/actual-1.png"));

        // Act
        double p = ImgDiffPercent.getDifferencePercent(expectedImage, actualImage);

        // Assert
        if (p != 0) {
            System.out.println("Difference: " + p);
        }
        assertTrue(p < THRESHOLD, "Difference " + p + " is greater than "
                + THRESHOLD + " for test " + folderName + ".");

        // Cleanup
        cleanupGeneratedFiles(new File(testFolder));
    }

    private static Stream<Arguments> provideImageFiles() {

        File dir = new File("./src/test/resources");
        File[] filesList = dir.listFiles();

        if (filesList == null) {
            throw new RuntimeException("Could not read files.");
        }

        return Stream.of(filesList)
                .filter(File::isDirectory)
                .sorted(Comparator.comparing(File::getName))
                .map(folder -> Arguments.of(folder.getAbsolutePath(), folder.getName()));
    }

    /**
     * Executes the given testCommandFile (the pandoc command to generate the PDF).
     */
    private void executePandocCommandFile(File testCommandFile)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(testCommandFile.getAbsolutePath());
        processBuilder.directory(testCommandFile.getParentFile());
        Process process = processBuilder.start();

        process.waitFor();

        System.out.println("Exit value: " + process.exitValue());
        System.out.println(IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8));
        System.out.println(IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));
    }

    /**
     * Executes pdftoppm to generate png files from the PDF for comparison.
     */
    private void generatePngImage(File testCommandFile)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("pdftoppm", "-r", "150", "-png", "actual.pdf", "actual");
        processBuilder.directory(testCommandFile.getParentFile());

        Process process = processBuilder.start();
        process.waitFor();

        System.out.println("Exit value: " + process.exitValue());
        System.out.println(IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8));
        System.out.println(IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));
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
