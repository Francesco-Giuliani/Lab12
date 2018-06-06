package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelectRiver(ActionEvent event) {
    	
    	River selected = this.boxRiver.getValue();
    	if(selected == null) {
    		this.txtResult.setText("No valid river selected. Select a valid river.\n");
    		return;
    	}
    	model.setCurrentlySelectedRiver(selected);
    	this.updateShownRiverValues();
    }

    private void updateShownRiverValues() {
		this.txtStartDate.setText(model.getCurrentlySelectedRiver().getStartDate().toString());
		this.txtEndDate.setText(model.getCurrentlySelectedRiver().getEndDate().toString());
		this.txtNumMeasurements.setText(""+model.getCurrentlySelectedRiver().getFlows().size());
		this.txtFMed.setText(""+model.getCurrentlySelectedRiver().getFlowAvg());
	}

	@FXML
    void doSimulation(ActionEvent event) {
		
		double k =0.0;
		
		if(model.getCurrentlySelectedRiver()==null) {
			this.txtResult.setText("No rivers selected. Select a river from the box\n");
			return;
		}
		String kstr = this.txtK.getText();
		try {
			k =Double.parseDouble(kstr);
		}catch(Exception e) {
			e.printStackTrace();
			this.txtResult.appendText("Type a double value for k.\n");
			return;
		}
		model.simulate(k);
		this.txtResult.appendText(model.printSimulationResults()+"\n");
    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";

    }
    public void setModel(Model model) {
    	this.model = model;
    	this.boxRiver.getItems().setAll(model.getAllRivers());
    }
    
}
