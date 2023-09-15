import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.ClientInfoStatus;
import java.util.List;
public class testovi {
    private static final String driverpath = "C:/Users/Boško/Desktop/tsoft/chromedriver.exe";
    private static WebDriver browser;

    @BeforeClass
    public static void startBrowser(){
        System.setProperty("webdriver.chrome.driver", driverpath);
        browser = new ChromeDriver();
    }

    @AfterClass
    public static void closeBrowser(){
        browser.close();
    }

    //1. Proveriti na sajtu škole da li se klikom na logo škole se odlazi na početnu stranicu.
    @Test
    public void test1(){
        browser.get("https://www.viser.edu.rs/");
        WebElement slika = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                "header > div > div > div.mainHeader__logo > a > img"));
        slika.click();
        Assert.assertEquals("https://www.viser.edu.rs/", browser.getCurrentUrl());
    }
    //2. Proveriti da li u navigaciji na sajtu škole ima 9 menija.
    @Test
    public void test2(){
        browser.get("https://www.viser.edu.rs/");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > header > nav > div > div"));
        Assert.assertEquals(9, lista.size());
    }
    //3. Proveriti da li je rukovodilac studijskog programa “Ekološki inženjering” Aleksandra Grujić.
    @Test
    public void test3(){
        browser.get("https://www.viser.edu.rs/smer/ei");
        WebElement element = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > main > section:nth-child(2) > div.grid-cell-30.grid-order-1.grid-margin-bottom-1.smerInfo__osoblje > div:nth-child(2) > div:nth-child(2) > p:nth-child(1) > a"));
        Assert.assertEquals("др Александра Грујић", element.getText());
    }
    //4. Proveriti da li radi login forma za logovanje studenata na studentskom servisu.
    @Test
    public void test4(){
        browser.get("https://www.viser.edu.rs/student/login");
        WebElement login = browser.findElement(By.name("username"));
        login.sendKeys("boskonrt1419");
        WebElement pass = browser.findElement(By.name("password"));
        pass.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("btnLogin"));
        button.click();
        Assert.assertEquals("https://www.viser.edu.rs/studenti", browser.getCurrentUrl());
    }
    //5. Proveriti pozadinsku boju zaglavlja tabele za prikaz položenih predmeta. Očekivana
    //boja je: rgba(1, 87, 155, 1).
    @Test
    public void test5(){
        browser.get("https://www.viser.edu.rs/student/login");
        WebElement login = browser.findElement(By.name("username"));
        login.sendKeys("boskonrt1419");
        WebElement pass = browser.findElement(By.name("password"));
        pass.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("btnLogin"));
        button.click();
        browser.get("https://www.viser.edu.rs/studenti/predmeti-i-ispiti");
        WebElement element = browser.findElement(By.cssSelector("body > main > section > table:nth-child(3) > thead > tr"));
        String boja = element.getCssValue("background-color");
        Assert.assertEquals("rgba(1, 87, 155, 1)", boja);
    }
    //6. Proveriti da li na svakom smeru osnovnih studija ima ukupno 180 ESPB.
    @Test
    public void test6(){
        String[] smerovi = {"rt", "nrt", "asuv", "ei", "avt", "epo", "elite", "nrtd"};
        for(String smer : smerovi){
            int brojac = 0;
            browser.get("https://www.viser.edu.rs/smer/" + smer);
            List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                    "main > section:nth-child(3) > div.grid-cell-70.grid-padding-right-1.grid-order-2 > table > tbody > " +
                    "tr > td.centered > b"));
            try{
                for(int i = 0; i < lista.size(); i++){
                    int broj = Integer.parseInt(lista.get(i).getText());
                    brojac += broj;
                }
                if(brojac != 180)
                    Assert.assertTrue(false);
            }
            catch (Exception e) {}
        }
    }
    //7. Proveriti da li ima 6 službi u okviru škole koje se mogu videti, kada se iz drugog
    //menija uzme šesti podmeni.
    @Test
    public void test7(){
        browser.get("https://www.viser.edu.rs/stranica/sluzbe");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                "div.contentContainer > main > div > div > ul:nth-child(3) > li"));
        Assert.assertEquals(6, lista.size());
    }
    //8. Proveriti da li je u poslednjem ispitnom roku bilo sedam ispita koje je držala mr Jelena Mitić.
    @Test
    public void test8(){
        browser.get("https://www.viser.edu.rs/raspored-ispita/%D0%88%D1%83%D0%BD%D0%B8/2021");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                "main > div:nth-child(3) > table > tbody > tr > td.nastavnik"));
        int brojac = 0;
        for(int i = 0; i < lista.size(); i++){
            String profesor = lista.get(i).getText();
            if(profesor.equals("мр Јелена Митић"))
                brojac++;
        }
            Assert.assertEquals(7, brojac);
    }
    //9. Proveriti da li je šef nekog studijskog programa osnovnih studija Jelena Mitić.
    @Test
    public void test9(){
        String[] smerovi = {"rt", "nrt", "asuv", "ei", "avt", "epo", "elite", "nrtd"};
        for(String smer : smerovi){
            browser.get("https://www.viser.edu.rs/smer/" + smer);
            String prof = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                    "main > section:nth-child(2) > div.grid-cell-30.grid-order-1.grid-margin-bottom-1.smerInfo__osoblje > " +
                    "div:nth-child(2) > div:nth-child(2) > p:nth-child(1) > a")).getText();
            if(prof.equals("мр Јелена Митић"))
                Assert.assertTrue(true);
        }

    }
    //10. Proveriti da li je u adresaru tekst na svakom dugmetu za odlazak na profil člana
    //nastavnog osoblja bele boje (vrednost je: rgb(255, 255, 255)).
    @Test
    public void test10(){
        browser.get("https://www.viser.edu.rs/adresar");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                "div.contentContainer > main > div > div.grid-cell-70.grid-padding-right-1.grid-order-2 > " +
                "div > div > div > div > a"));
        for(int i = 0; i < lista.size(); i++){
            String dugme = lista.get(i).getCssValue("color");
            String expected = "rgba(255, 255, 255, 1)";
            Assert.assertEquals(expected, dugme);
        }
    }
    //11. Proveriti da li src atribut slike na naslovnoj strani u odeljku “Kratki programi
    //studija” ima vrednost: https://www.viser.edu.rs/images/kratki_programi.png.
    @Test
    public void test11(){
        browser.get("https://www.viser.edu.rs/");
        WebElement slika = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                "main > div:nth-child(2) > article.obavestenja__spotlight.grid-cell-30.grid-padding-right-1 > " +
                "div:nth-child(8) > a > img"));
        String vrednost = slika.getAttribute("src");
        Assert.assertEquals("https://www.viser.edu.rs/images/kratki_programi.png", vrednost);
    }
    //12. Proveriti da li šalter bar jedne studentske službe radi od 13h do 15h
    //(https://www.viser.edu.rs/stranica/studentska-sluzba).
    @Test
    public void test12(){
        browser.get("https://www.viser.edu.rs/stranica/studentska-sluzba");
        boolean provera = false;
        List<WebElement> lista1 = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                "div.contentContainer > main > div > div > ul > li:nth-child(1)"));
        for(WebElement element : lista1){
            String[] niz = element.getText().split(" ");
            if(niz[3].equals("13.00") && niz[5].equals("15.00,")){
                provera = true;
                break;
            }
        }
        int[] brojevi = {4, 6};
        for(int broj : brojevi){
            WebElement element = browser.findElement(By.cssSelector("body > div.websiteContainer > " +
                    "div.contentContainer > main > div > div > ul:nth-child("+broj+") > li:nth-child(2)"));
            String[] niz2 = element.getText().split(" ");
            if(niz2[1].equals("13.00") && niz2[3].equals("15.00,")){
                provera = true;
                break;
            }
        }
        Assert.assertTrue(provera);
    }
    //13. Proveriti da li u svakoj laboratoriji postoji bar 20 računara, laboratorije se nalaze
    //u drugom meniju, sedmi podmeni.
    @Test
    public void test13(){
        browser.get("https://www.viser.edu.rs/stranica/racunarske-laboratorije");
        int[] brojevi = {4, 8, 11, 14, 18, 21, 24, 27, 30};
        for(int broj : brojevi){
            WebElement br = browser.findElement(By.cssSelector("body > div.websiteContainer > " +
                    "div.contentContainer > main > div > div > p:nth-child("+broj+")"));
            String[] niz = br.getText().split(" ");
            if(Integer.parseInt(niz[0]) < 20)
                Assert.assertTrue(false);
            break;
        }
    }

}