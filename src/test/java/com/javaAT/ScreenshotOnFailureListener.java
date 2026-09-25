package com.javaAT;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotOnFailureListener extends TestListenerAdapter {

    private static final Path SCREENSHOT_DIRECTORY = Paths.get("screenshots");
    private static final DateTimeFormatter FILE_TIME =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (!(testInstance instanceof SauceDemoTest)) {
            return;
        }

        WebDriver driver = ((SauceDemoTest) testInstance).getDriver();
        if (driver == null) {
            return;
        }

        try {
            Files.createDirectories(SCREENSHOT_DIRECTORY);
            String fileName = result.getMethod().getMethodName()
                    + "-" + FILE_TIME.format(LocalDateTime.now()) + ".png";
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), SCREENSHOT_DIRECTORY.resolve(fileName));
            System.out.println("Failure screenshot saved to: "
                    + SCREENSHOT_DIRECTORY.resolve(fileName).toAbsolutePath());
        } catch (IOException | RuntimeException exception) {
            throw new AssertionError("Could not save failure screenshot", exception);
        }
    }
}
