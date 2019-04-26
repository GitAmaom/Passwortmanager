package de.passwordmanager.entities;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
/**
 * Bean class for a table entry.
 * @author Moritz Schneider
 *
 */
@XmlRootElement(name="TableEntry")
public class TableEntry {
    
    /**
     * Contains the title of the TableEntry.
     */
    private SimpleStringProperty title;
    
    /**
     * Contains the username.
     */
    private SimpleStringProperty username;
    
    /**
     * Contains the password.
     */
    private SimpleStringProperty password;
    
    /**
     * Contains the url.
     */
    private SimpleStringProperty url;
    
    /**
     * Contains the email.
     */
    private SimpleStringProperty email;
    
    /**
     * Contains the notes.
     */
    private SimpleStringProperty notes;
    
    /**
     * Contains the creation datetime.
     */
    private SimpleStringProperty createDateTime;
    
    /**
     * Contains the last modification datetime.
     */
    private SimpleStringProperty modifyDateTime;
    
    /**
     * Constructs an empty TableEntry.
     */
    public TableEntry()  {
        this.createDateTime = new SimpleStringProperty(LocalDateTime.now().toString());
        this.modifyDateTime = new SimpleStringProperty(LocalDateTime.now().toString());
        this.title = new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.url = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        
        this.title.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString())); 
        this.username.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.password.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.url.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.email.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.notes.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
    }
    
    /**
     * Constructs a TableEntry using the parameters.
     * @param title the title.
     * @param username the username.
     * @param password the password.
     * @param url the url.
     * @param email the email.
     * @param notes the notes.
     */
    public TableEntry(String title, String username, String password, String url, String email, String notes)  {
        this.createDateTime = new SimpleStringProperty(LocalDateTime.now().toString());
        this.modifyDateTime = new SimpleStringProperty(LocalDateTime.now().toString());
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.url = new SimpleStringProperty(url);
        this.email = new SimpleStringProperty(email);
        this.notes = new SimpleStringProperty(notes);
        
        this.title.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString())); 
        this.username.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.password.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.url.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.email.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
        this.notes.addListener(e -> this.modifyDateTime.set(LocalDateTime.now().toString()));
    }

    /**
     * Gets the titleProperty.
     * @return the titleProperty.
     */
    public SimpleStringProperty titleProperty() {
        return this.title;
    }
    
    /**
     * Gets the title.
     * @return the title.
     */
    @XmlElement
    public String getTitle() {
        return this.title.get();
    }

    /**
     * Gets the usernameProperty.
     * @return the usernameProperty.
     */
    public SimpleStringProperty usernameProperty() {
        return this.username;
    }
    
    /**
     * Gets the username.
     * @return the username.
     */
    @XmlElement
    public String getUsername() {
        return this.username.get();
    }

    /**
     * Gets the passwordProperty.
     * @return the passwordProperty.
     */
    public SimpleStringProperty passwordProperty() {
        return this.password;
    }
    
    /**
     * Gets the password.
     * @return the password.
     */
    @XmlElement
    public String getPassword() {
        return this.password.get();
    }
    
    /**
     * Gets the urlProperty.
     * @return the urlProperty.
     */
    public SimpleStringProperty urlProperty() {
        return this.url;
    }
    
    /**
     * Gets the url.
     * @return the url.
     */
    @XmlElement
    public String getUrl() {
        return this.url.get();
    }

    /**
     * Gets the emailProperty.
     * @return the emailProperty.
     */
    public SimpleStringProperty emailProperty() {
        return this.email;
    }
    
    /**
     * Gets the email.
     * @return the email.
     */
    @XmlElement
    public String getEmail() {
        return this.email.get();
    }

    /**
     * Gets the notesProperty.
     * @return the notesProperty.
     */
    public SimpleStringProperty notesProperty() {
        return this.notes;
    }
    
    /**
     * Gets the notes.
     * @return the notes.
     */
    @XmlElement
    public String getNotes() {
        return this.notes.get();
    }
    
    /**
     * Gets the createDateTimeProperty.
     * @return the createDateTimeProperty.
     */
    public SimpleStringProperty getCreateDateTimeProperty() {
        return createDateTime;
    }
    
    /**
     * Gets the createDateTime.
     * @return the createDateTime.
     */
    @XmlElement
    public String getCreateDateTime() {
        return this.createDateTime.get();
    }

    /**
     * Gets the modifyDateTimeProperty.
     * @return the modifyDateTimeProperty.
     */
    public SimpleStringProperty getModifyDateTimeProperty() {
        return modifyDateTime;
    }
    
    /**
     * Gets the modifyDateTime.
     * @return the modifyDateTime.
     */
    @XmlElement
    public String getModifyDateTime() {
        return this.modifyDateTime.get();
    }
    
    /**
     * Sets the title
     * @param title the title.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Sets the username.
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username.set(username);
    }

    /**
     * Sets the password.
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password.set(password);
    }
    
    /**
     * Sets the url.
     * @param url the url.
     */
    public void setUrl(String url) {
        this.url.set(url);
    }

    /**
     * Sets the email.
     * @param email the email.
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Sets the notes.
     * @param notes the notes.
     */
    public void setNotes(String notes) {
        this.notes.set(notes);
    }
    
    /**
     * Sets the createDateTime.
     * @param createDateTime the createDateTime.
     */
    public void setCreateDateTime(String createDateTime) {
        this.createDateTime.set(createDateTime);
    }
    
    /**
     * Sets the modifyDateTime.
     * @param modifyDateTime the modifyDateTime.
     */
    public void setModifyDateTime(String modifyDateTime) {
        this.modifyDateTime.set(modifyDateTime);
    }
    
    /**
     * Prints the object to a string.
     */
    @Override
    public String toString() {
        String str = "";
        str += "Titel: " + getTitle() + ", ";
        str += "Username: " + getUsername() + ", ";
        str += "Password: " + getPassword() + ", ";
        str += "URL: " + getUrl() + ", ";
        str += "Email: " + getEmail() + ", ";
        str += "Notes: " + getNotes() + "\n";
        return str;
    }
        
}
