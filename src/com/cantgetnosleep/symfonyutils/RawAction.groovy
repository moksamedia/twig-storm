package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

class RawAction extends EditorAction {

    public RawAction() {
        super(new PluginActionHandler(RawAction))
    }

    public static String transform(String input) {
        String whitespace = input.find(/^\s*/)
        "${whitespace}{% raw %}\n$input\n${whitespace}{% endraw %}"
    }

}
