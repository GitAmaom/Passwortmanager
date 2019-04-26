package de.passwordmanager.services;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.passwordmanager.entities.EncryptedTableEntryList;

/**
 * Class that serializes the {@code EncryptedTableEntryList} with XML.
 * @author Moritz Schneider
 *
 */
public class EncryptedTableEntryListJAXB {

    /**
     * Serializes an EncryptedTableEntryList.
     * @param table the table that gets serialized.
     * @param file the file it gets serialized to.
     */
    public void marshall(EncryptedTableEntryList table, File file) {
        try {
            JAXBContext jc = JAXBContext.newInstance(EncryptedTableEntryList.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(table, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deserializes a file to an EncryptedTableEntryList.
     * @param file the file to deserialize.
     * @return the EncryptedTableEntryList.
     */
    public EncryptedTableEntryList unmarshall(File file) {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(EncryptedTableEntryList.class);
            Unmarshaller u = jc.createUnmarshaller();
            EncryptedTableEntryList list = (EncryptedTableEntryList) u.unmarshal(file);
            return list;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
