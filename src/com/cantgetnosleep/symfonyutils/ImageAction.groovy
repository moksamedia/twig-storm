package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

class ImageAction extends EditorAction {


    public ImageAction() {
        super(new MultiLineSelectActionHandler(ImageAction))
    }

    public static String transform(String input) {

        input.replaceAll(Regex.IMG_TAG_REGEX) {
            processImage(it[0])
        }
    }

    public static def processImage(String input) {
        String acc = ""

        input.find(Regex.SRC_REGEX) {
            acc += "{% image '${it[1]}' %}"
        }

        String attrs = getAllTagAttributesAsStringExceptSrc(input)

        acc += "<img src=\"{{ asset_url }}\" $attrs>"
        acc += '{% endimage %}'

        acc
    }

    public static def getAllTagAttributesAsStringExceptSrc(String tag) {

        def attributes = getAllTagAttributes(tag)

        attributes.remove("src")
        attributes.remove("SRC")

        String result = ""

        attributes.each { k, v ->
            if (v != null) {
                result += "$k='$v' "
            }
            else {
                result += "$k "
            }
        }

        result.trim()
    }

    public static def getAllTagAttributes(String tag) {

        String regex = Regex.TAG_ATTRIBUTE_REGEX

        /*
            This reges is a little janky, but to catch all data-* attributes AND
            be able to discard the data attributes with values, we capture the
            equals sign and check for it in the subsequent loop, so that we can
            discard any data attriubtes with values that were captured by the
            initial regex
         */
        String dataRegex = Regex.DATA_ATTRIBUTE_REGEX

        def attributes = [:]

        tag.findAll(regex) {
            attributes[(it[1].toLowerCase())] = it[3]
        }

        tag.findAll(dataRegex) {
            if (!it[1].contains('=')) {
                attributes[(it[1].toLowerCase())] = null
            }
        }

        return attributes
    }


    public static String getAllImageAttributesExceptSrc(String img) {

        String attributes = ""

        img.find(Regex.IMG_ATTRIBUTES) {
            attributes = it[1]
        }

        def attrs = attributes.split()

        def result = ""

        attrs.each {
            if (it =~ /src=/) {

            }
            else {
                result += it + " "
            }
        }

        result.trim()
    }
}