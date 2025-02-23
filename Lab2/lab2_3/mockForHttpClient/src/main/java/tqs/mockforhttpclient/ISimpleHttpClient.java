package tqs.mockforhttpclient;

import java.io.IOException;


public interface ISimpleHttpClient {
    
    public String doHttpGet(String url) throws IOException;
}
