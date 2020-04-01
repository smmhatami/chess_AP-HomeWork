package menues;

public class RegisterMenu extends Menu {

    public void processInputCommand(String command) {
        if (command.endsWith(" ") || command.startsWith(" ")) {
            System.out.println("invalid command");
            return;
        }
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("register") && splitCommand.length == 3) {
            if (User.isUserPassInvalid(splitCommand[1], splitCommand[2]))
                return;
            if (User.userExists(splitCommand[1])) {
                System.out.println("a user exists with this username");
                return;
            }
            processRegister(splitCommand[1], splitCommand[2]);
            System.out.println("register successful");
            return;
        }
        if (splitCommand[0].equals("login") && splitCommand.length == 3) {
            if (User.isUserPassInvalid(splitCommand[1], splitCommand[2]))
                return;
            if (!User.userExists(splitCommand[1])) {
                System.out.println("no user exists with this username");
                return;
            }
            processLogin(splitCommand[1], splitCommand[2]);
            return;
        }
        if (splitCommand[0].equals("remove") && splitCommand.length == 3) {
            if (User.isUserPassInvalid(splitCommand[1], splitCommand[2]))
                return;
            if (!User.userExists(splitCommand[1])) {
                System.out.println("no user exists with this username");
                return;
            }
            processRemove(splitCommand[1], splitCommand[2]);
            return;
        }
        if (splitCommand[0].equals("list_users") && splitCommand.length == 1) {
            User.printUsersList();
            return;
        }
        if (splitCommand[0].equals("help") && splitCommand.length == 1) {
            printHelp();
            return;
        }
        if (splitCommand[0].equals("exit") && splitCommand.length == 1) {
            System.out.println("program ended");
            Menu.setActiveMenu(null);
            return;
        }
        System.out.println("invalid command");
    }


    private void processRegister(String username, String password) {
        new User(username, password);
    }

    private void processLogin(String username, String password) {
        if (User.getUserByName(username).isPasswordWrong(password)) {
            System.out.println("incorrect password");
            return;
        }
        System.out.println("login successful");
        Menu.setActiveMenu(new UserMenu(User.getUserByName(username)));
    }

    private void processRemove(String username, String password) {
        if (User.getUserByName(username).isPasswordWrong(password)) {
            System.out.println("incorrect password");
            return;
        }
        System.out.println("removed " + username + " successfully");
        User.getAllUsers().remove(User.getUserByName(username));
    }

    private void printHelp() {
        System.out.println("register [username] [password]" + "\n" +
                "login [username] [password]" + "\n" +
                "remove [username] [password]" + "\n" +
                "list_users" + "\n" +
                "help" + "\n" +
                "exit");
    }

}
