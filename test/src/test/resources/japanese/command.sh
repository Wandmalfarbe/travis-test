#!/bin/bash

pandoc "document.md" -o "actual.pdf" --from markdown --template "../eisvogel.latex" --listings --pdf-engine=xelatex -V lang=en-us -V CJKmainfont="HiraginoSans-W4"