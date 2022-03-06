#!/bin/bash

abort() {
    printf "%s\n" "$@"
    exit 1
}

# Make sure BASH Is installed
if [ -z "${BASH_VERSION:-}" ]
then
    abort "Bash is required to execute this script."
fi

# Remove/Uninstall
rm /usr/local/bin/scalculator /usr/local/bin/scalculator.jar

# message
echo Successfully uninstalled.
