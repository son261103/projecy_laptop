package org.example.dev_ban_hang.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class dateUtils {
//    public static String formatDate(Date date) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        return formatter.format(date);
//    }
    public static String formatDate(LocalDateTime date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
