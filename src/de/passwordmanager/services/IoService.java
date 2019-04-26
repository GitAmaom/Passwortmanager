package de.passwordmanager.services;

import java.io.File;

import de.passwordmanager.entities.EncryptedTableEntryList;

/**
 * Service marshalling the EncryptedTableEntryList.
 * @author Moritz Schneider
 *
 */
public class IoService {

    /**
     * The file that gets read or written.
     */
    private File file;
    /**
     * The EncryptedTableEntryList-Marshaller.
     */
    private EncryptedTableEntryListJAXB jaxb;
    /**
     * The EncryptedTableEntryList.
     */
    private EncryptedTableEntryList list;
    
    /**
     * Class constructor.
     */
    public IoService() {
        this.jaxb = new EncryptedTableEntryListJAXB();
    }  
    
    /**
     * Serializes the list into the file.
     */
    public void writeFile() {
        jaxb.marshall(this.list, this.file);
    }
    
    /**
     * Reads the file and deserializes it to the EncryptedTableEntryList.
     * @param file the file to deserialize.
     * @return the EncryptedTableEntryList.
     */
    public EncryptedTableEntryList readFile(File file) {
        EncryptedTableEntryList te = jaxb.unmarshall(file);
        this.file = file;
        this.list = te;
        return list;
    }
    
    /**
     * Gets the EncryptedTableEntryList.
     * @return the EncryptedTableEntryList.
     */
    public EncryptedTableEntryList getList() {
        return this.list;
    }
    
    /**
     * Gets the file.
     * @return the file.
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Sets the file
     * @param path the filepath.
     */
    public void setFile(String path) {
        this.file = new File(path);
    }
    
    /**
     * Sets the file.
     * @param file the file.
     */
    public void setFile(File file) {
        this.file = file;
    }
    
    /**
     * Sets the list.
     * @param list the list.
     */
    public void setList(EncryptedTableEntryList list) {
        this.list = list;
    }
}
