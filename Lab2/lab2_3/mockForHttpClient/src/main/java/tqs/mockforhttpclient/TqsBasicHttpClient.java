package tqs.mockforhttpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * This class implements the ISimpleHttpClient interface and provides a real HTTP client
 * that performs GET requests to a remote API using Apache HttpClient.
 */
public class TqsBasicHttpClient implements ISimpleHttpClient {

    @Override
public String doHttpGet(String url) throws IOException {
    // Create a default CloseableHttpClient instance to send HTTP requests
    try (CloseableHttpClient client = HttpClients.createDefault()) {
        // Create an HTTP GET request for the given URL
        HttpGet request = new HttpGet(url);

        // Execute the request and get the response
        try (CloseableHttpResponse response = client.execute(request)) {
            // Retrieve the response entity (body of the response)
            HttpEntity entity = response.getEntity();

            // Convert the entity to a String and return it
            String jsonResponse = EntityUtils.toString(entity);
            System.out.println("API Response: " + jsonResponse);  // Adicionando log para depuração
            return jsonResponse;
        }
    }
}

}
