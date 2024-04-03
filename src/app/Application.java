package app;

import views.MainView;

public class Application {
    private static Application instance; // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public InterfaceDB DB = new DB();
    public DataAccess dataAdapter = new MySQLDataAdapter();
    public MainView mainView = new MainView();

    public static void main(String[] args) {
        Application.getInstance().DB.menu();
    }
}
