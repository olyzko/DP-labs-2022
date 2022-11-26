package TaskA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private DatabaseFile database;
    private static final String file = "src/main/java/TaskA/test.txt";
    private Controller readWriteLock = new Controller();

    public DatabaseController(DatabaseFile database) {
        this.database = database;
    }

    public void getPhoneNumbers(String username) {
        try {
            readWriteLock.readLock();
            List<String> res = new ArrayList<>();
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line = fileReader.readLine();
            while (line != null) {
                if (line.substring(0,1).equals(username))
                    res.add(line.substring(2));
                line = fileReader.readLine();
            }
            System.out.println("Found Phone Number: " + res + " for user: " + username);
            return;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readUnlock();
        }
        System.out.println("Found no phone number for: " + username);
    }

    public void getUsername(String phoneNumber) {
        try {
            readWriteLock.readLock();
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line = fileReader.readLine();
            while (line != null) {
                if (line.substring(2).equals(phoneNumber)) {
                    String user = line.substring(0,1);
                    System.out.println("Found Username: " + user + " by phone: " + phoneNumber);
                    return;
                }
                line = fileReader.readLine();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readUnlock();
        }
        System.out.println("Found no Username with phone: " + phoneNumber);
    }


    public void addRecord(String username, String phoneNumber) {
        PrintWriter pw = null;
        try {
            readWriteLock.writeLock();
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(username + " " + phoneNumber);
            System.out.println("Adding: " + username + " " + phoneNumber);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            assert pw != null;
            pw.close();
            readWriteLock.writeUnlock();
        }
    }

    public void deleteRecord(String username, String phoneNumber) {
        try {
            readWriteLock.writeLock();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String curr;
            String remove = username + " " + phoneNumber;
            int cnt = 0;
            while ((curr = reader.readLine()) != null) {
                String trimmedLine = curr.trim();
                if (trimmedLine.equals(remove)) break;
                cnt++;
            }
            reader.close();
            if (curr != null) {
                System.out.println("Removing: " + username + " " + phoneNumber);
                database.removeLines(cnt, 1);
            } else System.out.println("Found no user: " + username + " " + phoneNumber);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeUnlock();
        }
    }

}
