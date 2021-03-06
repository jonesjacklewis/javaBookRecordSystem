// Package deceleration
package com.jackljones.www;

// Imports

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;

// Class to create the different GUI scenes
public class SceneBuilder {


    // Instance variables
    private static final Validation validator = new Validation();
    private static FileHandling fileHandler;
    private static Stage primaryStage;
    private static Scene sceneHome;

    // Constructor
    public SceneBuilder(Stage primaryStage) {
        SceneBuilder.primaryStage = primaryStage;
        sceneHome = getSceneHome();
    }

    // Creates the home page scene
    public Scene getSceneHome() {

        // Set title
        primaryStage.setTitle("Book Record System - Home");

        int width = 300;
        int height = 300;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);

        Label lblDesc = new Label("What would you like to do today?");

        // Buttons
        Button btnAdd = new Button("Add Book Record");
        Button btnView = new Button("View Book Record");
        Button btnEdit = new Button("Edit Book Record");
        Button btnDelete = new Button("Delete Book Record");
        Button btnStats = new Button("Book Record Statistics");
        Button btnSearch = new Button("Search Books");

        // Events

        EventHandler<ActionEvent> loadAddRecord = e -> {
            Scene sceneAdd = getSceneAdd();
            primaryStage.setScene(sceneAdd);
        };

        EventHandler<ActionEvent> loadViewRecord = e -> {
            Scene sceneView = getSceneViewAll();
            primaryStage.setScene(sceneView);
        };

        EventHandler<ActionEvent> loadEditRecord = e -> {
            Scene sceneEdit = getSceneSelectEdit();
            primaryStage.setScene(sceneEdit);
        };

        EventHandler<ActionEvent> loadDeleteRecord = e -> {
            Scene sceneDelete = getSceneDelete();
            primaryStage.setScene(sceneDelete);
        };

        EventHandler<ActionEvent> loadStatRecord = e -> {
            Scene sceneStats = getSceneStatsHome();
            primaryStage.setScene(sceneStats);
        };

        EventHandler<ActionEvent> loadSearchRecord = e -> {
            Scene sceneSearch = getSceneSearchForm();
            primaryStage.setScene(sceneSearch);
        };


        // Setting Actions
        btnAdd.setOnAction(loadAddRecord);
        btnView.setOnAction(loadViewRecord);
        btnEdit.setOnAction(loadEditRecord);
        btnDelete.setOnAction(loadDeleteRecord);
        btnStats.setOnAction(loadStatRecord);
        btnSearch.setOnAction(loadSearchRecord);

        // Setting Layout
        FlowPane layoutHome = new FlowPane();
        layoutHome.setHgap(20);
        layoutHome.setVgap(22);

        layoutHome.getChildren().addAll(lblDesc, btnAdd, btnView, btnEdit, btnDelete, btnSearch, btnStats);

        return new Scene(layoutHome, 300, 250);

    }


    // Creates the scene to add records
    private Scene getSceneAdd() {

        // FileHandling for booksRecords.csv
        fileHandler = new FileHandling("bookRecords.csv");

        // Set tile
        primaryStage.setTitle("Book Record System - Add Record");

        int width = 300;
        int height = 600;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);

        // Buttons
        Button btnHome = new Button("Home");
        Button btnSave = new Button("Save");


        Label lblDesc = new Label("Use this form to add a book");

        // Form

        Label lblId = new Label("ID: ");
        Label lblIdVal = new Label(fileHandler.getNextId());

        Label lblISBN = new Label("ISBN: ");
        TextArea txtISBN = new TextArea();

        Label lblTitle = new Label("Title: ");
        TextArea txtTitle = new TextArea();

        Label lblAuthor = new Label("Author");
        TextArea txtAuthor = new TextArea();

        Label lblPages = new Label("Number of Pages: ");
        TextArea txtPages = new TextArea();

        Label lblGenre = new Label("Genre: ");
        ObservableList<String> options = observableArrayList();

        // Combo box options
        String[] genreOptions = {
                "Biography",
                "Fantasy",
                "Horror",
                "Mystery",
                "Non-Fiction",
                "Romance",
                "Science-Fiction",
                "Thriller"
        };

        Collections.addAll(options, genreOptions);

        ComboBox<String> cmbGenre = new ComboBox<>(options);
        // Set combo box to Biography
        cmbGenre.setValue(options.get(0));


        Label lblReview = new Label("Review");
        TextArea txtReview = new TextArea();


        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);


            primaryStage.setScene(sceneHome);
        };

        EventHandler<ActionEvent> saveRecord = actionEvent -> {

            fileHandler = new FileHandling("bookRecords.csv");

            // Get values
            String id = lblIdVal.getText();
            String isbn = txtISBN.getText().replace("-", "");  // Remove hyphen from 13 digit ISBNs
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String pages = txtPages.getText();
            String genre = cmbGenre.getValue();
            String review = txtReview.getText();


            // Build line
            String line = id + "," + isbn + "," + title + "," + author + "," + pages + "," + genre + "," + review;


            // Check validity
            boolean validIsbn = validator.validIsbn(isbn);
            boolean toSave = validIsbn;

            boolean validTitle = validator.validTitle(title);
            toSave = toSave && validTitle;

            boolean validAuthor = validator.validAuthor(author);
            toSave = toSave && validAuthor;

            boolean validPages = validator.validPages(pages);
            toSave = toSave && validPages;

            boolean isUnique = !fileHandler.isDuplicate(line, false);
            toSave = toSave && isUnique;

            if (toSave) {

                // Writes to file
                fileHandler.writeLineToFile(line);

                // Gets next ID
                lblIdVal.setText(fileHandler.getNextId());

                // Sets to defaults
                txtISBN.setText("");
                txtTitle.setText("");
                txtAuthor.setText("");
                txtPages.setText("");
                cmbGenre.setValue(options.get(0));
                txtReview.setText("");


                // causes refresh
                if (primaryStage.getWidth() == width) {
                    primaryStage.setWidth(primaryStage.getWidth() + 0.001);
                } else {
                    primaryStage.setWidth(primaryStage.getWidth() - 0.001);
                }


            } else {
                // Error boxes
                if (!validIsbn) {
                    errorBox("Invalid ISBN", "An ISBN must be either 10 or " +
                            "13 characters, containing only digits");
                    txtISBN.setText("");
                }

                if (!validTitle) {
                    errorBox("Invalid Title", "A title must be between 10 and " +
                            "256 characters");
                    txtTitle.setText("");
                }

                if (!validAuthor) {
                    errorBox("Invalid Author", "The authors name can only include " +
                            "alphabetic characters, full stops and hyphens. " +
                            "It must also be between 5 and 256 characters");
                    txtAuthor.setText("");
                }

                if (!validPages) {
                    errorBox("Invalid Pages", "The number of pages must be an " +
                            "integer between 1 and 32,768");
                    txtPages.setText("");
                }
                if (!isUnique) {
                    errorBox("Duplicate Record", "Attempting to add a duplicate " +
                            "record");
                    txtISBN.setText("");
                    txtTitle.setText("");
                    txtAuthor.setText("");
                    txtPages.setText("");
                    cmbGenre.setValue(options.get(0));
                    txtReview.setText("");


                    // causes refresh
                    if (primaryStage.getWidth() == width) {
                        primaryStage.setWidth(primaryStage.getWidth() + 0.001);
                    } else {
                        primaryStage.setWidth(primaryStage.getWidth() - 0.001);
                    }
                }

            }

        };

        btnHome.setOnAction(loadSceneHome);
        btnSave.setOnAction(saveRecord);

        // Sets layout
        VBox layoutAdd = new VBox();
        layoutAdd.setPadding(new Insets(10, 0, 10, 0));
        layoutAdd.setAlignment(Pos.CENTER);

        layoutAdd.getChildren().addAll(
                lblDesc, btnHome,
                lblId, lblIdVal,
                lblISBN, txtISBN,
                lblTitle, txtTitle,
                lblAuthor, txtAuthor,
                lblPages, txtPages,
                lblGenre, cmbGenre,
                lblReview, txtReview,
                btnSave
        );

        return new Scene(layoutAdd, 300, 250);
    }

    // Creates the scene to view all the records
    private Scene getSceneViewAll() {

        fileHandler = new FileHandling("bookRecords.csv");

        primaryStage.setTitle("Book Record System - View Records");

        int width = 900;
        int height = 500;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Button btnHome = new Button("Home");

        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            // Reset width
            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);

            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadSceneHome);

        Label lblDesc = new Label("Use this form to view all records, right click a row, to view in more detail.");

        // Get all lines in file as array
        String[] fileContents = fileHandler.readAllFile().split("\n");

        // Get Table
        TableView<Book> table = getTable(fileContents);


        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) { // On right click

                try {
                    // Gets the item
                    Book selectedItem = table.getSelectionModel().getSelectedItem();

                    Scene sceneSpecific = getSceneViewSpecific(selectedItem);
                    primaryStage.setScene(sceneSpecific);
                } catch (NullPointerException e) {  // Stop people clicking on header
                    final int w = 900;
                    final int h = 500;

                    primaryStage.setMinWidth(w);
                    primaryStage.setMaxWidth(w);
                    primaryStage.setWidth(w);

                    primaryStage.setMinHeight(h);
                    primaryStage.setMaxHeight(h);
                    primaryStage.setHeight(h);
                }


            }
        });


        // Set layout
        VBox layoutViewAll = new VBox();
        layoutViewAll.setPadding(new Insets(10, 0, 10, 0));
        layoutViewAll.setAlignment(Pos.CENTER);

        layoutViewAll.getChildren().addAll(
                lblDesc, btnHome,
                table
        );


        return new Scene(layoutViewAll, 300, 250);


    }

    // Creates the scene to view a specific record
    private Scene getSceneViewSpecific(Book book) {

        int width = 300;
        int height = 600;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);

        primaryStage.setTitle("Book Record System - View " + book.getTitle());

        Label lblId = new Label("ID: ");
        Label lblIdVal = new Label(book.getId()); // Using getter method
        lblIdVal.setStyle("-fx-font-weight: bold"); // Bolds font

        Label lblIsbn = new Label("ISBN: ");
        Label lblIsbnVal = new Label(book.getIsbn());
        lblIsbnVal.setStyle("-fx-font-weight: bold");

        Label lblTitle = new Label("Title: ");
        Label lblTitleVal = new Label(book.getTitle());
        lblTitleVal.setStyle("-fx-font-weight: bold");

        Label lblAuthor = new Label("Author: ");
        Label lblAuthorVal = new Label(book.getAuthor());
        lblAuthorVal.setStyle("-fx-font-weight: bold");

        Label lblPages = new Label("Pages: ");
        Label lblPagesVal = new Label(book.getPages());
        lblPagesVal.setStyle("-fx-font-weight: bold");

        Label lblGenre = new Label("Genre: ");
        Label lblGenreVal = new Label(book.getGenre());
        lblGenreVal.setStyle("-fx-font-weight: bold");

        Label lblReview = new Label("Review: ");
        Label lblReviewVal = new Label(book.getReview());
        lblReviewVal.setStyle("-fx-font-weight: bold");

        lblReviewVal.setWrapText(true);  // Allows the text to wrap if too long

        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {
            Scene sceneViewAll = getSceneViewAll();
            primaryStage.setScene(sceneViewAll);
        };

        Button btnHome = new Button("View All");
        btnHome.setOnAction(loadSceneHome);

        // Set layout
        VBox layoutViewSpecific = new VBox();
        layoutViewSpecific.setPadding(new Insets(10, 0, 10, 0));
        layoutViewSpecific.setAlignment(Pos.CENTER);

        layoutViewSpecific.getChildren().addAll(
                btnHome,
                lblId, lblIdVal,
                lblIsbn, lblIsbnVal,
                lblTitle, lblTitleVal,
                lblAuthor, lblAuthorVal,
                lblPages, lblPagesVal,
                lblGenre, lblGenreVal,
                lblReview, lblReviewVal
        );

        return new Scene(layoutViewSpecific, 300, 250);

    }


    // Creates the scene to select a record to edit
    private Scene getSceneSelectEdit() {

        fileHandler = new FileHandling("bookRecords.csv");

        primaryStage.setTitle("Book Record System - Edit Record");

        int width = 900;
        int height = 500;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Button btnHome = new Button("Home");

        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);

            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadSceneHome);

        Label lblDesc = new Label("Use this form to view all records, right click a row, to edit it.");

        String[] fileContents = fileHandler.readAllFile().split("\n");

        // Create table
        TableView<Book> table = getTable(fileContents);


        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {  // On right click

                try {
                    // Get selected item
                    Book selectedItem = table.getSelectionModel().getSelectedItem();

                    Scene sceneSpecific = getSceneEditSpecific(selectedItem);
                    primaryStage.setScene(sceneSpecific);
                } catch (NullPointerException e) {  // Stops from right clicking headers
                    final int w = 900;
                    final int h = 500;

                    primaryStage.setMinWidth(w);
                    primaryStage.setMaxWidth(w);
                    primaryStage.setWidth(w);

                    primaryStage.setMinHeight(h);
                    primaryStage.setMaxHeight(h);
                    primaryStage.setHeight(h);
                }


            }
        });


        // Sets layout
        VBox layoutSelectEdit = new VBox();
        layoutSelectEdit.setPadding(new Insets(10, 0, 10, 0));
        layoutSelectEdit.setAlignment(Pos.CENTER);

        layoutSelectEdit.getChildren().addAll(
                lblDesc, btnHome,
                table
        );


        return new Scene(layoutSelectEdit, 300, 250);

    }

    // Creates the scene to edit a specific record
    private Scene getSceneEditSpecific(Book book) {
        fileHandler = new FileHandling("bookRecords.csv");

        primaryStage.setTitle("Book Record System - Edit Record");

        int width = 300;
        int height = 600;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Button btnHome = new Button("Home");
        Button btnSave = new Button("Save");


        Label lblDesc = new Label("Use this form to add a book");

        Label lblId = new Label("ID: ");
        Label lblIdVal = new Label(book.getId()); // Using getter method

        Label lblISBN = new Label("ISBN: ");
        TextArea txtISBN = new TextArea(book.getIsbn());

        Label lblTitle = new Label("Title: ");
        TextArea txtTitle = new TextArea(book.getTitle());

        Label lblAuthor = new Label("Author");
        TextArea txtAuthor = new TextArea(book.getAuthor());

        Label lblPages = new Label("Number of Pages: ");
        TextArea txtPages = new TextArea(book.getPages());


        Label lblGenre = new Label("Genre: ");
        ObservableList<String> options = observableArrayList();

        String[] genreOptions = {
                "Biography",
                "Fantasy",
                "Horror",
                "Mystery",
                "Non-Fiction",
                "Romance",
                "Science-Fiction",
                "Thriller"
        };

        Collections.addAll(options, genreOptions);

        ComboBox<String> cmbGenre = new ComboBox<>(options);
        cmbGenre.setValue(options.get(options.indexOf(book.getGenre())));


        Label lblReview = new Label("Review");
        TextArea txtReview = new TextArea(book.getReview());


        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);


            primaryStage.setScene(sceneHome);
        };

        EventHandler<ActionEvent> saveRecord = actionEvent -> {

            String id = lblIdVal.getText();
            String isbn = txtISBN.getText().replace("-", "");  // Removes hyphen from 13 digit ISBN
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String pages = txtPages.getText();
            String genre = cmbGenre.getValue();
            String review = txtReview.getText();


            String line = id + "," + isbn + "," + title + "," + author + "," + pages + "," + genre + "," + review;

            // Validation

            boolean validIsbn = validator.validIsbn(isbn);
            boolean toSave = validIsbn;

            boolean validTitle = validator.validTitle(title);
            toSave = toSave && validTitle;

            boolean validAuthor = validator.validAuthor(author);
            toSave = toSave && validAuthor;

            boolean validPages = validator.validPages(pages);
            toSave = toSave && validPages;

            boolean isUnique = !fileHandler.isDuplicate(line, true);
            toSave = toSave && isUnique;

            if (toSave) {
                fileHandler = new FileHandling("bookRecords.csv");
                fileHandler.editRecord(book.getId(), line);

                sceneHome = getSceneHome();
                primaryStage.setScene(sceneHome);

                // causes refresh
                if (primaryStage.getWidth() == width) {
                    primaryStage.setWidth(primaryStage.getWidth() + 0.001);
                } else {
                    primaryStage.setWidth(primaryStage.getWidth() - 0.001);
                }


            } else {

                // Error handling

                if (!validIsbn) {
                    errorBox("Invalid ISBN", "An ISBN must be either 10 or " +
                            "13 characters, containing only digits");
                    txtISBN.setText("");
                }

                if (!validTitle) {
                    errorBox("Invalid Title", "A title must be between 10 and " +
                            "256 characters");
                    txtTitle.setText("");
                }

                if (!validAuthor) {
                    errorBox("Invalid Author", "The authors name can only include " +
                            "alphabetic characters, full stops and hyphens. " +
                            "It must also be between 5 and 256 characters");
                    txtAuthor.setText("");
                }

                if (!validPages) {
                    errorBox("Invalid Pages", "The number of pages must be an " +
                            "integer between 1 and 32,768");
                    txtPages.setText("");
                }
                if (!isUnique) {
                    errorBox("Duplicate Record", "Attempting to add a duplicate " +
                            "record");
                }

            }

        };

        btnHome.setOnAction(loadSceneHome);
        btnSave.setOnAction(saveRecord);

        // Sets layout

        VBox layoutAdd = new VBox();
        layoutAdd.setPadding(new Insets(10, 0, 10, 0));
        layoutAdd.setAlignment(Pos.CENTER);

        layoutAdd.getChildren().addAll(
                lblDesc, btnHome,
                lblId, lblIdVal,
                lblISBN, txtISBN,
                lblTitle, txtTitle,
                lblAuthor, txtAuthor,
                lblPages, txtPages,
                lblGenre, cmbGenre,
                lblReview, txtReview,
                btnSave
        );

        return new Scene(layoutAdd, 300, 250);
    }

    // Creates the scene to delete a record
    private Scene getSceneDelete() {

        fileHandler = new FileHandling("bookRecords.csv");

        primaryStage.setTitle("Book Record System - Delete Record");

        int width = 900;
        int height = 500;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Button btnHome = new Button("Home");

        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);

            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadSceneHome);

        Label lblDesc = new Label("Use this form to view all records, right click a row, to edit it.");

        String[] fileContents = fileHandler.readAllFile().split("\n");

        // Creates Table
        TableView<Book> table = getTable(fileContents);


        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {  // On right click

                try {
                    Book selectedItem = table.getSelectionModel().getSelectedItem();  // Gets selected item
                    String delID = selectedItem.getId();  // Gets ID

                    // Alert box
                    Alert msgBox = new Alert(Alert.AlertType.CONFIRMATION);
                    msgBox.setTitle("Confirm Deletion");
                    msgBox.setHeaderText("Confirm Deletion");
                    msgBox.setContentText("Are you sure you want to delete record with ID " + delID + "?");
                    msgBox.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {  // If they choose ok
                            fileHandler.deleteRecord(delID);  // delete record

                            // causes refresh
                            if (primaryStage.getWidth() == width) {
                                primaryStage.setWidth(primaryStage.getWidth() + 0.001);
                            } else {
                                primaryStage.setWidth(primaryStage.getWidth() - 0.001);
                            }

                            primaryStage.setScene(getSceneDelete());

                        }
                    });
                }catch (NullPointerException e) {  // Stops from affecting header
                        final int w = 900;
                        final int h = 500;

                        primaryStage.setMinWidth(w);
                        primaryStage.setMaxWidth(w);
                        primaryStage.setWidth(w);

                        primaryStage.setMinHeight(h);
                        primaryStage.setMaxHeight(h);
                        primaryStage.setHeight(h);
                    }



            }
        });


        // Sets layout
        VBox layoutSelectEdit = new VBox();
        layoutSelectEdit.setPadding(new Insets(10, 0, 10, 0));
        layoutSelectEdit.setAlignment(Pos.CENTER);

        layoutSelectEdit.getChildren().addAll(
                lblDesc, btnHome,
                table
        );


        return new Scene(layoutSelectEdit, 300, 250);

    }

    // Creates the search form
    private Scene getSceneSearchForm() {

        fileHandler = new FileHandling("bookRecords.csv");

        // Sets title
        primaryStage.setTitle("Book Record System - Search Record(s)");

        int width = 300;
        int height = 600;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Button btnHome = new Button("Home");
        Button btnSearch = new Button("Search");


        Label lblDesc = new Label("Use this form to search for a book/books");

        // Search Criteria
        Label lblId = new Label("ID: ");
        TextArea txtId = new TextArea();

        Label lblIsbn = new Label("ISBN: ");
        TextArea txtIsbn = new TextArea();

        Label lblTitle = new Label("Title: ");
        TextArea txtTitle = new TextArea();

        Label lblAuthor = new Label("Author: ");
        TextArea txtAuthor = new TextArea();

        Label lblPages = new Label("Pages: ");
        TextArea txtPages = new TextArea();

        Label lblGenre = new Label("Genre: ");
        ObservableList<String> options = observableArrayList();

        String[] defaultOptions = {
                "Biography",
                "Fantasy",
                "Horror",
                "Mystery",
                "Non-Fiction",
                "Romance",
                "Science-Fiction",
                "Thriller"
        };

        options.addAll(defaultOptions);
        options.add(""); // Blank Option

        ComboBox<String> cmbGenre = new ComboBox<>(options);
        cmbGenre.setValue(options.get(options.indexOf("")));

        Label lblReview = new Label("Review: ");
        TextArea txtReview = new TextArea();

        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            int width1 = 300;
            int height1 = 300;


            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);


            primaryStage.setScene(sceneHome);
        };

        EventHandler<ActionEvent> loadSearchRecord = actionEvent -> {

            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);

            String[] searchCriteria = {
                    txtId.getText(),
                    txtIsbn.getText().replace("-", ""),
                    txtTitle.getText(),
                    txtAuthor.getText(),
                    txtPages.getText(),
                    cmbGenre.getValue(),
                    txtReview.getText()
            };

            fileHandler = new FileHandling("bookRecords.csv");

            Object[] searchResultsObj = fileHandler.searchFile(searchCriteria).toArray(); // As an array
            String[] searchResult = new String[searchResultsObj.length];

            int index = 0;
            for (Object obj : searchResultsObj) {
                searchResult[index] = obj.toString(); // Convert to String
                index++;
            }

            Scene sceneSearchResult = getSceneSearchResult(searchResult);
            primaryStage.setScene(sceneSearchResult);

        };


        btnHome.setOnAction(loadSceneHome);
        btnSearch.setOnAction(loadSearchRecord);


        // Form layout
        VBox layoutSearchForm = new VBox();
        layoutSearchForm.setPadding(new Insets(10, 0, 10, 0));
        layoutSearchForm.setAlignment(Pos.CENTER);

        layoutSearchForm.getChildren().addAll(
                lblDesc, btnHome,
                lblId, txtId,
                lblIsbn, txtIsbn,
                lblTitle, txtTitle,
                lblAuthor, txtAuthor,
                lblPages, txtPages,
                lblGenre, cmbGenre,
                lblReview, txtReview,
                btnSearch
        );

        return new Scene(layoutSearchForm, 300, 250);
    }

    // Creates the scene to show the search result
    private Scene getSceneSearchResult(String[] details) {
        fileHandler = new FileHandling("bookRecords.csv");

        primaryStage.setTitle("Book Record System - Search Result");

        int width = 900;
        int height = 500;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Button btnHome = new Button("Home");

        EventHandler<ActionEvent> loadSceneHome = actionEvent -> {

            int width1 = 300;
            int height1 = 300;

            primaryStage.setMinWidth(width1);
            primaryStage.setMaxWidth(width1);
            primaryStage.setWidth(width1);

            primaryStage.setMinHeight(height1);
            primaryStage.setMaxHeight(height1);
            primaryStage.setHeight(height1);

            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadSceneHome);

        Label lblDesc = new Label("Use this form to view search results, " +
                "right click a row, to view in more detail.");

        TableView<Book> table = getTable(details); // Creates Table


        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) { // On right click

                Book selectedItem = table.getSelectionModel().getSelectedItem();

                Scene sceneSpecific = getSceneViewSpecific(selectedItem);  // View the item
                primaryStage.setScene(sceneSpecific);

            }
        });

        
        // Set layout
        VBox layoutSearchResult = new VBox();
        layoutSearchResult.setPadding(new Insets(10, 0, 10, 0));
        layoutSearchResult.setAlignment(Pos.CENTER);

        layoutSearchResult.getChildren().addAll(
                lblDesc, btnHome,
                table
        );


        return new Scene(layoutSearchResult, 300, 250);
    }

    // Creates the scene to select which statistics will be shown
    private Scene getSceneStatsHome() {
        primaryStage.setTitle("Book Record System - Statistics");
        fileHandler = new FileHandling("bookRecords.csv");

        int width = 300;
        int height = 300;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Label lblDesc = new Label("What statistics would you like to view?");
        Button btnHome = new Button("Home");


        // Buttons
        Button btnGenre = new Button("Pie Chart of Genres");
        Button btnPages = new Button("Graph of Number of Pages");
        Button btnAverages = new Button("Averages (Pages and Genres)");

        EventHandler<ActionEvent> loadHome = e -> {
            Scene sceneHome = getSceneHome();
            primaryStage.setScene(sceneHome);
        };

        EventHandler<ActionEvent> loadGenreStat = e -> {
            Scene sceneGenreStat = getSceneGenreStat();
            primaryStage.setScene(sceneGenreStat);
        };

        EventHandler<ActionEvent> loadPageStat = e -> {
            Scene scenePageStat = getScenePageStat();
            primaryStage.setScene(scenePageStat);
        };

        EventHandler<ActionEvent> loadPageAverages = e -> {
            Scene sceneAverages = getSceneAverages();
            primaryStage.setScene(sceneAverages);
        };

        btnHome.setOnAction(loadHome);

        btnGenre.setOnAction(loadGenreStat);
        btnPages.setOnAction(loadPageStat);
        btnAverages.setOnAction(loadPageAverages);


        // Layout details
        FlowPane layoutStat = new FlowPane();
        layoutStat.setHgap(20);
        layoutStat.setVgap(22);

        layoutStat.getChildren().addAll(lblDesc, btnHome, btnGenre, btnPages, btnAverages);

        return new Scene(layoutStat, 300, 250);

    }

    // Creates the scene to show a pie chart of the genres
    private Scene getSceneGenreStat() {

        primaryStage.setTitle("Book Record System - Genre Chart");

        int width = 500;
        int height = 500;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);

        Button btnHome = new Button("Home");

        EventHandler<ActionEvent> loadSceneHome = e -> {
            Scene sceneHome = getSceneHome();
            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadSceneHome);

        // Get the data for the Pie chart as a map
        Map<String, Integer> mapPieDate = fileHandler.countOccurrences(5);

        ObservableList<PieChart.Data> genreData = FXCollections.observableArrayList();

        // Add map data to the ObservableList
        for (String key : mapPieDate.keySet()) {
            genreData.add(new PieChart.Data(key, mapPieDate.get(key)));
        }

        // Create pie chart
        final PieChart genreChart = new PieChart(genreData);
        genreChart.setTitle("Genres"); // Add title

        
        // layout
        FlowPane layoutGenrePie = new FlowPane();
        layoutGenrePie.setHgap(20);
        layoutGenrePie.setVgap(22);


        layoutGenrePie.getChildren().addAll(btnHome, genreChart);

        return new Scene(layoutGenrePie, 300, 250);
    }

    // Creates the scene to show a line graph of the number of pages plotted against the IDs
    private Scene getScenePageStat() {

        primaryStage.setTitle("Book Record System - Pages Line Graph");

        int width = 600;
        int height = 500;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);

        Button btnHome = new Button("Home");

        EventHandler<ActionEvent> loadSceneHome = e -> {
            Scene sceneHome = getSceneHome();
            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadSceneHome);

        // Get data for line graph
        Map<Integer, Integer> mapLineGraph = fileHandler.getTwoIntFields(0, 4);

        
        // x axis
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setForceZeroInRange(false); // Doesn't need to start at 0


        // y axis
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false); // Doesn't need to start at 0


        final LineChart<Number, Number> linePageChart = new LineChart<>(xAxis, yAxis); // line chart with the axis

        linePageChart.setTitle("Number of Pages Over ID (Rough Estimation of Time)");  // title

        // axis labels
        xAxis.setLabel("ID Number");
        yAxis.setLabel("Number of Pages");


        // The series of number of pages
        XYChart.Series<Number, Number> numberPages = new XYChart.Series<>();
        numberPages.setName("Number of Pages Over ID (Rough Estimation of Time)");  // series name


        // adding the data to the line
        for (Integer key : mapLineGraph.keySet()) {
            numberPages.getData().add(new XYChart.Data<>(key, mapLineGraph.get(key)));
        }

        linePageChart.getData().add(numberPages);

        // layout
        FlowPane layoutPageLine = new FlowPane();
        layoutPageLine.setHgap(20);
        layoutPageLine.setVgap(22);


        layoutPageLine.getChildren().addAll(btnHome, linePageChart);

        return new Scene(layoutPageLine, 300, 250);
    }

    private Scene getSceneAverages() {

        primaryStage.setTitle("Book Record System - Averages");
        fileHandler = new FileHandling("bookRecords.csv");

        int width = 400;
        int height = 400;

        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setWidth(width);

        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setHeight(height);


        Label lblDesc = new Label("This Shows Statistics about the genres and number of pages.");
        Button btnHome = new Button("Home");

        Separator separatorA = new Separator();

        Label lblGenreHeading = new Label("Genre");
        lblGenreHeading.setStyle("-fx-font-weight: bold;-fx-font: 36 arial"); // Increased font and bold

        // Getting averages using FileHandling methods
        Label lblGenreMode = new Label("Mode");
        lblGenreMode.setStyle("-fx-font-weight: bold");
        Label lblGenreModeVal = new Label(fileHandler.getStringMode(5));

        Separator separatorB = new Separator();
        Label lblPageHeading = new Label("Pages");
        lblPageHeading.setStyle("-fx-font-weight: bold;-fx-font: 36 arial");
      

        Label lblPageMode = new Label("Mode");
        lblPageMode.setStyle("-fx-font-weight: bold");
        Label lblPageModeVal = new Label(fileHandler.getStringMode(4));
     

        Label lblPageMean = new Label("Mean");
        lblPageMean.setStyle("-fx-font-weight: bold");
        Label lblPageMeanVal = new Label(String.valueOf(fileHandler.getDoubleMean(4)));
       

        Label lblPageMedian = new Label("Median");
        lblPageMedian.setStyle("-fx-font-weight: bold");
        Label lblPageMedianVal = new Label(String.valueOf(fileHandler.getDoubleMedian(4)));
      

        Label lblPageRange = new Label("Range");
        lblPageRange.setStyle("-fx-font-weight: bold");
        Label lblPageRangeVal = new Label(String.valueOf(fileHandler.getIntRange(4)));
        Separator separatorC = new Separator();


        EventHandler<ActionEvent> loadHome = e -> {
            Scene sceneHome = getSceneHome();
            primaryStage.setScene(sceneHome);
        };

        btnHome.setOnAction(loadHome);


        // Setting layout
        VBox layoutAverage = new VBox();
        layoutAverage.setPadding(new Insets(10, 0, 10, 0));
        layoutAverage.setAlignment(Pos.CENTER);

        layoutAverage.getChildren().addAll(lblDesc, separatorA, lblGenreHeading,
                 lblGenreMode, lblGenreModeVal, separatorB, lblPageHeading,
                lblPageMode, lblPageModeVal,  lblPageMean, lblPageMeanVal,
                lblPageMedian, lblPageMedianVal,  lblPageRange, lblPageRangeVal, separatorC, btnHome
        );


        return new Scene(layoutAverage, 300, 250);
    }

    // Creates an error box
    private void errorBox(String title, String content) {

        Alert msgBox = new Alert(Alert.AlertType.ERROR);  // Type of error
        msgBox.setTitle(title); 
        msgBox.setHeaderText(title);
        msgBox.setContentText(content);
        msgBox.showAndWait().ifPresent(response -> {
        });


    }

    // Returns the details to create a table view
    private TableView<Book> getTable(String[] details) {


        TableView<Book> table = new TableView<>();  // TableView
        table.refresh();

        table.setEditable(true);

        TableColumn<Book, Integer> idColHeading = new TableColumn<>("ID");
        idColHeading.setCellValueFactory(new PropertyValueFactory<>("idInt"));  // Uses the Book.idInt property


        TableColumn<Book, String> isbnColHeading = new TableColumn<>("ISBN");
        isbnColHeading.setCellValueFactory(new PropertyValueFactory<>("isbn"));  // Uses the Book.isbn property

        TableColumn<Book, String> titleColHeading = new TableColumn<>("Title");
        titleColHeading.setCellValueFactory(new PropertyValueFactory<>("title"));  // Uses the Book.title property

        TableColumn<Book, String> authorColHeading = new TableColumn<>("Author");
        authorColHeading.setCellValueFactory(new PropertyValueFactory<>("author"));  // Uses the Book.author property

        TableColumn<Book, Integer> pagesColHeading = new TableColumn<>("Number of Pages");
        pagesColHeading.setCellValueFactory(new PropertyValueFactory<>("pagesInt"));  // Uses the Book.pagesInt property

        TableColumn<Book, String> genreColHeading = new TableColumn<>("Genre");
        genreColHeading.setCellValueFactory(new PropertyValueFactory<>("genre"));  // Uses the Book.genre property

        TableColumn<Book, String> reviewColHeading = new TableColumn<>("Review");
        reviewColHeading.setCellValueFactory(new PropertyValueFactory<>("review"));  // Uses the Book.review property


        TableColumn[] cols = {
                idColHeading,
                isbnColHeading,
                authorColHeading,
                titleColHeading,
                genreColHeading,
                pagesColHeading,
                reviewColHeading
        }; // The headings


        ObservableList<Book> recordRows = observableArrayList();

        recordRows.clear(); // Forces refresh if needed


        recordRows = getObservableList(details); // Gets an observable list

        table.setItems(recordRows);  // Sets the items
        table.getColumns().addAll(cols);  // adds the columns


        return table;
    }

    // Creates an observable list based on the passed details
    private ObservableList<Book> getObservableList(String[] details) {


        ObservableList<Book> observableList = FXCollections.observableArrayList();

        observableList.clear();  // Ensures its empty

        Book toAdd;


        String[] lineArray;

        for (String line : details) {


            lineArray = line.split(","); // Line as array

            try {
                toAdd = new Book(lineArray);  // Creates Book object from array
            } catch (ArrayIndexOutOfBoundsException e) {
                toAdd = new Book("", "", "", "", "", "", "");
            }

            observableList.add(toAdd);  // Adds item to list


        }

        return observableList;
    }

}
