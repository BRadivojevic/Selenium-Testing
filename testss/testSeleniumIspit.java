import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class testSeleniumIspit {
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
        WebElement slika = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > header > div > div > div.mainHeader__logo > a > img"));
        slika.click();
        Assert.assertEquals("https://www.viser.edu.rs/", browser.getCurrentUrl());
    }
    //2. Proveriti da li u navigaciji na sajtu škole ima 9 menija.
    @Test
    public void test2(){
        browser.get("https://www.viser.edu.rs/");
        List<WebElement> list = browser.findElements(By.cssSelector("div.dropdown"));
        Assert.assertEquals(9, list.size());
    }
    //3. Proveriti da li je rukovodilac studijskog programa “Ekološki inženjering” Aleksandra Grujić.
    @Test
    public void test3(){
        browser.get("https://www.viser.edu.rs/smer/ei");
        String actual = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > main > section:nth-child(2) > div.grid-cell-30.grid-order-1.grid-margin-bottom-1.smerInfo__osoblje > div:nth-child(2) > div:nth-child(2) > p:nth-child(1) > a")).getText();
        String expected = "др Александра Грујић";
        Assert.assertEquals(expected, actual);
    }
    //4. Proveriti da li radi login forma za logovanje studenata na studentskom servisu.
    @Test
    public void test4(){
        browser.get("https://www.viser.edu.rs/student/login");
        WebElement username = browser.findElement(By.name("username"));
        username.sendKeys("boskonrt1419");
        WebElement password = browser.findElement(By.name("password"));
        password.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("btnLogin"));
        button.click();
        String actual = browser.getCurrentUrl();
        String expected = "https://www.viser.edu.rs/studenti";
        Assert.assertEquals(expected, actual);
    }
    //5. Proveriti pozadinsku boju zaglavlja tabele za prikaz položenih predmeta. Očekivana
    //boja je: rgba(1, 87, 155, 1).
    @Test
    public void test5(){
        browser.get("https://www.viser.edu.rs/student/login");
        WebElement username = browser.findElement(By.name("username"));
        username.sendKeys("boskonrt1419");
        WebElement password = browser.findElement(By.name("password"));
        password.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("btnLogin"));
        button.click();
        browser.get("https://www.viser.edu.rs/studenti/predmeti-i-ispiti");
        WebElement heder = browser.findElement(By.className("predmeti__headers"));
        String boja = heder.getCssValue("background-color");
        Assert.assertEquals("rgba(1, 87, 155, 1)", boja);
    }
    //6. Proveriti da li na svakom smeru osnovnih studija ima ukupno 180 ESPB.
    @Test
    public void test6(){
        String[] smerovi={"avt","nrt","epo","asuv","rt","elite","is","nrtd"};
        for(String smer:smerovi) {
            int brojac = 0;
            browser.get("https://www.viser.edu.rs/smer/" + smer);
            List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                    "main > section:nth-child(3) > " +
                    "div.grid-cell-70.grid-padding-right-1.grid-order-2 > table > tbody > tr > td.centered > b"));
            try {
                for (int i = 0; i < lista.size(); i++) {
                    int broj = Integer.parseInt(lista.get(i).getText());
                    brojac += broj;
                }
                if (brojac != 180)
                    Assert.assertTrue(false);
            }
            catch (Exception ex) {}
        }
    }
    //7. Proveriti da li ima 6 službi u okviru škole koje se mogu videti, kada se iz drugog
    //menija uzme šesti podmeni.
    @Test
    public void test7(){
        browser.get("https://www.viser.edu.rs/stranica/sluzbe");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > main > div > div > ul > li"));
        Assert.assertEquals(6, lista.size());
    }
    //8. Proveriti da li je u poslednjem ispitnom roku bilo sedam ispita koje je držala mr Jelena Mitić.
    @Test
    public void test8(){
        browser.get("https://www.viser.edu.rs/raspored-ispita/%D0%88%D1%83%D0%BD%D0%B8/2021");
        List<WebElement> lista = browser.findElements(By.cssSelector(".nastavnik"));
        int brojac = 0;
        for(int i = 0; i < lista.size(); i++){
            if(lista.get(i).getText().equals("мр Јелена Митић"))
                brojac ++;
        }
        Assert.assertEquals(7, brojac);
    }
    //9. Proveriti da li je šef nekog studijskog programa osnovnih studija Jelena Mitić.
    @Test
    public void test9(){
        String[] smerovi = {"avt", "nrt", "epo", "asuv", "rt", "elite", "is", "nrtd"};
        boolean provera = false;
        for(String smer : smerovi) {
            browser.get("https://www.viser.edu.rs/smer/" + smer);
            String ime = browser.findElement(By.cssSelector("body > div.websiteContainer > div.contentContainer > main > section:nth-child(2) > div.grid-cell-30.grid-order-1.grid-margin-bottom-1.smerInfo__osoblje > div:nth-child(2) > div:nth-child(2) > p:nth-child(1)")).getText();
            if (ime.equals("мр Јелена Митић")) {
                provera = true;
                break;
            }
        }
        Assert.assertTrue(provera);
    }
    //10. Proveriti da li je u adresaru tekst na svakom dugmetu za odlazak na profil člana
    //nastavnog osoblja bele boje (vrednost je: rgb(255, 255, 255)).
    @Test
    public void test10(){
        browser.get("https://www.viser.edu.rs/adresar");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                "div.contentContainer > main > div > div.grid-cell-70.grid-padding-right-1.grid-order-2 > " +
                "div > div:nth-child(1) > div > div:nth-child(1) > a"));
        for(int i = 0; i < lista.size(); i++){
            String boja = lista.get(i).getCssValue("color");
            System.out.println(boja);
            String expected = "rgba(255, 255, 255, 1)";
            //if(boja.equals("rgb(255, 255, 255)")){
             //   Assert.assertTrue(true);
            //}
            Assert.assertEquals(expected, boja);
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
        String expected = "https://www.viser.edu.rs/images/kratki_programi.png";
        String actual = slika.getAttribute("src");
        Assert.assertEquals(expected, actual);
    }
    //12. Proveriti da li šalter bar jedne studentske službe radi od 13h do 15h
    //(https://www.viser.edu.rs/stranica/studentska-sluzba).
    @Test
    public void test12(){
        browser.get("https://www.viser.edu.rs/stranica/studentska-sluzba");
        boolean provera = false;
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                "div.contentContainer > main > div > div > ul > li:nth-child(1)"));
        for(WebElement element : lista){
            String niz[] = element.getText().split(" ");
            if(niz[3].equals("13.00") && niz[5].equals("15.00,")){
                provera = true;
                break;
            }
        }
        int[] brojevi = {4, 6};
        for(int broj : brojevi){
            WebElement element = browser.findElement(By.cssSelector("body > div.websiteContainer > " +
                    "div.contentContainer > main > div > div > ul:nth-child("+broj+") > li:nth-child(2)"));
            String[] niz = element.getText().split(" ");
            if(niz[1].equals("13.00") && niz[3].equals("15.00,")){
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
        boolean provera = true;
        for(int broj : brojevi){
            String paragraf = browser.findElement(By.cssSelector("body > div.websiteContainer > " +
                    "div.contentContainer > main > div > div > p:nth-child("+broj+")")).getText();
            String[] podelaPoReci = paragraf.split(" ");
            int brojac = Integer.parseInt(podelaPoReci[0]);
            if(brojac < 20){
                provera = false;
                break;
            }
        }
        Assert.assertTrue(provera);
    }
    //14. Proveriti u savetu (https://www.viser.edu.rs/stranica/savet) ima 6 profesora strukovnih
    //studija koji su članovi saveta.
    @Test
    public void test14(){
        browser.get("https://www.viser.edu.rs/stranica/savet");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                "div.contentContainer > main > div > div > ol > li"));
        String expected = "професор струковних студија";
        int brojac = 0;
        for(WebElement prof : lista){
            String tekst = prof.getText();
            if(tekst.contains(expected))
                brojac ++;
        }
        int exp = 6;
        Assert.assertEquals(exp, brojac);
    }
    //15. Proveriti da li se na stranici Akademije (prvi meni u navbaru) u elementu sa klasom grid-row
    //nalaze dve slike.
    @Test
    public void test15(){
        browser.get("https://www.viser.edu.rs/stranica/akademija");
        int expected = 2;
        int actual = browser.findElements(By.cssSelector("main img")).size();
        Assert.assertEquals(expected, actual);
    }
    //16. Proveriti da li na studentskom servisu stoji dobar žiro račun škole. Račun je 840-2119666-62.
    @Test
    public void test16(){
        browser.get("https://www.viser.edu.rs/student/login");
        WebElement username = browser.findElement(By.name("username"));
        username.sendKeys("boskonrt1419");
        WebElement password = browser.findElement(By.name("password"));
        password.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("btnLogin"));
        button.click();
        browser.get("https://www.viser.edu.rs/studenti");
        String actual = browser.findElement(By.cssSelector("body > main > aside > p:nth-child(4)")).getText();
        String expected = "840-2119666-62";
        Assert.assertEquals(expected, actual);
    }
    //17. Proveriti da li kontakt telefon svih zaposlenih u pravnoj službi
    //(https://www.viser.edu.rs/stranica/pravna-sluzba) sadrži 12 cifara (ne racuna se +)
    @Test
    public void test17(){
        browser.get("https://www.viser.edu.rs/stranica/pravna-sluzba");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > main > div > div > ul > li:nth-child(1) > a:nth-child(4)"));
        for(WebElement broj : lista){
            int brojac = 0;
            for(char c : broj.getText().toCharArray()){
                if(Character.isDigit(c)){
                    brojac ++;
                }
            }
            int expected = 12;
            Assert.assertEquals(expected, brojac);
        }
    }
    //18. Proveriti da li je, kada se ode na moodle sistem, pozadinska boja naslova sa tekstom "најновије вести"
    //rgb(26, 188, 156)
    @Test
    public void test18(){
        browser.get("https://lectio2.viser.edu.rs/login/index.php");
        WebElement username = browser.findElement(By.name("username"));
        username.sendKeys("boskonrt1419");
        WebElement password = browser.findElement(By.name("password"));
        password.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("loginbtn"));
        button.click();
        browser.get("https://lectio2.viser.edu.rs/");
        WebElement naslov = browser.findElement(By.className("title"));
        String expected = "rgb(26, 188, 156)";
        String actual = naslov.getCssValue("background-color");
        Assert.assertEquals(expected, actual);
    }
    //19. Proveriti da li na stranici https://www.viser.edu.rs/stranica/studentska-sluzba postoje tri fotografije
    //u glavnom delu stranice.
    @Test
    public void test19(){
        browser.get("https://www.viser.edu.rs/stranica/studentska-sluzba");
        int expected = 3;
        int actual = browser.findElements(By.cssSelector("main img")).size();
        Assert.assertEquals(expected, actual);
    }
    //20. Proveriti da li je na studenstskom servisu u odeljku Status i podaci boja teksta svih naslova plava
    //rgb(44, 76, 130)
    @Test
    public void test20(){
        browser.get("https://www.viser.edu.rs/student/login");
        boolean provera = true;
        WebElement username = browser.findElement(By.name("username"));
        username.sendKeys("boskonrt1419");
        WebElement password = browser.findElement(By.name("password"));
        password.sendKeys("hulg2714");
        WebElement button = browser.findElement(By.id("btnLogin"));
        button.click();
        browser.get("https://www.viser.edu.rs/studenti/status-i-podaci");
        List<WebElement> lista = browser.findElements(By.cssSelector("body > main > section > dl:nth-child(2) > dt"));
        for(int i = 0; i < lista.size(); i++){
            String boja = lista.get(i).getCssValue("background-color");
            System.out.println(boja);
            Assert.assertEquals("rgb(44, 76, 130)", boja);
        }
    }
    //21. Proveriti da li je na stranici upis ukupan broj studenata za upis u prvu godinu osnovnih studija 700.
    @Test
    public void test21() {
        browser.get("https://www.viser.edu.rs/upis");
        int[] brojevi = {2, 3, 4, 5, 6, 7, 8, 9};
        int brojac = 0;
        for (int broj : brojevi) {
            List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                    "main > div > div > table:nth-child(13) > tbody > tr:nth-child("+broj+") > td:nth-child(2)"));
            for (int i = 0; i < lista.size(); i++) {
                int stud = Integer.parseInt(lista.get(i).getText());
                brojac += stud;
                System.out.println(brojac);
                System.out.println(stud);
            }
        }
        Assert.assertEquals(700, brojac);
    }
    //22.
    @Test
    public void test22(){
        String[] smerovi={"avt","nrt","epo","asuv","rt","elite","is","nrtd"};
        for(String smer:smerovi) {
            int brojac = 0;
            browser.get("https://www.viser.edu.rs/smer/" + smer);
            List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > div.contentContainer > " +
                    "main > section:nth-child(3) > " +
                    "div.grid-cell-70.grid-padding-right-1.grid-order-2 > table > tbody > tr > td.centered > b"));
            try {
                for (int i = 0; i < lista.size(); i++) {
                    int broj = Integer.parseInt(lista.get(i).getText());
                    brojac += broj;
                }
                if (brojac != 180)
                    Assert.assertTrue(false);
            }
            catch (Exception ex) {}
        }
    }
    //23. Proveriti da li na akademskom kalendaru svaki drugi red tabele pocevsi od drugog
    // ima pozadinsku boju rgb(245, 245, 245)
    @Test
    public void test23(){
        browser.get("https://www.viser.edu.rs/akademski-kalendar");
        int[] brojevi = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40};
        for(int broj : brojevi){
            List<WebElement> lista = browser.findElements(By.cssSelector("body > div.websiteContainer > " +
                    "div.contentContainer > main > div > div.grid-cell-70.grid-padding-right-1.grid-order-2 > " +
                    "div:nth-child("+broj+")"));
            for(int i = 0; i < lista.size(); i++) {
                String boja = lista.get(i).getCssValue("background-color");
                System.out.println(boja);
                String expected = "rgba(245, 245, 245, 1)";
                //if(boja.equals("rgb(255, 255, 255)")){
                //   Assert.assertTrue(true);
                //}
                Assert.assertEquals(expected, boja);
            }
        }
    }
    //24. Proveriti da li Jelena Mitic predaje predmet sa sifrom 150503.
    @Test
    public void test24(){
        browser.get("https://www.viser.edu.rs/raspored-ispita/%D0%A1%D0%B5%D0%BF%D1%82%D0%B5%D0%BC%D0%B1%D0%B0%D1%80/2021");
        List<WebElement> lista = browser.findElements(By.cssSelector(".ispiti__row"));
        System.out.println(lista.size());
        for(int i = 0; i < lista.size(); i++){
            String ispit = lista.get(i).toString();
            if(ispit.contains("150503") && ispit.contains("мр Јелена Митић"))
                Assert.assertTrue(true);
        }
    }
}