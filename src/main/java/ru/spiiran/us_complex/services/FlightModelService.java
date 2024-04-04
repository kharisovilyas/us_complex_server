package ru.spiiran.us_complex.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;

import java.io.*;
import java.net.Socket;

@Service
public class FlightModelService {
    @Value("${smao.api.host}") // Путь к директории для загружаемых изображений
    private String smaoHost;
    @Value("${smao.api.directory}") // Путь к директории для загружаемых изображений
    private String smaoDirectory;
    @Value("${smao.api.port}") // Путь к директории для загружаемых изображений
    private Integer smaoPort;
    @Value("${smao.api.url.modelling.start}") // Путь к директории для загружаемых изображений
    private String smaoStartModellingURL;
    public dtoMessage startModelling() {
        try {
            connectionToSMAO();
            return new dtoMessage("SUCCESS", "Connection established ");
        } catch (IOException e) {
            e.printStackTrace();
            return new dtoMessage("SUCCESS", "Connection not established ");
        }
    }

    private void connectionToSMAO() throws IOException {
        Socket socket = new Socket(smaoHost, smaoPort);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println("Data to send");
        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String response = in.readLine();
        System.out.println("Response from server: " + response);
        socket.close();
    }

    public ResponseEntity<dtoMessage> pauseModelling(){

        return null;
    }
}


