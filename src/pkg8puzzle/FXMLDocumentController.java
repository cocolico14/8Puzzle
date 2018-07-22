/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pkg8puzzle;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.Color;

/**
 *
 * @author soheilchangizi
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button runButton;
    
    @FXML
    private Button editButton;
    
    @FXML
    private ChoiceBox method;
    
    @FXML
    private ToggleButton mtToggleButton;
    
    @FXML
    private ToggleButton mdToggleButton;
    
    @FXML
    private AnchorPane mainAnc;
    
    @FXML
    private TilePane tiles;
    
    @FXML
    private Label status;
    
    @FXML
    private Label time;
    
    @FXML
    private Label memory;
    
    @FXML
    private List<TextField> tilesT;
    
    private ToggleGroup group;
    
    private int posZ, xZ, yZ;
    
    private int[][] state;
    
    private boolean changed = false;
    
    
    private void changeLabelTile(int i, int val){
        String res = val + "";
        if(val == 0) res = "";
        ((Label)((StackPane)tiles.getChildren().get(i)).getChildren().get(1)).setText(res);
    }
    
    private void updatePosZ(String action){
        String tmp = "";
        switch(action){
            case "UP":
                tmp = ((Label)((StackPane)tiles.getChildren().get(posZ-3)).getChildren().get(1)).getText();
                changeLabelTile(posZ, Integer.valueOf(tmp));
                changeLabelTile(posZ-3, 0);
                posZ -= 3;
                break;
            case "DOWN":
                tmp = ((Label)((StackPane)tiles.getChildren().get(posZ+3)).getChildren().get(1)).getText();
                changeLabelTile(posZ, Integer.valueOf(tmp));
                changeLabelTile(posZ+3, 0);
                posZ += 3;
                break;
            case "LEFT":
                tmp = ((Label)((StackPane)tiles.getChildren().get(posZ-1)).getChildren().get(1)).getText();
                changeLabelTile(posZ, Integer.valueOf(tmp));
                changeLabelTile(posZ-1, 0);
                posZ -= 1;
                break;
            case "RIGHT":
                tmp = ((Label)((StackPane)tiles.getChildren().get(posZ+1)).getChildren().get(1)).getText();
                changeLabelTile(posZ, Integer.valueOf(tmp));
                changeLabelTile(posZ+1, 0);
                posZ += 1;
                break;
        }
        tmp = "";
    }
    
    @FXML
    private void handleRunButtonAction(ActionEvent event) throws IllegalStateException{
        
        runButton.setDisable(true);
        editButton.setDisable(true);
        PuzzleNode initM;
        ProblemTree pt;
        initM = new PuzzleNode(state, yZ, xZ);
        pt = new ProblemTree(initM);
        NumberFormat formatter = new DecimalFormat("#0.00000");
        reset();
        
        Thread thread = new Thread(() -> {
            Runnable updater = () -> {
                try{
                    if(!pt.getPath().isEmpty()) updatePosZ(pt.getPath().pop());
                }catch(NullPointerException e){
                    
                }
            };
            
            Platform.runLater(() -> {
                status.setText("Calculating the Path");
                status.setTextFill(Color.DARKORANGE);
            });
            
            
            long startTime = System.currentTimeMillis();
            switch(method.getValue().toString()){
                case "BFS":
                    pt.bfs(initM);
                    break;
                case "A*":
                    if(mtToggleButton.isSelected()) pt.astarMT(initM);
                    else pt.astarMD(initM);
                    break;
                case "RBFS":
                    if(mtToggleButton.isSelected()){
                        Heuristics hr = new Heuristics(initM.getState());
                        initM.setFn(hr.getMT());
                        pt.rbfsMT(initM, Integer.MAX_VALUE, 0);
                    }else{
                        Heuristics hhr = new Heuristics(initM.getState());
                        initM.setFn(hhr.getMD());
                        pt.rbfsMD(initM, Integer.MAX_VALUE, 0);
                    }
                    break;
                default:
                    pt.bfs(initM);
                    break;
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            
            Platform.runLater(() -> {
                time.setText(formatter.format((totalTime) / 1000d) + "s");
                memory.setText(pt.getMemoryUsed() + " node opened");
            });
            
            Platform.runLater(() -> {
                status.setText("Solved!");
                status.setTextFill(Color.DARKGREEN);
            });
            
            while(!pt.getPath().isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Platform.runLater(updater);
            }
            
            runButton.setDisable(false);
            editButton.setDisable(false);
        });
        thread.setDaemon(true);
        thread.start();
    }
    
    
    public EventHandler<KeyEvent> numericValidation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();
                if (txt_TextField.getText().length() >= max_Lengh) {
                    e.consume();
                }
                if(e.getCharacter().matches("[0-9.]")){
                    if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume();
                    }
                }else{
                    e.consume();
                }
            }
        };
    }
    
    @FXML
    public boolean stateValidation(){
        Set<Integer> check = new HashSet<>();
        int k =0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if(tilesT.get(k).getText().equals("")){
                    check.add(0);
                }else{
                    check.add(Integer.valueOf(tilesT.get(k).getText()));
                }
                k++;
            }
        }
        if(check.size() == 9){
            status.setText("Press Done Configuration");
            status.setTextFill(Color.DARKGREEN);
            return true;
        }
        status.setText("Not a Valid Init State!");
        status.setTextFill(Color.RED);
        return false;
    }
    
    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        if(!stateValidation()){
            status.setText("Not a Valid Init State!");
            status.setTextFill(Color.RED);
        }
        if(changed && stateValidation()){
            changed = false;
            editButton.setText("Edit");
            for (int i = 0; i < tilesT.size(); i++) {
                tilesT.get(i).setDisable(true);
                tilesT.get(i).setVisible(false);
                if(tilesT.get(i).getText().equals("")){
                    changeLabelTile(i, 0);
                }else{
                    changeLabelTile(i, Integer.valueOf(tilesT.get(i).getText()));
                }
            }
            int k = 0;
            for (int i = 0; i < state.length; i++) {
                for (int j = 0; j < state[i].length; j++) {
                    if(tilesT.get(k).getText().equals("")){
                        state[i][j] = 0;
                        posZ = k;
                        xZ = j;
                        yZ = i;
                    }else{
                        state[i][j] = Integer.valueOf(tilesT.get(k).getText());
                    }
                    k++;
                }
            }
            status.setText("Press Run to Solve / Edit to Change Configuration");
            status.setTextFill(Color.BLACK);
            runButton.setDisable(false);
        }else{
            changed = true;
            editButton.setText("Done");
            runButton.setDisable(true);
            for (int i = 0; i < tilesT.size(); i++) {
                tilesT.get(i).setDisable(false);
                tilesT.get(i).setVisible(true);
            }
        }
        
    }
    
    @FXML
    private void toggleAction(ActionEvent event){
        
        Toggle selectedTogger = group.getSelectedToggle();
        if(selectedTogger == mdToggleButton){
        }else if(selectedTogger == mtToggleButton){
        }
        
    }
    
    @FXML
    private void choiceBoxAction(ActionEvent event){
        
        if(method.getValue().equals("BFS")){
            mdToggleButton.setDisable(true);
            mtToggleButton.setDisable(true);
        }else{
            mdToggleButton.setDisable(false);
            mtToggleButton.setDisable(false);
        }
        
    }
    
    private void reset(){
        
        time.setText("");
        memory.setText("");
        posZ = (yZ * 3) + xZ;
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                changeLabelTile(k, state[i][j]);
                k++;
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int[][] stateHard = {{2,8,1},{4,6,3},{0,7,5}};
        state = stateHard;
        posZ = 6;
        xZ = 0;
        yZ = 2;
        
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                changeLabelTile(k, state[i][j]);
                k++;
            }
        }
        
        status.setText("Press Run to Solve / Edit to Change Configuration");
        status.setTextFill(Color.BLACK);
        
        for (int i = 0; i < tilesT.size(); i++) {
            tilesT.get(i).setDisable(true);
            tilesT.get(i).setVisible(false);
            tilesT.get(i).addEventFilter(KeyEvent.KEY_TYPED, numericValidation(1));
        }
        
        method.getItems().removeAll(method.getItems());
        method.getItems().addAll("BFS", "RBFS", "A*");
        method.getSelectionModel().select("BFS");
        
        group = new ToggleGroup();
        mdToggleButton.setToggleGroup(group);
        mtToggleButton.setToggleGroup(group);
        mdToggleButton.setSelected(true);
        mtToggleButton.setSelected(false);
        
    }
    
}
