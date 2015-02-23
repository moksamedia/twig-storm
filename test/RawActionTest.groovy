import com.cantgetnosleep.symfonyutils.JavascriptAction
import com.cantgetnosleep.symfonyutils.RawAction
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Created by cantgetnosleep on 2/23/15.
 */
class RawActionTest {

    @Test
    void testRawAction() {

        String html="""\
        <script>
			\$(document).ready(function(){
                \$(".changecolor").switchstylesheet( { seperator:"color"} );
            });
		</script>"""

        String expected="""\
        {% raw %}
        <script>
			\$(document).ready(function(){
                \$(".changecolor").switchstylesheet( { seperator:"color"} );
            });
		</script>
        {% endraw %}"""

        String result = RawAction.transform(html)

        assertEquals(expected,result)

    }
}
