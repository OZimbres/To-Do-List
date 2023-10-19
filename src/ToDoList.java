
//-----===IMPORTAÇÕES===-----//
import java.awt.BorderLayout;
import java.awt.Cursor;
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

import java.awt.event.MouseEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public class ToDoList extends JFrame {
    // -----===ATRIBUTOS===-----//
    //Importação dos métodos
    TaskControl taskControl = new TaskControl(this); //Métodos (lógica)
    EditTask editTask = new EditTask(this);
    Task task = new Task();
    

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
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //Botão Adicionar com cursor de maozinha
        
        deleteButton = new JButton("Excluir"); // Botão Excluir
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //Botão Excluir com cursor de maozinha
        
        markDoneButton = new JButton("Concluir"); // Botão Concluir
        markDoneButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //Botão Concluir com cursor de maozinha

        filterComboBox = new JComboBox<>(new String[] { "Todas", "Ativas", "Concluídas" }); //ComboBox
        filterComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR)); //ComboBox com cursor de maozinha

        clearCompletedButton = new JButton("Limpar Concluídas"); // Botão Limpar Concluídas
        clearCompletedButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //Botão Limpar Concluídas com cursor de maozinha

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
        mainPanel.add(inputPanel, BorderLayout.NORTH); // Jogando painel de inserção de tarefas pra cima ('inputPanel' está dentro de 'mainPanel')
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER); // Jogando painel de exibição das tarefas pra centro (colocando taskList (Lista ?) como scrollPanel)
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Jogando painel dos botões de controle pra baixo ('buttonPanel' está dentro de 'mainPanel')

        // Adiciona o painel principal à janela
        this.add(mainPanel);

        // -----===EVENT LISTENER===-----//
        // ---=Eventos Simples=---//
        // Tratamento de Botão
        addButton.addActionListener(e -> { // Evento para adicionar item
            taskControl.addTask();
        });
        // Tratamento de Botão
        deleteButton.addActionListener(e -> { // Evento para excluir item
            taskControl.deleteTask();
        });
        // Tratamento de Botão
        markDoneButton.addActionListener(e -> { // Evento para marcar Tarefa como Concluída
            taskControl.markTaskDone();
        });
        // Tratamento de Botão
        clearCompletedButton.addActionListener(e -> { // Evento para limpar Tarefa Concluída
            taskControl.clearCompletedTasks();
        });

        // Tratamento de Item(JComboBox)
        filterComboBox.addItemListener(e -> {
            taskControl.filterTasks();
        });

        //Tratamento de keyboard
        taskList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Este método é chamado quando uma tecla é digitada (pressionada e liberada).
                char keyChar = e.getKeyChar();
                if (keyChar == KeyEvent.VK_DELETE) {
                    // Se a tecla DELETE for pressionada, remova a tarefa
                    taskControl.deleteTask();
                } else if (keyChar == KeyEvent.VK_ENTER) {
                    // Se a tecla Enter for pressionada, conclui a tarefa
                    taskControl.markTaskDone();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {} // Este método é chamado quando uma tecla é pressionada.
            @Override
            public void keyReleased(KeyEvent e) {} // Este método é chamado quando uma tecla é liberada.
        });

        //Tratamento de mouse
        //Evento para editar tarefa (2 cliques)
        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //Try catch caso o item clicado dê como "Out of Bounds"
                    try {
                        int selectedIndex = taskList.getSelectedIndex();

                        //Opções da janela
                        Object[] opcoes = {"OK", "EDITAR"};
                        try {
                            int resposta = JOptionPane.showOptionDialog(null, (tasks.get(selectedIndex).getTitulo() +"\nDescrição: "+ tasks.get(selectedIndex).getDescricao()), "TAREFA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                            if(resposta == JOptionPane.NO_OPTION){
                                editTask.run();
                            }
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                        }

                    } catch (ArrayIndexOutOfBoundsException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }
            }
        });

        //Tratamento de keyboard
        taskInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { // Este método é chamado quando uma tecla é digitada (pressionada e liberada).
                char keyChar = e.getKeyChar();
                if (keyChar == KeyEvent.VK_ENTER) { // Se a tecla Enter for pressionada, adicione a tarefa
                    taskControl.addTask();
                }
            }
            @Override
            public void keyPressed(KeyEvent e){} // Este método é chamado quando uma tecla é pressionada.
            @Override
            public void keyReleased(KeyEvent e){} // Este método é chamado quando uma tecla é liberada.
        });

        // -----===Configurações de Exibição===-----//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(700, 300, 500, 400);
        this.setVisible(true);
    }

    //-----===GETTERS & SETTERS===-----//
    public JTextField getTaskInputField(){
        return taskInputField;
    }
    public JComboBox<String> getFilterComboBox(){
        return filterComboBox;
    }
    public JList<String> getTaskList(){
        return taskList;
    }
    public DefaultListModel<String> getListModel(){
        return listModel;
    }
    public List<Task> getTasks(){
        return tasks;
    }
    // -----===MÉTODOS===-----//
    // Método para rodar janela (atualizar exibição)
    public void run() {
        // Exibe janela
        this.setVisible(true);
    }
}