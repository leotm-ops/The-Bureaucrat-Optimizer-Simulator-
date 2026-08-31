package estudantes.entidades;

import professor.entidades.*;

/**
 * Classe que traz a lógica do algoritmo de organização e despacho de processos.
 * <br><br>
 * Você pode incluir novos atributos e métodos nessa classe para criar
 * lógicas mais complexas para o gerenciamento da organização e despacho de 
 * processos, mas eles não serão invocados diretamente pelo simulador e devem
 * respeitar propriedades de encapsulamento e coesão.
 * 
 * @autor Lara Moreira
 * @autor Leonardo Maraschin
 * @autor Ana Laura Führ
 *
 */

public class Burocrata {
    private int estresse = 0;
    private Mesa mesa;
    private Universidade universidade;
    
    /**
     * Construtor de Burocrata.
     * 
     * @param m mesa com os processos
     * @param u universidade com os montes dos cursos e a secretaria
     */
    public Burocrata(Mesa m, Universidade u){
        this.mesa = m;
        this.universidade = u;
    }
    
    /**
     * Executa a lógica de criação e despacho dos processos.
     * <br><br>
     * Esse método é o único método de controle invocado durante a simulação 
     * da universidade.
     * <br><br>
     * Aqui podem ser feitas todas as verificações sobre os documentos nos 
     * montes dos cursos e dos processos abertos na mesa do Burocrata. A partir 
     * dessas informações, você pode colocar documentos nos processos abertos
     * e despachar os processos para a secretaria acadêmica.
     * <br><br>
     * Cuidado com a complexidade do seu algoritmo, porque se ele demorar muito
     * serão criados menos documentos na sua execução e sua produtividade geral
     * vai cair.
     * <br><br>
     * Esse método será chamado a cada 50 milissegundos pelo simulador da
     * universidade.
     * <br><br>
     * <strong>O burocrata não pode manter documentos com ele</strong> depois
     * que o método trabalhar terminar de executar, ou seja, você deve devolver
     * para os montes dos cursos todos os documentos que você removeu dos montes
     * dos cursos.
     * 
     * @see professor.entidades.Universidade#despachar(Processo)
     * @see professor.entidades.Universidade#removerDocumentoDoMonteDoCurso(estudantes.entidades.Documento, professor.entidades.CodigoCurso)
     * @see professor.entidades.Universidade#devolverDocumentoParaMonteDoCurso(estudantes.entidades.Documento, professor.entidades.CodigoCurso) 
     */
    public void trabalhar() {
        // --  PRIMEIRA VERSAO DE LOGICA DE TRABALHO DO BUROCRATA --
        /* (4) Uma Portaria ou um Edital com 100 ou mais paginas eh um “documento substancial” e
        deve ser despachado em um processo sem qualquer outro documento junto. Contudo,
        Portarias e Editais que nao sejam mais validos podem ir junto de outros documentos mesmo
        que sejam substanciais.
        Aumentar o estresse eh pior do que ter uma eficiencia baixa, pois prezamos pela qualidade de
        vida das pessoas no servico publico

        seguindo essa especificacao, essa versao apenas despacha diretamente portarias e editais validos.
        a secretaria aceita esses despaches e o burocrata nunca eh estressado, mas despacha poucos processos

         */

        Processo[] processos = mesa.getProcessos();

        // percorre os documentos de todos os cursos da universidade
        for (CodigoCurso codigo : CodigoCurso.values()) {
            Documento[] documentos = universidade.pegarCopiaDoMonteDoCurso(codigo);

            for (Documento doc : documentos) {
                boolean SubstancialEValido = false;

                // verifica se eh edital ou portaria e se eh substancial e valido, se for, acende a flag
                if (doc instanceof Edital) {
                    Edital edital = (Edital) doc;
                    if (edital.getPaginas() >= 100 && edital.isValido()) {
                        SubstancialEValido = true;
                    }
                } else if (doc instanceof Portaria) {
                    Portaria portaria = (Portaria) doc;
                    if (portaria.getPaginas() >= 100 && portaria.isValido()) {
                        SubstancialEValido = true;
                    }
                }

                // se encontrou um doc subst e valido p despachar:
                if (SubstancialEValido) {
                    // tenta remover o doc do monte do curso
                    boolean foiRemovido = universidade.removerDocumentoDoMonteDoCurso(doc, codigo);

                    if (foiRemovido == true ) {
                        boolean despachado = false;

                        // coloca o doc em um processo e despacha
                        for (int i = 0; i < processos.length; i++) {
                            if (processos[i] != null) {
                                processos[i].adicionarDocumento(doc);
                                universidade.despachar(processos[i]);
                                despachado = true;
                                break;
                            }
                        }
                        // se nao tinha processo p colocar, devolve o doc p monte
                        if (despachado == false) {
                            universidade.devolverDocumentoParaMonteDoCurso(doc, codigo);
                        }
                    }

                }
            }
        }
    }


    /**
     * Retorna o valor atual de estresse do burocrata.
     * @return estresse atual
     */

    public int getEstresse(){
        return this.estresse;
    }
    
    /**
     * Aumenta o estresse do burocrata em uma unidade.
     * 
     * <strong>VOCÊ NÃO DEVERIA INVOCAR ESSE MÉTODO!!!</strong>
     */
    public void estressar(){
        this.estresse++;
    }
    
    /**
     * Aumenta o estresse do burocrata em 10 unidades.
     * 
     * <strong>VOCÊ NÃO DEVERIA INVOCAR ESSE MÉTODO!!!</strong>
     */
    public void estressarMuito(){
        this.estresse += 10;
    }
}