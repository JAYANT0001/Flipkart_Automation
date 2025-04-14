package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {

    ChromeDriver driver;
    String url = "https://www.flipkart.com/";
    Wrappers wrapper;
    WebDriverWait wait;
    String product1 = "Washing Machine";
    String product2 = "iPhone";
    String product3 = "Coffee Mug";

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start test case 01");
        driver.get(url);
        //Dissmissing popup if present
        wrapper.dismissLoginPopupIfPresent(driver, wait);
        WebElement searchElement = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='Pke_EE']")));
        wrapper.sendKeys(searchElement, product1);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement popularityElement = driver.findElement(By.xpath("//div[text()='Popularity']"));
        wait.until(ExpectedConditions.visibilityOf(popularityElement));
        wrapper.click(popularityElement, driver);
        //filtering on the basis of rating
        List<WebElement> ratingElements = null;
        //getting count of machine on the basis of rating
        int countOfMachine = wrapper.countOfMachineWith(driver, ratingElements, "//div[@class='XQDdHH']");
        System.out.println("Count of Washing Machine with less than or equal to 4 ratiing is :: " + countOfMachine);
        System.out.println("End test case 01");
    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start test case 02");
        driver.get(url);
        //Dismiss popup if present
        wrapper.dismissLoginPopupIfPresent(driver, wait);
        WebElement searchElement = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='Pke_EE']")));
        wrapper.sendKeys(searchElement, product2);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");

        //getting all items container into a list
        List<WebElement> containers = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
        System.out.println("Number of product containers found: " + containers.size());

        //running loop for container which all are present inside containers
        for (WebElement container : containers) {
            try {
                //getting title element from a particular container
                List<WebElement> titleElements = container.findElements(By.xpath(
                        ".//div[contains(@class,'KzDlHZ') or contains(@class,'IRpwTa') or contains(@class,'s1Q9rs')]"));

                if (titleElements.isEmpty())
                    continue;
                String title = titleElements.get(0).getText();

                //getting discount element from a particular container
                List<WebElement> discountElements = container.findElements(By.xpath(".//*[contains(text(),'% off')]"));
                if (discountElements.isEmpty())
                    continue;

                String discountText = discountElements.get(0).getText();
                int discount = Integer.parseInt(discountText.replaceAll("[^0-9]", ""));

                //printing title and discout of the product if discount is<17
                if (discount > 17) {
                    System.out.println("Title: " + title);
                    System.out.println("Discount: " + discount + "%");
                    System.out.println("----------------------------");
                }
            } catch (Exception e) {
                System.out.println("Error processing a product: " + e.getMessage());
            }
        }
        System.out.println("End test case 02");
    }

    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start test case 03");
        driver.get(url);
        //dismiss popup if present
        wrapper.dismissLoginPopupIfPresent(driver, wait);
        WebElement searchElement = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='Pke_EE']")));
        wrapper.sendKeys(searchElement, product3);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);
        //applying filter 
        WebElement filterElement = driver.findElement(
                By.xpath("//div[contains(text(),'4') and contains(text(),'above')]/preceding-sibling::div"));
        wrapper.click(filterElement, driver);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
        //getting all the container of elements into a list
        List<WebElement> containers = driver.findElements(By.xpath("//div[@class='slAVV4']"));
        System.out.println("Number of product containers found: " + containers.size());
        //definig a arrylist of HashMap for storing products
        List<HashMap<String, Object>> allProducts = new ArrayList<>();
        for (WebElement container : containers) {
            try {
                //getting the title element from a particular container
                WebElement titlement = container.findElement(By.xpath(".//a[@class='wjcEIp']"));
                String titleText = titlement.getText();

                //getting the image element from a particular container
                WebElement imageElement = container.findElement(By.xpath(".//img[@class='DByuf4']"));
                String imageURL = imageElement.getAttribute("src");

                //getting the review element from a particular container
                WebElement reviewElement = container.findElement(By.xpath(".//span[@class='Wphh3N']"));
                String reviewCounts = reviewElement.getText();
                int reviews = Integer.parseInt(reviewCounts.replaceAll("[^0-9]", ""));

                //defing a HashMap for storing the details of the product
                HashMap<String, Object> product = new HashMap<>();
                product.put("title", titleText);
                product.put("imageUrl", imageURL);
                product.put("reviews", reviews);

                //now storing the above product into that arraylist of HashMap
                allProducts.add(product);

            } catch (Exception e) {
                System.out.println("Error processing a product: " + e.getMessage());
            }
        }
        // sorting by lambda expression
        allProducts.sort((a, b) -> (int) b.get("reviews") - (int) a.get("reviews"));

        //print the intial 5 products details
        for (int i = 0; i < Math.min(5, allProducts.size()); i++) {
            Map<String, Object> product = allProducts.get(i);
            System.out.println("Title : " + product.get("title"));
            System.out.println("ImageURL : " + product.get("imageUrl"));
            System.out.println("Reviews : " + product.get("reviews"));
            System.out.println("_________________________________");
        }
        System.out.println("End test case 03");

    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wrapper = new Wrappers();

    }

    @AfterTest
    public void endTest() {

        driver.quit();

    }
}
