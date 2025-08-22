#!/bin/bash
set -e  # Exit immediately if a command fails

# --- Read from Jenkins environment variables or CLI arguments ---
SUITE_NAME=${SuiteName:-"defaultSuite"}  # Fall back to default if not provided
PLATFORM=${Platform:-"web"}              # Fall back to 'web'

echo "=== Running tests for Suite: $SUITE_NAME, Platform: $PLATFORM ==="

# Step 1: Generate Dynamic TestNG Suite
echo "=== Generating Dynamic TestNG Suite ==="
mvn exec:java 
  -Dexec.mainClass="com.example.tests.executions.DynamicSuiteGenerator" 
  -Dexec.args="$SUITE_NAME $PLATFORM"

# Step 2: Run Maven Test
echo "=== Executing Tests ==="
mvn test 
  -DsuiteXmlFile="dynamic-testng.xml" 