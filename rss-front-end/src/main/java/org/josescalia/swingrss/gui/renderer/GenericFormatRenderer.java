package org.josescalia.swingrss.gui.renderer;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DateFormat;
import java.text.Format;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 11/16/12
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenericFormatRenderer extends DefaultTableCellRenderer {
    private Format formatter;

    /*
    *   Use the specified formatter to format the Object
    */
    public GenericFormatRenderer(Format formatter) {
        this.formatter = formatter;
    }

    public void setValue(Object value) {
        //  Format the Object before setting its value in the renderer

        try {
            if (value != null)
                value = formatter.format(value);
        } catch (IllegalArgumentException e) {
        }

        super.setValue(value);
    }

    /*
      *  Use the default date/time formatter for the default locale
      */
    public static GenericFormatRenderer getDateTimeRenderer() {
        return new GenericFormatRenderer(DateFormat.getDateTimeInstance());
    }

    /*
      *  Use the default time formatter for the default locale
      */
    public static GenericFormatRenderer getTimeRenderer() {
        return new GenericFormatRenderer(DateFormat.getTimeInstance());
    }

}
