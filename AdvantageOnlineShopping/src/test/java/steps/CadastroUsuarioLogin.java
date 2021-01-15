package steps;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CadastroUsuarioLogin {

	private WebDriver driver;
	String loginEsperado;

	@Given("^que estou acessando a aplicação$")

	public void que_estou_acessando_a_aplicação() throws Throwable {

		try {
			System.setProperty("webdriver.chrome.driver",
			"C:\\Users\\jerso\\Git\\AdvantageOnlineShopping\\AdvantageOnlineShopping\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get("http://advantageonlineshopping.com/#/");
		} catch (Exception error) {
			System.out.println("Não foi encontrado o caminho do ChromeDriver ou não foi possivel acessar a aplicação");
		}
	}

	@When("^clico no botão USER$")
	public void clico_no_botão_USER() throws Throwable {

		try {

			WebElement botaoUser = driver.findElement(By.id("menuUser"));
			botaoUser.click();
		} catch (Exception error) {
			System.out.println("Não foi possivel acessar o menu de ");
		}
	}

	@When("^Clico no botão CREATE NEW ACCOUNT$")
	public void clico_no_botão_CREATE_NEW_ACCOUNT() throws Throwable {

		try {
			WebElement botaoCreateNewAccount = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.linkText("CREATE NEW ACCOUNT")));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", botaoCreateNewAccount);
		} catch (Exception error) {
			System.out.println("O WebDriver não conseguiu clicar no botão CREATE NEW ACCOUNT");
		}
	}

	@When("^Preencha os Campos \"([^\"]*)\" e \"([^\"]*)\"$")
	public void preencha_os_Campos_e(String login, String senha) throws Throwable {

		try {

			driver.findElement(By.name("usernameRegisterPage")).sendKeys(login);
			driver.findElement(By.name("passwordRegisterPage")).sendKeys(senha);
			driver.findElement(By.name("confirm_passwordRegisterPage")).sendKeys(senha);

			loginEsperado = login;

		} catch (Exception error) {
			System.out.println("O WebDriver não preencher login e senha");
		}
	}

	@When("^Preencha o campo email$")
	public void preencha_o_campo_email() throws Throwable {

		try {

			Faker faker = new Faker();

			driver.findElement(By.name("emailRegisterPage")).sendKeys(faker.internet().emailAddress());

		} catch (Exception error) {
			System.out.println("O WebDriver não preencher o e-mail");
		}
	}

	@When("^clique no botão I Agree e no botão Register$")
	public void clique_no_botão_I_Agree_e_no_botão_Register() throws Throwable {

		try {
			WebElement botaoIagree = driver.findElement(By.name("i_agree"));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", botaoIagree);

			WebElement botaoRegister = driver.findElement(By.id("register_btnundefined"));
			JavascriptExecutor executor2 = (JavascriptExecutor) driver;
			executor2.executeScript("arguments[0].click();", botaoRegister);
		}

		catch (Exception error) {
			System.out.println("O usuário já foi cadastrado na aplicação ou WebDriver não conseguiu clicar nos botões Iagree e Register, ");
		}

	}

	@Then("^o sistema apresenta o usuário logado$")
	public void o_sistema_apresenta_o_usuário_logado_no_sistema() throws Throwable {

		try {
			WebElement loginTela = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#menuUserLink > span")));

			String loginAtual = loginTela.getText();
			Assert.assertEquals(loginEsperado, loginAtual);
			
			File printLoginCadastro = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(printLoginCadastro, new File("C:\\Users\\jerso\\Git\\AdvantageOnlineShopping\\AdvantageOnlineShopping\\target\\cucumber\\printScreen\\printCadastro.png"));
			
		}

		catch (Exception error) {
			System.out.println("o WebDriver não conseguiu cadastrar o usuário ou retornou login diferente do esperado");
		}
		finally {
			driver.quit();
		}
	}

	@When("^Informe os Campos \"([^\"]*)\" e \"([^\"]*)\"$")
	public void Informe_os_Campos_e(String loginInformado, String senhaInformada) throws Throwable {

		try {
			driver.findElement(By.name("username")).sendKeys(loginInformado);
			driver.findElement(By.name("password")).sendKeys(senhaInformada);

		}

		catch (Exception error) {
			System.out.println("O WebDriver não preencher login e senha");
		}
	}

	@When("^clico no botão SIGN IN$")
	public void clico_no_botão_SIGN_IN() throws Throwable {
		try {

			WebElement botaoSingIn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign_in_btnundefined")));

			botaoSingIn.click();

		} catch (Exception error) {
			System.out.println("O WebDriver não clicou no botão de login");
		}
	}

	@Then("^o sistema apresenta o usuário que efetuou o login$")
	public void o_sistema_apresenta_o_usuário_que_efetuou_o_login() throws Throwable {

		try {

			WebElement efetuarLogin = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#menuUserLink > span")));

			String loginUsuario = efetuarLogin.getText();
			Assert.assertEquals("Jcunha03222", loginUsuario);
			
			File printLogin = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(printLogin, new File("C:\\Users\\jerso\\Git\\AdvantageOnlineShopping\\AdvantageOnlineShopping\\target\\cucumber\\printScreen\\printLogin.png"));
		}

		catch (Exception error) {
			System.out.println("O WebDriver não conseguiu validar o login de usuario");
		}

		finally {
			driver.quit();
		}
	}
}
