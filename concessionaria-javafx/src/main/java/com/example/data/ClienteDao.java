package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Cliente;

public class ClienteDao {

    private Connection conexao;
    
     public ClienteDao(){
        try {
            conexao = ConnectionFactory.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Cliente cliente) throws SQLException{
        var sql = "INSERT INTO clientes (nome, email) VALUES (?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, cliente.getNome());
        comando.setString(2, cliente.getEmail());
        comando.executeUpdate();
        
    }

    public List<Cliente> listarTodos() throws SQLException{
        var comando = conexao.prepareStatement("SELECT * FROM clientes");
        var resultado = comando.executeQuery();

        var lista = new ArrayList<Cliente>();

        while(resultado.next()){
            lista.add (
                new Cliente(
                    resultado.getLong("id"), 
                    resultado.getString("nome"), 
                    resultado.getString("email")
                 )
            );
        }

        return lista;
    }

    public void apagar(Cliente cliente) throws SQLException{
        var comando = conexao.prepareStatement("DELETE FROM clientes WHERE id=?");
        comando.setLong(1, cliente.getId());
        comando.executeUpdate();
    }

    public void atualizar(Cliente cliente) throws SQLException{
        var comando = conexao.prepareStatement("UPDATE clientes SET nome=?, email=? WHERE id=?");
        comando.setString(1, cliente.getNome());
        comando.setString(2, cliente.getEmail());
        comando.setLong(5, cliente.getId());
        comando.executeUpdate();
    }
}
