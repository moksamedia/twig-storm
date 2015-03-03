package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

class AssetAction extends EditorAction {


    public AssetAction() {
        super(new PluginActionHandler(AssetAction))
    }

    public static String transform(String input) {

        input.replaceAll(Regex.HREF_REGEX) {
            "href=\"${processAsset(it[1])}\""
        }.replaceAll(Regex.SRC_REGEX) {
            "src=\"${processAsset(it[1])}\""
        }.replaceAll(Regex.CSS_URL_REGEX) {
            "url(${processAsset(it[1])})"
        }
    }

    public static def processAsset(String input) {

        input = input.trim()

        if (input.contains("{{")) {
            input
        }
        else if (input.length() == 0) {
            input
        }
        else if (input[0] == "#") {
            input
        }
        else {
            "{{ asset('${input}') }}"
        }
    }
}