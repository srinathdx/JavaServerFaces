package com.example.jsfclient.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@ManagedBean
public class CsvDownloadBean {

    private static final String CSV_URL = "http://localhost:8080/api/csv/download";

    public void downloadCsv() {
        try {
            // Open connection to the Spring Boot backend
            URL url = new URL(CSV_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                // Set response headers
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");

                // Copy data from the backend to the client's output stream
                try (InputStream inputStream = connection.getInputStream();
                     OutputStream outputStream = response.getOutputStream()) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }

                facesContext.responseComplete();
            } else {
                System.out.println("Error: Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
