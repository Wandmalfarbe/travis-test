#!/bin/bash

pandoc "document.md" -o "actual.pdf" --from markdown --to beamer --template "../eisvogel.latex" --listings