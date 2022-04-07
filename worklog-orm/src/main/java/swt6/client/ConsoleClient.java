package swt6.client;

import swt6.orm.dao.SprintDAOImpl;
import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Sprint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsoleClient {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    private static SprintDAO sprintDAO = new SprintDAOImpl();

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    static Sprint readSprintFromCommandLine() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        LocalDate startDate = LocalDate.parse(promptFor(in, "start date (dd.mm.yyyy)"), formatter);
        LocalDate endDate = LocalDate.parse(promptFor(in, "end date (dd.mm.yyyy)"), formatter);
        Sprint sprint = new Sprint();
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);
        return sprint;
//        String availCmds = "Link sprint to project:\n" +
//                "y (yes)\n" +
//                "n (no)\n";
//
//        String userCmd = promptFor(in, availCmds);
//
//        try {
//            switch (userCmd) {
//                case "y":
//
//                    break;
//                default:
//                    break;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "Type following commands:\n" +
                "  - s (to insert sprint)\n" +
                "  - b (to insert backlog)\n" +
                "  - u (to insert userstory\n" +
                "  - i (to insert issue)\n" +
                "  - f (to insert feature\n" +
                "  - stats (to query statistics\n" +
                "  - q (to leave session)";

        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        try {

            while (!userCmd.equals("q")) {

                switch (userCmd) {
                    case "s":
                        Sprint sprint = readSprintFromCommandLine();
                        sprintDAO.insert(sprint);
                        System.out.println("Inserted:\n" + sprint);
                        break;
                    case "b":
                        break;

                    case "u":
                        break;
                    case "i":
                        break;
                    case "f":
                        break;
                    case "stats":
                        //TODO implement statistics
                        break;
                    default:
                        System.out.println("ERROR: invalid command");
                        break;
                }
                System.out.println(availCmds);
                userCmd = promptFor(in, "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // TODO: HibernateUtil.closeSessionFactory();
        }
    }
}
