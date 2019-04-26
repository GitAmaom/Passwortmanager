package de.passwordmanager.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.*;
/**
 * Wrapper class for TableEntry
 * @author Moritz Schneider
 *
 */
@XmlRootElement(name="TableEntries")
public class TableEntryList {
    
    /**
     * A list of TableEntry's
     */
    private ObservableList<TableEntry> list;
    
    /**
     * Class constructor.
     */
    public TableEntryList() {
        this.list = FXCollections.observableArrayList();
    }
    /**
     * Adds a TableEntry to the list.
     * @param entry the TableEntry.
     */
    public void addEntry(TableEntry entry) {
        this.list.add(entry);
    }
    
    /**
     * Removes a TableEntry from the list.
     * @param entry the TableEntry.
     */
    public void removeEntry(TableEntry entry) {
        this.list.remove(entry);
    }

    /**
     * Gets the list of TableEntry's.
     * @return the list.
     */
    @XmlElement(name="TableEntry")
    public ObservableList<TableEntry> getEntries() {
        return this.list;
    }
    
    /**
     * Sets the list of TableEntry's
     * @param entries the list of TableEntry's.
     */
    public void setEntries(ObservableList<TableEntry> entries) {
        this.list = entries;
    }
}
