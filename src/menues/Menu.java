package menues;

public class Menu {
    protected static Menu activeMenu;

    public static void setActiveMenu(Menu activeMenu) {
        Menu.activeMenu = activeMenu;
    }

    public static Menu getActiveMenu() {
        return activeMenu;
    }

    public void processInputCommand(String input) {
    }
}
