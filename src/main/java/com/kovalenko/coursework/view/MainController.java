package com.kovalenko.coursework.view;

import com.kovalenko.coursework.model.Corpus;
import com.kovalenko.coursework.service.MainService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@FXMLController
public class MainController {

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private TableView<WordTable> tableViewA;

    @FXML
    private TableView<WordTable> tableViewB;

    @FXML
    private TableView<WordTable> tableViewC;

    @FXML
    private Button button;

    @FXML
    private Label totalWordsLabel;

    @FXML
    private Label uniqueWordsLabel;

    @Autowired
    private MainService mainService;

    private Corpus corpus;

    public void initialize() {
        initTable(Arrays.asList(tableViewA, tableViewB, tableViewC));

        button.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(null);
            if(file != null) {
                try {
                    corpus = mainService.generateStat(new FileInputStream(file));
                    displayResults();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void displayResults() {
        setVisible(Arrays.asList(uniqueWordsLabel, totalWordsLabel, tableViewA, tableViewB, tableViewC));
        totalWordsLabel.setText(String.format("Total words: %d", corpus.getQuantity()));
        uniqueWordsLabel.setText(String.format("Unique words: %d", corpus.getUniqueWords()));

        ObservableList<WordTable> dataA = FXCollections.observableArrayList();
        ObservableList<WordTable> dataB = FXCollections.observableArrayList();
        ObservableList<WordTable> dataC = FXCollections.observableArrayList();

        for (Corpus.Word word : corpus.getWords()) {
            switch (word.getType()) {
                case A:
                    dataA.add(new WordTable(word));
                    break;
                case B:
                    dataB.add(new WordTable(word));
                    break;
                case C:
                    dataC.add(new WordTable(word));
                    break;
            }
        }
        tableViewA.setItems(dataA);
        tableViewB.setItems(dataB);
        tableViewC.setItems(dataC);
    }

    private void initTable(List<TableView> tableViews) {
        for (TableView tableView : tableViews) {
            TableColumn wordColumn = new TableColumn("Word");
            wordColumn.setCellValueFactory(new PropertyValueFactory<WordTable, String>("word"));

            TableColumn percentColumn = new TableColumn("%");
            percentColumn.setCellValueFactory(new PropertyValueFactory<WordTable, String>("frequency"));

            tableView.getColumns().addAll(wordColumn, percentColumn);
        }
    }

    private void setVisible(List<Control> controls) {
        controls.forEach(control -> control.setVisible(true));
    }

    public static class WordTable {
        private final SimpleStringProperty word;
        private final SimpleDoubleProperty frequency;

        public WordTable(Corpus.Word word) {
            this.word = new SimpleStringProperty(word.getValue());
            this.frequency = new SimpleDoubleProperty(word.getFrequency());
        }

        public String getWord() {
            return word.get();
        }


        public void setWord(String word) {
            this.word.set(word);
        }

        public double getFrequency() {
            return frequency.get();
        }

        public void setFrequency(double frequency) {
            this.frequency.set(frequency);
        }
    }

}

