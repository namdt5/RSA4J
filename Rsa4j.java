/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tbsolution.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author namdt5
 */
public class Rsa4j {

  // Path to your PEM file that store your public key and private key
  private String pemKeyFile = "key.pem";
  // Path to your PEM file that store your public key and private key
  private String partnerPemKeyFile = "partnerKey.pem";

  private PrivateKey privateKey = null;
  private PublicKey publicKey = null;
  private PublicKey partnerPublicKey = null;  // public key of partner

  public Rsa4j() throws IOException, GeneralSecurityException {
    Properties prop = new Properties();

    try {
      // Get PEM file from config
      InputStream input = new FileInputStream("../etc/config.cfg");
      prop.load(input);
      pemKeyFile = prop.getProperty("pem_key_file");
      partnerPemKeyFile = prop.getProperty("partner_pem_key_file");

      loadOwnerKeys();
      partnerPublicKey = loadPublicKey(partnerPemKeyFile);

    } catch (FileNotFoundException ex) {
      Logger.getLogger(Rsa4j.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public PrivateKey getPrivateKey() {
    return privateKey;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public PublicKey getPartnerPublicKey() {
    return partnerPublicKey;
  }

  /**
   *
   * @param text - base64 encoded string
   * @param priKey
   * @return String
   * @throws java.lang.Exception
   */
  public String decryptRsaByPrivateKey(byte[] text, PrivateKey priKey) throws Exception {
    byte[] dectyptedData = null;

    // get an RSA cipher object and print the provider
    final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

    text = Base64.getDecoder().decode(text);
    // decrypt the text using the private key
    cipher.init(Cipher.DECRYPT_MODE, priKey);
    dectyptedData = cipher.doFinal(text);

    return new String(dectyptedData);
  }

  /**
   * Encrypt data to send to partner
   *
   * @param text
   * @param pubKey
   * @return
   * @throws java.lang.Exception
   */
  public String encryptRsaByPublicKey(byte[] text, PublicKey pubKey) throws Exception {
    byte[] encryptedData = null;
    byte[] result = null;

    // get an RSA cipher object and print the provider
    final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

    // decrypt the text using the private key
    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    encryptedData = cipher.doFinal(text);
    result = Base64.getEncoder().encode(encryptedData);

    return new String(result);
  }

  /**
   * Load your private and public key from PEM file
   *
   * @throws IOException
   * @throws GeneralSecurityException
   */
  private void loadOwnerKeys()
          throws IOException, GeneralSecurityException {

    PrivateKey priKey = null;
    PublicKey pubKey = null;
    InputStream is = null;

    is = new FileInputStream(pemKeyFile);
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

    StringBuilder privateKeyBuilder = new StringBuilder();
    StringBuilder publicKeyBuilder = new StringBuilder();
    boolean isPrivateKey = false;
    boolean isPublicKey = false;

    for (String line = br.readLine(); line != null; line = br.readLine()) {
      if (!isPrivateKey) {
        if (line.startsWith("-----BEGIN ")
                && line.endsWith(" PRIVATE KEY-----")) {
          isPrivateKey = true;
        }
      } else {
        if (line.startsWith("-----END ")
                && line.endsWith(" PRIVATE KEY-----")) {
          isPrivateKey = false;
        }
        privateKeyBuilder.append(line);
      }

      if (!isPublicKey) {
        if (line.startsWith("-----BEGIN ")
                && line.endsWith(" PUBLIC KEY-----")) {
          isPublicKey = true;
        }
      } else {
        if (line.startsWith("-----END ")
                && line.endsWith(" PUBLIC KEY-----")) {
          isPublicKey = false;
        }
        publicKeyBuilder.append(line);
      }
    }

    // Private Key
    byte[] privateKeyEencoded = DatatypeConverter.parseBase64Binary(privateKeyBuilder.toString());
    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyEencoded);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    privateKey = kf.generatePrivate(privateKeySpec);

    // Public Key
    byte[] publicKeyEencoded = DatatypeConverter.parseBase64Binary(publicKeyBuilder.toString());
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyEencoded);
    KeyFactory kf2 = KeyFactory.getInstance("RSA");
    publicKey = kf2.generatePublic(publicKeySpec);

  }

  /**
   * Load private key from PEM file
   *
   * @param pemFile
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  public PrivateKey loadPrivateKey(String pemFile)
          throws IOException, GeneralSecurityException {

    PrivateKey key = null;
    InputStream is = null;

    is = new FileInputStream(pemFile);
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

    StringBuilder builder = new StringBuilder();
    boolean inKey = false;
    for (String line = br.readLine(); line != null; line = br.readLine()) {
      if (!inKey) {
        if (line.startsWith("-----BEGIN ")
                && line.endsWith(" PRIVATE KEY-----")) {
          inKey = true;
        }
        // continue;
      } else {
        if (line.startsWith("-----END ")
                && line.endsWith(" PRIVATE KEY-----")) {
          inKey = false;
          break;
        }
        builder.append(line);
      }
    }

    byte[] encoded = DatatypeConverter.parseBase64Binary(builder.toString());
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    key = kf.generatePrivate(keySpec);

    return key;
  }

  /**
   * Just Load public key from PEM file
   *
   * @param pemFile
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  public PublicKey loadPublicKey(String pemFile)
          throws IOException, GeneralSecurityException {

    PublicKey key = null;
    InputStream is = null;

    is = new FileInputStream(pemFile);
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

    StringBuilder builder = new StringBuilder();
    boolean inKey = false;
    for (String line = br.readLine(); line != null; line = br.readLine()) {

      if (!inKey) {
        if (line.startsWith("-----BEGIN ")
                && line.endsWith(" PUBLIC KEY-----")) {
          inKey = true;
        }
        // continue;
      } else {
        if (line.startsWith("-----END ")
                && line.endsWith(" PUBLIC KEY-----")) {
          inKey = false;
          break;
        }
        builder.append(line);
      }
    }

    byte[] encoded = DatatypeConverter.parseBase64Binary(builder.toString());
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    key = kf.generatePublic(keySpec);

    return key;
  }
}
