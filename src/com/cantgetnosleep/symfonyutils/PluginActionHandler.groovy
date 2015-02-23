package com.cantgetnosleep.symfonyutils

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler

/**
 * Created by cantgetnosleep on 2/22/15.
 */
class PluginActionHandler extends EditorWriteActionHandler {

    Class clazz;

    public PluginActionHandler(Class clazz) {
        this.clazz = clazz;
    }

    public void executeWriteAction(Editor editor, DataContext dataContext) {

        final SelectionModel selectionModel = editor.getSelectionModel();

        String selectedText = selectionModel.getSelectedText();

        if (selectedText == null) {
            return
        }

        String result = clazz.transform(selectedText.trim())

        editor.getDocument().replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), result);

    }

}
