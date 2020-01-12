package de.pascalwagler.eisvogeltests.latexoutput.variables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import de.pascalwagler.eisvogeltests.PandocUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

class Header {

    @TempDir
    Path workingDir;

    @Test
    void headerCenter() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\chead[Header Center]{Header Center}";

        // Act
        String actual = PandocUtil.runPandoc("header-center", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerDefault() throws IOException, InterruptedException {

        // Arrange
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

        // Act
        String actual = PandocUtil.runPandoc("header-default", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerLeft() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\lhead[2019-12-20]{Header Left}";

        // Act
        String actual = PandocUtil.runPandoc("header-left", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerRight() throws IOException, InterruptedException {

        // Arrange
        String expectedPart = "\\rhead[Test Header Right]{Header Right}";

        // Act
        String actual = PandocUtil.runPandoc("header-right", workingDir);

        // Assert
        assertThat(actual, containsString(expectedPart));
    }
}
