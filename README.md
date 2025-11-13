# Gauge + Selenium Java Template

Bu depo, Gauge ile yazılmış ve Selenium sürücüsünü kullanarak tarayıcı etkileşimi gerçekleştiren bir test/proje şablonudur. Hızlı başlangıç için yapılandırılmış örnekler ve çalışma talimatları içerir.

---

## Özellikler

* Gauge ile davranış odaklı test/specification yazımı
* Selenium WebDriver ile tarayıcı otomasyonu
* JUnit ile test çalıştırma (maven)
* Allure raporları için XML rapor desteği

---

## Önkoşullar

* Java 21+ (veya proje gereksinimlerinize uygun JDK)
* Maven
* Gauge CLI (`gauge`) yüklü
* (Tercihe bağlı) Allure CLI (rapor görüntüleme için)

---

## Şablonu kurma

Gauge şablonunu yüklemek için:

```bash
gauge --install java_selenium
```

Proje kökünde çalışıyorsanız, bağımlılıkları indirmek için:

```bash
mvn clean test-compile
```

---

## Nasıl çalışır / Kullanım

### Spec (Specification) oluşturma

`specs` dizini altına yeni bir dosya ekleyin, örn: `hello_world.spec`.
Aşağıdaki örnek Markdown biçiminde çalıştırılabilir bir specification dosyasıdır. Her başlık bir senaryoyu (scenario), her madde ise bir adımı (step) temsil eder.

**Örnek - `hello_world.spec`**

```markdown
Sample Specification
====================

This is an executable specification file. This file follows markdown syntax. Every heading in this file denotes a scenario. Every bulleted point denotes a step.
To execute this specification, use
	mvn test

* Navigate to "http://getgauge.io"

Search for Gauge Documentation
------------------------------

* Go to Gauge Get Started Page

Read more about Specifications
```

Specification dosyalarını çalıştırmak için:

```bash
mvn test
```

### Adım (Step) implementasyonları 

`java` tarafında her step için implementasyon eklenir. Selenium tabanlı projelerde Java implementasyonları Selenium API çağrılarını içerir.

Öneri: Testlerinizi Page Object Pattern ile modelleyin — WebDriver destek sınıflarını Page Object olarak organize etmek bakım ve okunabilirlik sağlar.

**Örnek Java sınıfı — `SampleTest.java`**

```java
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class SampleTest {

    @Step("Navigate to <url>")
    public void navigateTo(String url) {
        com.web.base.utils.driver.Driver.driver.get(url);
        assertTrue(com.web.base.utils.driver.Driver.driver.getTitle().contains("Gauge"));
    }

    @Step("Go to Gauge Get Started Page")
    public void gotoGetStartedPage() throws InterruptedException {
        WebElement getStartedButton = com.web.base.utils.driver.Driver.driver.findElement(By.xpath("//*[@id=\"content\"]/section[1]/section/div[2]/a[1]"));
        getStartedButton.click();
        Gauge.writeMessage("Page title is ", com.web.base.utils.driver.Driver.driver.getTitle());
    }
}
```

> Her Gauge step implementasyonu `@Step` annotasyonu ile işaretlenir. Annotasyon içinde step’in metin deseni (pattern) bulunur.

Daha fazla bilgi için Gauge Java Step Implementations dokümantasyonuna göz atın.

---

## Allure Raporu

Allure raporu oluşturmak için önce Gauge XML rapor eklentisini kurun, ardından Allure ile raporu üretip açın:

```bash
# Gauge xml-report eklentisini yükleyin
gauge install xml-report

# Testleri çalıştırdıktan sonra XML raporundan Allure raporu oluşturun
allure generate ./reports/xml-report -c -o ./allure-report

# Oluşturulan raporu açın
allure open ./allure-report
```

Not: `allure` komutlarının çalışması için Allure CLI yüklü olmalıdır. Alternatif olarak CI ortamlarında raporları saklamak için `allure serve` veya CI entegrasyon adımlarını kullanabilirsiniz.

---

## Dosya ve Dizin Yapısı (Örnek)

```
├── specs/
│   └── hello_world.spec
├── src/
│   └── test/
│       └── java/
│           └── SampleTest.java
├── pom.xml
└── README.md
```

---

## Katkıda Bulunma

Geri bildirim ve katkılar memnuniyetle karşılanır. Lütfen pull request veya issue açınız.

---



