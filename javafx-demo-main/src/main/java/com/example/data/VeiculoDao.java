package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Veiculo;

public class VeiculoDao {

    final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final String USER = "pf1389";
    final String PASS = "fiap23";

    public void inserir(Veiculo veiculo) throws SQLException{

        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO veiculos (marca, modelo, ano, valor) VALUES (?, ?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, veiculo.getMarca());
        comando.setString(2, veiculo.getModelo());
        comando.setInt(3, veiculo.getAno());
        comando.setBigDecimal(4, veiculo.getValor());
        comando.executeUpdate();

        conexao.close();
        
    }

    public List<Veiculo> listarTodos() throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
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
                    resultado.getBigDecimal("valor")
                 )
            );
        }

        conexao.close();
        return lista;
    }

    public void apagar(Veiculo veiculo) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM veiculos WHERE id=?");
        comando.setLong(1, veiculo.getId());
        comando.executeUpdate();
        conexao.close();
    }

    public void atualizar(Veiculo veiculo) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE veiculos SET marca=?, modelo=?, ano=?, valor=? WHERE id=?");
        comando.setString(1, veiculo.getMarca());
        comando.setString(2, veiculo.getModelo());
        comando.setInt(3, veiculo.getAno());
        comando.setBigDecimal(4, veiculo.getValor());
        comando.setLong(5, veiculo.getId());
        comando.executeUpdate();
        conexao.close();
    }
    
}
