import com.cantgetnosleep.symfonyutils.ConditionalCommentsAction
import org.junit.Test

import java.util.regex.Matcher

import static org.junit.Assert.*

/**
 * Created by cantgetnosleep on 2/22/15.
 */
class ConditionalCommentsTest {

    String html = """<head>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<meta name="description" content="">
		<meta name="author" content="">

		<title>REEN</title>

		<!-- Bootstrap Core CSS -->
		<link href="assets/css/bootstrap.min.css" rel="stylesheet">

		<!-- Customizable CSS -->
		<link href="assets/css/main.css" rel="stylesheet" data-skrollr-stylesheet>
		<link href="assets/css/green.css" rel="stylesheet" title="Color">
		<link href="assets/css/owl.carousel.css" rel="stylesheet">
		<link href="assets/css/owl.transitions.css" rel="stylesheet">
		<link href="assets/css/animate.min.css" rel="stylesheet">

        {% raw %} <!--[if gt IE 4]>{% endraw %}
			<script src="assets/js/html5shiv.js"></script>
			<script src="assets/js/respond.min.js"></script>
		{% raw %}<![endif]-->{% endraw %}

		<!-- Fonts -->
		<link href="http://fonts.googleapis.com/css?family=Lato:400,900,300,700" rel="stylesheet">
		<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700,400italic,700italic" rel="stylesheet">

		<!--[if gt IE 9]>
			<script src="assets/js/html5shiv.js"></script>
			<script src="assets/js/respond.min.js"></script>
		<![endif]-->

		<!-- Icons/Glyphs -->
		<link href="assets/fonts/fontello.css" rel="stylesheet">

		<!-- Favicon -->
		<link rel="shortcut icon" href="assets/images/favicon.ico">

		<!-- HTML5 elements and media queries Support for IE8 : HTML5 shim and Respond.js -->
		<!--[if lt IE 9]>
			<script src="assets/js/html5shiv.js"></script>
			<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>"""


    @Test
    void testMatchCommentStart() {
        assertTrue("<!--[if lt IE 9]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertTrue("<!--[if IE 6]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertTrue("<!--[if lt IE 9]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertTrue("<!--[if IE 6]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("<!--[if lt IE 9]d>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("<--[if IE 6]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
    }


    @Test
    void testMatchCommentEnd() {

        assertTrue("<!-- <![endif]-->" ==~ ConditionalCommentsAction.REGEX_COMMENT_END)
        assertTrue("<![endif]-->" ==~ ConditionalCommentsAction.REGEX_COMMENT_END)
        assertFalse("<![dog]-->" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("<[endif]-->" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
    }

    @Test
    void testDontMatchAlreadyProcessed() {
        assertFalse("}<!--[if lt IE 9]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("}<!--[if IE 6]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("{% raw %} <!--[if lt IE 9]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("{% raw %}   <!--[if IE 6]>" ==~ ConditionalCommentsAction.REGEX_COMMENT_START)
        assertFalse("<![endif]-->{" ==~ ConditionalCommentsAction.REGEX_COMMENT_END)
        assertFalse("<-- <![endif]-->{" ==~ ConditionalCommentsAction.REGEX_COMMENT_END)
        assertFalse("<![endif]--> {% endraw %}" ==~ ConditionalCommentsAction.REGEX_COMMENT_END)
        assertFalse("<-- <![endif]-->    {% endraw %}" ==~ ConditionalCommentsAction.REGEX_COMMENT_END)
    }

    @Test
    void testMatchBlock() {

        Matcher matcher = html =~ ConditionalCommentsAction.REGEX_COMMENT_START

        assertEquals(matcher.count, 2)

        matcher.find(0)

        assertEquals(1160, matcher.start())
        assertEquals(1177, matcher.end())

        matcher.find(1177)

        assertEquals(1552, matcher.start())
        assertEquals(1569, matcher.end())

        def results = []
        html.findAll(ConditionalCommentsAction.REGEX_COMMENT_START) {
            results += it[0]
        }

        assertEquals(['<!--[if gt IE 9]>', '<!--[if lt IE 9]>'], results)


        matcher = html =~ ConditionalCommentsAction.REGEX_COMMENT_END

        assertEquals(matcher.count, 2)

        matcher.find(0)

        assertEquals(1282, matcher.start())
        assertEquals(1294, matcher.end())

        matcher.find(1294)

        assertEquals(matcher.start(), 1674)
        assertEquals(matcher.end(), 1686)

        results = []
        html.findAll(ConditionalCommentsAction.REGEX_COMMENT_END) {
            results += it[0]
        }

        assertEquals(['<![endif]-->', '<![endif]-->'], results)
    }


    @Test
    void testTransform() {

        String input = """<!--[if lt IE 9]>
        {% javascripts
            'bundles/reensite/js/html5shiv.js'
            'bundles/reensite/js/respond.min.js' %}
            <script src="{{ asset_url }}"></script>
        {% endjavascripts %}
        <![endif]-->"""

        String expected = """{% raw %}<!--[if lt IE 9]>{% endraw %}
        {% javascripts
            'bundles/reensite/js/html5shiv.js'
            'bundles/reensite/js/respond.min.js' %}
            <script src="{{ asset_url }}"></script>
        {% endjavascripts %}
        {% raw %}<![endif]-->{% endraw %}"""

        assertEquals(expected, ConditionalCommentsAction.transform(input))

    }
}
