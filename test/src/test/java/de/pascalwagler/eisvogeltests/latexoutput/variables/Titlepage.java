package de.pascalwagler.eisvogeltests.latexoutput.variables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import de.pascalwagler.eisvogeltests.PandocUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

class Titlepage {

    @TempDir
    Path workingDir;

    @Test
    void titlepage() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "%%\n"
                + "%% begin titlepage\n"
                + "%%\n"
                + "\\begin{titlepage}\n"
                + "\\newgeometry{left=6cm}\n"
                + "\\newcommand{\\colorRule}[3][black]{\\textcolor[HTML]{#1}{\\rule{#2}{#3}}}\n"
                + "\\begin{flushleft}\n"
                + "\\noindent\n"
                + "\\\\[-1em]\n"
                + "\\color[HTML]{5F5F5F}\n"
                + "\\makebox[0pt][l]{\\colorRule[435488]{1.3\\textwidth}{4pt}}\n"
                + "\\par\n"
                + "\\noindent\n"
                + "\n"
                + "{\n"
                + "  \\setstretch{1.4}\n"
                + "  \\vfill\n"
                + "  \\noindent {\\huge \\textbf{\\textsf{Test Titlepage}}}\n"
                + "    \\vskip 2em\n"
                + "  \\noindent {\\Large \\textsf{Author}}\n"
                + "  \\vfill\n"
                + "}\n"
                + "\n"
                + "\n"
                + "\\textsf{2020-01-11}\n"
                + "\\end{flushleft}\n"
                + "\\end{titlepage}\n"
                + "\\restoregeometry\n"
                + "\n"
                + "%%\n"
                + "%% end titlepage\n"
                + "%%";

        // Act
        String actual = PandocUtil.runPandoc("titlepage", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void titlepageColor() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\definecolor{titlepage-color}{HTML}{FF3D24}\n"
                + "\\newpagecolor{titlepage-color}\\afterpage{\\restorepagecolor}\n";

        // Act
        String actual = PandocUtil.runPandoc("titlepage-color", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void titlepageTextColor() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\color[HTML]{FF3D24}";

        // Act
        String actual = PandocUtil.runPandoc("titlepage-text-color", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void titlepageRuleColor() throws IOException, InterruptedException {

        // Arrange
        String expectedRuleDefinition =
                "\\newcommand{\\colorRule}[3][black]{\\textcolor[HTML]{#1}{\\rule{#2}{#3}}}";
        String expectedRuleUsage = "\\makebox[0pt][l]{\\colorRule[FF3D24]{1.3\\textwidth}{4pt}}";

        // Act
        String actual = PandocUtil.runPandoc("titlepage-rule-color", workingDir);

        // Assert
        assertThat(actual, containsString(expectedRuleDefinition));
        assertThat(actual, containsString(expectedRuleUsage));
    }

    @Test
    void titlepageRuleHeight() throws IOException, InterruptedException {

        // Arrange
        String expectedRuleDefinition =
                "\\newcommand{\\colorRule}[3][black]{\\textcolor[HTML]{#1}{\\rule{#2}{#3}}}";
        String expectedRuleUsage = "\\makebox[0pt][l]{\\colorRule[435488]{1.3\\textwidth}{11pt}}";

        // Act
        String actual = PandocUtil.runPandoc("titlepage-rule-height", workingDir);

        // Assert
        assertThat(actual, containsString(expectedRuleDefinition));
        assertThat(actual, containsString(expectedRuleUsage));
    }

    @Test
    void titlepageBackground() throws IOException, InterruptedException {

        // Arrange
        String expectedPart =
                "\\tikz[remember picture,overlay] \\node[inner sep=0pt] at (current page.center){\\includegraphics[width=\\paperwidth,height=\\paperheight]{image.pdf}};";
        String expectedDifferentLayout = "% The titlepage with a background image has other text spacing and text size\n"
                + "{\n"
                + "  \\setstretch{2}\n"
                + "  \\vfill\n"
                + "  \\vskip -8em\n"
                + "  \\noindent {\\huge \\textbf{\\textsf{Test Titlepage}}}\n"
                + "    \\vskip 2em\n"
                + "  \\noindent {\\Large \\textsf{Author} \\vskip 0.6em \\textsf{2020-01-11}}\n"
                + "  \\vfill\n"
                + "}";

        // Act
        String actual = PandocUtil.runPandoc("titlepage-background", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
        assertThat(actual, containsString(expectedDifferentLayout));
    }
}
