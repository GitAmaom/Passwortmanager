package de.passwordmanager.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * 
 * Class wrapping GeneratorSettings in a list.
 * @author Moritz Schneider
 *
 */
@XmlRootElement(name="Settings")
public class GeneratorSettingList {

    /**
     * The list of settings.
     */
    private ObservableList<GeneratorSetting> settings;
    
    public GeneratorSettingList() {
        this.settings = FXCollections.observableArrayList();
    }
    
    /**
     * Add a setting to the list.
     * @param setting the GeneratorSetting.
     */
    public void add(GeneratorSetting setting) {
        this.settings.add(setting);
    }
    
    /**
     * Removes a setting from the list.
     * @param setting the GeneratorSetting.
     */
    public void remove(GeneratorSetting setting) {
        this.settings.remove(setting);
    }
    
    /**
     * Gets the list of settings.
     * @return the list of settings.
     */
    @XmlElement(name="Setting")
    public ObservableList<GeneratorSetting> getSettings() {
        return this.settings;
    }
    
    /**
     * Sets the list of settings.
     * @param settings the list of settings.
     */
    public void setSettings(ObservableList<GeneratorSetting> settings) {
        this.settings = settings;
    }
}
