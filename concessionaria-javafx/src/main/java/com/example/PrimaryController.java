package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.ClienteDao;
import com.example.data.VeiculoDao;
import com.example.model.Cliente;
import com.example.model.Veiculo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ComboBox;
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
    @FXML private ComboBox<Cliente> cboCliente;

    @FXML TableView<Veiculo> tabelaVeiculos;
    @FXML TableColumn<Veiculo, String> colMarca;
    @FXML TableColumn<Veiculo, String> colModelo;
    @FXML TableColumn<Veiculo, Integer> colAno;
    @FXML TableColumn<Veiculo, BigDecimal> colValor;
    @FXML TableColumn<Veiculo, Cliente> colCLiente;

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;

    @FXML TableView<Cliente> tabelaClientes;
    @FXML TableColumn<Cliente, String> colNome;
    @FXML TableColumn<Cliente, String> colEmail;


    private VeiculoDao veiculoDao = new VeiculoDao();
    private ClienteDao clienteDao = new ClienteDao();

    public void salvarVeiculo(){

        var veiculo = new Veiculo(
            null, 
            txtModelo.getText(), 
            txtMarca.getText(), 
            Integer.valueOf(txtAno.getText()), 
            new BigDecimal(txtValor.getText()),
            cboCliente.getSelectionModel()
                        .getSelectedItem()
        );

        try{
            veiculoDao.inserir(veiculo);
            tabelaVeiculos.getItems().add(veiculo);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void salvarCliente(){

        var cliente = new Cliente(
            null, 
            txtNome.getText(), 
            txtEmail.getText()
        );

        try{
            clienteDao.inserir(cliente);
            tabelaClientes.getItems().add(cliente);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void consultarVeiculo() {
        try {
            veiculoDao.listarTodos().forEach(veiculo -> tabelaVeiculos.getItems().add(veiculo));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possível carregar os dados do banco");
        }
    }

    private void consultarCliente() {
        try {
            clienteDao.listarTodos().forEach(cliente -> tabelaClientes.getItems().add(cliente));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possível carregar os dados do banco");
        }
    }

    public void apagarVeiculo(){
        if(!confirmarExclusao()) return;
        
        try {
            var veiculoSelecionado = tabelaVeiculos.getSelectionModel().getSelectedItem();
            if (veiculoSelecionado == null) {
                mostrarMensagemDeErro("Você deve selecionar um veículo para apagar");
                return;
            }
            
            veiculoDao.apagar(veiculoSelecionado);
            tabelaVeiculos.getItems().remove(veiculoSelecionado);
            
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o veículo do banco de dados");
        }

    }

    public void apagarCliente(){
        if(!confirmarExclusao()) return;
        
        try {
            var clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
            if (clienteSelecionado == null) {
                mostrarMensagemDeErro("Você deve selecionar um cliente para apagar");
                return;
            }
            
            clienteDao.apagar(clienteSelecionado);
            tabelaClientes.getItems().remove(clienteSelecionado);
            
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o cliente do banco de dados");
        }

    }

    private boolean confirmarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o item selecionado? Essa ação não poderá ser desfeita.");
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

    public void carregarComboBoxCLiente() {
        try {
            cboCliente.getItems().addAll(clienteDao.listarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao carregar dados dos clientes!");
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(event -> {
            atualizarVeiculo(event.getRowValue().modelo(event.getNewValue()));
        });

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colMarca.setOnEditCommit(event -> {
            atualizarVeiculo(event.getRowValue().marca(event.getNewValue()));
        });

        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAno.setOnEditCommit(event -> {
            atualizarVeiculo(event.getRowValue().ano(event.getNewValue()));
        });

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());

        colCLiente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        
        tabelaVeiculos.setEditable(true);
        tabelaClientes.setEditable(true);

        consultarVeiculo();
        consultarCliente();
        carregarComboBoxCLiente();
    }

    private void atualizarVeiculo(Veiculo veiculo) {
        try {
            veiculoDao.atualizar(veiculo);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao atualizar dados do veículo");
        }
    }

    private void atualizarCliente(Cliente cliente) {
        try {
            clienteDao.atualizar(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao atualizar dados do cliente");
        }
    }

   
}
