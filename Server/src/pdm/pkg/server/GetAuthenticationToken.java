package pdm.pkg.server;

import java.io.IOException;

import pdm.pkg.server.SecureStorage;
import pdm.pkg.server.AuthenticationUtil;

public class GetAuthenticationToken {

	public static void main(String[] args) throws IOException {
		String token = AuthenticationUtil.getToken(SecureStorage.USER,
				SecureStorage.PASSWORD);
		System.out.println(token);
	}
}