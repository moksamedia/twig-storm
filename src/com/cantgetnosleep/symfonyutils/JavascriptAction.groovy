package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

class JavascriptAction extends EditorAction {

    public JavascriptAction() {
        super(new PluginActionHandler(JavascriptAction))
    }

    public static String transform(String input) {

        String whitespace = input.find(/^\s*/)

        String acc = "{% javascripts\n"

        def lines = input.tokenize("\n")

        int i = 0;

        lines.each {
            it.find(Regex.HREF_REGEX) {
                if (i==lines.size() - 1) {
                    acc += "\t'${it[1]}' %}\n"
                }
                else {
                    acc += "\t'${it[1]}'\n"
                }
            }
            i++
        }

        acc += "\t" + '<script src="{{ asset_url }}"></script>' + "\n"
        acc += '{% endjavascripts %}' + "\n"

        acc

        String whitespaced = ""
        acc.eachLine {
            whitespaced += whitespace + it + "\n"
        }
        whitespaced
    }

}