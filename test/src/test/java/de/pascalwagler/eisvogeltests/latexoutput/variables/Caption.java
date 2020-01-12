package de.pascalwagler.eisvogeltests.latexoutput.variables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import de.pascalwagler.eisvogeltests.PandocUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

class Caption {

    @TempDir
    Path workingDir;

    @Test
    void captionDefault() throws IOException, InterruptedException {

        // Arrange
        String expectedPart =
                "\\usepackage[font={stretch=1.2}, textfont={color=caption-color}, position=top, skip=4mm, labelfont=bf, singlelinecheck=false, justification=raggedright]{caption}";

        // Act
        String actual = PandocUtil.runPandoc("caption-default", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void captionJustification() throws IOException, InterruptedException {

        // Arrange
        String expectedPart =
                "\\usepackage[font={stretch=1.2}, textfont={color=caption-color}, position=top, skip=4mm, labelfont=bf, singlelinecheck=false, justification=raggedleft]{caption}";

        // Act
        String actual = PandocUtil.runPandoc("caption-justification", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    /**
     * Captions are not changed when converting to beamer because beamer is kind of unsupported
     * in Eisvogel.
     */
    @Test
    void captionJustification_noCaptionModificationWhenConvertingToBeamer()
            throws IOException, InterruptedException {

        // Arrange
        String expectedPart =
                "\\usepackage[font={stretch=1.2}, textfont={color=caption-color}, position=top, skip=4mm, labelfont=bf, singlelinecheck=false, justification=raggedright]{caption}";
        String[] toBeamerCommand = {
                "<pandoc-command>", "<document.md>",
                "--out", "<actual.tex>",
                "--from", "markdown",
                "--to", "beamer",
                "--template", "<eisvogel-template>"};

        // Act
        String actual =
                PandocUtil.runPandoc("caption-justification", toBeamerCommand, workingDir);

        // Assert
        assertThat(actual, not(containsString(expectedPart)));
    }
}
