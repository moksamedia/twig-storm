package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.editor.actionSystem.EditorAction

/**
 * Created by cantgetnosleep on 2/22/15.
 */
class ConditionalCommentsAction extends EditorAction {

    public static REGEX_COMMENT_START = /(?<!(\}\s{0,5}))(<!--\[[^\]]*]>)/
    // Matches <!--<![endif]--> AND <![endif]-->
    public static REGEX_COMMENT_END = /(<!--(\s)*)*<!\[endif\]-->(?!(\s{0,5}\{)+)/

    public ConditionalCommentsAction() {
        super(new MultiLineSelectActionHandler(ConditionalCommentsAction))
    }

    public static String transform(String input) {
        input.replaceAll(REGEX_COMMENT_START) {
            "{% raw %}${it[0]}{% endraw %}"
        }.replaceAll(REGEX_COMMENT_END) {
            "{% raw %}${it[0]}{% endraw %}"
        }
    }
}
