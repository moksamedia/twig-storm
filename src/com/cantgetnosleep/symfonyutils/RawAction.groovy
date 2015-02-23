package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

class RawAction extends EditorAction {

    public RawAction() {
        super(new MultiLineActionHandler(RawAction))
    }

    public static String transform(String input, String whitespace = '') {
        "$whitespace{% raw %}\n$input\n{% endraw %}"
    }

}
