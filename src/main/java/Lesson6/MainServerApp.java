package Lesson6;

import Lesson3.serverSide.service.MyServer;
import Lesson6.serverSide.service.BaseAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainServerApp {
    private static final Logger logger = LogManager.getLogger(MainServerApp.class);
    public static void main(String[] args) {
        BaseAuthService.logPrintStart();
        new MyServer();
    }
}
