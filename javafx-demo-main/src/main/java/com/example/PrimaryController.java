package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.VeiculoDao;
import com.example.model.Veiculo;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController implements Initializable {

    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtAno;
    @FXML private TextField txtValor;

    @FXML TableView<Veiculo> tabela;
    @FXML TableColumn<Veiculo, String> colMarca;
    @FXML TableColumn<Veiculo, String> colModelo;
    @FXML TableColumn<Veiculo, Integer> colAno;
    @FXML TableColumn<Veiculo, BigDecimal> colValor;

    private VeiculoDao veiculoDao = new VeiculoDao();

    public void salvar(){

        var veiculo = new Veiculo(
            null, 
            txtModelo.getText(), 
            txtMarca.getText(), 
            Integer.valueOf(txtAno.getText()), 
            new BigDecimal(txtValor.getText())
        );

        try{
            veiculoDao.inserirVeiculo(veiculo);
            tabela.getItems().add(veiculo);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void consultar() {
        try {
            veiculoDao.listarTodos().forEach(veiculo -> tabela.getItems().add(veiculo));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possível carregar os dados do banco");
        }
    }

    public void apagar(){
        if(!confirmarExclusao()) return;
        
        try {
            var veiculoSelecionado = tabela.getSelectionModel().getSelectedItem();
            if (veiculoSelecionado == null) {
                mostrarMensagemDeErro("Você deve selecionar um veículo para apagar");
                return;
            }
            
            veiculoDao.apagarVeiculo(veiculoSelecionado);
            tabela.getItems().remove(veiculoSelecionado);
            
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o veículo do banco de dados");
        }

    }

    private boolean confirmarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o veículo selecionado? Essa ação não poderá ser desfeita.");
        return alert.showAndWait()
                .get()
                .getButtonData()
                .equals(ButtonData.OK_DONE);
    }

    private void mostrarMensagemDeErro(String mensagem) {
        var alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText(mensagem);
        alert.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(event -> {
            atualizar(event.getRowValue().modelo(event.getNewValue()));
        });

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colMarca.setOnEditCommit(event -> {
            atualizar(event.getRowValue().marca(event.getNewValue()));
        });

        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAno.setOnEditCommit(event -> {
            atualizar(event.getRowValue().ano(event.getNewValue()));
        });

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(event -> {
            atualizar(event.getRowValue().valor(event.getNewValue()));
        });

        tabela.setEditable(true);

        consultar();

    }

    private void atualizar(Veiculo veiculo) {
        try {
            veiculoDao.atualizarVeiculo(veiculo);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao atualizar dados do veículo");
        }
    }

   
}
