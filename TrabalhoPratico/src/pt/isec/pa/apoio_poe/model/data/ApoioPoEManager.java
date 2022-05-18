package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.*;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;
import pt.isec.pa.apoio_poe.model.memento.IMemento;
import pt.isec.pa.apoio_poe.model.memento.IOriginator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApoioPoEManager implements Serializable, IOriginator {

    @Serial
    private static final long serialVersionUID = 1L;
    private ApoioPoE apoioPOE;

    public ApoioPoEManager(ApoioPoE apoioPOE) {
        this.apoioPOE = apoioPOE;
    }

    public int getFaseBloqueada() {
        return apoioPOE.getFaseBloqueada();
    }

    public void setFaseBloqueada(int faseBloqueada) {
        apoioPOE.setFaseBloqueada(faseBloqueada);
    }

    public boolean adicionaAluno(long nAluno, String nome, String email, String siglaCurso, String siglaRamo,
                                 double classificacao, boolean acessoEstagio) {
        return apoioPOE.adicionaAluno(nAluno, nome, email, siglaCurso, siglaRamo, classificacao, acessoEstagio);
    }

    public boolean adicionaDocente(String nome, String email) {
        return apoioPOE.adicionaDocente(nome, email);
    }

    public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado,
                                    String areasDestino, String entidadeOuDocente) {
        return apoioPOE.adicionaProposta(tipo, id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente);
    }

    public boolean adicionaProposta(String tipo, String id, String titulo, String areasDestino, String entidadeOuDocente) {
        return apoioPOE.adicionaProposta(tipo, id, titulo, areasDestino, entidadeOuDocente);
    }

    public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado) {
        return apoioPOE.adicionaProposta(tipo, id, titulo, nAlunoAssociado);
    }

    public boolean adicionaCandidatura(long nAluno, ArrayList<String> propostas) {
        return apoioPOE.adicionaCandidatura(nAluno, propostas);
    }

    public boolean atribuirPropostaAluno(String idProposta, long nAluno) {
        return apoioPOE.atribuirPropostaAluno(idProposta, nAluno);
    }
    public boolean atribuicaoAutomaticaPropostasComAluno() {
        return apoioPOE.atribuicaoAutomaticaPropostasComAluno();
    }

    public boolean atribuirPropostaDocenteOrientador(String idProposta, String email) {
        return apoioPOE.atribuirPropostaDocenteOrientador(idProposta, email);
    }
    public boolean associacaoAutomaticaDocentesProponentes() {
        return apoioPOE.associacaoAutomaticaDocentesProponentes();
    }

    public Aluno getAluno(long nAluno) {
        return apoioPOE.getAluno(nAluno);
    }
    public Docente getDocente(String email) {
        return apoioPOE.getDocente(email);
    }
    public Proposta getProposta(String id) {
        return apoioPOE.getProposta(id);
    }
    public Candidatura getCandidatura(long nAluno) {
        return apoioPOE.getCandidatura(nAluno);
    }
    public PropostaAtribuida getPropostaAtribuida(String id) {
        return apoioPOE.getPropostaAtribuida(id);
    }

    public ArrayList<Aluno> getAlunos() {
        return apoioPOE.getAlunos();
    }
    public ArrayList<Docente> getDocentes() {
        return apoioPOE.getDocentes();
    }
    public ArrayList<Proposta> getPropostas() {
        return apoioPOE.getPropostas();
    }
    public ArrayList<Candidatura> getCandidaturas() {
        return apoioPOE.getCandidaturas();
    }
    public ArrayList<PropostaAtribuida> getPropostasAtribuidas() {
        return apoioPOE.getPropostasAtribuidas();
    }

    public boolean removeAluno(long nAluno) {
        return apoioPOE.removeAluno(nAluno);
    }

    public boolean removeDocente(String email) {
        return apoioPOE.removeDocente(email);
    }

    public boolean removeProposta(String id) {
        return apoioPOE.removeProposta(id);
    }

    public boolean removeCandidatura(long id) {
        return apoioPOE.removeCandidatura(id);
    }

    public boolean removePropostaAtribuida(String id) {
        return apoioPOE.removePropostaAtribuida(id);
    }

    public boolean removeOrientadorPropostaAtribuida(String id) {
        return apoioPOE.removeOrientadorPropostaAtribuida(id);
    }

    public boolean propostasSufecienteParaRamo(String ramo) {
        return apoioPOE.propostasSufecienteParaRamo(ramo);
    }

    public boolean todasCandidaturasComPropostaAtribuida() {
        return apoioPOE.todasCandidaturasComPropostaAtribuida();
    }

    public int calculaNumeroOrientacoesDocente(String email) {
        return apoioPOE.calculaNumeroOrientacoesDocente(email);
    }

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
            ErrorOccurred.getInstance().setError(ErrorsTypes.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }

        return true;
    }

    public boolean adicionaDocentesDeFicheiro(String nomeFicheiro){

        try (FileReader fr = new FileReader(nomeFicheiro);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null){
                String[] tokens=line.split(",");

                adicionaDocente(tokens[0], tokens[1]);
            }

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }

        return true;
    }

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
            ErrorOccurred.getInstance().setError(ErrorsTypes.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }

        return true;
    }

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
            ErrorOccurred.getInstance().setError(ErrorsTypes.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }

        return true;
    }

    public boolean exportAlunosCsv(String filename){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
            )
        ){

            StringBuilder sb = new StringBuilder();

            for(var aluno : apoioPOE.getAlunos()){

                sb.append(aluno.getnAluno()).append(",").append(aluno.getNome()).append(",");
                sb.append(aluno.getEmail()).append(",").append(aluno.getSiglaCurso()).append(",");
                sb.append(aluno.getSiglaRamo()).append(",").append(aluno.getClassificacao()).append(",");
                sb.append(aluno.isAcessoEstagio()).append(System.lineSeparator());
            }

            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }

        return true;
    }
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
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }
        return true;
    }
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

                    if (e.getnAlunoAssociado() != 0)
                        sb.append(",").append(e.getnAlunoAssociado());

                    sb.append(System.lineSeparator());

                } else if (proposta instanceof Projeto p) {
                    sb.append("T2,").append(p.getId()).append(",").append(p.getRamosDestino()).append(",");
                    sb.append(p.getTitulo()).append(",").append(p.getEmailDocente());

                    if (p.getnAlunoAssociado() != 0)
                        sb.append(",").append(p.getnAlunoAssociado());

                    sb.append(System.lineSeparator());
                }
                else if(proposta instanceof Autoproposto a){
                    sb.append("T3,").append(a.getId()).append(",").append(a.getTitulo()).append(",");
                    sb.append(a.getnAlunoAssociado()).append(System.lineSeparator());
                }
            }
            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }
        return true;
    }
    public boolean exportCandidaturasCsv(String filename){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
            )
        ){

            StringBuilder sb = new StringBuilder();

            for(var candidatura : apoioPOE.getCandidaturas()){

                    sb.append(candidatura.getnAluno());

                    for(var proposta : candidatura.getIdPropostas())
                        sb.append(",").append(proposta);

                sb.append(System.lineSeparator());
            }

            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }
        return true;
    }
    public boolean exportPropostasAtribuidasCsv(String filename, boolean guardarOrientador){
        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filename)
                )
        )
        ){

            StringBuilder sb = new StringBuilder();

            for(var propostaAtribuida : apoioPOE.getPropostasAtribuidas()){

                sb.append(propostaAtribuida.getId()).append(",").append(propostaAtribuida.getnAlunoAssociado());
                sb.append(",").append(propostaAtribuida.getOrdemPreferencia());

                if(guardarOrientador && propostaAtribuida.getEmailDocenteOrientador() != null)
                    sb.append(",").append(propostaAtribuida.getEmailDocenteOrientador());

                sb.append(System.lineSeparator());
            }

            pw.print(sb);

        }catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }
        return true;
    }

    private ApoioPoE getApoioPOE(){return apoioPOE.clone();}

    private static class ApoioPoEManagerMemento implements IMemento{

        private final ApoioPoE apoioPOE;

        ApoioPoEManagerMemento(ApoioPoEManager base) {
            this.apoioPOE = base.getApoioPOE();
        }

    }

    @Override
    public IMemento save() {
        return new ApoioPoEManagerMemento(this);
    }

    @Override
    public void restore(IMemento memento) {

        if(memento instanceof ApoioPoEManagerMemento apoioPoEManagerMemento){
            this.apoioPOE = apoioPoEManagerMemento.apoioPOE;

        }
    }

    public String getTipoProposta(String id) {
        return apoioPOE.getTipoProposta(id);
    }

    public boolean editaAluno(long nAluno, String nome, String siglaCurso, String siglaRamo,
                              String classificacao, String acessoEstagio) {
        return apoioPOE.editaAluno(nAluno, nome, siglaCurso, siglaRamo, classificacao, acessoEstagio);
    }

    public boolean editaDocente(String email, String nome) {
        return apoioPOE.editaDocente(email, nome);
    }

    public boolean editaProposta(String id, String titulo, String ramos, String entidade_docente, String nAluno) {
        return apoioPOE.editaProposta(id, titulo, ramos, entidade_docente, nAluno);
    }

    public boolean editaProposta(String id, String titulo, String nAluno) {
        return apoioPOE.editaProposta(id, titulo, nAluno);
    }

    public boolean editaCandidatura(long nAluno, ArrayList<String> propostas) {
        return apoioPOE.editaCandidatura(nAluno, propostas);
    }

    public String consultarAlunosFase5(boolean comPropostaAtribuida) {
        return apoioPOE.consultarAlunosFase5(comPropostaAtribuida);
    }

    public String consultarAlunos(boolean comOrientadorAssociado) {
        return apoioPOE.consultarAlunos(comOrientadorAssociado);
    }

    public String consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean semCandidatura) {
        return apoioPOE.consultarAlunos(autoproposta, comCandidatura, semCandidatura);
    }

    public String consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean comPropostaAtribuida, boolean semPropostaAtribuida) {
        return apoioPOE.consultarAlunos(autoproposta, comCandidatura, comPropostaAtribuida, semPropostaAtribuida);
    }

    public String consultarDocentes(String filtro) {
        return apoioPOE.consultarDocentes(filtro);
    }

    public String consultarPropostas(boolean propostasAtribuidas) {
        return apoioPOE.consultarPropostas(propostasAtribuidas);
    }

    public String consultarPropostas(boolean autopropostasAlunos, boolean propostasDocentes, boolean comCandidatura, boolean semCandidatura) {
        return apoioPOE.consultarPropostas(autopropostasAlunos, propostasDocentes, comCandidatura, semCandidatura);
    }

    public String consultarPropostasFase3(boolean autopropostasAlunos, boolean propostasDocentes, boolean propostasDisponiveis, boolean propostasAtribuidas) {
        return apoioPOE.consultarPropostasFase3(autopropostasAlunos, propostasDocentes, propostasDisponiveis, propostasAtribuidas);
    }

    public ArrayList<PropostaAtribuida> consultarPropostasAtribuidasDocente(String email) {
        return apoioPOE.consultarPropostasAtribuidasDocente(email);
    }

    public ArrayList<Aluno> getAlunosSemPropostaAtribuida(boolean soEstagio) {
        return apoioPOE.getAlunosSemPropostaAtribuida(soEstagio);
    }
}

