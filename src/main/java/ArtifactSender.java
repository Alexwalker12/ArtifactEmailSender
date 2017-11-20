
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class ArtifactSender {

    String recipient;
    String credentialsPath;
    String shareUrl;

    String emailContents = "/Users/alexwalker/DropboxApp/EmailSender/testDocumentation";


    String from = "Alexwalker97@outlook.com";
    String to;


    //User Credentials
    String username;
    String password;

    Properties credentialsProps = new Properties();
    InputStream inputProp;

    public static void main(String[] args) throws FileNotFoundException {
        ArtifactSender artifactSender = new ArtifactSender();
        if (args.length != 3) {
            System.out.println("Missing Arguments: input path of file to upload, and path to key.");
            return;
        }
//        artifactSender.go(args);

        artifactSender.readEmail();
    }

    public void go(String[] args) {

        recipient = args[0];
        credentialsPath = args[1];
        shareUrl = args[2];

        String host = "smtp-mail.outlook.com";
        String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        try {
            inputProp = new FileInputStream(credentialsPath);
            credentialsProps.load(inputProp);

            username = credentialsProps.getProperty("username");
            password = credentialsProps.getProperty("password");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                //Crete a default MimeMessage object
                Message message = new MimeMessage(session);

                //set from: header field of the header
                message.setFrom(new InternetAddress(from));

                //set to: header field of the header
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

                //set Subject: Header of the header
                message.setSubject("Test Subject");

                //Set the message of the email
                message.setText("hello, This is a test email ");

                //Send email
                Transport.send(message);
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    public void readEmail() throws FileNotFoundException {
        String emailString = new Scanner(new File(emailContents)).useDelimiter("/s").next();

        String testVar = "this test var";

        ArrayList<String> substitutions = new ArrayList<>();

        for (int i = 0; i < substitutions.size(); i++ ) {
            emailString = emailString.replace("$" + i + 1, substitutions.get(i));
        }
    }
}
