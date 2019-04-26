package de.passwordmanager.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.passwordmanager.services.CryptService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class for encrypting a TableEntryList.
 * @author Internet
 *
 */
@XmlRootElement(name = "TableEntries")
public class EncryptedTableEntryList {

    /**
     * The CryptService.
     */
    private CryptService service;
    
    /**
     * Password Hash of the masterpassword for checking.
     */
    private String pwCheckHash;
    
    /**
     * The list of encrypted table entries.
     */
    private ObservableList<TableEntry> list;

    /**
     * Class constructor.
     */
    public EncryptedTableEntryList() {
        this.service = new CryptService();
        this.list = FXCollections.observableArrayList();
    }

    /**
     * Class constructor that encrypts a given TableEntryList with the masterpassword.
     * @param list the TableEntryList.
     * @param masterpassword the masterpassword that gets used for encrypting.
     */
    public EncryptedTableEntryList(TableEntryList list, String masterpassword) {
        this.service = new CryptService();
        this.list = FXCollections.observableArrayList();
        for (TableEntry te : list.getEntries()) {
            String title_encrypted = service.encodeBytesBase64(service.encryptToMessage(te.getTitle(), masterpassword));
            String username_encrypted = service
                    .encodeBytesBase64(service.encryptToMessage(te.getUsername(), masterpassword));
            String password_encrypted = service
                    .encodeBytesBase64(service.encryptToMessage(te.getPassword(), masterpassword));
            String url_encrypted = service.encodeBytesBase64(service.encryptToMessage(te.getUrl(), masterpassword));
            String email_encrypted = service.encodeBytesBase64(service.encryptToMessage(te.getEmail(), masterpassword));
            String notes_encrypted = service.encodeBytesBase64(service.encryptToMessage(te.getNotes(), masterpassword));
            TableEntry teCopy = new TableEntry(title_encrypted, username_encrypted, password_encrypted, url_encrypted,
                    email_encrypted, notes_encrypted);
            teCopy.setCreateDateTime(
                    service.encodeBytesBase64(service.encryptToMessage(te.getCreateDateTime(), masterpassword)));
            //teCopy.setModifyDateTime(
            //        service.encodeBytesBase64(service.encryptToMessage(te.getModifyDateTime(), masterpassword)));
            this.addEntry(teCopy);
        }
        this.pwCheckHash = service.hashPasswordToMessage(masterpassword);
    }

    /**
     * Decrypts the TableEntryList using the masterpassword.
     * @param masterpassword The masterpassword.
     * @return the decrypted TableEntryList.
     */
    public TableEntryList decrypt(String masterpassword) {

        TableEntryList list = new TableEntryList();
        for (TableEntry te : this.getEntries()) {
            String title_decrypted = service.decryptMessage(service.decodeStringBase64(te.getTitle()), masterpassword);
            String username_decrypted = service.decryptMessage(service.decodeStringBase64(te.getUsername()),
                    masterpassword);
            String password_decrypted = service.decryptMessage(service.decodeStringBase64(te.getPassword()),
                    masterpassword);
            String url_decrypted = service.decryptMessage(service.decodeStringBase64(te.getUrl()), masterpassword);
            String email_decrypted = service.decryptMessage(service.decodeStringBase64(te.getEmail()), masterpassword);
            String notes_decrypted = service.decryptMessage(service.decodeStringBase64(te.getNotes()), masterpassword);
            TableEntry decryptedEntry = new TableEntry(title_decrypted, username_decrypted, password_decrypted, url_decrypted,
                    email_decrypted, notes_decrypted);
            decryptedEntry.setCreateDateTime(service.decryptMessage(service.decodeStringBase64(te.getCreateDateTime()), masterpassword));
            //decryptedEntry.setModifyDateTime(service.decryptMessage(service.decodeStringBase64(te.getModifyDateTime()), masterpassword));
            list.addEntry(decryptedEntry);
        }
        return list;
    }

    /**
     * Adds a TableEntry to the list.
     * @param te the TableEntry.
     */
    public void addEntry(TableEntry te) {
        list.add(te);
    }

    /**
     * Removes the TableEntry from the list.
     * @param te the TableEntry.
     */
    public void removeEntry(TableEntry te) {
        list.remove(te);
    }

    /**
     * Gets the TableEntryList.
     * @return the TableEntryList.
     */
    @XmlElement(name = "TableEntry")
    public ObservableList<TableEntry> getEntries() {
        return this.list;
    }

    /**
     * Gets the pwCheckHash.
     * @return the pwCheckHash.
     */
    @XmlElement(name = "CheckHash")
    public String getPwCheckHash() {
        return this.pwCheckHash;
    }
    
    /**
     * Sets the pwCheckHash.
     * @param hash - the pwCheckHash.
     */
    public void setPwCheckHash(String hash) {
        this.pwCheckHash = hash;
    }
}
