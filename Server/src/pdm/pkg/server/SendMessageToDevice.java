package pdm.pkg.server;

import java.io.IOException;

import pdm.pkg.server.MessageUtil;

public class SendMessageToDevice {

	public static void main(String[] args) throws IOException {
		// "Message to your device." is the message we will send to the Android app
		int responseCode = MessageUtil.sendMessage(
				ServerConfiguration.AUTHENTICATION_TOKEN,
				ServerConfiguration.REGISTRATION_ID, "Message to your device.");
		System.out.println(responseCode);
	}
}