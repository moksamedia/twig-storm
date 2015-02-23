package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

class StylesheetAction extends EditorAction {

    public StylesheetAction() {
        super(new MultiLineActionHandler(StylesheetAction))
    }

    public static String transform(String input) {

        String acc = "{% stylesheets\n"

        def lines = input.tokenize("\n")

        int i = 0;

        lines.each {
            it.find(Regex.HREF_REGEX) {
                if (i==lines.size() - 1) {
                    acc += "\t'${it[1]}' filter='cssrewrite' %}\n"
                }
                else {
                    acc += "\t'${it[1]}'\n"
                }
            }
            i++
        }

        acc += "\t" + '<link rel="stylesheet" src="{{ asset_url }}" />' + "\n"
        acc += '{% endstylesheets %}' + "\n"

        acc
    }

}
