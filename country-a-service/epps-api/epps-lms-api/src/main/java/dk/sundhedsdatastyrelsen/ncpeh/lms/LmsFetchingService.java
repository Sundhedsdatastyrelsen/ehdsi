package dk.sundhedsdatastyrelsen.ncpeh.lms;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Service
public class LmsFetchingService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LmsFetchingService.class);
    @Value("${lmsftp.server}")
    private String server;

    @Value("${lmsftp.port}")
    private int port;

    @Value("${lmsftp.username}")
    private String username;

    @Value("${lmsftp.password}")
    private String password;

    public String getLmsDataFromServer(String lmsFile) {
        try {
            return downloadTextFileInMemory(server, port, username, password, String.format("/LMS/NYESTE/%s", lmsFile));

        } catch (IOException e) {
            throw new RuntimeException("Failed downloading new data from LMS-Medicinprinser");
        }
    }

    /**
     * Downloads a text file from an FTP server as a String.
     * Co-Authored by Chad
     */
    private String downloadTextFileInMemory(
        String server, int port,
        String user, String pass,
        String remoteFilePath
    ) throws IOException {

        @SuppressWarnings("squid:S5332") // Does not support SFTP
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            boolean loggedIn = ftpClient.login(user, pass);
            if (!loggedIn) {
                throw new IOException("Error: Unable to log in to FTP server. Check credentials.");
            }

            // Use local passive mode to avoid firewall issues
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(ftpClient.retrieveFileStream(remoteFilePath), Charset.forName("cp850")))) {

                if (reader == null) {
                    throw new IOException("Error: Could not open remote file stream. Check the file path or permissions.");
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                // Important
                boolean completed = ftpClient.completePendingCommand();
                if (!completed) {
                    throw new IOException("Error: Failed to complete pending command after file retrieval.");
                }

                return sb.toString();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException ex) {
                    log.error("Error: Failed to logout of FTP client.");
                }
                ftpClient.disconnect();
            }
        }
        return null;
    }
}
