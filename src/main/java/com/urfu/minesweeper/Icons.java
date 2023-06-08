package com.urfu.minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Icons {
    private static Map<String, ImageIcon> tileIcons;

    static {
        setTileIcons();
    }
    private static void setTileIcons() {
        Map<String, ImageIcon> tileIcons = new HashMap<>();
        String folderName = "tileIcons";
        InputStream folderStream = Icons.class.getClassLoader().getResourceAsStream(folderName);
        if (folderStream == null) {
            Icons.tileIcons = null;
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(folderStream));
        List<String> fileNames = reader.lines().toList();
        for (String fileName : fileNames) {
            InputStream iconStream = Icons.class.getClassLoader().getResourceAsStream(folderName + "/" + fileName);
            if (iconStream == null) {
                Icons.tileIcons = null;
                return;
            }
            try {
                tileIcons.put(fileName.split("\\.")[0], new ImageIcon(ImageIO.read(iconStream)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Icons.tileIcons = tileIcons;
    }

    public static ImageIcon get(String iconName) {
        return tileIcons.get(iconName);
    }
}
