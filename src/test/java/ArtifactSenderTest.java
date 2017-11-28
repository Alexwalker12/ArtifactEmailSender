import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ArtifactSenderTest {

    ArtifactSender testObject = new ArtifactSender();

    ArtifactSenderTest() throws FileNotFoundException {
    }

    @Test
    void setArguments() {
        System.out.println("setArguments is being Tested.");
        String[] args ={"/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailCredentials", "/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailMessage", "/Users/alexwalker/DropboxApp/ArtifactEmailSender/emailDetails", "/Users/alexwalker/DropboxApp/ArtifactEmailSender/substitutions"};
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
    void editEmail() throws FileNotFoundException {
        System.out.println("editEmail is being Tested.");
        String testString = "$$1";
        String testsubs = "testSub";
        String expectedString = "TestVar1";
        String actualString = testObject.editEmail(testString, testsubs);

    }

    @Test
    void readEmailDetails() {
        System.out.println("readEmailDetails is being tested.");
        testObject.readEmailDetails("/Users/alexwalker/DropboxApp/ArtifactEmailSender/testData/testEmailDetails");
        assertEquals("123qwerty@test.com", testObject.getFrom());
        assertEquals("testemail@test.com", testObject.getRecipient());
        assertEquals("This is a test subject from the emailDetails file.", testObject.getSubject());
        assertEquals("steve@stevemail.com", testObject.getCarbonCopyRecipient());
    }

    @Test
    void readCredentials() throws FileNotFoundException{
        System.out.println("readCredentials is being tested.");
        testObject.readCredentials("/Users/alexwalker/DropboxApp/ArtifactEmailSender/testData/TestEmailCredentials");
        assertEquals("123qwerty@test.com", testObject.getUsername());
        assertEquals("123qwerty", testObject.getPassword());
        assertEquals("123.321.qwerty", testObject.getHost());
        assertEquals("123", testObject.getPort());
    }

}