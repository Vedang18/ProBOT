import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Abhisheks on 16-Sep-17.
 */
public class HtmlUnitTests {
    @Test
    public void bookRoom() throws Exception {
        try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52)) {

            //set proxy username and password
            final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addNTLMCredentials("abhisheks", "abhiProJul17", "apps.prorigo.com", 80, "", "");

            final HtmlPage page = webClient.getPage("http://apps.prorigo.com/conference/Booking");

            final HtmlForm form = page.getForms().get(0);
            final HtmlButton button = form.getFirstByXPath("//*[@id=\"Submit\"]");

            HtmlSelect select = (HtmlSelect) page.getElementById("ConferenceRooms");
            HtmlOption option = select.getOptionByValue("dedfcc1d-d413-e311-9c19-0025648b200e");
            select.setSelectedAttribute(option, true);

            HtmlInput inputStartTime = form.getInputByName("StartTime");
            inputStartTime.setValueAttribute("11:00 PM");

            HtmlInput inputEndTime = form.getInputByName("EndTime");
            inputEndTime.setValueAttribute("11:30 PM");

            HtmlInput inputReason = form.getInputByName("Title");
            inputReason.type("Test Booking By HtmlUnit");

            final HtmlPage page2 = button.click();
        }
    }
}
