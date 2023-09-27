package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Cliente;

public class ClienteDao {

    final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final String USER = "pf1389";
    final String PASS = "fiap23";

    public void inserir(Cliente cliente) throws SQLException{

        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO clientes (nome, email) VALUES (?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, cliente.getNome());
        comando.setString(2, cliente.getEmail());
        comando.executeUpdate();

        conexao.close();
        
    }

    public List<Cliente> listarTodos() throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
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

        conexao.close();
        return lista;
    }

    public void apagar(Cliente cliente) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM clientes WHERE id=?");
        comando.setLong(1, cliente.getId());
        comando.executeUpdate();
        conexao.close();
    }

    public void atualizar(Cliente cliente) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE clientes SET nome=?, email=? WHERE id=?");
        comando.setString(1, cliente.getNome());
        comando.setString(2, cliente.getEmail());
        comando.setLong(5, cliente.getId());
        comando.executeUpdate();
        conexao.close();
    }
    
}
