import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import java.time.Duration


@Composable
@Preview
fun App() {
    var count by remember { mutableStateOf(0) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    count++
                }
            ) {
                Text(if (count == 0) "Hello World" else "Clicked ${count}!")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    count = 0
                }
            ) {
                Text("Reset")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    val service = ChromeDriverService.Builder().build()
                    val driver = ChromeDriver(service)
//                    driver.get("https://twitter.com/search?q=web3")
                    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

//                    WebDriverWait(driver, Duration.ofSeconds(10)).until(
//                        ExpectedConditions.presenceOfElementLocated(
//                            By.cssSelector(
//                                "main section"
//                            )
//                        )
//                    )

                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500))
                    val title = driver.title
                    println(title)

                    val textBox = driver.findElement(By.name("my-text"))
                    val submitButton = driver.findElement(By.cssSelector("button"))

                    textBox.sendKeys("Selenium");
                    submitButton.click();

                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500))
                    val response = driver.findElement(By.id("message"))
                    println(response.text)

                    driver.quit();
                },
            ) {
                Text("Scrap")
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
        App()
    }
}
