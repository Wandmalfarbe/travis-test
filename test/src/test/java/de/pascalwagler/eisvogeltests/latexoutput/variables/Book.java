package de.pascalwagler.eisvogeltests.latexoutput.variables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import de.pascalwagler.eisvogeltests.PandocUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

class Book {

    @TempDir
    Path workingDir;

    /**
     * Use the documentclass {@code scrbook} when running with {@code book: false} (the default
     * value).
     */
    @Test
    void book_default() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "{scrartcl}"; // documentclass

        // Act
        String actual = PandocUtil.runPandoc("book-default", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    /**
     * Use the documentclass {@code scrbook} when running with {@code book: true}.
     */
    @Test
    void book() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "{scrbook}"; // documentclass

        // Act
        String actual = PandocUtil.runPandoc("book", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }
}
