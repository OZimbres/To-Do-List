import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class EditTask extends JFrame {
    //-----===ATRIBUTOS===-----//
    JPanel mainPanel = new JPanel(); //Painel principal

    JLabel exibirDataCriacao = new JLabel(); //Data criação
    JLabel exibirDataConclusao = new JLabel(); //Data conclusão
    JTextArea campoTitulo = new JTextArea(); //Título
    JTextArea campoDescricao = new JTextArea(); //Descrição
    JButton concluir = new JButton("Concluir"); //Concluir
    JButton cancelar = new JButton("Cancelar"); //Cancelar

    ArrayList<JComponent> componentes = new ArrayList<JComponent>(){
        {
            add(exibirDataCriacao);
            add(exibirDataConclusao);
            add(campoTitulo);
            add(campoDescricao);
            add(concluir);
            add(cancelar);
        }
    };


    public EditTask(ToDoList toDoList) {
        super("EDITAR TAREFA");

        //Adicionando Painel ao JFrame
        this.add(mainPanel);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints elemento = new GridBagConstraints(); // variavel de controle de exibição de cada elemento

        //Posição dos Componentes (BagConstraints)
        int[][] componentesPosicao = {
            {0, 0, 2, 1, 2, 1, 10, 10, 10, 10}, //Data Criação
            {0, 1, 2, 1, 2, 1, 10, 10, 10, 10}, //Data conclusão
            {0, 2, 2, 2, 2, 2, 10, 10, 10, 10}, //Título
            {0, 3, 2, 5, 2, 5, 10, 10, 10, 10}, //Descrição
            {0, 4, 1, 1, 1, 1, 10, 10, 10, 10}, //Concluir
            {1, 4, 1, 1, 1, 1, 10, 10, 10, 10} //Cancelar
        };

        for (int i = 0; i < componentes.size(); i++) {
            elemento.gridx = componentesPosicao[i][0];
            elemento.gridy = componentesPosicao[i][1];
            elemento.gridwidth = componentesPosicao[i][2];
            elemento.gridheight = componentesPosicao[i][3];
            elemento.weightx = componentesPosicao[i][4];
            elemento.weighty = componentesPosicao[i][5];
            elemento.insets = new Insets(componentesPosicao[i][6], componentesPosicao[i][7], componentesPosicao[i][8], componentesPosicao[i][9]);

            //Configurando baseado no tipo de componente
            switch (i) {
                case 0, 1:
                    //"Criando" JLabel
                    JLabel label = (JLabel) componentes.get(i);
                    //Adicionando item ao painel
                    mainPanel.add(label, elemento);
                    break;

                case 2, 3:
                    //"Criando" JTextArea
                    JTextArea campo = (JTextArea) componentes.get(i);
                    campo.setLineWrap(true);

                    // Scroll caso o número seja muito grande
                    JScrollPane areaScroll = new JScrollPane(campo);
                    // Definindo o tipo de scroll
                    areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    
                    if(i == 2){
                        //Fill horizontal
                        elemento.fill = GridBagConstraints.HORIZONTAL;

                        //Definindo a fonte
                        Font font = new Font("Arial", Font.BOLD, 40);
                        //Setando a fonte no JTextArea
                        campo.setFont(font);
                    }
                    else{
                        //Fill horizontal
                        elemento.fill = GridBagConstraints.HORIZONTAL;
                    }
                    
                    //Adicionando item ao painel
                    mainPanel.add(areaScroll, elemento);
                    break;

                case 4, 5:
                    //"Criando" JButton
                    JButton botao = (JButton) componentes.get(i);
                    //Adicionando item ao painel
                    mainPanel.add(botao, elemento);
                    break;
            
                default:
                    break;
            }
        }



        
        // int selectedIndex = toDoList.getTaskList().getSelectedIndex();
        // // Obtém a tarefa selecionada
        // Task selectedTask = toDoList.getTasks().get(selectedIndex);
        // // Abre uma janela de diálogo para editar informações da tarefa
        // //Try catch pra evitar a atualização da descrição sendo que está como null
        // try {
        //     if (!newTitle.isEmpty()) {
        //         // Atualiza a descrição da tarefa
        //         selectedTask.setTitulo(newTitle);
        //         // Atualiza a lista de tarefas
        //         taskControl.updateTaskList();
        //     }
        //     else{
        //         while (newTitle.isEmpty()) {
        //             JOptionPane.showMessageDialog(buttonPanel, "O campo deve ser preenchido", newTitle, selectedIndex, null);
                    
        //             newTitle = JOptionPane.showInputDialog(ToDoList.this, "Editar Tarefa", selectedTask.getTitulo());
                    
        //             // Atualiza a descrição da tarefa
        //             selectedTask.setTitulo(newTitle);
        //             // Atualiza a lista de tarefas
        //             taskControl.updateTaskList();
        //         }
        //     }                          
        // } catch (NullPointerException exception) {
        //     //Não é necesário exibir o erro, apenas indicar ao usuário que a ação foi interrompida
        //     JOptionPane.showMessageDialog(null, "Ação Cancelada");
        // }





        // -----===Configurações de Exibição===-----//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(750, 300, 350, 400);
    }

    // Método para rodar janela (atualizar exibição)
    public void run() {
        // Exibe janela
        this.setVisible(true);
    }
}
