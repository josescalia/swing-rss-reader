package org.josescalia.swingrss.util;

import javax.swing.*;

/**
 * Created by josescalia on 31/01/15.
 */
public class TestLF {

    public static void main(String[] args) {
        for(UIManager.LookAndFeelInfo lfInfo : UIManager.getInstalledLookAndFeels()){
            System.out.println(lfInfo.getName());
            System.out.println(lfInfo.getClassName());
        }
    }
}
