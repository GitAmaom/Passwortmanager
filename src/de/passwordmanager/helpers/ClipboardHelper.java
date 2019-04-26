package de.passwordmanager.helpers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;

/**
 * Class for loading saving Strings to the clipboard.
 * @author Moritz Schneider
 *
 */
public class ClipboardHelper {

    /**
     * Gets the text stored in the clipboard.
     * @return a String containing the text stored in the clipboard.
     */
    public static String getClipboard() {
        return (String) Clipboard.getSystemClipboard().getContent(DataFormat.PLAIN_TEXT);
    }
    
    /**
     * Saves a string in the clipboard.
     * @param str the string that gets saved.
     */
    public static void setClipboard(String str) {
        Map<DataFormat, Object> map = new HashMap<DataFormat, Object>();
        map.put(DataFormat.PLAIN_TEXT, str);
        Clipboard.getSystemClipboard().setContent(map);
    }
}
