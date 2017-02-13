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
      String encryptedData = rsa.encryptRsaByPublicKey("Hello! I'm RSA4J".getBytes(), myPublicKey);
      System.out.println("Encrypted data: " + encryptedData);

      // Test decrypt by my private key 
      encryptedData = "agp5lTTD44mZ06EU77X3aby3f7fruhXt7UaG37HyR6XofPB7MMRuVE9JDhDACyauOhXzUHiKa0/DI4WD5dOB1qqmzGxjvIGJyIdVI6Nj3fXX17jVXncPlgfuYJ7TfYo2yqDMbqlhcoSfSgtbrdXtpnqiC1U5+t9a5u+V3MZXt4AUTGaVzz+udSGB/ocyz7ONVr4TyT5MULL5QrO/kNKUMr6VG5JwUeYyJNX/bxpjOWJupdLd5YQPEWV1DJmOVENV682HHl1/CqAG6ayhQbtPV+6GdlDwxgP2+omyTNANr8q7VwEvMt8mnV+839Yk25iQRaLNCxzoJA9xFPDY+rHXm2kRKYAcpaidPMoFh+ZPLAh+Fm1lLFdYgzvW5uWscyYNCmyRzcqQt3tmdRiWmOsOO/VE2CL55TuIZlRRYgy+yY9Go64opusL7FbEeVLSf0jQa+h8zwSuK+Cz+BCd54sxsuAWVQqXHxfLPJBEeWaQ2Iwns+HPxDZCL6eqPOvC6M4X5darE5ber2nnpVLfPRMXZ1ppo6Dbv15i+29/GgaySiB9f34hdECds2x88OLBB7kyeFFAPjkQs2IXEkY4cD0AugDqEHfm12HnTZi159mpl47LaAYZK2nXfpKLfsV4GB65o7u9uFQ9FjYC4jiZn8pccPQQ7/ZelFEG6gf8XKREdUs=";
      
      PrivateKey myPrivateKey = rsa.getPrivateKey(); 
      String decryptedData = rsa.decryptRsaByPrivateKey(encryptedData.getBytes(), myPrivateKey);
      System.out.println("Raw data: " + decryptedData);

    } catch (Exception ex) {
      java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    ============ RESULT ============
    Raw data: Hi! I'm RSA4J!
    
