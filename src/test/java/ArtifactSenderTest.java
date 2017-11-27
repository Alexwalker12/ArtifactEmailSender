import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ArtifactSenderTest {
    @Test
    void setArguments() throws FileNotFoundException {
        System.out.println("setArguments is being Tested.");
        String[] args ={"/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailCredentials", "/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailMessage", "/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailDetails", "/Users/alexwalker/DropboxApp/ArtifactEmailSender/substitutions"};
        ArtifactSender testObject = new ArtifactSender();
        testObject.setArguments(args);
        assertEquals("/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailCredentials", testObject.getCredentialsPath());
        assertEquals("/Users/alexwalker/DropboxApp/ArtifactEmailSender/substitutions", testObject.getSubstitutionPath());
        assertEquals("/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailMessage", testObject.getEmailMessagePath());
        assertEquals("/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailDetails", testObject.getEmailDetailsPath());
    }

    @Test
    void createEmail() {
    }

    @Test
    void editEmail() {
    }

    @Test
    void readEmailDetails() throws FileNotFoundException {
        System.out.println("readEmailDetails is being tested.");
        ArtifactSender testObject = new ArtifactSender();
        testObject.readEmailDetails();
        assertEquals("alexwalker97@outlook.com", testObject.getFrom());
        assertEquals("alexjohn.walker@o2.com", testObject.getRecipient());
        assertEquals("This is a test subject from the emailDetails file.", testObject.getSubject());
        assertEquals("walkerzcrisps@gmail.com", testObject.getCarbonCopyRecipient());
    }

    @Test
    void readCredentials() {
    }

}