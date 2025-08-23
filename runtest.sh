#!/bin/bash
set -e  # Exit immediately if a command fails

# --- Read from Jenkins environment variables or CLI arguments ---
SUITE_NAME=${SUITE_NAME:-"defaultSuite"}
PLATFORM=${PLATFORM:-"defaultWeb"}
FEATURE=${FEATURE:-"default"}

echo "=== Running tests for Suite: $SUITE_NAME, Platform: $PLATFORM, Feature: $FEATURE ==="

# Step 1: Generate Dynamic TestNG Suite
echo "=== Generating Dynamic TestNG Suite ==="
mvn exec:java -Dexec.mainClass="com.example.executions.DynamicSuiteGenerator" -Dexec.args="$SUITE_NAME $PLATFORM $FEATURE"

echo "📄 Printing dynamic-testng.xml content..."
cat src/test/resources/dynamic-testng.xml

# Step 2: Run Maven Test
echo "=== Executing Tests ==="
mvn test -DsuiteXmlFile=src/test/resources/dynamic-testng.xml