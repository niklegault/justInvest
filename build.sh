#!/bin/bash

# Define the main build output directory
BUILD_DIR="build"
LIB_DIR="${BUILD_DIR}/lib"
CLASSES_DIR="${BUILD_DIR}/classes"

# Primary Dependency: spring-security-crypto (required by PasswordHashing.java)
SPRING_VERSION="6.2.0"
SPRING_ARTIFACT="spring-security-crypto"
SPRING_FILE="${SPRING_ARTIFACT}-${SPRING_VERSION}.jar"
SPRING_URL="https://repo1.maven.org/maven2/org/springframework/security/${SPRING_ARTIFACT}/${SPRING_VERSION}/${SPRING_FILE}"

# Transitive Dependency: commons-logging (required by spring-security-crypto)
LOGGING_VERSION="1.3.0"
LOGGING_ARTIFACT="commons-logging"
LOGGING_FILE="${LOGGING_ARTIFACT}-${LOGGING_VERSION}.jar"
LOGGING_URL="https://repo1.maven.org/maven2/${LOGGING_ARTIFACT}/${LOGGING_ARTIFACT}/${LOGGING_VERSION}/${LOGGING_FILE}"

# Application details
MAIN_CLASS="ca.carleton.niklegault.justInvest.Application"
OUTPUT_JAR="justInvest.jar"
MANIFEST_FILE="${BUILD_DIR}/MANIFEST.MF"

echo "--- 1. Cleaning up previous build artifacts ---"
rm -rf ${BUILD_DIR}
rm -f ${OUTPUT_JAR}
mkdir ${BUILD_DIR}
mkdir ${LIB_DIR}
mkdir ${CLASSES_DIR}

echo "--- 2. Downloading Dependencies into ${LIB_DIR} ---"

# Download spring-security-crypto
echo "Downloading ${SPRING_FILE}..."
if wget -L -o "${LIB_DIR}/${SPRING_FILE}" "${SPRING_URL}"; then
    echo "${SPRING_FILE} downloaded successfully."
else
    echo "ERROR: Failed to download ${SPRING_FILE}. Exiting."
    exit 1
fi

# Download commons-logging
echo "Downloading ${LOGGING_FILE}..."
if wget -L -o "${LIB_DIR}/${LOGGING_FILE}" "${LOGGING_URL}"; then
    echo "${LOGGING_FILE} downloaded successfully."
else
    echo "ERROR: Failed to download ${LOGGING_FILE}. Exiting."
    exit 1
fi

# Set the combined classpath variable
DEPENDENCY_PATH="${LIB_DIR}/${SPRING_FILE}:${LIB_DIR}/${LOGGING_FILE}"


echo "--- 3. Creating Manifest File (${MANIFEST_FILE}) ---"
echo "Main-Class: ${MAIN_CLASS}" > ${MANIFEST_FILE}
echo "Class-Path: ${SPRING_FILE} ${LOGGING_FILE}" >> ${MANIFEST_FILE}
echo "" >> ${MANIFEST_FILE}


echo "--- 4. Compiling Java Source Files ---"
find src/main/java -name "*.java" > sources.txt
if javac -cp "${DEPENDENCY_PATH}" -d ${CLASSES_DIR} @sources.txt; then
    echo "Compilation successful."
else
    echo "ERROR: Compilation failed. Exiting."
    rm -f sources.txt
    exit 1
fi
rm -f sources.txt


echo "--- 5. Packaging Resources (CommonPasswords.txt) ---"
cp src/main/resources/CommonPasswords.txt ${CLASSES_DIR}/


echo "--- 6. Creating Executable JAR: ${BUILD_DIR}/${OUTPUT_JAR} ---"
jar cvfm ${BUILD_DIR}/${OUTPUT_JAR} ${MANIFEST_FILE} -C ${CLASSES_DIR} .
echo "Successfully created ${OUTPUT_JAR}."


echo "--- 7. Preparing Run Environment ---"
cp "${LIB_DIR}/${SPRING_FILE}" ${BUILD_DIR}/
cp "${LIB_DIR}/${LOGGING_FILE}" ${BUILD_DIR}/

rm -rf ${LIB_DIR}

echo "========================================"
echo "BUILD COMPLETE! Files are in the '${BUILD_DIR}' directory."
echo "To run the application, use the command:"
echo "java -jar ${BUILD_DIR}/${OUTPUT_JAR}"
echo "========================================"