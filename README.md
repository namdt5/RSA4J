# RSA4J
java encrypt decrypt using public private key - RSA4J

This class help to Encrypt/ Decrypt a string by public/private key. 

Note: 
- These keys is stored in 2 files in "etc" folder

Usage: 

    try {
      Rsa4j rsa = new Rsa4j(); 
      PublicKey myPublicKey = rsa.getPublicKey();
      
      // Test encrypt by my public key
      String encryptedData = rsa.encryptRsaByPublicKey("Hi! I'm RSA4J".getBytes(), myPublicKey);
      System.out.println("Encrypted data: " + encryptedData);

      // Test decrypt by my private key
      PrivateKey myPrivateKey = rsa.getPrivateKey(); 
      String decryptedData = rsa.decryptRsaByPrivateKey(encryptedData.getBytes(), myPrivateKey);
      System.out.println("Raw data: " + decryptedData);

    } catch (Exception ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    ============ RESULT ============
    Encrypted data: NI0CTwiYVKK0+QZ9GnreyicW0hmczBQiZFI3a9BO4ILhq/0uTFRxnVOF3lhNw00TE2FKxXZP0IjGi5aOq/7ZiV712nT8drH8SV7vXXvf/1/F2LYvj2FtQCAl9BfQw7sWmapBVGtWnQq24tWVzznN9EO38B/1fFybx87zWp+wrmj+RKBipQxwfCRT27wj+2yX6a+1QWR2SohjLyW53O60u6sur9TDGnp2wGbuiLc4KUBe/7nwADz8MesIBHxl3TPZ1qqrajaHeq2GxfmdAdt8xf9qfHNcdPYyzRV+7wm/sCaue53vD1Pc5cHUkjVnbfmc5vlhlvKS5CuA5Y12sPTQxwyBEhr3gTr/rKbOhmXj1uDZItI4V7qZ6o/7AMruI/HvF3QddRfJpRkJNNWlVgEpppDOXIyP2pK23K8dmKPca8qVlbyRreD9JJSirSTArCEom5drWiUAx+2vbmxUMeQEEWymKVo7FEgocH2BfAlhE4WtRWLPR76anewsR1pM0YOug8r7LzrUjpf/elXdVoJwgXN5t9fxQgdphZZvzVoB1iJbjcpCY6m4GCygV069v32ohmB4P+LixSLSQlciP13TtM3gBnQL5eMLH6l0W5aPVVIpHLS9FoPvAK29wHAHTNU9PneLAP+zNnYn3QncwRUZHWsbEY0XNC77bRIwiPnd5sg=
    Raw data: Hi! I'm RSA4J
    
