import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.Cookie.cookie;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;
import static org.mockserver.model.Parameter.param;

public class MockServer {
    private ClientAndServer mockServer;

    @Before
    public void startMockServer() {
        mockServer = startClientAndServer(9000);
    }

    public void apicall() {
        new MockServerClient("localhost", 9000)
                .when(
                        HttpRequest.request()
                                .withMethod("GET")
                                .withPath("/getIncentiveDetails")
//                                .withBody("{username: 'foo', password: 'bar'}")
                )
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody("Mock Response")
                );
    }

    @Test
    public void test() throws IOException {
        apicall();

        URL url = new URL("http://localhost:9000/getIncentiveDetails");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        System.out.println(con.getResponseCode());

    }


    @After
    public void stopMockServer() {
        mockServer.stop();
    }
}
