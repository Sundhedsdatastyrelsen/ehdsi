package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FilterInputStream;
import java.io.IOException;

@Slf4j
public class FtpConnection implements AutoCloseable {
    private final FTPClient ftpClient;

    FtpConnection(ServerInfo conn) throws IOException {
        ftpClient = new FTPClient();
        ftpClient.connect(conn.host(), conn.port());
        if (!ftpClient.login(conn.user(), conn.password())) {
            throw new IOException("Error: Unable to log in to FTP server. Check credentials.");
        }
        // Use local passive mode to avoid firewall issues
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
    }

    public RawDataProvider rawDataProvider() {
        return table -> new FilterInputStream(ftpClient.retrieveFileStream(table.ftpPath())) {
            @Override
            public void close() throws IOException {
                super.close();
                if (!ftpClient.completePendingCommand()) {
                    throw new IOException("Error: Failed to complete pending command after file retrieval.");
                }
            }
        };
    }

    @Override
    public void close() {
        try {
            ftpClient.logout();
        } catch (IOException e) {
            // ignore
        }
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            log.error("IOException while disconnecting from FTP server", e);
        }
    }

    public record ServerInfo(
        String host,
        int port,
        String user,
        String password
    ) {
    }
}
