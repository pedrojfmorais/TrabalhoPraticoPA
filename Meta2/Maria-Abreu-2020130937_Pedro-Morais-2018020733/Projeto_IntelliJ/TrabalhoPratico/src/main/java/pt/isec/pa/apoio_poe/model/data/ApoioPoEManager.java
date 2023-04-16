package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.*;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorType;
import pt.isec.pa.apoio_poe.model.memento.IMemento;
import pt.isec.pa.apoio_poe.model.memento.IOriginator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Classe ApoioPoEManager que serve de proxy e decorator para a classe ApoioPoE
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class ApoioPoEManager implements Serializable, IOriginator {

    /**
     * Versão do Serial da classe
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Objeto da classe ApoioPoE, onde são guardados os dados
     */
    private ApoioPoE apoioPOE;

    /**
     * construtor público
     * @param apoioPOE - objeto da classe ApoioPoE, onde são guardados os dados
     */
    public ApoioPoEManager(ApoioPoE apoioPOE) {
        this.apoioPOE = apoioPOE;
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return valor devolvido pela classe ApoioPoE
     */
    public int getFaseBloqueada() {
        return apoioPOE.getFaseBloqueada();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param faseBloqueada - novo valor para a fase bloqueada
     */
    public void setFaseBloqueada(int faseBloqueada) {
        apoioPOE.setFaseBloqueada(faseBloqueada);
    }

    /**
     *  método proxy para a classe ApoioPoE
     * @param nAluno - número de aluno
     * @param nome - nome do aluno
     * @param email - email do aluno
     * @param siglaCurso - sigla do curso
     * @param siglaRamo - sigla do ramo
     * @param classificacao - classificação do aluno
     * @param acessoEstagio - tem acesso a estágio? sim ou não
     * @return se é possível adicionar o aluno ou não
     */
    public boolean adicionaAluno(long nAluno, String nome, String email, String siglaCurso, String siglaRamo,
                                 double classificacao, boolean acessoEstagio) {
        return apoioPOE.adicionaAluno(nAluno, nome, email, siglaCurso, siglaRamo, classificacao, acessoEstagio);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nome - nome do docente
     * @param email - email do docente
     * @return se é possível adicionar o docente ou não
     */
    public boolean adicionaDocente(String nome, String email) {
        return apoioPOE.adicionaDocente(nome, email);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param tipo - tipo de proposta
     * @param id - id da proposta
     * @param titulo - título da proposta
     * @param nAlunoAssociado - número do aluno associado à proposta
     * @param areasDestino - área correspondente à proposta
     * @param entidadeOuDocente - entidade ou docente associados à proposta
     * @return se a proposta foi adicionada ou não
     */
    public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado,
                                    String areasDestino, String entidadeOuDocente) {
        return apoioPOE.adicionaProposta(tipo, id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param tipo - tipo de proposta
     * @param id - id da proposta
     * @param titulo - título da proposta
     * @param areasDestino - área correspondente à proposta
     * @param entidadeOuDocente - entidade ou docente associados à proposta
     * @return se a proposta foi adionada ou não
     */
    public boolean adicionaProposta(String tipo, String id, String titulo, String areasDestino, String entidadeOuDocente) {
        return apoioPOE.adicionaProposta(tipo, id, titulo, areasDestino, entidadeOuDocente);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param tipo - tipo de proposta
     * @param id - id da proposta
     * @param titulo - título da proposta
     * @param nAlunoAssociado - número do aluno associado à proposta
     * @return se a proposta foi adicionada ou não
     */
    public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado) {
        return apoioPOE.adicionaProposta(tipo, id, titulo, nAlunoAssociado);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número de aluno
     * @param propostas - lista de proposta
     * @return se a candidatura foi adicionada ou não
     */
    public boolean adicionaCandidatura(long nAluno, ArrayList<String> propostas) {
        return apoioPOE.adicionaCandidatura(nAluno, propostas);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param idProposta - id da proposta
     * @param nAluno - número de aluno
     * @return se a proposta foi atribuída ou não
     */
    public boolean atribuirPropostaAluno(String idProposta, long nAluno) {
        return apoioPOE.atribuirPropostaAluno(idProposta, nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return se existem propostas para atribuir automaticamente
     */
    public boolean atribuicaoAutomaticaPropostasComAluno() {
        return apoioPOE.atribuicaoAutomaticaPropostasComAluno();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param idProposta - id da proposta
     * @param email - email do docente
     * @return se a proposta foi atribuída ou não
     */
    public boolean atribuirPropostaDocenteOrientador(String idProposta, String email) {
        return apoioPOE.atribuirPropostaDocenteOrientador(idProposta, email);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return se algum docente foi atribuído
     */
    public boolean associacaoAutomaticaDocentesProponentes() {
        return apoioPOE.associacaoAutomaticaDocentesProponentes();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número de aluno
     * @return clone do aluno pretendido, ou null caso este não exitsa
     */
    public Aluno getAluno(long nAluno) {
        return apoioPOE.getAluno(nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param email - email do docente a procurar
     * @return clone do docente pretendido ou null caso este não exista
     */
    public Docente getDocente(String email) {
        return apoioPOE.getDocente(email);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta a procurar
     * @return clone da proposta pretendida ou null caso esta não exista
     */
    public Proposta getProposta(String id) {
        return apoioPOE.getProposta(id);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número do aluno a procurar
     * @return clone da candidatura pretendida ou null caso esta não exista
     */
    public Candidatura getCandidatura(long nAluno) {
        return apoioPOE.getCandidatura(nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta atribuída a procurar
     * @return clone da proposta atribuída pretendida ou null caso esta não exista
     */
    public PropostaAtribuida getPropostaAtribuida(String id) {
        return apoioPOE.getPropostaAtribuida(id);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return clone com a lista de alunos ordenada
     */
    public ArrayList<Aluno> getAlunos() {
        return apoioPOE.getAlunos();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return clone com a lista de docentes ordenada
     */
    public ArrayList<Docente> getDocentes() {
        return apoioPOE.getDocentes();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return clone com a lista de propostas ordenada
     */
    public ArrayList<Proposta> getPropostas() {
        return apoioPOE.getPropostas();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return clone com a lista de candidaturas ordenada
     */
    public ArrayList<Candidatura> getCandidaturas() {
        return apoioPOE.getCandidaturas();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return clone com a lista de propostas atribuídas ordenada
     */
    public ArrayList<PropostaAtribuida> getPropostasAtribuidas() {
        return apoioPOE.getPropostasAtribuidas();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número de aluno
     * @return se o aluno foi removido ou não
     */
    public boolean removeAluno(long nAluno) {
        return apoioPOE.removeAluno(nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param email - email do docente
     * @return se o docente foi removido ou não
     */
    public boolean removeDocente(String email) {
        return apoioPOE.removeDocente(email);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta
     * @return se a proposta foi removida ou não
     */
    public boolean removeProposta(String id) {
        return apoioPOE.removeProposta(id);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número de aluno
     * @return se a candidatura foi removida ou não
     */
    public boolean removeCandidatura(long nAluno) {
        return apoioPOE.removeCandidatura(nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta
     * @return se a proposta atribuída foi removida ou não
     */
    public boolean removePropostaAtribuida(String id) {
        return apoioPOE.removePropostaAtribuida(id);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta atribuída
     * @return se o docente orientador foi removido ou não
     */
    public boolean removeOrientadorPropostaAtribuida(String id) {
        return apoioPOE.removeOrientadorPropostaAtribuida(id);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param ramo - ramo a verificar
     * @return se existem propostas suficentes
     */
    public boolean propostasSufecienteParaRamo(String ramo) {
        return apoioPOE.propostasSuficienteParaRamo(ramo);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return se todas as candidaturas têm proposta atribuída
     */
    public boolean todasCandidaturasComPropostaAtribuida() {
        return apoioPOE.todasCandidaturasComPropostaAtribuida();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param email - email do docente
     * @return número de orientações para um determinado docente
     */
    public int calculaNumeroOrientacoesDocente(String email) {
        return apoioPOE.calculaNumeroOrientacoesDocente(email);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta
     * @return tipo de proposta de uma determinada proposta
     */
    public String getTipoProposta(String id) {
        return apoioPOE.getTipoProposta(id);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número de aluno a editar
     * @param nome - novo nome do aluno
     * @param siglaCurso - nova sigla do curso do aluno
     * @param siglaRamo - nova sigla do ramo do aluno
     * @param classificacao - nova classificação do aluno
     * @param acessoEstagio - o aluno tem acesso a estágio? sim ou não
     * @return se o aluno foi editado ou não
     */
    public boolean editaAluno(long nAluno, String nome, String siglaCurso, String siglaRamo,
                              String classificacao, String acessoEstagio) {
        return apoioPOE.editaAluno(nAluno, nome, siglaCurso, siglaRamo, classificacao, acessoEstagio);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param email - email do docente a editar
     * @param nome - novo nome do docente
     * @return se o docente foi editado ou não
     */
    public boolean editaDocente(String email, String nome) {
        return apoioPOE.editaDocente(email, nome);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta a editar
     * @param titulo - novo título da proposta
     * @param ramos - novo ramo da proposta
     * @param entidade_docente - novos entidade ou docente associados à proposta
     * @param nAluno - novo número de aluno
     * @return se a proposta foi editada ou não
     */
    public boolean editaProposta(String id, String titulo, String ramos, String entidade_docente, String nAluno) {
        return apoioPOE.editaProposta(id, titulo, ramos, entidade_docente, nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param id - id da proposta a editar
     * @param titulo - novo título da proposta
     * @param nAluno - novo número de aluno da proposta
     * @return se a autoproposta foi editada ou não
     */
    public boolean editaProposta(String id, String titulo, String nAluno) {
        return apoioPOE.editaProposta(id, titulo, nAluno);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param nAluno - número do aluno associado à candidatura, que vai ser editada
     * @param propostas - lista de propostas a editar
     * @return se a candidatura foi editada
     */
    public boolean editaCandidatura(long nAluno, ArrayList<String> propostas) {
        return apoioPOE.editaCandidatura(nAluno, propostas);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param comPropostaAtribuida - lista de alunos que têm proposta atribuída e que não têm proposta atribuída
     * @return lista de alunos, segundo os filtors da fase 5
     */
    public ArrayList<Aluno> consultarAlunosFase5(boolean comPropostaAtribuida) {
        return apoioPOE.consultarAlunosFase5(comPropostaAtribuida);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param comOrientadorAssociado - lista de alunos com arientadores e sem orientadores
     * @return lista de alunos com orientadores associados
     */
    public ArrayList<Aluno> consultarAlunos(boolean comOrientadorAssociado) {
        return apoioPOE.consultarAlunos(comOrientadorAssociado);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param autoproposta - lista de alunos com autoproposta atribuída e sem autoproposta atribuída
     * @param comCandidatura - lista de alunos com candidatura atribuída
     * @param semCandidatura - lista de alunos sem candidatura atribuída
     * @return lista de alunos segundo os filtros recebidos
     */
    public ArrayList<Aluno> consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean semCandidatura) {
        return apoioPOE.consultarAlunos(autoproposta, comCandidatura, semCandidatura);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param autoproposta - lista de alunos com autoprosta
     * @param comCandidatura - lista de alunos com candidatura
     * @param comPropostaAtribuida - lista de alunos com proposta atribuída
     * @param semPropostaAtribuida - lista de alunos sem proposta atribuída
     * @return lista de alunos, segundo filtros recebidos
     */
    public ArrayList<Aluno> consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean comPropostaAtribuida, boolean semPropostaAtribuida) {
        return apoioPOE.consultarAlunos(autoproposta, comCandidatura, comPropostaAtribuida, semPropostaAtribuida);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param filtro - email do docente
     * @return lista de orientações dos docentes
     */
    public ArrayList<String> consultarDocentes(String filtro) {
        return apoioPOE.consultarDocentes(filtro);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param propostasAtribuidas - boolean propostas atribuídas ou não atribuídas
     * @return lista de propostas
     */
    public ArrayList<Proposta> consultarPropostas(boolean propostasAtribuidas) {
        return apoioPOE.consultarPropostas(propostasAtribuidas);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param autopropostasAlunos - autopropostas de alunos
     * @param propostasDocentes - propostas de docentes
     * @param comCandidatura - propostas com candidatura
     * @param semCandidatura - propostas sem candidatura
     * @return lista de propostas
     */
    public ArrayList<Proposta> consultarPropostas(boolean autopropostasAlunos, boolean propostasDocentes, boolean comCandidatura, boolean semCandidatura) {
        return apoioPOE.consultarPropostas(autopropostasAlunos, propostasDocentes, comCandidatura, semCandidatura);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param autopropostasAlunos - autopropostas de alunos
     * @param propostasDocentes - propostas de docentes
     * @param propostasDisponiveis - propostas que ainda não foram atribuídas
     * @param propostasAtribuidas - propostas atribuídas
     * @return lista de propostas, segundo os filtros
     */
    public ArrayList<Proposta> consultarPropostasFase3(boolean autopropostasAlunos, boolean propostasDocentes, boolean propostasDisponiveis, boolean propostasAtribuidas) {
        return apoioPOE.consultarPropostasFase3(autopropostasAlunos, propostasDocentes, propostasDisponiveis, propostasAtribuidas);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param email - email do docente
     * @return lista de propostas
     */
    public ArrayList<PropostaAtribuida> consultarPropostasAtribuidasDocente(String email) {
        return apoioPOE.consultarPropostasAtribuidasDocente(email);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param soEstagio - só alunos com acesso a estágio ou todos os alunos
     * @return lista de alunos sem propostas atribuídas
     */
    public ArrayList<Aluno> getAlunosSemPropostaAtribuida(boolean soEstagio) {
        return apoioPOE.getAlunosSemPropostaAtribuida(soEstagio);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param ramo - ramo a verificar
     * @return número de alunos por ramo
     */
    public int nAlunosPorRamo(String ramo) {
        return apoioPOE.nAlunosPorRamo(ramo);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @param ramo - ramo a verificar
     * @return número de propostas para um determinado ramo
     */
    public int propostasPorRamo(String ramo) {
        return apoioPOE.propostasPorRamo(ramo);
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return um array com o número de propostas atribuídas, não atribuídas e total de propostas
     */
    public ArrayList<Integer> propostasAtribuidas_NaoAtribuidas_Total() {
        return apoioPOE.propostas_Atribuidas_NaoAtribuidas_Total();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return lista nome e número de estágios das 5 empresas com mais estágios
     */
    public HashMap<String, Number> top5EmpresasEstagio() {
        return apoioPOE.top5EmpresasEstagio();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return lista de docentes e número de orientações dos 5 docentes com mais orientações
     */
    public HashMap<String, Number> top5DocentesOrientacoes() {
        return apoioPOE.top5DocentesOrientacoes();
    }

    /**
     * método proxy para a classe ApoioPoE
     * @return número de propostas atribuídas com docente orientador
     */
    public int getNumPropotasAtribuidasComOrientador() {
        return apoioPOE.getNumPropotasAtribuidasComOrientador();
    }

    /**
     * função decorator para a classe ApoioPoE que permite adicionar alunos, a partir de um ficheiro csv
     * @param nomeFicheiro - nome do ficheiro com os alunos
     * @return verdadeiro se conseguir adicionar os alunos
     */
    public boolean adicionaAlunosDeFicheiro(String nomeFicheiro){

        try (FileReader fr = new FileReader(nomeFicheiro);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null){
                String[] tokens=line.split(",");

                if(!Boolean.parseBoolean(tokens[6]) && !tokens[6].equalsIgnoreCase("false"))
                    continue;

                adicionaAluno(Long.parseLong(tokens[0]), tokens[1], tokens[2], tokens[3],
                        tokens[4], Double.parseDouble(tokens[5]), Boolean.parseBoolean(tokens[6]));

            }

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }

        if(ErrorOccurred.getInstance().getLastError() != ErrorType.NONE)
            ErrorOccurred.getInstance().setError(ErrorType.PROBLEMS_READING_ALUNOS_FILE);

        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite adicionar docentes, a partir de um ficheiro csv
     * @param nomeFicheiro - nome do ficheiro com os docentes
     * @return verdadeiro se conseguir adicionar os docentes
     */
    public boolean adicionaDocentesDeFicheiro(String nomeFicheiro){

        try (FileReader fr = new FileReader(nomeFicheiro);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null){
                String[] tokens=line.split(",");

                adicionaDocente(tokens[0], tokens[1]);
            }

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }

        if(ErrorOccurred.getInstance().getLastError() != ErrorType.NONE)
            ErrorOccurred.getInstance().setError(ErrorType.PROBLEMS_READING_DOCENTES_FILE);

        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite adicionar propostas, a partir de um ficheiro csv
     * @param nomeFicheiro - nome do ficheiro com as propostas
     * @return verdadeiro se conseguir adicionar as propostas
     */
    public boolean adicionaPropostasDeFicheiro(String nomeFicheiro){

        try (FileReader fr = new FileReader(nomeFicheiro);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null){
                String[] tokens = line.split(",");

                switch (tokens[0]){
                    case "T1", "T2" -> {
                        if(tokens.length == 6)
                            adicionaProposta(tokens[0], tokens[1], tokens[3], Long.parseLong(tokens[5]), tokens[2], tokens[4]); // com numero de aluno associado
                        else
                            adicionaProposta(tokens[0], tokens[1], tokens[3], tokens[2], tokens[4]); // sem numero de aluno associado
                    }
                    case "T3" -> adicionaProposta(tokens[0], tokens[1], tokens[2], Long.parseLong(tokens[3]));
                }
            }

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }

        if(ErrorOccurred.getInstance().getLastError() != ErrorType.NONE)
            ErrorOccurred.getInstance().setError(ErrorType.PROBLEMS_READING_PROPOSTAS_FILE);

        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite adicionar candidaturas, a partir de um ficheiro csv
     * @param nomeFicheiro - nome do ficheiro com as candidaturas
     * @return verdadeiro se conseguir adicionar as candidaturas
     */
    public boolean adicionaCandidaturaDeFicheiro(String nomeFicheiro){

        try(FileReader fr = new FileReader(nomeFicheiro);
            BufferedReader br = new BufferedReader(fr)){

            String line;
            while((line = br.readLine()) != null){

                String[] tokens = line.split(",");

                adicionaCandidatura(Long.parseLong(tokens[0]), new ArrayList<>(
                        List.of(Arrays.copyOfRange(tokens, 1, tokens.length)))
                );

            }

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }

        if(ErrorOccurred.getInstance().getLastError() != ErrorType.NONE)
            ErrorOccurred.getInstance().setError(ErrorType.PROBLEMS_READING_CANDIDATURAS_FILE);

        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite exportar alunos para um ficheiro csv
     * @param filename - nome do ficheiro de destino
     * @return se conseguiu exportar os dados
     */
    public boolean exportAlunosCsv(String filename){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
        )
        ){

            StringBuilder sb = new StringBuilder();

            for(var aluno : apoioPOE.getAlunos()){

                sb.append(aluno.getNAluno()).append(",").append(aluno.getNome()).append(",");
                sb.append(aluno.getEmail()).append(",").append(aluno.getSiglaCurso()).append(",");
                sb.append(aluno.getSiglaRamo()).append(",").append(aluno.getClassificacao()).append(",");
                sb.append(aluno.isAcessoEstagio()).append(System.lineSeparator());
            }

            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }

        return true;
    }
    /**
     * função decorator para a classe ApoioPoE que permite exportar docentes para um ficheiro csv
     * @param filename - nome do ficheiro de destino
     * @return se conseguiu exportar os dados
     */
    public boolean exportDocentesCsv(String filename){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
        )
        ){

            StringBuilder sb = new StringBuilder();

            for(var docente : apoioPOE.getDocentes()){

                sb.append(docente.getNome()).append(",").append(docente.getEmail()).append(System.lineSeparator());

            }

            pw.print(sb);

        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }
        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite exportar propostas para um ficheiro csv
     * @param filename - nome do ficheiro de destino
     * @return se conseguiu exportar os dados
     */
    public boolean exportPropostasCsv(String filename){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
        )
        ){

            StringBuilder sb = new StringBuilder();

            for(var proposta : apoioPOE.getPropostas()){

                if(proposta instanceof Estagio e) {
                    sb.append("T1,").append(e.getId()).append(",").append(e.getAreasDestino()).append(",");
                    sb.append(e.getTitulo()).append(",").append(e.getEntidadeAcolhimento());

                    if (e.getNAlunoAssociado() != 0)
                        sb.append(",").append(e.getNAlunoAssociado());

                    sb.append(System.lineSeparator());

                } else if (proposta instanceof Projeto p) {
                    sb.append("T2,").append(p.getId()).append(",").append(p.getRamosDestino()).append(",");
                    sb.append(p.getTitulo()).append(",").append(p.getEmailDocente());

                    if (p.getNAlunoAssociado() != 0)
                        sb.append(",").append(p.getNAlunoAssociado());

                    sb.append(System.lineSeparator());
                }
                else if(proposta instanceof Autoproposto a){
                    sb.append("T3,").append(a.getId()).append(",").append(a.getTitulo()).append(",");
                    sb.append(a.getNAlunoAssociado()).append(System.lineSeparator());
                }
            }
            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }
        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite exportar candidaturas para um ficheiro csv
     * @param filename - nome do ficheiro de destino
     * @return se conseguiu exportar os dados
     */
    public boolean exportCandidaturasCsv(String filename){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
        )
        ){

            StringBuilder sb = new StringBuilder();

            for(var candidatura : apoioPOE.getCandidaturas()){

                sb.append(candidatura.getNAluno());

                for(var proposta : candidatura.getIdPropostas())
                    sb.append(",").append(proposta);

                sb.append(System.lineSeparator());
            }

            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }
        return true;
    }

    /**
     * função decorator para a classe ApoioPoE que permite exportar propostas atribuídas para um ficheiro csv
     * @param filename - nome do ficheiro de destino
     * @return se conseguiu exportar os dados
     */
    public boolean exportPropostasAtribuidasCsv(String filename, boolean guardarOrientador){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
        )
        ){

            StringBuilder sb = new StringBuilder();

            for(var propostaAtribuida : apoioPOE.getPropostasAtribuidas()){

                sb.append(propostaAtribuida.getId()).append(",").append(propostaAtribuida.getNAlunoAssociado());
                sb.append(",").append(propostaAtribuida.getOrdemPreferencia());

                if(guardarOrientador && propostaAtribuida.getEmailDocenteOrientador() != null)
                    sb.append(",").append(propostaAtribuida.getEmailDocenteOrientador());

                sb.append(System.lineSeparator());
            }

            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }
        return true;
    }

    /**
     * função privada que permite obter um clone dos dados
     * @return clone dos dados
     */
    private ApoioPoE getApoioPOE(){return apoioPOE.clone();}

    /**
     * nested class que representa um snapshot aos dados
     */
    private static class ApoioPoEManagerMemento implements IMemento{

        /**
         * cópia dos dados
         */
        private final ApoioPoE apoioPOE;

        /**
         * construtor que copia os dados
         * @param base - objeto de onde são copiados os dados
         */
        ApoioPoEManagerMemento(ApoioPoEManager base) {
            this.apoioPOE = base.getApoioPOE();
        }

        /**
         * função que permite obter os dados copiados
         * @return cópia dos dados
         */
        @Override
        public ApoioPoE getSnapshot() {
            return apoioPOE;
        }
    }

    /**
     * função que permite criar um save dos dados atuais
     * @return save dos dados atuais
     */
    @Override
    public IMemento save() {
        return new ApoioPoEManagerMemento(this);
    }

    /**
     * função que permite restaurar os dados a partir de um save
     * @param memento - objeto da classe ApoioPoEManagerMemento com os dados
     */
    @Override
    public void restore(IMemento memento) {

        if(memento instanceof ApoioPoEManagerMemento apoioPoEManagerMemento){
            this.apoioPOE = apoioPoEManagerMemento.getSnapshot();

        }
    }
}
