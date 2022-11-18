import jdk.jfr.Description;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTest {
    @Test
    @Description("Проверь, что работают переходы из булок в соусы")
    public void checkActiveBuns() {
        objConstructorPage.clickChapterBuns();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(objConstructorPage.selectBuns)));
        assertEquals("Булки", driver.findElement(objConstructorPage.selectBuns).getText());
    }

    @Test
    @Description("Проверь, что работают переходы из соусов в начинки")
    public void checkActiveSauces() {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(objConstructorPage.nonSelectSauces)));
        objConstructorPage.clickChapterSauces();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(objConstructorPage.selectSauces)));
        assertEquals("Соусы", driver.findElement(objConstructorPage.selectSauces).getText());
    }

    @Test
    @Description("Проверь, что работают переходы из начинок в булки")
    public void checkActiveFilling() {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(objConstructorPage.nonSelectFilling)));
        objConstructorPage.clickChapterFilling();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(objConstructorPage.selectFilling)));
        assertEquals("Начинки", driver.findElement(objConstructorPage.selectFilling).getText());
    }
}