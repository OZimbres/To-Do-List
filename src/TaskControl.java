import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class TaskControl {
    //-----===ATRIBUTOS===-----//
    private ToDoList toDoList;

    //Construtor
    public TaskControl(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    // Adicionar tarefa (Botão Adicionar)
    public void addTask() {
        // Adiciona uma nova task à lista de tasks
        String taskDescription = toDoList.getTaskInputField().getText().trim();// remove espaços vazios

        try {
            if (!taskDescription.isEmpty()) {
                Task newTask = new Task(taskDescription);
                toDoList.getTasks().add(newTask);
                updateTaskList();
                toDoList.getTaskInputField().setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Adicione Uma Tarefa a Ser Feita!", "Alerta", JOptionPane.WARNING_MESSAGE);
            }   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Remover tarefa (Botão Remover)
    public void deleteTask() {
        try {
            if(toDoList.getTasks().size() > 0){
                //Try catch caso o item clicado dê como "Out of Bounds"
                try {
                    int selectedIndex = toDoList.getTaskList().getSelectedIndex();
                    if(selectedIndex != -1){
                        if (JOptionPane.showConfirmDialog(null, "Deseja Excluir Essa Tarefa?", "Excluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            // Exclui a task selecionada da lista de tasks
                            toDoList.getTasks().remove(selectedIndex);
                            updateTaskList();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Selecione alguma tarefa.");
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // botão para marcar as tasks como concluidas
    public void markTaskDone() {
        //Try catch caso o item clicado dê como "Out of Bounds"
        try {
            int selectedIndex = toDoList.getTaskList().getSelectedIndex();

            Task task = toDoList.getTasks().get(selectedIndex);

            if (!task.isFeito()) { // Verifica se a tarefa já não está concluída
                if (JOptionPane.showConfirmDialog(null, "Deseja Concluir Esta Tarefa?", "Concluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    // Marca a task selecionada como concluída
                    task.setFeito(true);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dataConclusao = dateFormat.format(new Date()); // Obtém a data e hora atuais
                    task.setDescricao(task.getDescricao() + " (Concluída em " + dataConclusao + ") \u2714");
                    updateTaskList();
                }
            }
        } catch (IndexOutOfBoundsException exception) {
            //Não é necesário exibir o erro, apenas indicar ao usuário que a ação foi interrompida
            JOptionPane.showMessageDialog(null, "É necessário selecionar uma tarefa!");
        }
    }

    // Filtrar tarefas (ComboBox)
    public void filterTasks() {
        // Filtra as tasks com base na seleção do JComboBox
        String filter = (String) toDoList.getFilterComboBox().getSelectedItem();
        toDoList.getListModel().clear();
        for (Task task : toDoList.getTasks()) {
            if (filter.equals("Todas") || (filter.equals("Ativas") && !task.isFeito()) || (filter.equals("Concluídas") && task.isFeito())) {
                toDoList.getListModel().addElement(task.getDescricao());
            }
        }
    }

    // Limpar tarefas concluídas (Botão Limpar Concluídas)
    public void clearCompletedTasks() {
        if (JOptionPane.showConfirmDialog(null, "Deseja Excluir Todas as Tarefas Concluidas?", "Excluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            List<Task> completedTasks = new ArrayList<>();
            for (Task task : toDoList.getTasks()) {
                if (task.isFeito()) {
                    completedTasks.add(task);
                }
            }
            toDoList.getTasks().removeAll(completedTasks);
            updateTaskList();
        }
    }

    // Método pra atualizar exibição
    public void updateTaskList() {
        // Atualiza a lista de tasks exibida na GUI
        toDoList.getListModel().clear();
        for (Task task : toDoList.getTasks()) {
            toDoList.getListModel().addElement(task.getDescricao()); // Removi o operador ternário pois a atualização de descrição está no método 'markTaskDone' (não vai mais utilizar isFeito na exibição, sua função será apenas lógica)
        }
    }
}
