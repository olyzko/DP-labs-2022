package TaskA;

import java.io.*;

public class DatabaseFile {
    private String file;
    DatabaseFile(String databaseName){
        this.file = databaseName;
    }

    public void removeLines( int startLine, int numLines) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder("");
            int linenumber = 0;
            String line;

            while ((line = br.readLine()) != null) {
                if (linenumber < startLine || linenumber >= startLine + numLines)
                    sb.append(line).append("\n");
                linenumber++;
            }
            if (startLine + numLines > linenumber)
                System.out.println("End of file.");
            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
