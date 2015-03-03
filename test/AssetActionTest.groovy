import com.cantgetnosleep.symfonyutils.AssetAction
import org.junit.Test
import static org.junit.Assert.assertEquals

/**
 * Created by cantgetnosleep on 3/2/15.
 */
class AssetActionTest {

    @Test
    void testAction() {

        String html = """<li><a href="portfolio.html">3 Columns Grid</a></li><li><a href="portfolio2.html">3 Columns Details Grid</a></li><img class="logo img-intext" src="assets/images/logo-white.svg" alt="">"""
        String result = AssetAction.transform(html)
        String expected = """<li><a href="{{ asset('portfolio.html') }}">3 Columns Grid</a></li><li><a href="{{ asset('portfolio2.html') }}">3 Columns Details Grid</a></li><img class="logo img-intext" src="{{ asset('assets/images/logo-white.svg') }}" alt="">"""

        assertEquals(expected, result)

    }

    @Test
    void testUrlAction() {

        String html = """<div class="item" style="background-image: url(images/art/slider05.jpg);">"""

        String result = AssetAction.transform(html)
        String expected = """<div class="item" style="background-image: url({{ asset('images/art/slider05.jpg') }});">"""

        assertEquals(expected, result)

    }

    @Test
    void testSkipAnchors() {

        String html = """<a href="#portfolio">3 Columns Grid</a>"""
        String expected = """<a href="#portfolio">3 Columns Grid</a>"""
        String result = AssetAction.transform(html)

        assertEquals(expected, result)

        html = """<a href="#">3 Columns Grid</a>"""
        expected = """<a href="#">3 Columns Grid</a>"""
        result = AssetAction.transform(html)

        assertEquals(expected, result)

        html = """<a href=" #">3 Columns Grid</a>"""
        expected = """<a href="#">3 Columns Grid</a>"""
        result = AssetAction.transform(html)

        assertEquals(expected, result)

        html = """<a href="portfolio.html#some-anchor">3 Columns Grid</a>"""
        expected = """<a href="{{ asset('portfolio.html#some-anchor') }}">3 Columns Grid</a>"""
        result = AssetAction.transform(html)

        assertEquals(expected, result)
    }

}
