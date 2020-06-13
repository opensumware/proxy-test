package br.com.sumware.proxy;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;

public class ProxyTestTest {
    @Test
	public void proxyTest() {
		try {
			String[] args = {"-h", "38.91.100.122", "-p", "3128", "-t", "https://www.terra.com.br"};
			ProxyTest.main(args);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Test");
	}
}