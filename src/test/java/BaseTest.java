import api.model.ResponseWithToken;
import api.model.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.*;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static ConstructorPage objConstructorPage;
    protected static EnterPage objEnterPage;
    protected static PersonalAreaPage objPersonalAreaPage;
    protected static RegistrationPage objRegistrationPage;
    protected static RecoveryPage objRecoveryPage;
    static Faker faker;
    static String name;
    static String email;
    static String password;
    static User user;


    @BeforeClass
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://stellarburgers.nomoreparties.site/");
        objConstructorPage = new ConstructorPage(driver);
        objEnterPage = new EnterPage(driver);
        objPersonalAreaPage = new PersonalAreaPage(driver);
        objRegistrationPage = new RegistrationPage(driver);
        objRecoveryPage = new RecoveryPage(driver);
        wait = new WebDriverWait(driver, 5000);
        faker = new Faker();
        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = String.valueOf(faker.number().numberBetween(1000000, 99999999));
        user = new User(email, password);
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @After
    public void clearData() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login");
        if (response.statusCode() == 200) {
            ResponseWithToken token = response.body().as(ResponseWithToken.class);

            if (token != null) {
                Response deleteUser = given().header("Authorization", token.getAccessToken())
                        .delete("/api/auth/user");
                deleteUser.then().assertThat().statusCode(202);
            }
        }
        driver.manage().deleteAllCookies();
        ((WebStorage) driver).getLocalStorage().clear();
        ((WebStorage) driver).getSessionStorage().clear();
    }
    @AfterClass
    public static void tearDown(){
        driver.quit();
    }
}