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

    //------ esse eh o unico metodo chamado pelo simulador, ele contem toda a logica de trabalho do burocrata
    //       com metodos auxiliares -------
    public void trabalhar() {
        // percorre os montes de todos os cursos
        for(CodigoCurso codigo : CodigoCurso.values()){
            Documento[] documentos = universidade.pegarCopiaDoMonteDoCurso(codigo);

            // analisa cada documento um a um
            for(Documento doc : documentos){
                // verifica se o doc atual eh substancial e valido
                if(ehSubstancialValido(doc)){
                    despacharDocSubstancial(doc, codigo); // despacha
                }
                // se nao for, tenta alocar em algum processo da mesa
                AlocaEDespachaDocComum(doc, codigo);
            }
        }
        //
        for(Processo processo : mesa.getProcessos()){
            if(processo != null && processo.contarDocumentos() > 0){
                universidade.despachar(processo);
            }
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------


   /* (4) Uma Portaria ou um Edital com 100 ou mais páginas é um “documento substancial” e
    deve ser despachado em um processo sem qualquer outro documento junto. Contudo,
    Portarias e Editais que não sejam mais válidos podem ir junto de outros documentos mesmo
    que sejam substanciais */

    // metodo que verifica se o doc eh substancial e valido p despachar em um processo sem outros docs
    // se retornar falso, tratar da alocacao dele com outros docs em um processo

    private boolean ehSubstancialValido(Documento doc){
        if(doc instanceof Edital){ // verifica se é edital
            Edital edital = (Edital) doc;
            // retorna true se tem mais de 100 pag e é valido (condicao p ser substancial)
            return edital.getPaginas() >= 100 && edital.isValido();
        }

        if(doc instanceof Portaria){ // verifica se é portaria
            Portaria portaria = (Portaria) doc;
            // retorna true se tem mais de 100 pag e é valido (condicao p ser substancial)
            return portaria.getPaginas() >= 100 && portaria.isValido();
        }

        return false;
    }

    // metodo p achar um processo vazio para colocar documentos:
    private Processo encontrarProcessoVazio(){
        Processo[] processosDaMesa = mesa.getProcessos();

        for(Processo processo : processosDaMesa){
            if(processo != null && processo.contarDocumentos() == 0){ // se existir e nao possuir documentos
                return processo; // eh um processo vazio
            }
        }
        return null; // null se nao encontrou nenhum processo vazio
    }

    // metodo responsavel por despachar documentossubstanciais validos
    private boolean despacharDocSubstancial(Documento doc, CodigoCurso codigo){
        Processo processo = encontrarProcessoVazio(); // procura um processo vazio

        if(processo == null){ // se nao tem processo vazio, nao despacha
            return false;
        }

        // se chegou aqui, tem proceso vazio, entao retira o doc substancial do monte
        boolean removido = universidade.removerDocumentoDoMonteDoCurso(doc, codigo);

        if(removido == false){ // se nao conseguir remover sai do metodo
            return false;
        }

        processo.adicionarDocumento(doc); // add doc no processo vazio
        universidade.despachar(processo); // despacha doc diretamente
        return true;
    }

    // (1) Um processo não pode conter Documentos de cursos de graduação
    // e cursos de pósgraduação ao mesmo tempo quando for despachado.

    // baseado nessa espec, esse metodo verifica se ha mistura de docs de graduacao e pos:
    private boolean respeitaGradEPos(Documento documento, Processo processo, CodigoCurso codigo){
        Documento[] documentosDoProcesso = processo.pegarCopiaDoProcesso();
        boolean novoEhGraduacao = false;

        if(codigo.name().startsWith("GRADUACAO")){
            novoEhGraduacao = true;
        }

        for(Documento doc : documentosDoProcesso){
            boolean existenteEhGraduacao = false;
            if(doc.getCodigoCurso().name().startsWith("GRADUACAO")){
                existenteEhGraduacao = true;
            }

            if(novoEhGraduacao != existenteEhGraduacao){
                return false;
            }
        }

        return true;
    }

    /*(2) Um processo não pode misturar Documentos Administrativos e Documentos Acadêmicos,
    mas atas podem estar em qualquer processo. Assim, uma Norma não pode ser despachada
    junto de um Histórico no mesmo processo, por exemplo*/
    private boolean respeitaAcademicoAdministrativo(Documento documento, Processo processo){
        if (documento instanceof Ata) {
            return true; // atas podem ser colocadas com qualquer um dos documentos
        }

        Documento[] documentosDoProcesso = processo.pegarCopiaDoProcesso();

        boolean novoEhAdministrativo = false;
        boolean novoEhAcademico = false;

        if (documento instanceof DocumentoAdministrativo) {
            novoEhAdministrativo = true; // verifica se eh doc administrativo
        }

        if (documento instanceof DocumentoAcademico) {
            novoEhAcademico = true; // verifica se eh doc academico
        }

        for (Documento doc : documentosDoProcesso) {
            if (doc instanceof Ata) { // um dos documentos do processo eh ata - ok: segue p ver outros
                continue;
            }

            if (novoEhAcademico && doc instanceof DocumentoAdministrativo) {
                return false;
            }

            if (novoEhAdministrativo && doc instanceof DocumentoAcademico) {
                return false;
            }
        }
        return true;
    }

    // esse metodo verifica se as regras para adicionar diferentes documentos em processos sao respeitadas
    // e retorna true em caso afirmativo, para adicionar um novo doc em um processo
    private boolean podeAddDocumento(Documento documento, Processo processo, CodigoCurso codigo){
        // Contudo, Portarias e Editais que não sejam mais válidos podem ir junto de outros documentos mesmo
        // que sejam substanciais.

        if (documento instanceof Norma) { // se o doc for uma norma
            Norma norma = (Norma) documento; // o trata como norma para usar o metodo isValido()

            // se for norma, mas nao for edital nem portaria e for invalida, eh rejeitada
            if (!(norma instanceof Edital) && !(norma instanceof Portaria)) {
                if (!norma.isValido()) {
                    return false;
                }
            }
        }

        // so pode adicionar documentos no proceso se respeitar as regras p nao estressar o burocrata
        if(!respeitaAcademicoAdministrativo(documento, processo)
          || !respeitaGradEPos(documento, processo, codigo)
          || !respeitaQtdDePags(documento, processo)
          || !respeitaRegraDiploma(documento, processo)
          || !respeitaRegraAtestados(documento, processo)
          || !respeitaRegraCircularesEOficios(documento, processo)){
            return false;
        }
        return true;
    }

    // (6) Diplomas so podem ser despachados junto de outros Diplomas, Certificados ou Atas.
    private boolean respeitaRegraDiploma(Documento documento, Processo processo){
        Documento[] documentosDoProcesso = processo.pegarCopiaDoProcesso();

        // caso o novo documento seja um diploma:
        if(documento instanceof Diploma){ // se o novo documento eh diploma
            for(Documento doc : documentosDoProcesso){
                // os outros documentos do processo so podem ser diplomas, certificados ou atas
                if(!(doc instanceof Diploma) && !(doc instanceof Certificado) && !(doc instanceof  Ata)){
                    return false;
                }
            }
        }
        // caso exista diploma no processo:
        for(Documento doc : documentosDoProcesso){
            if(doc instanceof Diploma){ // se existe um diploma no processo
                // o novo documento precisa ser diploma, certificado ou ata
                if(!(documento instanceof Diploma) && !(documento instanceof Certificado) && !(documento instanceof  Ata)){
                    return false;
                }
            }

        }
        return true;
    }

    //(7) Atestados de diferentes categorias nao podem estar em um mesmo processo.
    private boolean respeitaRegraAtestados(Documento documento, Processo processo){
        if (!(documento instanceof Atestado)) { // se o novo documento nao eh atestado, nao precisa verificar
            return true;
        }
        Atestado novoAtestado = (Atestado) documento; // se o novo doc eh atestado, o trata como atestado

        Documento[] documentosDoProcesso = processo.pegarCopiaDoProcesso(); // pega todos os docs ja existentes no processo

        for(Documento doc : documentosDoProcesso){
            if(doc instanceof  Atestado){ // para cada atestado ja existente no processo
                Atestado atestadoExistente = (Atestado) doc;

                // verifica se suas categorias sao iguais a da novo doc atestado
                if(!novoAtestado.getCategoria().equals(atestadoExistente.getCategoria())){
                    return false;
                }
            }
        }
        return true;
    }

    // esse metodo trata todos os documentos que nao sao substanciais
    private boolean AlocaEDespachaDocComum(Documento doc, CodigoCurso codigo){
        Processo[] processosDaMesa = mesa.getProcessos();

        for(Processo processo : processosDaMesa){
            if(processo != null && podeAddDocumento(doc, processo, codigo)){
                // remove o documento do monte so depois de garantir que vai colocar no processo
                boolean removido = universidade.removerDocumentoDoMonteDoCurso(doc, codigo);
                if(removido == true){ // adiciona documento no processo
                    processo.adicionarDocumento(doc);

                    // se o processo atingiu 200 pags
                    if(calcularPaginasProcesso(processo) >= 230){
                        universidade.despachar(processo); // ja despacha p liberar a mesa p um novo processo
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // esse metodo serve p calcular a qtd de paginas ja existentes em um processo
    // criei ele para evitar repeticao de codigo pq precisei dele mais de uma vez

    private int calcularPaginasProcesso(Processo processo) {
        int total = 0;
        for (Documento doc : processo.pegarCopiaDoProcesso()) { // percorre cada documento existente no processo
            total += doc.getPaginas(); // e adiciona a quantidade de pags dele no total de pags do processo
        }
        return total;
    }

    /* (5) Diferentes Circulares e Oficios so podem ser despachados no mesmo processo se tiverem
        um destinatario em comum. Por exemplo, um Ofício para “Ana Moura”, uma Circular para
        “Dulce Pontes” e “Ana Moura”, e uma Circular para “Ana Moura”, “Antonio Variacoes” e
        “Amalia Rodrigues” podem ser despachados juntos no mesmo processo, porque Ana Moura
        eh o destinatario comum entre todos. */

    private boolean respeitaRegraCircularesEOficios(Documento documento, Processo processo) {
        String[] destsNovoDoc = pegarDestinatarios(documento); // pega destinatarios do novo doc

        if (destsNovoDoc == null) { // se nao tem destinatarios, nao eh tratado pq nao eh oficio nem circular
            return true;
        }

        Documento[] docsExistentes = processo.pegarCopiaDoProcesso();

        // para cada destinatario do novo doc, verifica se ele eh o destinatario comum
        for (String candidato : destsNovoDoc) {
            if(candidato == null){
                continue;
            }
            boolean ehComum = true;

            // testa esse candidato com todos os docs do processo
            for(Documento docExistente : docsExistentes){
                String[] destsExistente = pegarDestinatarios(docExistente);

                if(destsExistente != null){
                    boolean achouNesseDoc = false;
                    for(String dest : destsExistente){
                        if(candidato.equals(dest)){
                            achouNesseDoc = true;
                            break;
                        }
                    }
                    // se nao ta nesse doc, nao eh comum p todos
                    if(!achouNesseDoc){
                        ehComum = false;
                        break; // para de testar esse candidato e avanca p proximo
                    }
                }
            }
            // se o candidato a des comum era realmente comum a todos
            if(ehComum){
                return true; // respeitou a regra
            }
        }
        // se testou todos os candidatos a dest comum e nenhum foi comum a todod
        return false; // nao respeitou a regra
    }

    // inicio do codigo gerado por IA:
    // Método auxiliar simples para extrair destinatários como array de String
    private String[] pegarDestinatarios(Documento doc) {
        if (doc instanceof Oficio) {
            Oficio oficio = (Oficio) doc;
            // Transforma o único destinatário em um array de 1 posição para facilitar
            return new String[]{ oficio.getDestinatario() };
        }

        if (doc instanceof Circular) {
            Circular circular = (Circular) doc;
            return circular.getDestinatarios(); // já é String[]
        }

        return null; // Não é Ofício nem Circular
    }
    // fim do codigo gerado por IA


    /*A mesa do burocrata so pode ter cinco processos abertos para colocacao de documentos.
    Ainda, uma informacao de extrema importancia eh que cada processo suporta, no maximo, 250
    paginas. Colocar paginas acima do limite causa rompimento da pasta do processo durante o
    transporte (depois que ela eh despachada para a secretaria academica) e perda de todos os
    documentos envolvidos, causando um aumento substancial de estresse do burocrata que
    recebe uma advertencia administrativa.

    esse metodo trata a regra acima e eh imprescindivel pq garante que o estresse nao seja super incrementado
    com a perda dos documentos por excesso de paginas*/
    private boolean respeitaQtdDePags(Documento documento, Processo processo){
        int pagsAtuais = calcularPaginasProcesso(processo); // ve a qtd de paginas que o processo ja tem
        int pagsComNovoDoc = pagsAtuais + documento.getPaginas(); // soma com a qtd de pags do novo documento do proceso

        if(pagsComNovoDoc > 250){ // se tiver mais q 250 pags
            return false; // nao respeita a qtd limite
        }
        return true; // se for menor, respeita o limite
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