package de.passwordmanager.services;

import java.io.File;

import de.passwordmanager.entities.EncryptedTableEntryList;
import de.passwordmanager.entities.TableEntry;
import de.passwordmanager.entities.TableEntryList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * Class for managing operations on the table.
 * @author Moritz Schneider
 *
 */
public class TableService {

    /**
     * The list of table entries.
     */
    private TableEntryList list;
    
    /**
     * The service for serializing the EncryptedTableEntryList.
     */
    private IoService io;
    
    /**
     * The service used for encrypting the TableEntryList.
     */
    private CryptService crypt;
    
    /**
     * List that gets wrapped around the TableEntryList.
     */
    private SortedList<TableEntry> sortedList;
    
    /**
     * Constructs the tableservice by initialising the services and creating a new TableEntryList.
     */
    public TableService() {
        this.io = new IoService();
        this.crypt = new CryptService();
        createNewList();
        this.sortedList = new SortedList<TableEntry>(list.getEntries());
    }
   
    /**
     * Unserializes a file and sets the new TableEntryList.
     * @param file the file to be read.
     * @param password password to unencrypt the file.
     * @return true if the process was successfull, else false.
     */
    public boolean readFileToTable(File file, String password) {
        EncryptedTableEntryList encrypted = io.readFile(file);
        if(this.crypt.checkPasswordHash(password, encrypted.getPwCheckHash())) {
            this.list = encrypted.decrypt(password);
            this.sortedList = new SortedList<TableEntry>(list.getEntries());
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Encrypts the current TableEntryList and serializes it to a file.
     * @param file the file it gets serialized to.
     * @param password the password used for encryption.
     */
    public void writeTableToFile(File file, String password) {
        this.io.setFile(file);
        this.io.setList(new EncryptedTableEntryList(list, password));
        this.io.writeFile();
    }
    
    /**
     * Adds a TableEntry with specified parameters to the list.
     * @param title the title.
     * @param username the username.
     * @param password the password.
     * @param url the url.
     * @param email the email.
     * @param notes the notes.
     */
    public void addEntry(String title, String username, String password, String url, String email, String notes) {
        this.list.addEntry(new TableEntry(title, username, password, url, email, notes));
    }
    
    /**
     * Removes a TableEntry from the list.
     * @param entry the TableEntry.
     */
    public void removeEntry(TableEntry entry) {
        this.list.removeEntry(entry);
    }
    
    /**
     * Creates a new TableEntryList.
     */
    public void createNewList() {
        this.list = new TableEntryList();
        this.sortedList = new SortedList<TableEntry>(list.getEntries());
    } 
    
    /**
     * Gets the sorted list.
     * @return the sorted list.
     */
    public SortedList<TableEntry> getSortedList() {
        return sortedList;
    }
    
    /**
     * Gets the list of table entries.
     * @return an observable list of table entries.
     */
    public ObservableList<TableEntry> getListEntries() {
        return list.getEntries();
    }
}
