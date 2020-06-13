/*
 * Copyright (C) 2020 the original author or authors. 
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

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;

/**
 * Test the Proxy Test Tool
 * 
 * @author Alexandre Oliveira
 * @since 1.0
 *
 */
public class ProxyTestTest {
	private static final String HOST = "38.91.100.122";
	private static final String PORT = "3128";
	private static final String TARGET = "https://www.terra.com.br";

	@Test
	public void proxyTestSSL() {
		try {
			String[] args = { "-h", HOST, "-p", PORT, "-t", TARGET };
			ProxyTest.main(args);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Test");
	}

	@Test
	public void proxyTestNoSSL() {
		try {
			String[] args = { "-h", HOST, "-p", PORT, "-t", TARGET, "--disableSSL" };
			ProxyTest.main(args);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Test");
	}

	@Test
	public void proxyHelp() {
		try {
			String[] args = { "--help" };
			ProxyTest.main(args);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Test");
	}
}