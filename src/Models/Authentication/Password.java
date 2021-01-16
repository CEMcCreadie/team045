package Models.Authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;

public class Password {

    private String password;
    private byte[] salt;

    public Password(String password) {
        this.password = password;
        this.salt = generateSalt();
    }

    public Password(String password,byte[] salt) {
        this.password = password;
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getPassword(){
        return password;
    }

    public String get_SHA_384_SecurePassword()
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            // prepend salt onto password
            md.update(this.getSalt());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private byte[] generateSalt()
    {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] saltGenerated = new byte[16];
            sr.nextBytes(saltGenerated);
            return saltGenerated;
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
