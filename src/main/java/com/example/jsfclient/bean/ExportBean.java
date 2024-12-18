package com.example.jsfclient.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@ManagedBean
public class ExportBean {

    public void downloadDataAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("http://localhost:8081/api/csv/trigger");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                FacesContext context = FacesContext.getCurrentInstance();
                
                // Prepare the response for file download
                context.getExternalContext().setResponseContentType("application/octet-stream");
                context.getExternalContext().setResponseHeader("Content-Disposition", "attachment;filename=data.txt");
                
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    context.getExternalContext().getResponseOutputStream().write(buffer, 0, bytesRead);
                }

                context.responseComplete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}