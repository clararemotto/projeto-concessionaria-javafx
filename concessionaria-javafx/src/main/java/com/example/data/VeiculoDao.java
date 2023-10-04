package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Cliente;
import com.example.model.Veiculo;

public class VeiculoDao {

     private Connection conexao;
    
    public VeiculoDao(){
        try {
            conexao = ConnectionFactory.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Veiculo veiculo) throws SQLException{
        var sql = "INSERT INTO veiculos (marca, modelo, ano, valor) VALUES (?, ?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, veiculo.getMarca());
        comando.setString(2, veiculo.getModelo());
        comando.setInt(3, veiculo.getAno());
        comando.setBigDecimal(4, veiculo.getValor());
        comando.setLong(5, veiculo.getCliente().getId());
        comando.executeUpdate();
        
    }

    public List<Veiculo> listarTodos() throws SQLException{
        var comando = conexao.prepareStatement("SELECT * FROM veiculos");
        var resultado = comando.executeQuery();

        var lista = new ArrayList<Veiculo>();

        while(resultado.next()){
            lista.add (
                new Veiculo(
                    resultado.getLong("id"), 
                    resultado.getString("marca"), 
                    resultado.getString("modelo"), 
                    resultado.getInt("ano"), 
                    resultado.getBigDecimal("valor"),
                    new Cliente(
                        resultado.getLong("cliente_id"),
                        resultado.getString("nome"),
                        resultado.getString("email"))
                )
            );
        }
        return lista;
    }

    public void apagar(Veiculo veiculo) throws SQLException{
        var comando = conexao.prepareStatement("DELETE FROM veiculos WHERE id=?");
        comando.setLong(1, veiculo.getId());
        comando.executeUpdate();
    }

    public void atualizar(Veiculo veiculo) throws SQLException{
        var comando = conexao.prepareStatement("UPDATE veiculos SET marca=?, modelo=?, ano=?, valor=? WHERE id=?");
        comando.setString(1, veiculo.getMarca());
        comando.setString(2, veiculo.getModelo());
        comando.setInt(3, veiculo.getAno());
        comando.setBigDecimal(4, veiculo.getValor());
        comando.setLong(5, veiculo.getId());
        comando.executeUpdate();
    }
    
}
