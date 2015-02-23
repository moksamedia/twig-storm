import com.cantgetnosleep.symfonyutils.JavascriptAction
import org.junit.Test
import static org.junit.Assert.assertEquals

/**
 * Created by cantgetnosleep on 2/23/15.
 */
class JavascriptActionTest {

    @Test
    void testJavascriptBlock() {
        String html="\t<link href=\"http://fonts.googleapis.com/css?family=Lato:400,900,300,700\" rel=\"stylesheet\">\n\t<link href=\"http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700,400italic,700italic\" rel=\"stylesheet\">\n"

        String expected="\t{% javascripts\n\t\t'http://fonts.googleapis.com/css?family=Lato:400,900,300,700'\n\t\t'http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700,400italic,700italic' %}\n\t\t<script src=\"{{ asset_url }}\"></script>\n\t{% endjavascripts %}\n"

        String result = JavascriptAction.transform(html)

        assertEquals(expected,result)
    }
}
