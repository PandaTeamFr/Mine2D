package celo.mine2d.utils;

import celo.mine2d.ui.GameFrame;

import java.io.File;

/**
 * Created by loucass003 on 28/12/2015.
 */
public class OSUtils {

    public static String GAME_FOLDER = "Mine2D";

    public static File getGameDir()
    {
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (OperatingSystem.getCurrentPlatform().ordinal())
        {
            case 0:
                workingDirectory = new File(userHome, "." + GAME_FOLDER);
                break;
            case 1:
                String applicationData = System.getenv("APPDATA");
                String folder = applicationData != null ? applicationData : userHome;

                workingDirectory = new File(folder, "." + GAME_FOLDER);
                break;
            case 2:
                workingDirectory = new File(userHome, "Library/Application Support/" + GAME_FOLDER);
                break;
            default:
                workingDirectory = new File(userHome, "." + GAME_FOLDER);
        }
        return workingDirectory;
    }

}
