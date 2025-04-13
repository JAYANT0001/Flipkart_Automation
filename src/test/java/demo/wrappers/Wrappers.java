package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
   
  

    public boolean click(WebElement element, WebDriver driver) {
        try {
            if (element != null && element.isDisplayed()) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);

                element.click();
                return true;
            } else {
                System.out.println("Element not found or not visible.");
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
            return false;
        } catch (ElementNotInteractableException e) {
            System.out.println("Element is not interactable: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred while clicking the element: " + e.getMessage());
            return false;
        }
    }

    public boolean sendKeys(WebElement element, String text) {
        try {

            if (element != null && element.isDisplayed()) {

                element.clear();

                element.sendKeys(text);
                System.out.println("Text entered successfully.");
                return true;
            } else {
                System.out.println("Element not found or not visible.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while entering text: " + e.getMessage());
            return false;
        }
    }

    public boolean navigateToURL(String url, WebDriver driver) {
        try {

            String currentURL = driver.getCurrentUrl();

            if (!currentURL.equals(url)) {
                driver.get(url);
                System.out.println("Navigated to the new URL: " + url);
                return true;
            } else {
                System.out.println(
                        "The given URL is the same as the current URL. No navigation needed.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while navigating to the URL: " + e.getMessage());
            return false;
        }
    }

    public WebElement findElementWithRetry(By by, WebDriver driver) {
        int retryCount = 0;
        WebElement element = null;

        while (retryCount < 3) {
            try {
                element = driver.findElement(by);
                System.out.println("Element found on attempt " + (retryCount + 1));
                return element;
            } catch (NoSuchElementException e) {
                retryCount++;
                System.out.println("Attempt " + retryCount + " failed. Retrying...");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("Element not found after 3 attempts.");
        return null;
    }

    public int countOfMachineWith(WebDriver driver, List<WebElement> ratingElements, String xpath) {
        int count = 0;
        ratingElements = driver.findElements(By.xpath(xpath));
        for (WebElement rating : ratingElements) {
            double rate = Double.parseDouble(rating.getText());
            if (rate <= 4.0) {
                count++;
            }
        }
        return count;
    }

    public void dismissLoginPopupIfPresent(WebDriver driver, WebDriverWait wait) {
        try {
            Thread.sleep(2000);
         
            WebElement popupCloseElement = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'JFPqaw')]/span")));
            popupCloseElement.click();
            System.out.println("POPup is closed");

        } catch (Exception e) {
            System.out.println("POPup not foung.....continuing the execution");
        }
    }
}
