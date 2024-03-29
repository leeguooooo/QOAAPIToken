package com.leeguoo;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class QOASecurityUtil {

  public static String makeToken(String appcode, String password) {
    return "token__" + byteToHexString(
        encrypt(appcode + "__" + System.currentTimeMillis(), password));
  }

  private static String byteToHexString(byte[] bytes) {
    StringBuffer sb = new StringBuffer(bytes.length);
    String sTemp;
    for (int i = 0; i < bytes.length; i++) {
      sTemp = Integer.toHexString(0xFF & bytes[i]);
      if (sTemp.length() < 2) {
        sb.append(0);
      }
      sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
  }

  public static byte[] encrypt(String content, String password) {
    try {
      DESKeySpec keySpec = new DESKeySpec(password.getBytes());
      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, SecretKeyFactory.getInstance("DES").generateSecret(keySpec),
          new IvParameterSpec(keySpec.getKey()));
      return cipher.doFinal(content.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}