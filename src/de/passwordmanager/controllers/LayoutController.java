package de.passwordmanager.controllers;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import de.passwordmanager.entities.TableEntry;
import de.passwordmanager.helpers.ClipboardHelper;
import de.passwordmanager.services.ApplicationService;
import de.passwordmanager.services.TableService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * Controller class of the "Layout".
 * 
 * @author Moritz Schneider
 *
 */
public class LayoutController {

    /**
     * Borderpane which is the root element of the view.
     */
    @FXML
    private BorderPane layout;

    /**
     * Tableview for displaying all table entries.
     */
    @FXML
    private TableView<TableEntry> table;

    /**
     * Table Column for displaying the titles.
     */
    @FXML
    private TableColumn<TableEntry, String> titleTableColumn;

    /**
     * Table Column for displaying the usernames.
     */
    @FXML
    private TableColumn<TableEntry, String> usernameTableColumn;

    /**
     * Table Column for displaying the passwords.
     */
    @FXML
    private TableColumn<TableEntry, String> passwordTableColumn;

    /**
     * Table Column for displaying the urls.
     */
    @FXML
    private TableColumn<TableEntry, String> urlTableColumn;

    /**
     * Table Column for displaying the emails.
     */
    @FXML
    private TableColumn<TableEntry, String> emailTableColumn;

    /**
     * Table Column for displaying the notes.
     */
    @FXML
    private TableColumn<TableEntry, String> notesTableColumn;

    /**
     * Table Column for displaying the create date of the table entry.
     */
    @FXML
    private TableColumn<TableEntry, String> createDateTimeTableColumn;

    /**
     * Table Column for displaying the last modified date of the table entry.
     */
    @FXML
    private TableColumn<TableEntry, String> modifyDateTimeTableColumn;

    /**
     * Button for adding a table entry.
     */
    @FXML
    private Button addEntryButton;

    /**
     * Button for removing a table entry.
     */
    @FXML
    private Button removeEntryButton;

    /**
     * Button for editing a table entry.
     */
    @FXML
    private Button editEntryButton;

    /**
     * Button for duplicating a table entry.
     */
    @FXML
    private Button duplicateEntryButton;

    /**
     * Menu for all actions manipulating a table entry.
     */
    @FXML
    private Menu manipulateMenu;

    /**
     * Menu for all actions moving a table entry.
     */
    @FXML
    private Menu moveMenu;

    /**
     * Menu for all clipboard actions on a table entry.
     */
    @FXML
    private Menu clipboardMenu;

    /**
     * Menu in a context menu for all actions manipulating a table entry.
     */
    @FXML
    private Menu contextManipulateMenu;

    /**
     * Menu in a context menu for all actions moving a table entry.
     */
    @FXML
    private Menu contextMoveMenu;

    /**
     * Menu in a context menu for all clipboard actions on a table entry.
     */
    @FXML
    private Menu contextClipboardMenu;
    
    /**
     * MenuItem for creating a new file.
     */
    @FXML
    private MenuItem newMenuItem;
    
    /**
     * MenuItem for opening a file.
     */
    @FXML
    private MenuItem openMenuItem;
    
    /**
     * MenuItem saving the current file.
     */
    @FXML
    private MenuItem saveMenuItem;
    
    /**
     * MenuItem saving the current file under a specific path.
     */
    @FXML
    private MenuItem saveUnderMenuItem;
    
    /**
     * MenuItem for opening the url of the currently selected table entry.
     */
    @FXML
    private MenuItem openURLMenuItem;
    
    /**
     * MenuItem for opening the password generator.
     */
    @FXML
    private MenuItem passwordgeneratorMenuItem;
    
    /**
     * MenuItem for opening the password checker.
     */
    @FXML
    private MenuItem passwordcheckerMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the title table column.
     */
    @FXML
    private CheckMenuItem titleCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the username table column.
     */
    @FXML
    private CheckMenuItem usernameCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the password table column.
     */
    @FXML
    private CheckMenuItem passwordCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the url table column.
     */
    @FXML
    private CheckMenuItem urlCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the email table column.
     */
    @FXML
    private CheckMenuItem emailCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the notes table column.
     */
    @FXML
    private CheckMenuItem notesCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the create date table column.
     */
    @FXML
    private CheckMenuItem createDateCheckMenuItem;
    
    /**
     * CheckMenuItem for setting the visibility of the modify date table column.
     */
    @FXML
    private CheckMenuItem modifyDateCheckMenuItem;

    /**
     * The currently opened file.
     */
    private File file;
    
    /**
     * The currently set password for encrypting the file.
     */
    private String password;
    
    /**
     * An Application Service for opening other windows.
     */
    private ApplicationService applications;
    
    /**
     * A Table Service containg the list which is loaded in the table.
     */
    private TableService tableservice;

    /**
     * A Seperator for seperating properties.
     */
    private static final String PROPERTY_SEPERATOR = ":";
    
    /**
     * A Seperator.
     */
    private static final String STANDARD_SEPERATOR = ";";

    
    /**
     * Initializes the Controller.
     */
    public void initialize() {
        this.titleTableColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        this.usernameTableColumn.setCellValueFactory(data -> data.getValue().usernameProperty());
        this.passwordTableColumn.setCellValueFactory(data -> data.getValue().passwordProperty());
        this.urlTableColumn.setCellValueFactory(data -> data.getValue().urlProperty());
        this.emailTableColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        this.notesTableColumn.setCellValueFactory(data -> data.getValue().notesProperty());
        this.createDateTimeTableColumn
                .setCellValueFactory(data -> data.getValue().getCreateDateTimeProperty());
        this.modifyDateTimeTableColumn
                .setCellValueFactory(data -> data.getValue().getModifyDateTimeProperty());

        this.titleCheckMenuItem.selectedProperty()
                .bindBidirectional(this.titleTableColumn.visibleProperty());
        this.usernameCheckMenuItem.selectedProperty()
                .bindBidirectional(this.usernameTableColumn.visibleProperty());
        this.passwordCheckMenuItem.selectedProperty()
                .bindBidirectional(this.passwordTableColumn.visibleProperty());
        this.urlCheckMenuItem.selectedProperty()
                .bindBidirectional(this.urlTableColumn.visibleProperty());
        this.emailCheckMenuItem.selectedProperty()
                .bindBidirectional(this.emailTableColumn.visibleProperty());
        this.notesCheckMenuItem.selectedProperty()
                .bindBidirectional(this.notesTableColumn.visibleProperty());
        this.createDateCheckMenuItem.selectedProperty()
                .bindBidirectional(this.createDateTimeTableColumn.visibleProperty());
        this.modifyDateCheckMenuItem.selectedProperty()
                .bindBidirectional(this.modifyDateTimeTableColumn.visibleProperty());

        this.passwordTableColumn.setCellFactory(
                new Callback<TableColumn<TableEntry, String>, TableCell<TableEntry, String>>() {
                    @Override
                    public TableCell<TableEntry, String> call(
                            TableColumn<TableEntry, String> column) {
                        return new TableCell<TableEntry, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                if (item == null | empty) {
                                    setText("");
                                } else {
                                    setText("\u25CF\u25CF\u25CF\u25CF\u25CF\u25CF\u25CF\u25CF\u25CF");
                                }
                            }
                        };
                    }
                });

        BooleanBinding isRowSelectedBinding = this.table.getSelectionModel().selectedItemProperty()
                .isNull();

        this.duplicateEntryButton.disableProperty().bind(isRowSelectedBinding);
        this.editEntryButton.disableProperty().bind(isRowSelectedBinding);
        this.removeEntryButton.disableProperty().bind(isRowSelectedBinding);
        this.moveMenu.disableProperty().bind(isRowSelectedBinding);
        this.manipulateMenu.disableProperty().bind(isRowSelectedBinding);
        this.clipboardMenu.disableProperty().bind(isRowSelectedBinding);
        this.contextManipulateMenu.disableProperty().bind(isRowSelectedBinding);
        this.contextMoveMenu.disableProperty().bind(isRowSelectedBinding);
        this.contextClipboardMenu.disableProperty().bind(isRowSelectedBinding);
        this.openURLMenuItem.disableProperty().bind(isRowSelectedBinding);

        this.tableservice = new TableService();
        this.tableservice.getSortedList().comparatorProperty()
                .bind(this.table.comparatorProperty());
        reload();
        loadTableState();
        this.applications = new ApplicationService(this, this.layout.getParent());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layout.getScene().getWindow().setOnCloseRequest(e -> saveTableState());
            }
        });
    }

    /**
     * Gets the currently selected TableEntry.
     * @return the currently selected TableEntry.
     */
    public TableEntry getSelectedTableEntry() {
        return this.table.getSelectionModel().getSelectedItem();
    }

    /**
     * Gets the tableservice.
     * @return the tableservice.
     */
    public TableService getTableservice() {
        return tableservice;
    }

    /**
     * Moves the selected row to the top.
     */
    @FXML
    private void rowToTop() {
        if (isRowSelected()) {
            TableEntry toMove = getSelectedTableEntry();
            int toMoveIndex = this.table.getSelectionModel().getSelectedIndex();
            this.tableservice.getListEntries().remove(toMoveIndex);
            this.tableservice.getListEntries().add(0, toMove);
        }
    }

    /**
     * Moves the selected row to the bottom.
     */
    @FXML
    private void rowToBottom() {
        if (isRowSelected()) {
            TableEntry toMove = getSelectedTableEntry();
            int toMoveIndex = this.table.getSelectionModel().getSelectedIndex();
            this.tableservice.getListEntries().remove(toMoveIndex);
            this.tableservice.getListEntries().add(this.table.getItems().size(), toMove);
        }
    }

    /**
     * Moves the selected row one up.
     */
    @FXML
    private void rowUp() {
        if (isRowSelected()) {
            TableEntry toMove = getSelectedTableEntry();
            int toMoveIndex = this.table.getSelectionModel().getSelectedIndex();
            if (toMoveIndex != 0) {
                this.tableservice.getListEntries().remove(toMoveIndex);
                this.tableservice.getListEntries().add(toMoveIndex - 1, toMove);
            }
        }
    }

    /**
     * Moves the selected row one down.
     */
    @FXML
    private void rowDown() {
        if (isRowSelected()) {
            TableEntry toMove = getSelectedTableEntry();
            int toMoveIndex = this.table.getSelectionModel().getSelectedIndex();
            if (toMoveIndex != this.table.getItems().size() - 1) {
                this.tableservice.getListEntries().remove(toMoveIndex);
                this.tableservice.getListEntries().add(toMoveIndex + 1, toMove);
            }
        }
    }

    /**
     * Clears the selection.
     */
    @FXML
    private void clearSelect() {
        this.table.getSelectionModel().clearSelection();
    }

    /**
     * Opens the new entry dialog by which a new table entry can be added.
     */
    @FXML
    private void addEntryButtonClick() {
        this.applications.showNewEntryDialog();
    }

    /**
     * Shows the edit entry dialog by which a table entry can be edited.
     */
    @FXML
    private void editEntryButtonClick() {
        if (isRowSelected()) {
            this.applications.showEditEntryDialog();
        }
    }
    
    /**
     * Removes the selected table entry.
     */
    @FXML
    private void removeEntryButtonClick() {
        if (isRowSelected()) {
            this.tableservice.removeEntry(this.getSelectedTableEntry());
        }
    }

    /**
     * Duplicates the currently selected table entry.
     */
    @FXML
    private void duplicateEntryButtonClick() {
        if (isRowSelected()) {
            TableEntry toDuplicate = getSelectedTableEntry();
            this.tableservice.addEntry(toDuplicate.getTitle(), toDuplicate.getUsername(),
                    toDuplicate.getPassword(), toDuplicate.getUrl(), toDuplicate.getEmail(),
                    toDuplicate.getNotes());
        }
    }

    /**
     * Creates a new table.
     */
    @FXML
    private void newMenuItemClick() {
        this.tableservice.createNewList();
        this.file = null;
        this.password = null;
        reload();
    }

    /**
     * Saves the table in a file.
     * @param event the ActionEvent.
     */
    @FXML
    private void saveMenuItemClick(ActionEvent event) {
        if (this.file == null && this.password == null) {
            saveUnderMenuItemClick(event);
        } else {
            this.tableservice.writeTableToFile(this.file, this.password);
        }
    }

    /**
     * Saves the table in a specified file.
     * @param event event the ActionEvent.
     */

    @FXML
    private void saveUnderMenuItemClick(ActionEvent event) {
        final FileChooser chooser = this.applications.getFileChooser();
        final Window window = this.layout.getScene().getWindow();
        final File temp = chooser.showSaveDialog(window);
        if (temp != null) {
            this.password = this.applications.showEnterPasswordDialog();
            if (this.password.isEmpty()) {
                temp.delete();
            } else {
                this.file = temp;
                this.tableservice.writeTableToFile(this.file, this.password);
            }
        }
    }
    
    /**
     * Closes the window.
     */
    
    @FXML
    private void closeApplication(MouseEvent event) {
        ((Stage)((Node) event.getSource()).getScene().getWindow()).hide();
    }

    /**
     * Opens a table saved in a file.
     */
    @FXML
    private void openMenuItemClick() throws IOException {
        FileChooser chooser = this.applications.getFileChooser();
        this.file = chooser.showOpenDialog(this.layout.getScene().getWindow());
        if (this.file != null) {
            this.password = this.applications.showEnterPasswordDialog();
            if (this.tableservice.readFileToTable(this.file, this.password)) {
                reload();
            } else {
                this.applications.showPasswordWrongAlert();
            }
        }
    }

    /**
     * Opens the password generator.
     */
    @FXML
    private void openPasswordGenerator() {
        this.applications.showPasswordgenerator();
    }

    /**
     * Opens the password checker.
     */
    @FXML
    private void openPasswordChecker() {
        this.applications.showPasswordChecker();
    }

    /**
     * Copies the selected title to the clipboard.
     */
    @FXML
    private void copyTitle() {
        this.propertyToCB(this.getSelectedTableEntry().titleProperty());
    }

    /**
     * Copies the selected username to the clipboard.
     */
    @FXML
    private void copyUsername() {
        this.propertyToCB(this.getSelectedTableEntry().usernameProperty());
    }
    
    /**
     * Copies the selected password to the clipboard.
     */
    @FXML
    private void copyPassword() {
        this.propertyToCB(this.getSelectedTableEntry().passwordProperty());
    }

    /**
     * Copies the selected url to the clipboard.
     */
    @FXML
    private void copyURL() {
        this.propertyToCB(this.getSelectedTableEntry().urlProperty());
    }

    
    /**
     * Copies the selected email to the clipboard.
     */
    @FXML
    private void copyEmail() {
        this.propertyToCB(this.getSelectedTableEntry().emailProperty());
    }

    /**
     * Copies the selected notes to the clipboard.
     */
    @FXML
    private void copyNotes() {
        this.propertyToCB(this.getSelectedTableEntry().notesProperty());
    }

    /**
     * Copies the selected creation date to the clipboard.
     */
    @FXML
    private void copyCreateDate() {
        this.propertyToCB(this.getSelectedTableEntry().getCreateDateTimeProperty());
    }

    
    /**
     * Copies the selected change date to the clipboard.
     */
    @FXML
    private void copyChangeDate() {
        this.propertyToCB(this.getSelectedTableEntry().getModifyDateTimeProperty());
    }

    
    /**
     * Opens the selected url in a webbrowser.
     */
    @FXML
    private void openUrl() {
        String url = getSelectedTableEntry().getUrl(); // TODO: Errorhandling,
                                                       // Alert erstellen
                                                       // TODO: Warning
                                                       // erstellen "diese URL
                                                       // wird jetzt geöffnet"
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the current sorting.
     */
    @FXML
    private void desort() {
        this.table.getSortOrder().clear();
    }

    /**
     * Copies a Stringproperty to the clipboard.
     * @param property the stringproperty.
     */
    private void propertyToCB(final Property<String> property) {
        final String string = property.getValue();
        ClipboardHelper.setClipboard(string);
    }

    private void reload() {
        this.table.setItems(this.tableservice.getSortedList());
        this.tableservice.getSortedList().comparatorProperty()
                .bind(this.table.comparatorProperty());
    }

    /**
     * Determines if a row is selected and shows an alert if not.
     * @return true if a row is selected, else false.
     */
    private boolean isRowSelected() {
        if (getSelectedTableEntry() == null) {
            this.applications.showNoEntrySelectedAlert();
        }
        return (getSelectedTableEntry() != null);
    }

    /**
     * Saves the table state in a file called "tablestate".
     */
    private void saveTableState() {
        String tablestate = "";
        int i = 0;
        for (TableColumn<TableEntry, ?> column : this.table.getColumns()) {
            tablestate = tablestate.concat(
                    column.getId() + PROPERTY_SEPERATOR + i + PROPERTY_SEPERATOR + column.getWidth()
                            + PROPERTY_SEPERATOR + column.isVisible() + STANDARD_SEPERATOR);
            i++;
        }
        File file = new File("tablestate"); // TODO: Create method in IO Helper Class.
        try {
            PrintWriter out = new PrintWriter(file);
            out.write(tablestate);
            out.close();
        } catch (FileNotFoundException e) {
        }

    }

    /**
     * Loads the tablestate from the file called "tablestate".
     */
    private void loadTableState() {
        String tablestate = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("tablestate"));
            tablestate = br.readLine();
            br.close();
        } catch (IOException e) {
        }
        loadTableState(tablestate);
    }

    /**
     * Loads the tablestate from a specific tablestate.
     * @param tableState the tablestate as a String.
     */
    private void loadTableState(String tableState) {
        if (!tableState.equals("")) {
            String[] states = tableState.split(STANDARD_SEPERATOR);
            if (states.length == this.table.getColumns().size()) {
                for (int i = 0; i < states.length; i++) {
                    String[] properties = states[i].split(PROPERTY_SEPERATOR);
                    TableColumn<TableEntry, ?> column = getTableColumnById(this.table,
                            properties[0]);
                    if (column == null) {
                        return;
                    }
                    this.table.getColumns().remove(column);
                    this.table.getColumns().add(Integer.parseInt(properties[1]), column);
                    resizeColumn(column, Double.parseDouble(properties[2]));
                    column.setVisible(Boolean.parseBoolean(properties[3]));
                }
            }
        }
    }

    // TODO: Move to helper class
    /**
     * Searches a table view for a column specified by the id.
     * 
     * @param tableview The tableview.
     * @param id        The id of the table column.
     * @return The table column.
     */
    private <T> TableColumn<T, ?> getTableColumnById(TableView<T> tableview, String id) {
        for (TableColumn<T, ?> col : tableview.getColumns())
            if (col.getId().equals(id))
                return col;
        return null;
    }

    // TODO: Move to helper class.
    /**
     * Resizes the a table column on runtime while preserving the minimum and
     * maximum size.
     * 
     * @param        <T> The type of the table column.
     * @param column The table column.
     * @param size   The size as a double.
     */
    private <T> void resizeColumn(TableColumn<T, ?> column, double size) {
        double minTemp = column.getMinWidth();
        double maxTemp = column.getMaxWidth();
        column.setMinWidth(size);
        column.setMaxWidth(size);
        column.setMinWidth(minTemp);
        column.setMaxWidth(maxTemp);
    }

}
