package de.pascalwagler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

class LatexOutputTest {

    private static final String[] pandocCommand = {
            "pandoc", "<document.md>",
            "--out", "<actual.tex>",
            "--from", "markdown",
            "--to", "latex",
            "--template", "eisvogel.latex"};

    @TempDir
    Path workingDir;

    @Test
    void footerCenter() {

        String expectedPart = "\\cfoot[Footer Center]{Footer Center}";

        String actual = PandocUtil.runPandoc("footer-center", pandocCommand, workingDir);

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

        String actual = PandocUtil.runPandoc("footer-default", pandocCommand, workingDir);

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerLeft() {

        String expectedPart = "\\lfoot[\\thepage]{Footer Left}";

        String actual = PandocUtil.runPandoc("footer-left", pandocCommand, workingDir);

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void footerRight() {

        String expectedPart = "\\rfoot[Author]{Footer Right}";

        String actual = PandocUtil.runPandoc("footer-right", pandocCommand, workingDir);

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerCenter() {

        String expectedPart = "\\chead[Header Center]{Header Center}";

        String actual = PandocUtil.runPandoc("header-center", pandocCommand, workingDir);

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

        String actual = PandocUtil.runPandoc("header-default", pandocCommand, workingDir);

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerLeft() {

        String expectedPart = "\\lhead[2019-12-20]{Header Left}";

        String actual = PandocUtil.runPandoc("header-left", pandocCommand, workingDir);

        assertThat(actual, containsString(expectedPart));
    }

    @Test
    void headerRight() {

        String expectedPart = "\\rhead[Test Header Right]{Header Right}";

        String actual = PandocUtil.runPandoc("header-right", pandocCommand, workingDir);

        assertThat(actual, containsString(expectedPart));
    }
}
