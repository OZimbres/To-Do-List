
//-----===IMPORTAÇÕES===-----//
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ToDoList extends JFrame {
    // -----===ATRIBUTOS===-----//
    // Atributos 'comuns'
    private JPanel mainPanel; // Painel Principal
    private JTextField taskInputField; // Campo para Nomear Tarefa

    // Botões
    private JButton addButton; // Botão Adicionar
    private JButton deleteButton; // Botão Deletar
    private JButton markDoneButton; // Botão Concluir
    private JButton clearCompletedButton; // Botão Limpar Concluídas

    // ComboBox
    private JComboBox<String> filterComboBox; // ComboBox (Todas / Ativas / Concluídas)

    // Listas
    private JList<String> taskList; // Lista ?
    private DefaultListModel<String> listModel; // Lista ?
    private List<Task> tasks; // Lista que armazena Tarefas

    // -----===CONSTRUTOR===-----//
    public ToDoList() {
        
        // Contrutor herdado
        super("To-Do List App");

        // -----===Inicialização dos Componentes===-----//
        // Inicializa o painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Inicializa a lista de tasks e a lista de tasks concluídas
        tasks = new ArrayList<>(); // Lista que armazena tarefas
        listModel = new DefaultListModel<>(); // Lista ?
        taskList = new JList<>(listModel); // Lista ?

        // Inicializa campos de entrada, botões e JComboBox
        taskInputField = new JTextField(); // Campo para Nomear Tarefa
        addButton = new JButton("Adicionar"); // Botão Adicionar
        deleteButton = new JButton("Excluir"); /// Botão Excluir
        markDoneButton = new JButton("Concluir"); /// Botão Concluir
        filterComboBox = new JComboBox<>(new String[] { "Todas", "Ativas", "Concluídas" }); // Combo Box
        clearCompletedButton = new JButton("Limpar Concluídas"); // Botão Limpar Concluídas

        // Configuração do painel de entrada (Painel Superior)
        JPanel inputPanel = new JPanel(new BorderLayout()); // Painel
        inputPanel.add(taskInputField, BorderLayout.CENTER); // Campo para nomear tarefa no centro
        inputPanel.add(addButton, BorderLayout.EAST); // Botão adicionar à direita

        // Configuração do painel de botões (Painel Inferior)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Painel
        buttonPanel.add(deleteButton); // Botão Excluir
        buttonPanel.add(markDoneButton); // Botão Concluir
        buttonPanel.add(filterComboBox); // ComboBox
        buttonPanel.add(clearCompletedButton); // Botão Limpar Concluídas

        // Adiciona os componentes ao painel principal (Exibe tarefas)
        mainPanel.add(inputPanel, BorderLayout.NORTH); // Jogando painel de inserção de tarefas pra cima ('inputPanel'
                                                       // está dentro de 'mainPanel')
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER); // Jogando painel de exibição das tarefas pra
                                                                       // centro (colocando taskList (Lista ?) como
                                                                       // scrollPanel)
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Jogando painel dos botões de controle pra baixo
                                                        // ('buttonPanel' está dentro de 'mainPanel')

        // Adiciona o painel principal à janela
        this.add(mainPanel);

        // -----===EVENT LISTENER===-----//
        // ---=Eventos Simples=---//
        // Tratamento de botão
        addButton.addActionListener(e -> { // Evento para adicionar item
            addTask();
        });
        // Tratamento de Botão
        deleteButton.addActionListener(e -> { // Evento para excluir item
            deleteTask();
        });
        // Tratamento de Botão
        markDoneButton.addActionListener(e -> { // Evento para marcar Tarefa como Concluída
            markTaskDone();
        });
        // Tratamento de Botão
        clearCompletedButton.addActionListener(e -> { // Evento para limpar Tarefa Concluída
            clearCompletedTasks();
        });
        // Tratamento de Item(JComboBox)
        filterComboBox.addItemListener(e -> {
            filterTasks();
        });
        taskInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Este método é chamado quando uma tecla é digitada (pressionada e liberada).
                char keyChar = e.getKeyChar();
                if (keyChar == KeyEvent.VK_ENTER) {
                    // Se a tecla Enter for pressionada, adicione a tarefa
                    addTask();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Este método é chamado quando uma tecla é pressionada.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Este método é chamado quando uma tecla é liberada.
            }
        });



        // -----===Configurações de Exibição===-----//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 300);
        this.setVisible(true);
    }

    // -----===MÉTODOS===-----//
    // Adicionar tarefa (Botão Adicionar)
    private void addTask() {
        // Adiciona uma nova task à lista de tasks
        String taskDescription = taskInputField.getText().trim();// remove espaços vazios
        if (!taskDescription.isEmpty()) {
            Task newTask = new Task(taskDescription);
            tasks.add(newTask);
            updateTaskList();
            taskInputField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Adicione Uma Tarefa a Ser Feita!", "Alerta",
                    JOptionPane.WARNING_MESSAGE);
        }
        
    }

    // Remover tarefa (Botão Remover)
    private void deleteTask() {
        if (JOptionPane.showConfirmDialog(null, "Deseja Excluir Essa Tarefa?",
                "Excluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            // Exclui a task selecionada da lista de tasks
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
                tasks.remove(selectedIndex);
                updateTaskList();
            }
        }
    }

    //botão para  marcar as tasks como concluidas
    private void markTaskDone() {
        int selectedIndex = taskList.getSelectedIndex();
    
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task task = tasks.get(selectedIndex);
    
            if (!task.isFeito()) { // Verifica se a tarefa já não está concluída
                if (JOptionPane.showConfirmDialog(null, "Deseja Concluir Esta Tarefa?",
                        "Concluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    // Marca a task selecionada como concluída
                    task.setFeito(true);
                    task.setDescricao(task.getDescricao() + " (Concluída) \u2714");
                    updateTaskList();
                }
            }
        }
    }
    

    // Filtrar tarefas (ComboBox)
    private void filterTasks() {
        // Filtra as tasks com base na seleção do JComboBox
        String filter = (String) filterComboBox.getSelectedItem();
        listModel.clear();
        for (Task task : tasks) {
            if (filter.equals("Todas") || (filter.equals("Ativas") && !task.isFeito())
                    || (filter.equals("Concluídas") && task.isFeito())) {
                listModel.addElement(task.getDescricao());
            }
        }
    }

    // Limpar tarefas concluídas (Botão Limpar Concluídas)
    private void clearCompletedTasks() {
        if (JOptionPane.showConfirmDialog(null, "Deseja Excluir Todas as Tarefas Concluidas?",
                "Excluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            List<Task> completedTasks = new ArrayList<>();
            for (Task task : tasks) {
                if (task.isFeito()) {
                    completedTasks.add(task);
                }
            }
            tasks.removeAll(completedTasks);
            updateTaskList();
        }

    }

    
    // Método pra atualizar exibição
    private void updateTaskList() {
        // Atualiza a lista de tasks exibida na GUI
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task.getDescricao()); // Removi o operador ternário pois a atualização de descrição
                                                       // está no método 'markTaskDone' (não vai mais utilizar isFeito
                                                       // na exibição, sua função será apenas lógica)
        }
    }

    // Método para rodar janela (atualizar exibição)
    public void run() {
        // Exibe janela
        this.setVisible(true);
    }

}
