package gui;

import dao.AlunoDAO;
import model.Aluno;
import model.AlunoTableModel;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Screen {
    private JPanel contentPane;
    private JTextField nameField;
    private JFormattedTextField dataNascField;
    private JFormattedTextField cpfField;
    private JTextField pesoField;
    private JTextField alturaField;
    private JButton salvarButton;
    private JComboBox nomesDropdown;
    private JButton editarButton;
    private JButton deletarButton;
    private JButton IMCButton;
    private JButton cancelarButton;
    private JTable alunosTable;
    private JButton PesquisarButton;
    private JTextField pesquisarField;
    private JComboBox pesquisarDropdown;


    private boolean editMode = false;
    private Aluno aluno = new Aluno("","",0,0,"");
    private AlunoDAO alunoDAO = new AlunoDAO();

    public Screen() {
        cancelarButton.setEnabled(false);

        AlunoTableModel alunoTableModel = new AlunoTableModel(alunoDAO.getAllAlunos());
        alunosTable.setModel(alunoTableModel);

        DefaultComboBoxModel modelNameAndCpf = new DefaultComboBoxModel();
        for (Aluno aluno : alunoDAO.getAllAlunos()) {
            modelNameAndCpf.addElement(aluno.getNome());
        }
        nomesDropdown.setModel(modelNameAndCpf);


        List<String> options = Arrays.asList("Todos", "Nome", "Data Nasc.", "Peso", "Altura", "CPF");
        DefaultComboBoxModel<String> modelPesquisar = new DefaultComboBoxModel<>(options.toArray(new String[0]));
        pesquisarDropdown.setModel(modelPesquisar);



        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editMode) {
                    alunoDAO.updateAluno(aluno,
                            nameField.getText(),
                            dataNascField.getText(),
                            Float.parseFloat(pesoField.getText()),
                            Float.parseFloat(alturaField.getText()),
                            cpfField.getText());

                    editMode = false;
                    salvarButton.setText("Salvar");
                    cancelarButton.setEnabled(false);

                    aluno.setNome(nameField.getText());
                    aluno.setDataNascimento(dataNascField.getText());
                    aluno.setPeso(Float.parseFloat(pesoField.getText()));
                    aluno.setAltura(Float.parseFloat(alturaField.getText()));
                    aluno.setCpf(cpfField.getText());

                    alunoTableModel.setAlunos(alunoDAO.getAllAlunos());
                    alunoTableModel.fireTableDataChanged();

                    modelNameAndCpf.removeAllElements();
                    for (Aluno aluno : alunoDAO.getAllAlunos()) {
                        modelNameAndCpf.addElement(aluno.getNome());
                    }

                    nameField.setText("");
                    dataNascField.setText("");
                    pesoField.setText("");
                    alturaField.setText("");
                    cpfField.setText("");

                } else {
                    if (nameField.getText().isEmpty() || pesoField.getText().isEmpty() || alturaField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(contentPane,"Preencha todos os campos");
                    }else {
                        aluno.setNome(nameField.getText());
                        aluno.setDataNascimento(dataNascField.getText());
                        aluno.setPeso(Float.parseFloat(pesoField.getText()));
                        aluno.setAltura(Float.parseFloat(alturaField.getText()));
                        aluno.setCpf(cpfField.getText());

                        alunoDAO.inserirAluno(aluno);
                        modelNameAndCpf.addElement(aluno.getNome());
                        alunoTableModel.setAlunos(alunoDAO.getAllAlunos());
                        alunoTableModel.fireTableDataChanged();

                        JOptionPane.showMessageDialog(contentPane, "Aluno inserido com sucesso!");

                        nameField.setText("");
                        dataNascField.setText("");
                        alturaField.setText("");
                        pesoField.setText("");
                        cpfField.setText("");
                    }
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMode = true;
                cancelarButton.setEnabled(true);
                salvarButton.setText("Atualizar");

                aluno = findByName(nomesDropdown.getSelectedItem().toString());

                nameField.setText(aluno.getNome());
                dataNascField.setText(aluno.getDataNascimento());
                alturaField.setText(Float.toString(aluno.getAltura()));
                pesoField.setText(Float.toString(aluno.getPeso()));
                cpfField.setText(aluno.getCpf());
            }
        });


        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarButton.setText("Salvar");
                editMode = false;
                nameField.setText("");
                dataNascField.setText("");
                alturaField.setText("");
                pesoField.setText("");
                cpfField.setText("");

                cancelarButton.setEnabled(false);
            }
        });


        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarButton.doClick();

                int response = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente deletar o aluno?","Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION) {
                    aluno = findByName(nomesDropdown.getSelectedItem().toString());
                    alunoDAO.removerAluno(aluno);

                    modelNameAndCpf.removeAllElements();
                    for (Aluno aluno : alunoDAO.getAllAlunos()) {
                        modelNameAndCpf.addElement(aluno.getNome());
                    }


                    alunoTableModel.setAlunos(alunoDAO.getAllAlunos());
                    alunoTableModel.fireTableDataChanged();

                    JOptionPane.showMessageDialog(contentPane, "Aluno deletado com sucesso!");
                }
            }
        });
        IMCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aluno = findByName(nomesDropdown.getSelectedItem().toString());
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    int i = 1;
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    File file = new File(path + File.separator + aluno.getNome() + " IMC.txt");

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                        List<String> text = aluno.calcularIMC();

                        for (String s : text) {
                            bw.write(s);
                            bw.newLine();
                        }
                        bw.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        PesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelNameAndCpf.removeAllElements();
                String option = pesquisarDropdown.getSelectedItem().toString();
                if (pesquisarField.getText().isEmpty()) {
                    option = "Todos";
                    pesquisarDropdown.setSelectedItem(option);
                }
                switch (option){
                    case "Nome":
                        option = "nome";
                        break;
                    case "Data Nasc.":
                        option = "DataNasc";
                        break;
                    case "Peso":
                        option = "peso";
                        break;
                    case "Altura":
                        option = "altura";
                        break;
                    case "CPF":
                        option = "cpf";
                }
                if (option.equals("Todos")) {
                    for (Aluno aluno : alunoDAO.getAllAlunos()) {
                        modelNameAndCpf.addElement(aluno.getNome());
                    }
                    alunoTableModel.setAlunos(alunoDAO.getAllAlunos());
                } else {
                    for (Aluno aluno : alunoDAO.getAllAlunosBy(option, pesquisarField.getText())) {
                        modelNameAndCpf.addElement(aluno.getNome());
                    }
                    alunoTableModel.setAlunos(alunoDAO.getAllAlunosBy(option, pesquisarField.getText()));
                }
                alunoTableModel.fireTableDataChanged();
            }
        });
    }

    private Aluno findByName(String name){
        for (Aluno aluno : alunoDAO.getAllAlunos()) {
            if (aluno.getNome().equals(name)) {
                return aluno;
            }
        }
        return null;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Segunda Avaliação");
        Image image = Toolkit.getDefaultToolkit().getImage("resources/fish.png");
        frame.setIconImage(image);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.createAndShowGUI();
    }

    private void createUIComponents() {
        cpfField = new JFormattedTextField();
        dataNascField = new JFormattedTextField();
        try {
            MaskFormatter formatterCPF = new MaskFormatter("###.###.###-##");
            cpfField.setFormatterFactory(new DefaultFormatterFactory(formatterCPF));
            MaskFormatter formatterData = new MaskFormatter("##/##/####");
            dataNascField.setFormatterFactory(new DefaultFormatterFactory(formatterData));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}
