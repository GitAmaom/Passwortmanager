package de.passwordmanager.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class containing password generator settings.
 * @author Moritz Schneider
 *
 */
@XmlRootElement(name="GeneratorSetting")
public final class GeneratorSetting {

    /**
     * The name of the setting.
     */
    private SimpleStringProperty name;
    /**
     * The number of characters used to generate a password.
     */
    private SimpleIntegerProperty numberOfChars;
    /**
     * Property determines if the setting will be editable in the application.
     */
    private SimpleBooleanProperty editable;
    /**
     * True if uppercase characters are used to generate a password.
     */
    private SimpleBooleanProperty upperCaseSelected;
    /**
     * True if lowercase characters are used to generate a password.
     */
    private SimpleBooleanProperty lowerCaseSelected;
    /**
     * True if digits are used to generate a password.
     */
    private SimpleBooleanProperty digitsSelected;
    /**
     * True if special characters are used to generate a password.
     */
    private SimpleBooleanProperty specialCharactersSelected;
    /**
     * True if own characters are used to generate a password.
     */
    private SimpleBooleanProperty ownCharactersSelected;
    /**
     * All own characters as a string.
     */
    private SimpleStringProperty ownCharactersText;

    /**
     * Constructs the standardsettings.
     */
    public GeneratorSetting() {
        this.name = new SimpleStringProperty("Neues Profil");
        this.numberOfChars = new SimpleIntegerProperty(12);
        this.editable = new SimpleBooleanProperty(true);
        this.upperCaseSelected = new SimpleBooleanProperty(true);
        this.lowerCaseSelected = new SimpleBooleanProperty(true);
        this.digitsSelected = new SimpleBooleanProperty(true);
        this.specialCharactersSelected = new SimpleBooleanProperty(true);
        this.ownCharactersSelected = new SimpleBooleanProperty(false);
        this.ownCharactersText = new SimpleStringProperty("");
    }

    /**
     * Constructs a setting using the specified settings.
     * @param name the name of the generator setting.
     * @param numberOfChars the number of characters.
     * @param upperCaseSelected whether uppercase characters should be used.
     * @param lowerCaseSelected whether lowercase characters should be used.
     * @param digitsSelected whether digits should be used.
     * @param specialCharactersSelected whether special characters should be used.
     * @param ownCharactersSelected whether own characters should be used.
     * @param ownCharactersText own characters.
     */
    public GeneratorSetting(String name, int numberOfChars,
            boolean upperCaseSelected, boolean lowerCaseSelected,
            boolean digitsSelected, boolean specialCharactersSelected,
            boolean ownCharactersSelected, String ownCharactersText) {

        this.name = new SimpleStringProperty(name);
        this.numberOfChars = new SimpleIntegerProperty(numberOfChars);
        this.editable = new SimpleBooleanProperty(true);
        this.upperCaseSelected = new SimpleBooleanProperty(upperCaseSelected);
        this.lowerCaseSelected = new SimpleBooleanProperty(lowerCaseSelected);
        this.digitsSelected = new SimpleBooleanProperty(digitsSelected);
        this.specialCharactersSelected = new SimpleBooleanProperty(
                specialCharactersSelected);
        this.ownCharactersSelected = new SimpleBooleanProperty(
                ownCharactersSelected);
        this.ownCharactersText = new SimpleStringProperty(ownCharactersText);
    }


    /**
     * Gets the nameProperty.
     * @return the nameProperty.
     */
    public SimpleStringProperty getNameProperty() {
        return this.name;
    }

    /**
     * Gets the numberOfCharsProperty.
     * @return the numberOfCharsProperty.
     */
    public SimpleIntegerProperty getNumberOfCharsProperty() {
        return this.numberOfChars;
    }

    /**
     * Gets the editableProperty.
     * @return the editableProperty.
     */
    public SimpleBooleanProperty getEditableProperty() {
        return this.editable;
    }

    /**
     * Gets the UpperCaseSelectedProperty.
     * @return the upperCaseSelectedProperty
     */
    public SimpleBooleanProperty getUpperCaseSelectedProperty() {
        return this.upperCaseSelected;
    }

    /**
     * Gets the lowerCaseSelectedProperty.
     * @return the lowerCaseSelectedProperty
     */
    public SimpleBooleanProperty getLowerCaseSelectedProperty() {
        return this.lowerCaseSelected;
    }

    /**
     * Gets the digitsSelectedProperty.
     * @return the digitsSelectedProperty
     */
    public SimpleBooleanProperty getDigitsSelectedProperty() {
        return this.digitsSelected;
    }

    /**
     * Gets the specialCharactersSelectedProperty.
     * @return the specialCharactersSelectedProperty
     */
    public SimpleBooleanProperty getSpecialCharactersSelectedProperty() {
        return this.specialCharactersSelected;
    }

    /**
     * Gets the ownCharactersSelectedProperty.
     * @return the ownCharactersSelectedProperty
     */
    public SimpleBooleanProperty getOwnCharactersSelectedProperty() {
        return this.ownCharactersSelected;
    }

    /**
     * Gets the ownCharactersTextProperty.
     * @return the ownCharactersTextProperty
     */
    public SimpleStringProperty getOwnCharactersTextProperty() {
        return this.ownCharactersText;
    }

    /**
     * Gets the name.
     * @return the name.
     */
    @XmlElement
    public String getName() {
        return this.name.get();
    }

    /**
     * Gets the numberOfChars.
     * @return the numberOfChars.
     */
    @XmlElement
    public int getNumberOfChars() {
        return this.numberOfChars.get();
    }

    /**
     * Gets the upperCaseSelected.
     * @return the upperCaseSelected.
     */
    @XmlElement
    public boolean getUpperCaseSelected() {
        return this.upperCaseSelected.get();
    }

    /**
     * Gets the lowerCaseSelected.
     * @return the lowerCaseSelected.
     */
    @XmlElement
    public boolean getLowerCaseSelected() {
        return this.lowerCaseSelected.get();
    }

    /**
     * Gets the digitsSelected.
     * @return the digitsSelected.
     */
    @XmlElement
    public boolean getDigitsSelected() {
        return this.digitsSelected.get();
    }

    /**
     * Gets the specialCharactersSelected
     * @return the specialCharactersSelected.
     */
    @XmlElement
    public boolean getSpecialCharactersSelected() {
        return this.specialCharactersSelected.get();
    }

    /**
     * Gets the ownCharactersSelected.
     * @return the ownCharactersSelected.
     */
    @XmlElement
    public boolean getOwnCharactersSelected() {
        return this.ownCharactersSelected.get();
    }

    /**
     * Gets the ownCharactersText.
     * @return the ownCharactersText.
     */
    @XmlElement
    public String getOwnCharactersText() {
        return this.ownCharactersText.get();
    }
    
    /**
     * Gets the editable.
     * @return the editable.
     */
    @XmlElement
    public boolean isEditable() {
        return this.editable.get();
    }

    /**
     * Sets the name.
     * @param v value of the name.
     */
    public void setName(String v) {
        this.name.setValue(v);
    }

    /**
     * Sets the editable.
     * @param v value of the editable.
     */
    public void setEditable(boolean v) {
        this.editable.set(v);
    }

    /**
     * Sets the numberOfChars.
     * @param v value of the numberOfChars.
     */
    public void setNumberOfChars(int v) {
        this.numberOfChars.set(v);
    }

    /**
     * Sets the upperCaseSelected.
     * @param v value of the upperCaseSelected.
     */
    public void setUpperCaseSelected(boolean v) {
        this.upperCaseSelected.setValue(v);
    }

    /**
     * Sets the lowerCaseSelected.
     * @param v value of the lowerCaseSelected.
     */
    public void setLowerCaseSelected(boolean v) {
        this.lowerCaseSelected.setValue(v);
    }

    /**
     * Sets the digitsSelected.
     * @param v value of the digitsSelected.
     */
    public void setDigitsSelected(boolean v) {
        this.digitsSelected.setValue(v);
    }

    /**
     * Sets the specialCharactersSelected.
     * @param v value of the specialCharactersSelected.
     */
    public void setSpecialCharactersSelected(boolean v) {
        this.specialCharactersSelected.setValue(v);
    }

    /**
     * Sets the ownCharactersSelected.
     * @param v value of the ownCharactersSelected.
     */
    public void setOwnCharactersSelected(boolean v) {
        this.ownCharactersSelected.setValue(v);
    }

    /**
     * Sets the ownCharactersText.
     * @param v value of the ownCharactersText.
     */
    public void setOwnCharactersText(String v) {
        this.ownCharactersText.setValue(v);
    }
}

