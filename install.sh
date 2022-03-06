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

# Download
rm /usr/local/bin/scalculator /usr/local/bin/scalculator.jar # Make sure scalculator is removed before installing it with curl
curl -f https://raw.githubusercontent.com/scalculator/scalculator/main/scalculator.jar -o /usr/local/bin/scalculator.jar && echo "Successfully installed the Scalculator JAR file." || echo "Failed to install the Scalculator JAR file with curl."
printf '#!/bin/sh\njava -jar scalculator.jar' > /usr/local/bin/scalculator
chmod +x /usr/local/bin/scalculator # make script be able to execute
