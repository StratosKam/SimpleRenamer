/*
 * This file is part of SimpleRenamer.
 *
 * JComplete is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SimpleRenamer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JComplete.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Stratos Kamadanis
 */
package javaFx;

import datatypes.FileItem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Processor;

import java.util.HashMap;

public class SimpleRenamerFX extends Application {
    private Processor processor;

    private Scene mainScene;
    private Stage mainStage;
    private BorderPane mainPane;
    private GridPane filesList;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        initProc();

        filesList=new GridPane();
        mainPane = new BorderPane();
//        mainPane.setCenter(filesList);

        Button renameCalculateButton=new Button("Suggest names");
        renameCalculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                processor.calculateProposed();
                updateFilesView();
            }
        });
        Button renameButton=new Button("logic.Rename");
        renameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                processor.doRename();
                updateFilesView();
            }
        });
        Button clearButton=new Button("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                processor.clear();
                updateFilesView();
            }
        });



        HBox bottom=new HBox();
        bottom.getChildren().add(renameCalculateButton);
        bottom.getChildren().add(clearButton);
        bottom.getChildren().add(renameButton);
        mainPane.setBottom(bottom);



        mainScene = new Scene(mainPane, 550, 400);
        mainScene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.LINK);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        mainScene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    db.getFiles().forEach(processor::addFile);
                    updateSelectedFilesView();
//                    Platform.runLater(()-> db.getFiles().stream().forEach(file->processor.addFile(file)));
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        mainStage.setTitle("The Renames Guy");
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    private void updateFilesView() {
        mainPane.setCenter(getFilesPane());
    }

    private void updateSelectedFilesView() {
        mainPane.setCenter(getSelectedFilesPane());
    }

    private Node getSelectedFilesPane(){
        ScrollPane scrollPane=new ScrollPane();

        GridPane pane=new GridPane();
        int i=0;
        HashMap<Integer, FileItem> files=processor.getSelectedFiles();
        for(Integer key:files.keySet()){
            pane.addRow(i++, getNodeFileItem(files.get(key)));
        }
        scrollPane.setContent(pane);
        return scrollPane;
    }
    private Node getFilesPane(){
        ScrollPane scrollPane=new ScrollPane();

        GridPane pane=new GridPane();
        int i=0;
        HashMap<Integer, FileItem> files=processor.getSelectedFiles();
        HashMap<Integer,String> proposed=processor.getProposed();
        for(Integer key:files.keySet()){
            pane.addRow(i++, getNodeFileItem(files.get(key)),getNodeFileItem(proposed.get(key)));
        }
        scrollPane.setContent(pane);
        return scrollPane;
    }

    private Node getNodeFileItem(FileItem fileItem){
        Label label=new Label(fileItem.getFileName());
        return label;
    }
    private Node getNodeFileItem(String fileName){
        Label label=new Label(fileName);
        return label;
    }
    private void initProc() {
        processor=new Processor();
    }
}
