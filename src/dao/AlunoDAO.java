package dao;

import factory.ConnectionFactory;
import model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoDAO {
    private Connection connection;

    public AlunoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void inserirAluno (Aluno aluno) {

        String sql = "INSERT INTO aluno(nome, dataNasc, peso, altura, cpf) VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setFloat(3, aluno.getPeso());
            stmt.setFloat(4, aluno.getAltura());
            stmt.setString(5, aluno.getCpf());

            stmt.execute();
        }
        catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public ArrayList<Aluno> getAllAlunos() {
        ArrayList<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT nome, dataNasc, peso, altura, cpf FROM aluno";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String dataNascimento = rs.getString("dataNasc");
                float peso = rs.getFloat("peso");
                float altura = rs.getFloat("altura");
                String cpf = rs.getString("cpf");

                Aluno aluno = new Aluno(nome, dataNascimento, peso, altura, cpf);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public ArrayList<Aluno> getAllAlunosBy(String s1, String s2) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT nome, dataNasc, peso, altura, cpf FROM aluno WHERE " + s1 + " LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + s2 + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String dataNascimento = rs.getString("dataNasc");
                    float peso = rs.getFloat("peso");
                    float altura = rs.getFloat("altura");
                    String cpf = rs.getString("cpf");

                    Aluno aluno = new Aluno(nome, dataNascimento, peso, altura, cpf);
                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public Aluno getAluno(String cpf) {
        String sql = "SELECT * FROM aluno WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Aluno(
                        rs.getString("nome"),
                        rs.getString("dataNasc"),
                        rs.getFloat("peso"),
                        rs.getFloat("altura"),
                        rs.getString("cpf")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAluno (Aluno aluno, String nome, String dataNascimento, float peso, float altura, String cpf) {
        String sql = "UPDATE aluno SET nome = ?, dataNasc = ?, peso = ?, altura = ?, cpf = ? WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, dataNascimento);
            stmt.setFloat(3, peso);
            stmt.setFloat(4, altura);
            stmt.setString(5, cpf);
            stmt.setString(6, aluno.getCpf());

            int rowsAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerAluno(Aluno aluno) {
        String sql = "DELETE FROM aluno WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
