

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class ArtifactSender {
    public ArtifactSender() throws FileNotFoundException {
    }
    String credentialsPath;//"/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailCredentials"
    String substitutionPath; //"/Users/alexwalker/DropboxApp/ArtifactEmailSender/substitutions"
    String emailMessagePath; //"/Users/alexwalker/DropboxApp/ArtifactEmailSender/testDocumentation";
    String emailDetailsPath; //"/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailDetails";


    String from;

    String recipient;
    String subject;
    String carbonCopyRecipient;

    String completedEmail;

    //User Credentials
    String username;
    String password;

    //get the credentials for the senders email.
    Properties credentialsProps = new Properties();
    InputStream inputProp;

    //get the information about the email, e.g. Subject, Recipient, carbonCopyRecipient.
    Properties infoProps = new Properties();
    InputStream inputInfoProp;

    //Email Server host and port
    String host = "smtp-mail.outlook.com";
    String port = "587";

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length != 4) {
            System.out.println("Missing Arguments: input path of file to upload, and path to key.");
            return;
        }
        ArtifactSender artifactSender = new ArtifactSender();
        System.out.println("Sending Email...");


        artifactSender.setArguments(args);
        artifactSender.readCredentials();
        artifactSender.readEmailDetails();
        artifactSender.readEmail();
        artifactSender.createEmail(args);

        System.out.println("\n" + "Email Sent.");
    }

    public void setArguments(String[] args) {
        credentialsPath = args[0];
        emailMessagePath = args[1];
        emailDetailsPath = args[2];
        substitutionPath = args[3];
    }

    public void createEmail(String[] args) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

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
            //set cc: header field of the header
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(carbonCopyRecipient));
            //set Subject: Header of the header
            message.setSubject(subject);
            //Set the message of the email
            message.setText(completedEmail);
            //Send email
            Transport.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }

    }
    public void readEmail() throws FileNotFoundException {

        String emailMessage = new Scanner(new File(emailMessagePath)).useDelimiter("/s").next();
        ArrayList<String> substitutions = new ArrayList<>();

        //Populating the ArrayList with strings from the substitutions file.
        Scanner scanner = new Scanner(new File(substitutionPath)).useDelimiter("\\s");
        while (scanner.hasNext()) {
            substitutions.add(scanner.next());
        }
        scanner.close();

        for (int i = 0; i < substitutions.size(); i++ ) {
            emailMessage = emailMessage.replace("$$" + (i+1), substitutions.get(i));
        }
        completedEmail = emailMessage;
        System.out.print(emailMessage);
    }

    public void readEmailDetails() {

        try {
            inputInfoProp = new FileInputStream(emailDetailsPath);
            infoProps.load(inputInfoProp);

            recipient = infoProps.getProperty("Recipient");
            subject = infoProps.getProperty("Subject");
            carbonCopyRecipient = infoProps.getProperty("CC");
            from = infoProps.getProperty("From");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void readCredentials() {
        try {
            inputProp = new FileInputStream(credentialsPath);
            credentialsProps.load(inputProp);

            username = credentialsProps.getProperty("username");
            password = credentialsProps.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
