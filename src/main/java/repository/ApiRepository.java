package repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ApiRepository<T> {
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();
    private Class<T> clazz;
    public ApiRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> getList(URI uri) throws IOException, InterruptedException {
        final HttpResponse<String> response = getStringHttpResponse(uri);
        if (response.statusCode() != 404) {
            return mapper.readValue(response.body(), mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }
        return Collections.emptyList();
    }

    public Optional<T> getObject(URI uri) throws IOException, InterruptedException {
        final HttpResponse<String> response = getStringHttpResponse(uri);
        if (response.statusCode() == 200 || response.statusCode() == 304) {
            return Optional.of(mapper.readValue(response.body(), clazz));
        }
        return Optional.empty();
    }

    private HttpResponse<String> getStringHttpResponse(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}