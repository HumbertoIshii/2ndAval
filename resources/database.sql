create database avaliacao;
use avaliacao;

create table aluno(
                      id int not null primary key auto_increment,
                      nome varchar(255),
                      dataNasc varchar(255),
                      peso float,
                      altura float,
                      cpf varchar(255) unique
);
select * from aluno;

insert into aluno (nome, dataNasc, peso, altura, cpf) values
                                                          ('Ana Souza', '15/05/2000', 58.5, 1.65, '123.456.789-01'),
                                                          ('Bruno Oliveira', '22/08/1998', 72.3, 1.78, '987.654.321-00'),
                                                          ('Carlos Silva', '10/02/2002', 80.1, 1.85, '192.837.465-01'),
                                                          ('Diana Costa', '01/12/1999', 62.4, 1.70, '564.738.291-02'),
                                                          ('Eliane Pereira', '18/03/2001', 55.2, 1.60, '837.264.509-03'),
                                                          ('Felipe Santos', '25/11/1997', 90.0, 1.88, '654.321.987-04'),
                                                          ('Gabriela Lima', '30/07/2003', 50.5, 1.55, '293.847.561-05'),
                                                          ('Henrique Rocha', '12/09/2000', 75.4, 1.82, '748.392.610-06'),
                                                          ('Isabela Martins', '19/10/1996', 65.0, 1.68, '384.756.192-07'),
                                                          ('Joaquim Alves', '23/04/2001', 80.7, 1.80, '765.432.109-08'),
                                                          ('Karina Barbosa', '14/06/2002', 62.8, 1.72, '647.382.910-09'),
                                                          ('Lucas Ferreira', '09/01/1995', 95.3, 1.90, '836.472.159-10'),
                                                          ('Maria Oliveira', '05/11/2003', 53.9, 1.62, '283.746.509-11'),
                                                          ('Natan Rodrigues', '25/02/1999', 78.5, 1.83, '192.837.645-12'),
                                                          ('Olga Gomes', '18/07/2000', 59.1, 1.67, '738.492.651-13'),
                                                          ('Paulo Costa', '02/04/1998', 82.0, 1.84, '384.756.192-14'),
                                                          ('Quésia Duarte', '11/12/2001', 54.6, 1.64, '827.364.509-15'),
                                                          ('Rafael Alves', '03/09/1997', 70.2, 1.77, '948.572.631-16'),
                                                          ('Sofia Martins', '28/05/2000', 63.0, 1.69, '364.758.291-17'),
                                                          ('Thiago Pereira', '21/06/1998', 85.5, 1.86, '583.926.710-18');