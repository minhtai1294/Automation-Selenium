# Automation-Selenium

This project is an automation testing framework built with **Selenium**, **TestNG**, **Log4j**, **ExtentReports**, and **RestAssured**. It is designed for robust, maintainable, and scalable web and API test automation.

## Features

- **Selenium WebDriver** for browser automation
- **TestNG** for test orchestration and reporting
- **Log4j** for flexible logging
- **ExtentReports** for rich HTML test reports
- **RestAssured** for API testing
- Modular structure for easy test management and extension

## Project Structure

```
.
├── pom.xml                  # Maven build configuration
├── runtest.sh               # Shell script to run tests
├── src/
│   ├── main/java/           # Main Java source code (utilities, core)
│   └── test/java/           # Test classes
│   └── test/resources/      # Test resources (e.g., testng.xml, log4j.properties)
├── target/                  # Build output
└── test-output/             # TestNG and ExtentReports output
```

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.x

### Installation

Clone the repository:

```sh
git clone https://github.com/yourusername/automation-selenium.git
cd automation-selenium
```

Install dependencies:

```sh
mvn clean install
```

### Running Tests

To run all tests using Maven and TestNG:

```sh
mvn test
```

Or use the provided shell script:

```sh
./runtest.sh
```

Test results and reports will be generated in the `test-output/` directory. Open `test-output/index.html` in your browser to view the detailed report.

## Logging

Logging is configured via Log4j (org.apache.logging.log4j). You can adjust logging settings in `src/test/resources/log4j2.xml`.

## Custom Utilities

- Logging utilities: `com.example.utils.LogUtils`
- Reporting integration: ExtentReports via custom listeners

## Contributing

Feel free to fork the repository and submit pull requests.

## License

This project is licensed under the MIT License.

---

*Automation-Selenium: Fast, reliable, and maintainable test automation for web and API projects.*
