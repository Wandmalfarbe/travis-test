#!/bin/bash

pandoc "document.md" -o "actual.pdf" --from markdown --template "../eisvogel.latex" --highlight-style kate