package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.VeiculoDao;
import com.example.model.Veiculo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController implements Initializable {

    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtAno;
    @FXML private TextField txtValor;
    
    @FXML TableView<Veiculo> tabela;
    // obj e tipo
    @FXML TableColumn<Veiculo, String> colMarca;
    @FXML TableColumn<Veiculo, String> colModelo;
    @FXML TableColumn<Veiculo, Integer> colAno;
    @FXML TableColumn<Veiculo, BigDecimal> colValor;
    

    public void salvar(){

            var veiculo = new Veiculo(
            null,
            txtMarca.getText(),
            txtModelo.getText(),
            //conversão de string para int
            Integer.valueOf(txtAno.getText()),
            //conversão de string para bigDecimal
            new BigDecimal(txtValor.getText())
        );

        var veiculoDao = new VeiculoDao();
        try{
            veiculoDao.inserir(veiculo);
            tabela.getItems().add(veiculo);

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    //como cada coluna vai carregar um obj
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }
}
