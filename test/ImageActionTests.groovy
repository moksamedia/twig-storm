import com.cantgetnosleep.symfonyutils.ImageAction
import org.junit.Test
import static org.junit.Assert.*


/**
 * Created by cantgetnosleep on 2/17/15.
 */

class ImageActionTests {

    @Test
    void testGetAllAttributes() {

        String img = "<img src=\"assets/images/art/human06.jpg\" class=\"img-circle\" data-some-data>"
        String attrs = ImageAction.getAllImageAttributesExceptSrc(img)
        assertEquals('class="img-circle" data-some-data', attrs)
    }

    @Test
    void testGetAllAttributesDict() {

        String img = "<img src=\"assets/images/art/human06.jpg\" class=\"img-circle\" data-some-data data-columns=\"3\" id=\"dude\">"

        def attrs = ImageAction.getAllTagAttributes(img)

        def expected = ['src':'assets/images/art/human06.jpg', 'class':'img-circle', 'data-columns':'3', 'id':'dude', 'data-some-data':null]

        assertEquals(expected.size(), attrs.size())

        expected.each { k, v ->
            assertEquals(v, attrs[(k)])
        }

    }


    @Test
    void testGetAllAttributesAsStringExceptRef() {

        String img = "<img src=\"assets/images/art/human06.jpg\" class=\"img-circle\" data-some-data data-columns=\"3\" id=\"dude\">"

        String attrs = ImageAction.getAllTagAttributesAsStringExceptSrc(img)

        assertEquals('class=\'img-circle\' data-columns=\'3\' id=\'dude\' data-some-data', attrs)

    }

    @Test
    void testCodeBlock() {
        String html = """<div class="col-sm-6 inner-top-sm">
\t\t\t\t<figure>

\t\t\t\t\t<div class="icon-overlay icn-link">
\t\t\t\t\t\t<a href="portfolio-post.html"><img src="images/art/work02.jpg" alt=""></a>
\t\t\t\t\t</div><!-- /.icon-overlay -->

\t\t\t\t\t<figcaption class="bordered no-top-border">
\t\t\t\t\t\t<div class="info">
\t\t\t\t\t\t\t<h3><a href="portfolio-post.html">Grand Motel gets an identity brushup</a></h3>
\t\t\t\t\t\t\t<p>Identity</p>
\t\t\t\t\t\t</div><!-- /.info -->
\t\t\t\t\t</figcaption>

\t\t\t\t</figure>
\t\t\t</div><!-- /.col -->"""

        String result = ImageAction.transform(html);

        String expected = """<div class="col-sm-6 inner-top-sm">
\t\t\t\t<figure>

\t\t\t\t\t<div class="icon-overlay icn-link">
\t\t\t\t\t\t<a href="portfolio-post.html">{% image 'images/art/work02.jpg' %}<img src="{{ asset_url }}" alt=''>{% endimage %}</a>
\t\t\t\t\t</div><!-- /.icon-overlay -->

\t\t\t\t\t<figcaption class="bordered no-top-border">
\t\t\t\t\t\t<div class="info">
\t\t\t\t\t\t\t<h3><a href="portfolio-post.html">Grand Motel gets an identity brushup</a></h3>
\t\t\t\t\t\t\t<p>Identity</p>
\t\t\t\t\t\t</div><!-- /.info -->
\t\t\t\t\t</figcaption>

\t\t\t\t</figure>
\t\t\t</div><!-- /.col -->"""

        assertEquals(expected, result)

    }

}

