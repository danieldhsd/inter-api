package com.danieldhsd.interapi.service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danieldhsd.interapi.exception.UsuarioJaExistenteException;
import com.danieldhsd.interapi.model.Usuario;
import com.danieldhsd.interapi.repository.UsuariosRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuariosRepository usuariosRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuariosRepository.findByEmail(usuario.getEmail());

		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new UsuarioJaExistenteException(
					String.format("Usuário com o e-mail %s já cadastrado.", usuario.getEmail()));
		}

		return usuariosRepository.save(usuario);
	}

	public Usuario criptografarUsuario(Usuario usuario) {
		if (usuario.getChavePublicaCriptografia() == null)
			return usuario;

		try {
			PublicKey publicKey = getPublicKey(usuario.getChavePublicaCriptografia());
			String name = Base64.getEncoder().encodeToString(encrypt(usuario.getNome(), publicKey));
			String email = Base64.getEncoder().encodeToString(encrypt(usuario.getEmail(), publicKey));
			
			usuario.setNome(name);
			usuario.setEmail(email);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	private static byte[] encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data.getBytes());
    }

	private static PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			publicKey = keyFactory.generatePublic(keySpec);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return publicKey;
	}
}
