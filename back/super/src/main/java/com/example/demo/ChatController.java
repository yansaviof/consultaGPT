package com.example.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class ChatController {

    @PostMapping("/chat")
    public String chat(@RequestBody String pergunta) throws Exception {
        String accessToken = "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";
        String url = "https://api.openai.com/v1/chat/completions";

        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", pergunta);

        json.append("messages", message);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        // assume que o JSON está armazenado em uma string chamada jsonString
        JSONObject json2 = new JSONObject(response.body());

        // obtém a lista de objetos "choices"
        JSONArray choicesArray = json2.getJSONArray("choices");

        // percorre a lista de objetos "choices" e extrai as informações relevantes
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < choicesArray.length(); i++) {
            JSONObject choiceObject = choicesArray.getJSONObject(i);
            JSONObject messageObject = choiceObject.getJSONObject("message");
            String messageContent = messageObject.getString("content");
            messages.add(messageContent);
        }

        // agora, a lista "messages" contém o conteúdo de todas as mensagens na lista de objetos "choices"
        // converte a lista em uma string separada por vírgulas e salva na variável "choicesString"
        String choicesString = String.join(",", messages);
        System.out.println(choicesString);
        System.out.println("-------------------------------");
        return choicesString;
    }

}