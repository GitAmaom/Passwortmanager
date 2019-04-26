package de.passwordmanager.services;

import java.io.File;

import de.passwordmanager.entities.GeneratorSetting;
import de.passwordmanager.entities.GeneratorSettingList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Profile Service managing the password generator profiles.
 * @author Moritz Schneider
 *
 */
public class ProfileService {

    /**
     * List of passwordgenerator profiles.
     */
    private GeneratorSettingList profilesList;

    /**
     * Constructs the service with standard passwordgenerator profiles.
     */
    public ProfileService() {
        
        File file = new File("profiles.xml");
        if(file.exists()) {
            this.profilesList = new GeneratorSettingListJAXB().unmarshall(file);
        } else {
            this.profilesList = new GeneratorSettingList();
            GeneratorSetting veryStrong = new GeneratorSetting("Sehr stark", 30, true, true, true, true, false, "");
            GeneratorSetting strong = new GeneratorSetting("Stark", 20, true, true, true, true, false, "");
            GeneratorSetting normal = new GeneratorSetting("Normal", 12, true, true, true, true, false, "");
            GeneratorSetting sufficient = new GeneratorSetting("Ausreichend", 10, true, true, true, false, false, "");
            
            veryStrong.setEditable(false);
            strong.setEditable(false);
            normal.setEditable(false);
            sufficient.setEditable(false);
            
            this.profilesList.add(veryStrong);
            this.profilesList.add(strong);
            this.profilesList.add(normal);
            this.profilesList.add(sufficient);
            
        }
    }

    /**
     * Creates an empty passwordgenerator profile and adds it to the list.
     */
    public void createNewProfile() {
        GeneratorSetting profile = new GeneratorSetting();
        this.profilesList.add(profile);
    }
    
    /**
     * Removes the passwordgenerator profile from the list.
     * @param profile the passwordgenerator profile to remove.
     */
    public void removeProfile(GeneratorSetting profile) {
        this.profilesList.remove(profile);
    }

    /**
     * Gets the GeneratorSettingList
     * @return the GeneratorSettingList.
     */
    public GeneratorSettingList getProfilesList() {
        return this.profilesList;
    }
    
    /**
     * Gets the list of passwordgenerator profiles.
     * @return an observable list of passwordgenerator profiles.
     */
    public ObservableList<GeneratorSetting> getProfiles() {
        return this.profilesList.getSettings();
    }
}
