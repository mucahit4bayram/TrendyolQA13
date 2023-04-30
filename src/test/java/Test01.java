import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class Test01 {

    //trendyol anasayfaya git
    //elektronik sekmesi üzerine gel
    //bilgisayar ve tablet sekmesine gel
    //tablet sekmesi tıklanır
    //arama kutusuna "ipad" yazıp arat
    //ipad sonuçlarının çıktığını doğrula
    //ürünleri list'e at
    //while,doWhile
    //list'in eleman sayısı 50 ise aşağı scroll yapma
    //24 üründen sonra diğer ürünleri beklerken webdriverwait, dinamik wait ile bekleme yap
    //ilk 49 ürünün title'larını bir text dosyasına yazdır
    //50. ürüne tıkla

    static WebDriver driver ;

    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test
    public void trendYol(){
        driver.get("https://www.trendyol.com/");
        WebDriverWait webDriverWait = new WebDriverWait(driver,Duration.ofSeconds(15));
        WebElement reklam = driver.findElement(By.cssSelector("div[class=\"modal-close\"]"));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class=\"modal-close\"]")));
        reklam.click();

        Actions actions = new Actions(driver);
        WebElement elektronics = driver.findElement(By.xpath("//a[@class=\"category-header\" and text()='Elektronik']"));
        actions.moveToElement(elektronics).pause(Duration.ofSeconds(3)).perform();


        WebElement bilgisayarTablet = driver.findElement(By.xpath("//a[@class=\"sub-category-header\" and normalize-space()='Bilgisayar & Tablet']"));
        actions.moveToElement(bilgisayarTablet).click().perform();

        //a[@class="category-header" and text()='Elektronik']

        WebElement aramaKutusu = driver.findElement(By.cssSelector("input[data-testid=\"suggestion\"]"));
        actions.sendKeys(aramaKutusu,"ipad"+ Keys.ENTER).perform();


        String aramaKelime = driver.findElement(By.cssSelector("input[data-testid=\"suggestion\"]")).getAttribute("value");
        System.out.println("aramaKelime = " + aramaKelime);

        Assert.assertEquals("ipad",aramaKelime);

        List<WebElement> urunler = driver.findElements(By.xpath("//div[@class='image-overlay-body']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)",urunler.get(urunler.size()-1));

    }
}
