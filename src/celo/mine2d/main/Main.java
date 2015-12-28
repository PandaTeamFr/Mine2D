package celo.mine2d.main;

import celo.mine2d.ui.GameFrame;
import celo.mine2d.utils.LogFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created by loucass003 on 21/12/2015.
 */
public class Main {

    public static String GAME_NAME = "Mine2D";

    public GameFrame frame;
    public boolean debug = true;
    public Logger logger;
    public static Main instance;

    public Main() {
        this.instance = this;
        this.frame = new GameFrame(this);
    }


    public void init() {
        logger = Logger.getLogger(GAME_NAME);
        logger.setUseParentHandlers(false);

        LogFormatter formatter = new LogFormatter();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);

        logger.addHandler(handler);
        initGame();
    }

    public void initGame() {
        this.frame.init();
    }

    public static void main(String[] args) {
        new Main().init();
    }

    public Logger getLogger() {
        return logger;
    }

    public GameFrame getFrame() {
        return frame;
    }

    public static String getGameName() {
        return GAME_NAME;
    }

    public static Main getInstance() {
        return instance;
    }
}
