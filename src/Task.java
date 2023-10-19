//Classe pra guardar as tarefas

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    //Atributos
    private String titulo;
    private String descricao;
    private boolean feito;
    private String dataCriacao;
    private String dataConclusao;

    //Construtor vazio
    public Task() {
        //Adicionando hora da criação
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.dataCriacao = dateFormat.format(new Date()); // Obtém a data e hora atuais
    }
    //Construtor
    public Task(String titulo) {
        this.titulo = titulo;
        this.descricao = "";
        this.feito = false; //Nenhuma tarefa começa concluída, por isso o false
        //Adicionando hora da criação
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.dataCriacao = dateFormat.format(new Date()); // Obtém a data e hora atuais
    }

    //Get & Set Descrição
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    //Get & Set Descrição
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //Get & Set Feito
    public boolean isFeito() {
        return feito;
    }
    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    //Get & Set dataCriacao
    public String getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }   

    //Get & Set dataConclusao
    public String getDataConclusao() {
        return dataConclusao;
    }
    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }   
}