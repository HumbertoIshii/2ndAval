package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AlunoTableModel extends AbstractTableModel {
    private ArrayList<Aluno> alunos;
    private String[] colunas = {"Nome", "Data de Nascimento", "Peso", "Altura", "CPF"};

    public AlunoTableModel(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public int getRowCount() {
        return this.alunos.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aluno aluno = this.alunos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return aluno.getNome();
                case 1:
                    return aluno.getDataNascimento();
                    case 2:
                        return aluno.getPeso();
                        case 3:
                            return aluno.getAltura();
                            case 4:
                                return aluno.getCpf();
                                default:
                                    return null;
        }
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
        fireTableDataChanged();
    }
}
