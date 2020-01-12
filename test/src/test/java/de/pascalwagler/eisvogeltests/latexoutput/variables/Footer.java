package de.pascalwagler.eisvogeltests.latexoutput.variables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import de.pascalwagler.eisvogeltests.PandocUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

class Footer {

    @TempDir
    Path workingDir;

    @Test
    void footerCenter() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\cfoot[Footer Center]{Footer Center}";

        // Act
        String actual = PandocUtil.runPandoc("footer-center", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerDefault() throws IOException, InterruptedException {

        // Arrange
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

        // Act
        String actual = PandocUtil.runPandoc("footer-default", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerLeft() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\lfoot[\\thepage]{Footer Left}";

        // Act
        String actual = PandocUtil.runPandoc("footer-left", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerRight() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\rfoot[Author]{Footer Right}";

        // Act
        String actual = PandocUtil.runPandoc("footer-right", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }
}
