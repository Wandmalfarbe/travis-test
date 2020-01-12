package de.pascalwagler.eisvogeltests.latexoutput.variables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import de.pascalwagler.eisvogeltests.PandocUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

class Toc {

    @TempDir
    Path workingDir;

    /**
     * A {@code \newline} should be inserted after the beamer toc only when converting to beamer.
     */
    @Test
    void tocOwnPage_beamer() throws IOException, InterruptedException {

        // Arrange
        String expectedPartForBeamer = "\\tableofcontents[hideallsubsections]\n"
                + "\\end{frame}\n"
                + "\\newpage";
        String expectedPartForLatex = "\\tableofcontents\n"
                + "\\newpage";
        String[] withTocCommandToBeamer = {
                "<pandoc-command>", "<document.md>",
                "--out", "<actual.tex>",
                "--from", "markdown",
                "--to", "beamer",
                "--template", "<eisvogel-template>",
                "--toc"};

        // Act
        String actual = PandocUtil.runPandoc("toc-own-page", withTocCommandToBeamer, workingDir);

        // Assert
        assertThat(actual, containsString(expectedPartForBeamer));
        assertThat(actual, not(containsString(expectedPartForLatex)));
    }

    /**
     * No {@code \newline} should be inserted after the beamer toc per default when converting to
     * beamer.
     */
    @Test
    void tocOwnPage_beamerDefault() throws IOException, InterruptedException {

        // Arrange
        String expectedPartForBeamer = "\\tableofcontents[hideallsubsections]\n"
                + "\\end{frame}\n"
                + "\\newpage";
        String expectedPartForLatex = "\\tableofcontents\n"
                + "\\newpage";
        String[] withTocCommandToBeamer = {
                "<pandoc-command>", "<document.md>",
                "--out", "<actual.tex>",
                "--from", "markdown",
                "--to", "beamer",
                "--template", "<eisvogel-template>",
                "--toc"};

        // Act
        String actual = PandocUtil.runPandoc("toc-own-page-default", withTocCommandToBeamer, workingDir);

        // Assert
        assertThat(actual, not(containsString(expectedPartForBeamer)));
        assertThat(actual, not(containsString(expectedPartForLatex)));
    }

    /**
     * A {@code \newline} should be inserted after the LaTeX toc only when converting to LaTeX.
     */
    @Test
    void tocOwnPage_latex() throws IOException, InterruptedException {

        // Arrange
        String expectedPartForLatex = "\\tableofcontents\n"
                + "\\newpage";
        String expectedPartForBeamer = "\\tableofcontents[hideallsubsections]\n"
                + "\\end{frame}\n"
                + "\\newpage";
        String[] withTocCommandToLatex = {
                "<pandoc-command>", "<document.md>",
                "--out", "<actual.tex>",
                "--from", "markdown",
                "--to", "latex",
                "--template", "<eisvogel-template>",
                "--toc"};

        // Act
        String actual = PandocUtil.runPandoc("toc-own-page", withTocCommandToLatex, workingDir);

        // Assert
        assertThat(actual, containsString(expectedPartForLatex));
        assertThat(actual, not(containsString(expectedPartForBeamer)));
    }

    /**
     * No {@code \newline} should be inserted after the LaTeX toc per default when converting to
     * LaTeX.
     */
    @Test
    void tocOwnPage_latexDefault() throws IOException, InterruptedException {

        // Arrange
        String expectedPartForLatex = "\\tableofcontents\n"
                + "\\newpage";
        String expectedPartForBeamer = "\\tableofcontents[hideallsubsections]\n"
                + "\\end{frame}\n"
                + "\\newpage";
        String[] withTocCommandToLatex = {
                "<pandoc-command>", "<document.md>",
                "--out", "<actual.tex>",
                "--from", "markdown",
                "--to", "latex",
                "--template", "<eisvogel-template>",
                "--toc"};

        // Act
        String actual =
                PandocUtil.runPandoc("toc-own-page-default", withTocCommandToLatex, workingDir);

        // Assert
        assertThat(actual, not(containsString(expectedPartForLatex)));
        assertThat(actual, not(containsString(expectedPartForBeamer)));
    }
}
