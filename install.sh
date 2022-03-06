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

# Check if `java` exists in their system
if ! [ -x "$(command -v java)" ]
then
    abort "Java must be installed. You can install by here: https://java.com/en/download/help/download_options.html"
fi

# Download
rm /usr/local/bin/scalculator /usr/local/bin/scalculator.jar # Make sure scalculator is removed before installing it with curl
curl -f https://raw.githubusercontent.com/scalculator/scalculator/main/scalculator.jar -o /usr/local/bin/scalculator.jar && echo "Successfully installed the Scalculator JAR file." || echo "Failed to install the Scalculator JAR file with curl."
printf '#!/bin/sh\njava -jar scalculator.jar' > /usr/local/bin/scalculator
chmod +x /usr/local/bin/scalculator # make script be able to execute
