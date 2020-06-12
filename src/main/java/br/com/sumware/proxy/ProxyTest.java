/*
 * Copyright (C) 2016-2020 the original author or authors. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package br.com.sumware.proxy;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;

/**
 * Proxy Test
 * 
 * @author Alexandre Oliveira
 * @since 1.0
 *
 */
public class ProxyTest {
    private static final Log logger = LogFactory.getLog(ProxyTest.class);
    @Parameter(names = { "--disableSSL" }, description = "Disable SSL Verification", required = false)
    private static boolean disableSSL = true;

    @Parameter(names = { "-u" }, description = "Proxy Username", required = false)
    private static String proxyUsername;

    @Parameter(names = { "-P" }, description = "Proxy Password", required = false)
    private static String proxyPassword;

    @Parameter(names = { "-h" }, description = "Proxy Host", required = true)
    private  String proxyHost;

    @Parameter(names = { "-p" }, description = "Proxy Port", required = true)
    private String proxyPort = "3128";

    @Parameter(names = { "-t" }, description = "Target URL to test", required = true)
    private String url = null;

    @Parameter(names = { "--user-agent" }, description = "User Agent", required = false)
    private static final String USER_AGENT = "Mozilla/5.0";

    @Parameter(names = "--help", description = "Print usage instructions", help = true)
	private boolean help = false;

    public static void main(String... argv) throws ClientProtocolException, IOException {
		ProxyTest main = new ProxyTest();
		JCommander jCommander = new JCommander();
		jCommander.addObject(main);

		try {
			jCommander.parse(argv);
			if (main.help) {
				jCommander.usage();
				return;
			}

			System.out.println("Proxy Test Tool.");

			main.run();
		} catch (ParameterException e) {
			logger.info("Error: " + e.getLocalizedMessage());
			jCommander.usage();
		}

	}

    private void run() throws ClientProtocolException, IOException {
        HttpClientBuilder httpClient = HttpClientBuilder.create();
        if (StringUtils.isNotEmpty(proxyHost) && StringUtils.isNotEmpty(proxyPort)) {
            HttpHost httpHost = new HttpHost(proxyHost, Integer.parseInt(proxyPort), "http");
            httpClient.setProxy(httpHost);
            if (StringUtils.isNotEmpty(proxyUsername) && StringUtils.isNotEmpty(proxyPassword)) {
                BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword);
                AuthScope authscope = new AuthScope(proxyHost, Integer.parseInt(proxyPort));
                basicCredentialsProvider.setCredentials(authscope, (Credentials) credentials);
                httpClient.setDefaultCredentialsProvider((CredentialsProvider) basicCredentialsProvider);
                httpClient.setProxyAuthenticationStrategy((AuthenticationStrategy) new ProxyAuthenticationStrategy());
            }
        }
        logger.info("HttpClient SSL validation [security.connection.disablessl=" + disableSSL + "]");
        if (disableSSL)
            httpClient.setSSLHostnameVerifier((HostnameVerifier) NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient closeableHttpClient = httpClient.build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);

        System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
        httpResponse.close();
    }
}
