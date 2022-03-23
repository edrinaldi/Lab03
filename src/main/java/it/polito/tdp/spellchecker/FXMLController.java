/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Dictionary model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbLingua"
    private ComboBox<String> cmbLingua; // Value injected by FXMLLoader

    @FXML // fx:id="txtErrori"
    private Label txtErrori; // Value injected by FXMLLoader

    @FXML // fx:id="txtInput"
    private TextArea txtInput; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML // fx:id="txtTempo"
    private Label txtTempo; // Value injected by FXMLLoader

    @FXML
    void doChooseLanguage(ActionEvent event) {
    	String language = cmbLingua.getValue();
    	if (language != null) {
    		this.model.loadDictionary(language);
    	}
    }
    
    @FXML
    void doClearText(ActionEvent event) {
    	this.txtInput.clear();
    	this.txtRisultato.clear();
    	this.txtErrori.setText("");
    	this.txtTempo.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long start = System.nanoTime();
    	long end;
    	this.txtRisultato.clear();
    	
    	String input = this.txtInput.getText().replaceAll("[.,\\/#!$%\\^&\\*;:{}=?\\-_`~()\\[\\]\"]", "").toLowerCase();
    	String[] array = input.split(" ");
    	List<RichWord> inputTextList = new ArrayList<RichWord>();
    	int numWrong = 0;
    	
    	if (this.cmbLingua.getValue() == null) {
    		this.txtRisultato.setText("Please choose the language.");
    		return;
    	}
    	
    	for (String s : array) {
    		inputTextList.add(new RichWord(s, false));
    	}
    	
    	inputTextList = this.model.spellCheckText(inputTextList);
    	
    	for (RichWord w : inputTextList) {
    		if (!w.isCorretta()) {
    	    	this.txtRisultato.appendText(w.getParola() + "\n");
    	    	numWrong++;
    		}
    	}
    	
    	if (numWrong > 1) {
        	this.txtErrori.setText("The text contains " + numWrong + " errors");
    	}
    	else if (numWrong == 1) {
        	this.txtErrori.setText("The text contains " + numWrong + " error");
    	}
    	else {
        	this.txtErrori.setText("The text doesn't contain any errors");
    	}
    	end = System.nanoTime();
    	this.txtTempo.setText("Spell check completed in " + (double) ((end - start) / Math.pow(10, 9)) + " seconds");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbLingua != null : "fx:id=\"cmbLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTempo != null : "fx:id=\"txtTempo\" was not injected: check your FXML file 'Scene.fxml'.";

        cmbLingua.getItems().clear();
    	cmbLingua.getItems().add("Italian");
    	cmbLingua.getItems().add("English");
    }

    public void setModel(Dictionary model) {
    	this.model = model;
    }
}
