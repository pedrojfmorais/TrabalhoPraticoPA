package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.*;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorType;

import java.io.*;
import java.util.*;

/**
 * Classe ApoioPoE que guarda as informações dos alunos, docentes e propostas e a lógica do programa
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class ApoioPoE implements Serializable, Cloneable {

   /**
    * Versão do Serial da classe
    */
   @Serial
   private static final long serialVersionUID = 1L;

   /**
    * Indica a fase bloqueada
    */
   private int faseBloqueada;

   /**
    * Lista de alunos, em que a chave é o número de aluno
    */
   private final HashMap<Long, Aluno> alunos;
   /**
    * Lista de docentes, em que a chave é o email do docente
    */
   private final HashMap<String, Docente> docentes;
   /**
    * Lista de propostas, em que a chave é o id da proposta
    */
   private final HashMap<String, Proposta> propostas;
   /**
    * Lista de candidaturas, em que a chave é o número de aluno associado à candidatura
    */
   private final HashMap<Long, Candidatura> candidaturas;
   /**
    * Lista de propostas atribuídas, em que a chave é o id da proposta
    */
   private final HashMap<String, PropostaAtribuida> propostasAtribuidas;

   /**
    * Construtor público
    */
   public ApoioPoE() {
      alunos = new HashMap<>();
      docentes = new HashMap<>();
      propostas = new HashMap<>();
      candidaturas = new HashMap<>();
      propostasAtribuidas = new HashMap<>();

      faseBloqueada = 0;
   }

   /**
    * Obter o número correspondente à fase bloqueada
    * @return faseBloqueada - número correspondente à fase bloqueada
    */
   public int getFaseBloqueada() {
      return faseBloqueada;
   }

   /**
    * Alterar o número correspondente à fase bloqueada
    * @param faseBloqueada nova faseBloqueada
    */
   public void setFaseBloqueada(int faseBloqueada) {
      this.faseBloqueada = faseBloqueada;
   }


   /**
    * Adicionar alunos, realizando as verificações necessárias
    * @param nAluno - número de aluno
    * @param nome - nome do aluno
    * @param email - email do aluno
    * @param siglaCurso - sigla correspondente ao curso do aluno
    * @param siglaRamo - sigla correspondente ao ramo do aluno
    * @param classificacao - classificação do aluno
    * @param acessoEstagio - o aluno tem acesso a estágio? sim ou não
    * @return se o aluno foi adicionado ou não
    */

   public boolean adicionaAluno(long nAluno, String nome, String email, String siglaCurso, String siglaRamo,
                                double classificacao, boolean acessoEstagio) {

      if (alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_NUMERO_ALUNO);
         return false;
      }

      if (docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_EMAIL);
         return false;
      }

      for (var aluno : alunos.values())
         if (aluno.getEmail().equals(email)) {
            ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_EMAIL);
            return false;
         }

      if (!Aluno.cursos.contains(siglaCurso)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_CURSO);
         return false;
      }

      if (!Aluno.ramos.contains(siglaRamo)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_RAMO);
         return false;
      }

      if (classificacao > 1.0 || classificacao < 0.0) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_CLASSIFICACAO);
         return false;
      }

      alunos.put(nAluno, new Aluno(nAluno, nome, email, siglaCurso, siglaRamo, classificacao, acessoEstagio));

      return true;
   }

   /**
    * Adicionar docentes, fazendo as verificações necessárias
    * @param nome - nome do docente
    * @param email - email do docente
    * @return se o docente foi adicionado ou não
    */
   public boolean adicionaDocente(String nome, String email) {

      if (docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_EMAIL);
         return false;
      }

      for (var aluno : alunos.values())
         if (aluno.getEmail().equals(email)) {
            ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_EMAIL);
            return false;
         }

      docentes.put(email, new Docente(nome, email));

      return true;
   }

   /**
    * Adicionar propostas com aluno associado, fazendo as verificações necessárias
    * @param tipo - tipo de proposta
    * @param id - id da proposta
    * @param titulo - titulo da proposta
    * @param nAlunoAssociado - número do aluno associado à proposta
    * @param areasDestino - área correspondente à proposta
    * @param entidadeOuDocente - entidade ou docente associados à proposta
    * @return se a proposta foi adicionada ou não
    */
   public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado,
                                   String areasDestino, String entidadeOuDocente) {

      if (propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      if (!alunos.containsKey(nAlunoAssociado)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      String[] areas = areasDestino.trim().split("\\|");
      if (!Aluno.ramos.containsAll(List.of(areas))) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_RAMO);
         return false;
      }

      for (var proposta : this.propostas.values())
         if (proposta.getNAlunoAssociado() == nAlunoAssociado) {
            ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }

      switch (tipo) {
         case "T1" -> propostas.put(id, new Estagio(id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente));
         case "T2" -> {

            if (!docentes.containsKey(entidadeOuDocente)) {
               ErrorOccurred.getInstance().setError(ErrorType.INVALID_DOCENTE);
               return false;
            }

            propostas.put(id, new Projeto(id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente));
         }
         default -> {
            return false;
         }
      }

      return true;
   }

   /**
    * Adicionar propostas sem associar aluno, fazendo as verificações necessárias
    * @param tipo - tipo de proposta
    * @param id - id da proposta
    * @param titulo - título da proposta
    * @param areasDestino - área correspondente à proposta
    * @param entidadeOuDocente - entidade ou docente associados à proposta
    * @return se a proposta foi adicionada ou não
    */
   public boolean adicionaProposta(String tipo, String id, String titulo,
                                   String areasDestino, String entidadeOuDocente) {

      if (propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      String[] areas = areasDestino.trim().split("\\|");

      if (!Aluno.ramos.containsAll(List.of(areas))) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_RAMO);
         return false;
      }

      switch (tipo) {
         case "T1" -> propostas.put(id, new Estagio(id, titulo, areasDestino, entidadeOuDocente));
         case "T2" -> {

            if (!docentes.containsKey(entidadeOuDocente)) {
               ErrorOccurred.getInstance().setError(ErrorType.INVALID_DOCENTE);
               return false;
            }

            propostas.put(id, new Projeto(id, titulo, areasDestino, entidadeOuDocente));
         }
         default -> {
            return false;
         }
      }

      return true;
   }

   /**
    * Adicionar proposta do tipo autoproposta, realizando as verificações necessárias
    * @param tipo - tipo da proposta
    * @param id - id da proposta
    * @param titulo - título da proposta
    * @param nAlunoAssociado - número do aluno associado à proposta
    * @return se a proposta foi adicionada ou não
    */
   public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado) {

      if (propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      if (!alunos.containsKey(nAlunoAssociado)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      for (var proposta : propostas.values())
         if (proposta.getNAlunoAssociado() == nAlunoAssociado) {
            ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }

      if ("T3".equals(tipo)) {
         propostas.put(id, new Autoproposto(id, titulo, nAlunoAssociado));
      } else
         return false;

      return true;
   }

   /**
    * Adicionar candidaturas, realizando as verificações necessárias
    * @param nAluno - número de aluno
    * @param propostas - lista de propostas
    * @return se a candidatura foi adicionada ou não
    */
   public boolean adicionaCandidatura(long nAluno, ArrayList<String> propostas) {

      if (propostas.isEmpty()) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      if (candidaturas.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_CANDIDATURA);
         return false;
      }

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      for (var proposta : propostas) {
         if (!this.propostas.containsKey(proposta)) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
            return false;
         }

         if (this.propostas.get(proposta).getNAlunoAssociado() != 0) {
            ErrorOccurred.getInstance().setError(ErrorType.PROPOSTA_JA_TEM_ALUNO_ASSOCIADO);
            return false;
         }

         Proposta propostaAtual = this.propostas.get(proposta);

         boolean areaDoAlunoCorrespondeADaProposta = false;
         if(propostaAtual instanceof Estagio e) {
            for (var area : e.getAreasDestino().split("\\|"))
               if (area.equals(alunos.get(nAluno).getSiglaRamo()))
                  areaDoAlunoCorrespondeADaProposta = true;

         } else if(propostaAtual instanceof Projeto p) {
            for (var area : p.getRamosDestino().split("\\|"))
               if (area.equals(alunos.get(nAluno).getSiglaRamo()))
                  areaDoAlunoCorrespondeADaProposta = true;
         }

         if(!areaDoAlunoCorrespondeADaProposta){
            ErrorOccurred.getInstance().setError(ErrorType.ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM);
            return false;
         }

      }

      for (var proposta : this.propostas.values())
         if (proposta.getNAlunoAssociado() == nAluno) {
            ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }



      HashSet<String> testeDuplicados = new HashSet<>(propostas);
      if (testeDuplicados.size() != propostas.size()) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_ID_PROPOSTA);
         return false;
      }
      candidaturas.put(nAluno, new Candidatura(nAluno, propostas));

      return true;
   }

   /**
    * Atribuir propostas aos alunos, realizando as verificações necessárias
    * @param idProposta - id da proposta
    * @param nAluno - número de aluno
    * @return se a proposta foi atribuída ou não
    */
   public boolean atribuirPropostaAluno(String idProposta, long nAluno) {

      int ordemPreferencia = 1;

      if (propostasAtribuidas.containsKey(idProposta)) {
         ErrorOccurred.getInstance().setError(ErrorType.PROPOSTA_JA_FOI_ATRIBUIDA);
         return false;
      }

      Proposta propostaAtual = propostas.get(idProposta);

      if (propostaAtual == null) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      for (var propostaAtribuida : propostasAtribuidas.values())
         if (propostaAtribuida.getNAlunoAssociado() == nAluno) {
            ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }

      boolean areaDoAlunoCorrespondeADaProposta = false;
      if(propostaAtual instanceof Estagio e) {
         for (var area : e.getAreasDestino().split("\\|"))
            if (area.equals(alunos.get(nAluno).getSiglaRamo()))
               areaDoAlunoCorrespondeADaProposta = true;

      } else if(propostaAtual instanceof Projeto p) {
         for (var area : p.getRamosDestino().split("\\|"))
            if (area.equals(alunos.get(nAluno).getSiglaRamo()))
               areaDoAlunoCorrespondeADaProposta = true;
      }

      if(!areaDoAlunoCorrespondeADaProposta){
         ErrorOccurred.getInstance().setError(ErrorType.ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM);
         return false;
      }

      Candidatura candidaturaAluno = getCandidatura(nAluno);
      if (candidaturaAluno != null) {
         for (var idProp : candidaturaAluno.getIdPropostas()) {

            if (idProp.equals(idProposta)) {
               ordemPreferencia = candidaturaAluno.getIdPropostas().indexOf(idProp) + 1;
               break;
            }
         }
      }

      propostasAtribuidas.put(idProposta, new PropostaAtribuida(idProposta, propostaAtual.getTitulo(),
              nAluno, ordemPreferencia));

      return true;
   }

   /**
    * Atrbuição automática de propostas, que já têm aluno associado
    * @return se existem propostas para atribuir automaticamente
    */
   public boolean atribuicaoAutomaticaPropostasComAluno() {

      HashSet<Proposta> propostasAtribuir = new HashSet<>();

      for (var proposta : propostas.values())

         if (proposta instanceof Autoproposto || (proposta instanceof Projeto && proposta.getNAlunoAssociado() != 0))
            propostasAtribuir.add(proposta);

      if (propostasAtribuir.isEmpty())
         return false;

      for (var p : propostasAtribuir)
         atribuirPropostaAluno(p.getId(), p.getNAlunoAssociado());

      return true;
   }

   /**
    * Atribuir propostas aos docentes orientadores
    * @param idProposta - id da proposta
    * @param email - email do docente
    * @return se a proposta foi atribuída ou não
    */
   public boolean atribuirPropostaDocenteOrientador(String idProposta, String email) {

      if (!propostasAtribuidas.containsKey(idProposta) && propostas.containsKey(idProposta)) {
         ErrorOccurred.getInstance().setError(ErrorType.PROPOSTA_AINDA_NAO_ATRIBUIDA);
         return false;
      }

      if (!propostas.containsKey(idProposta)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      if (!docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_DOCENTE);
         return false;
      }

      propostasAtribuidas.get(idProposta).setEmailDocenteOrientador(email);

      return true;
   }

   /**
    * Atribuição automática de docentes às propostas atribuídas
    * @return se algum docente foi atribuído
    */
   public boolean associacaoAutomaticaDocentesProponentes() {

      boolean associadoAlgum = false;

      for (var propostaAtribuida : getPropostasAtribuidas()) {

         if (getProposta(propostaAtribuida.getId()) instanceof Projeto p) {
            propostaAtribuida.setEmailDocenteOrientador(p.getEmailDocente());
            associadoAlgum = true;
         }
      }

      return associadoAlgum;
   }

   /**
    * Obter clone de um aluno
    * @param nAluno - número do aluno a procurar
    * @return clone do aluno pretendido, ou null caso este não exitsa
    */
   public Aluno getAluno(long nAluno) {
      Aluno aux = alunos.get(nAluno);
      return aux != null ? aux.clone() : null;
   }

   /**
    * Obter clone de um docente
    * @param email - email do docente a procurar
    * @return clone do docente pretendido ou null caso este não exista
    */
   public Docente getDocente(String email) {
      Docente aux = docentes.get(email);
      return aux != null ? aux.clone() : null;
   }

   /**
    * Obter clone de uma proposta
    * @param id - id da proposta a procurar
    * @return clone da proposta pretendida ou null caso esta não exista
    */
   public Proposta getProposta(String id) {
      Proposta aux = propostas.get(id);
      return aux != null ? aux.clone() : null;
   }

   /**
    * Obter clone de uma candidatura
    * @param nAluno - número do aluno a procurar
    * @return clone da candidatura pretendida ou null caso esta não exista
    */
   public Candidatura getCandidatura(long nAluno) {
      Candidatura aux = candidaturas.get(nAluno);
      return aux != null ? aux.clone() : null;
   }

   /**
    * Obter clone de uma proposta atribuída
    * @param id - id da proposta atribuída a procurar
    * @return clone da proposta atribuída pretendida ou null caso esta não exista
    */
   public PropostaAtribuida getPropostaAtribuida(String id) {
      PropostaAtribuida aux = propostasAtribuidas.get(id);
      return aux != null ? aux.clone() : null;
   }

   /**
    * Obter todos os alunos
    * @return clone com a lista de alunos ordenada
    */
   public ArrayList<Aluno> getAlunos() {
      ArrayList<Aluno> alunosOrdenados = new ArrayList<>(alunos.values());

      Collections.sort(alunosOrdenados);
      return (ArrayList<Aluno>) alunosOrdenados.clone();
   }

   /**
    * Obter todos os docentes
    * @return clone com a lista de docentes ordenada
    */
   public ArrayList<Docente> getDocentes() {
      ArrayList<Docente> docentesOrdenados = new ArrayList<>(docentes.values());

      Collections.sort(docentesOrdenados);
      return (ArrayList<Docente>) docentesOrdenados.clone();
   }

   /**
    * Obter todas as propostas
    * @return clone com a lista de propostas ordenada
    */
   public ArrayList<Proposta> getPropostas() {
      ArrayList<Proposta> propostasOrdenadas = new ArrayList<>(propostas.values());
      Collections.sort(propostasOrdenadas);
      return (ArrayList<Proposta>) propostasOrdenadas.clone();
   }

   /**
    * Obter todas as candidaturas
    * @return clone com a lista de candidaturas ordenada
    */
   public ArrayList<Candidatura> getCandidaturas() {
      ArrayList<Candidatura> candidaturasOrdenadas = new ArrayList<>(candidaturas.values());
      Collections.sort(candidaturasOrdenadas);
      return (ArrayList<Candidatura>) candidaturasOrdenadas.clone();
   }

   /**
    * Obter todas as propostas atribuídas
    * @return clone com a lista de propostas atribuídas ordenada
    */
   public ArrayList<PropostaAtribuida> getPropostasAtribuidas() {
      ArrayList<PropostaAtribuida> propostaAtribuidaOrdenadas = new ArrayList<>(propostasAtribuidas.values());
      Collections.sort(propostaAtribuidaOrdenadas);
      return (ArrayList<PropostaAtribuida>) propostaAtribuidaOrdenadas.clone();
   }

   /**
    * Remover um aluno, através do número de aluno
    * @param nAluno - número de aluno
    * @return se o aluno foi removido ou não
    */
   public boolean removeAluno(long nAluno) {

      if(!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      if(candidaturas.containsKey(nAluno))
         removeCandidatura(nAluno);

      ArrayList<String> propostasRemover = new ArrayList<>();

      for (var proposta : getPropostas()) {
         if (proposta instanceof Autoproposto && proposta.getNAlunoAssociado() == nAluno)
            propostasRemover.add(proposta.getId());

         if (proposta.getNAlunoAssociado() == nAluno)
            proposta.setNAlunoAssociado(0);
      }

      for (var p : propostasRemover)
         removeProposta(p);

      //já é removida a proposta atribuida ou no removeCandidatura() ou no removeProposta()

      return alunos.remove(nAluno) != null;
   }

   /**
    * Remover um docente, através do email
    * @param email - email do docente
    * @return se o docente foi removido ou não
    */
   public boolean removeDocente(String email) {

      if(!docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_DOCENTE);
         return false;
      }

      ArrayList<String> propostasRemover = new ArrayList<>();

      for (var proposta : getPropostas())
         if (proposta instanceof Projeto p)
            if (p.getEmailDocente().equals(email))
               propostasRemover.add(proposta.getId());

      for (var idPropostaRemover : propostasRemover)
         removeProposta(idPropostaRemover);

      for (var propostaAtribuida : getPropostasAtribuidas())
         if (propostaAtribuida.getEmailDocenteOrientador().equals(email))
            removeOrientadorPropostaAtribuida(propostaAtribuida.getId());

      return docentes.remove(email) != null;
   }

   /**
    * Remover uma proposta
    * @param id - id da proposta
    * @return se a proposta foi removida ou não
    */
   public boolean removeProposta(String id) {

      if(!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      if(propostasAtribuidas.containsKey(id))
         removePropostaAtribuida(id);

      for (var candidatura : getCandidaturas()) {
         candidatura.removeProposta(id);

         if (candidatura.getIdPropostas().isEmpty())
            removeCandidatura(candidatura.getNAluno());
      }

      return propostas.remove(id) != null;
   }

   /**
    * Remover candidatura de um aluno, através do respetivo número de aluno
    * @param nAluno - número de aluno
    * @return se a candidatura foi removida ou não
    */
   public boolean removeCandidatura(long nAluno) {

      if(!candidaturas.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_CANDIDATURA);
         return false;
      }

      if (getCandidatura(nAluno) == null)
         return false;

      for (var propostaAtribuida : getPropostasAtribuidas())
         if (propostaAtribuida.getNAlunoAssociado() == nAluno)
            removePropostaAtribuida(propostaAtribuida.getId());

      return candidaturas.remove(nAluno) != null;
   }

   /**
    * Remover propostas atribuídas
    * @param id - id da proposta
    * @return se a proposta atribuída foi removida ou não
    */
   public boolean removePropostaAtribuida(String id) {

      if(!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      return propostasAtribuidas.remove(id) != null;
   }

   /**
    * Remover docente orientador atribuído a uma proposta
    * @param id - id da proposta atribuída
    * @return se o docente orientador foi removido ou não
    */
   public boolean removeOrientadorPropostaAtribuida(String id) {

      if (!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      if (propostasAtribuidas.get(id) == null) {
         ErrorOccurred.getInstance().setError(ErrorType.PROPOSTA_AINDA_NAO_ATRIBUIDA);
         return false;
      }

      propostasAtribuidas.get(id).setEmailDocenteOrientador(null);

      return propostasAtribuidas.get(id).getEmailDocenteOrientador() == null;
   }

   /**
    * Verifica se existem propostas suficientes para os alunos de um determinado ramo
    * @param ramo - ramo a verificar
    * @return se existem propostas suficentes
    */
   public boolean propostasSuficienteParaRamo(String ramo) {
      return propostasPorRamo(ramo) >= nAlunosPorRamo(ramo);
   }

   /**
    * Obter número de propostas para um determinado ramo
    * @param ramo - ramo a verificar
    * @return número de propostas para um determinado ramo
    */
   public int propostasPorRamo(String ramo){
      int contadorPropostaRamo = 0;

      for (var proposta : propostas.values()) {

         if (proposta instanceof Projeto p) {

            if (List.of(p.getRamosDestino().split("\\|")).contains(ramo))
               contadorPropostaRamo++;

         } else if (proposta instanceof Estagio e) {

            if (List.of(e.getAreasDestino().split("\\|")).contains(ramo))
               contadorPropostaRamo++;
         }
      }
      return contadorPropostaRamo;
   }

   /**
    * Obter o número de alunos por ramo
    * @param ramo - ramo a verificar
    * @return número de alunos por ramo
    */
   public int nAlunosPorRamo(String ramo){
      int contadorAlunosRamo = 0;

      for (var aluno : alunos.values())
         if (aluno.getSiglaRamo().equals(ramo))
            contadorAlunosRamo++;

      return contadorAlunosRamo;
   }

   /**
    * Verifica se todas as candidaturas têm proposta atribuída
    * @return se toas as candidaturas têm proposta atribuída
    */
   public boolean todasCandidaturasComPropostaAtribuida() {

      ArrayList<Long> alunosComPropostaAtribuida = new ArrayList<>();

      for (var propostasAtribuida : propostasAtribuidas.values())
         alunosComPropostaAtribuida.add(propostasAtribuida.getNAlunoAssociado());

      return alunosComPropostaAtribuida.containsAll(candidaturas.keySet());
   }

   /**
    * Calcula o número de orientações para um determinado docente
    * @param email - email do docente
    * @return número de orientações para um determinado docente
    */
   public int calculaNumeroOrientacoesDocente(String email) {
      int contador = 0;

      for (var propostaAtribuida : getPropostasAtribuidas())
         if (propostaAtribuida.getEmailDocenteOrientador() != null &&
                 propostaAtribuida.getEmailDocenteOrientador().equals(email))
            contador++;

      return contador;
   }

   /**
    * Construtor por cópia privado
    * @param apoioPoE - objeto a ser copiado
    */
   private ApoioPoE(ApoioPoE apoioPoE) {

      this.faseBloqueada = apoioPoE.faseBloqueada;

      this.alunos = new HashMap<>();
      this.docentes = new HashMap<>();
      this.propostas = new HashMap<>();
      this.candidaturas = new HashMap<>();
      this.propostasAtribuidas = new HashMap<>();

      // deep copy dos HashMap
      for (var key : apoioPoE.alunos.keySet())
         this.alunos.put(key, apoioPoE.alunos.get(key).clone());

      for (var key : apoioPoE.docentes.keySet())
         this.docentes.put(key, apoioPoE.docentes.get(key).clone());

      for (var key : apoioPoE.propostas.keySet())
         this.propostas.put(key, apoioPoE.propostas.get(key).clone());

      for (var key : apoioPoE.candidaturas.keySet())
         this.candidaturas.put(key, apoioPoE.candidaturas.get(key).clone());

      for (var key : apoioPoE.propostasAtribuidas.keySet())
         this.propostasAtribuidas.put(key, apoioPoE.propostasAtribuidas.get(key).clone());
   }

   /**
    * Clone do objeto ApoioPoE
    * @return clone
    */
   @Override
   protected ApoioPoE clone() {
      return new ApoioPoE(this);
   }

   /**
    * Obter o tipo de proposta de uma determinada proposta
    * @param id - id da proposta
    * @return tipo de proposta de uma determinada proposta
    */
   public String getTipoProposta(String id) {

      if (getProposta(id) == null)
         return null;

      return getProposta(id).tipoProposta();
   }

   /**
    * Editar aluno
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

      double dClassificacao = 0.0;

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      if (!siglaCurso.isBlank())
         if (!Aluno.cursos.contains(siglaCurso)) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_CURSO);
            return false;
         }

      if (!siglaRamo.isBlank())
         if (!Aluno.ramos.contains(siglaRamo)) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_RAMO);
            return false;
         }

      if (!classificacao.isBlank()) {
         dClassificacao = Double.parseDouble(classificacao);

         if (dClassificacao > 1.0 || dClassificacao < 0.0) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_CLASSIFICACAO);
            return false;
         }
      }

      Aluno aluno = alunos.get(nAluno);

      if (!nome.isBlank())
         aluno.setNome(nome);

      if (!siglaCurso.isBlank())
         aluno.setSiglaCurso(siglaCurso);

      if (!siglaRamo.isBlank())
         aluno.setSiglaRamo(siglaRamo);

      if (!classificacao.isBlank())
         aluno.setClassificacao(dClassificacao);

      if (!acessoEstagio.isBlank())
         aluno.setAcessoEstagio(Boolean.parseBoolean(acessoEstagio));

      return true;

   }

   /**
    * Editar docentes
    * @param email - email do docente a editar
    * @param nome - novo nome do docente
    * @return se o docente foi editado ou não
    */
   public boolean editaDocente(String email, String nome) {

      if (!docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_DOCENTE);
         return false;
      }

      if (nome.isBlank())
         return false;

      docentes.get(email).setNome(nome);

      return true;
   }

   /**
    * Editar propostas
    * @param id - id da proposta a editar
    * @param titulo - novo título da proposta
    * @param ramos - novo ramo da proposta
    * @param entidade_docente - novos entidade ou docente associados à proposta
    * @param nAluno - novo número de aluno
    * @return se a proposta foi editada ou não
    */
   public boolean editaProposta(String id, String titulo, String ramos, String entidade_docente, String nAluno) {

      if (!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      String[] array_ramos = ramos.trim().split("\\|");
      if (!ramos.isBlank() && !Aluno.ramos.containsAll(List.of(array_ramos))) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_RAMO);
         return false;
      }

      if (!nAluno.isBlank()) {
         if (!alunos.containsKey(Long.parseLong(nAluno)) && !nAluno.equals("0")) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
            return false;
         }

         for (var proposta : this.propostas.values())
            if (proposta.getNAlunoAssociado() == Long.parseLong(nAluno) && !proposta.getId().equals(id) && !nAluno.equals("0")) {
               ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_PROPOSTA);
               return false;
            }
      }

      Proposta proposta = propostas.get(id);

      if (!titulo.isBlank())
         proposta.setTitulo(titulo);

      if (!nAluno.isBlank())
         proposta.setNAlunoAssociado(Long.parseLong(nAluno));

      if (proposta instanceof Estagio e) {

         if (!entidade_docente.isBlank())
            e.setEntidadeAcolhimento(entidade_docente);

         if (!ramos.isBlank())
            e.setAreasDestino(ramos);
      }

      if (proposta instanceof Projeto p) {
         if (!entidade_docente.isBlank())
            p.setEmailDocente(entidade_docente);

         if (!ramos.isBlank())
            p.setRamosDestino(ramos);
      }

      return true;
   }

   /**
    * Editar autopropostas
    * @param id - id da proposta a editar
    * @param titulo - novo título da proposta
    * @param nAluno - novo número de aluno da proposta
    * @return se a autoproposta foi editada ou não
    */
   public boolean editaProposta(String id, String titulo, String nAluno) {

      if (!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
         return false;
      }

      if (!nAluno.isBlank()) {
         if (!alunos.containsKey(Long.parseLong(nAluno)) && !nAluno.equals("0")) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
            return false;
         }

         for (var proposta : this.propostas.values())
            if (proposta.getNAlunoAssociado() == Long.parseLong(nAluno) && !proposta.getId().equals(id) && !nAluno.equals("0")) {
               ErrorOccurred.getInstance().setError(ErrorType.ALUNO_JA_TEM_PROPOSTA);
               return false;
            }
      }

      Proposta proposta = propostas.get(id);

      if (!titulo.isBlank())
         proposta.setTitulo(titulo);

      if (!nAluno.isBlank())
         proposta.setNAlunoAssociado(Long.parseLong(nAluno));

      return true;
   }

   /**
    * Editar candidaturas
    * @param nAluno - número do aluno associado à candidatura, que vai ser editada
    * @param propostas - lista de propostas a editar
    * @return se a candidatura foi editada
    */
   public boolean editaCandidatura(long nAluno, ArrayList<String> propostas) {

      if (propostas.isEmpty()) {
         ErrorOccurred.getInstance().setError(ErrorType.SEM_PROPOSTAS_ESPECIFICADAS);
         return false;
      }

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.INVALID_NUMERO_ALUNO);
         return false;
      }

      if (!candidaturas.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorType.ALUNO_NAO_TEM_CANDIDATURA);
         return false;
      }

      for (var proposta : propostas) {
         if (!this.propostas.containsKey(proposta)) {
            ErrorOccurred.getInstance().setError(ErrorType.INVALID_ID_PROPOSTA);
            return false;
         }

         if (this.propostas.get(proposta).getNAlunoAssociado() != 0) {
            ErrorOccurred.getInstance().setError(ErrorType.PROPOSTA_JA_TEM_ALUNO_ASSOCIADO);
            return false;
         }
      }

      HashSet<String> testeDuplicados = new HashSet<>(propostas);
      if (testeDuplicados.size() != propostas.size()) {
         ErrorOccurred.getInstance().setError(ErrorType.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      candidaturas.get(nAluno).setIdPropostas(propostas);

      return true;
   }

   /**
    * Consultar alunos, segundo os filtros da fase 5
    * @param comPropostaAtribuida - lista de alunos que têm proposta atribuída e que não têm proposta atribuída
    * @return lista de alunos, segundo os filtors da fase 5
    */
   public ArrayList<Aluno> consultarAlunosFase5(boolean comPropostaAtribuida){

      HashSet<Aluno> resultado = new HashSet<>();

      if(comPropostaAtribuida)

         for (var propostasAtribuidas : propostasAtribuidas.values())
            resultado.add(alunos.get(propostasAtribuidas.getNAlunoAssociado()));

      else{
         HashSet<Long> alunosComProposta = new HashSet<>();

         for(var propostasAtribuidas : propostasAtribuidas.values())
            alunosComProposta.add(propostasAtribuidas.getNAlunoAssociado());

         for(var aluno : alunos.values())
            if(!alunosComProposta.contains(aluno.getNAluno()) && candidaturas.get(aluno.getNAluno()) != null)
               resultado.add(aluno);
      }

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   /**
    * Consultar alunos com orientadores associados
    * @param comOrientadorAssociado - lista de alunos com arientadores e sem orientadores
    * @return lista de alunos com orientadores associados
    */
   public ArrayList<Aluno> consultarAlunos(boolean comOrientadorAssociado) {

      HashSet<Aluno> resultado = new HashSet<>();

      if (comOrientadorAssociado) {
         for (var propostasAtribuidas : propostasAtribuidas.values())
            if (propostasAtribuidas.getEmailDocenteOrientador() != null)
               resultado.add(alunos.get(propostasAtribuidas.getNAlunoAssociado()));
      } else
         for (var propostasAtribuidas : propostasAtribuidas.values())
            if (propostasAtribuidas.getEmailDocenteOrientador() == null)
               resultado.add(alunos.get(propostasAtribuidas.getNAlunoAssociado()));

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   /**
    * Consultar alunos segundo os filtros recebidos
    * @param autoproposta - lista de alunos com autoproposta atribuída e sem autoproposta atribuída
    * @param comCandidatura - lista de alunos com candidatura atribuída
    * @param semCandidatura - lista de alunos sem candidatura atribuída
    * @return lista de alunos segundo os filtros recebidos
    */
   public ArrayList<Aluno> consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean semCandidatura) {
      HashSet<Aluno> resultado = new HashSet<>();

      if (!autoproposta && !comCandidatura && !semCandidatura)
         resultado = new HashSet<>(alunos.values());

      if (autoproposta) {
         for (var proposta : propostas.values())
            if (proposta instanceof Autoproposto)
               resultado.add(alunos.get(proposta.getNAlunoAssociado()));
      }

      if (comCandidatura) {
         for (var candidatura : candidaturas.values())
            resultado.add(alunos.get(candidatura.getNAluno()));
      }

      if (semCandidatura) {
         HashSet<Long> alunosComCandidatura = new HashSet<>();

         for (var candidatura : candidaturas.values())
            alunosComCandidatura.add(candidatura.getNAluno());

         for (var aluno : alunos.values())
            if (!alunosComCandidatura.contains(aluno.getNAluno()))
               resultado.add(aluno);
      }

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   /**
    * Consultar alunos segundo filtros recebidos
    * @param autoproposta - lista de alunos com autoprosta
    * @param comCandidatura - lista de alunos com candidatura
    * @param comPropostaAtribuida - lista de alunos com proposta atribuída
    * @param semPropostaAtribuida - lista de alunos sem proposta atribuída
    * @return lista de alunos, segundo filtros recebidos
    */
   public ArrayList<Aluno> consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean comPropostaAtribuida, boolean semPropostaAtribuida) {

      HashSet<Aluno> resultado = new HashSet<>();
      HashSet<Aluno> resultadoComPropostaAtribuida = new HashSet<>();

      if (!autoproposta && !comCandidatura && !comPropostaAtribuida && !semPropostaAtribuida)
         resultado = new HashSet<>(alunos.values());

      if (autoproposta) {
         for (var proposta : propostas.values())
            if (proposta instanceof Autoproposto)
               resultado.add(alunos.get(proposta.getNAlunoAssociado()));
      }

      if (comCandidatura) {
         for (var candidatura : candidaturas.values())
            resultado.add(alunos.get(candidatura.getNAluno()));
      }

      if (comPropostaAtribuida) {
         for (var propostasAtribuidas : propostasAtribuidas.values()) {
            resultado.add(alunos.get(propostasAtribuidas.getNAlunoAssociado()));
            resultadoComPropostaAtribuida.add(alunos.get(propostasAtribuidas.getNAlunoAssociado()));
         }
      }

      if (semPropostaAtribuida) {
         HashSet<Long> alunosComProposta = new HashSet<>();

         for (var propostasAtribuidas : propostasAtribuidas.values())
            alunosComProposta.add(propostasAtribuidas.getNAlunoAssociado());

         for (var aluno : alunos.values())
            if (!alunosComProposta.contains(aluno.getNAluno()))
               resultado.add(aluno);
      }

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   /**
    * Consultar número de orientações dos docentes
    * @param filtro - email do docente
    * @return lista de orientações dos docentes
    */
   public ArrayList<String> consultarDocentes(String filtro){
      ArrayList<String> res  = new ArrayList<>();

      if(!filtro.isBlank()) {

         if(docentes.get(filtro) == null)
            return null;

         res.add(docentes.get(filtro).getNome());
         res.add(String.valueOf(calculaNumeroOrientacoesDocente(filtro)));

      } else{

         int max = 0, min = 0;
         double media = 0;

         for(var docente : docentes.values()){
            int nOrientacoes = calculaNumeroOrientacoesDocente(docente.getEmail());

            if(nOrientacoes < min)
               min = nOrientacoes;

            if(nOrientacoes > max)
               max = nOrientacoes;

            media += nOrientacoes;
         }

         media /= docentes.size();

         if(media == 0.0)
            return null;

         res.add(String.valueOf(max));
         res.add(String.valueOf(min));
         res.add(String.valueOf(media));
      }

      return res;
   }

   /**
    * Consultar propostas
    * @param propostasAtribuidas - boolean propostas atribuídas ou não atribuídas
    * @return lista de propostas
    */
   public ArrayList<Proposta> consultarPropostas(boolean propostasAtribuidas){

      HashSet<String> resultado = new HashSet<>();
      StringBuilder sb = new StringBuilder();

      if(propostasAtribuidas){
         for(var proposta : propostas.values())
            for(var propostasAtribuida : this.propostasAtribuidas.values())
               if(propostasAtribuida.getId().equals(proposta.getId())) {
                  resultado.add(proposta.getId());
                  break;
               }
      }else {

         HashSet<Proposta> propostasAtribuidaHS = new HashSet<>();

         for(var proposta : propostas.values())
            for(var propostasAtribuida : this.propostasAtribuidas.values())
               if(propostasAtribuida.getId().equals(proposta.getId())) {
                  propostasAtribuidaHS.add(proposta);
                  break;
               }

         for(var proposta : propostas.values())
            if(!propostasAtribuidaHS.contains(proposta))
               resultado.add(proposta.getId());

      }

      ArrayList<String> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      ArrayList<Proposta> res = new ArrayList<>();

      for(var proposta : resultadoOrdenado) {
         if (propostasAtribuidas)
            res.add(this.propostasAtribuidas.get(proposta));
         else
            res.add(propostas.get( proposta));
         sb.append(System.lineSeparator());
      }

      return res;
   }

   /**
    * Consultar propostas, segundo os filtros recebidos
    * @param autopropostasAlunos - autopropostas de alunos
    * @param propostasDocentes - propostas de docentes
    * @param comCandidatura - propostas com candidatura
    * @param semCandidatura - propostas sem candidatura
    * @return lista de propostas
    */
   public ArrayList<Proposta> consultarPropostas(boolean autopropostasAlunos, boolean propostasDocentes, boolean comCandidatura, boolean semCandidatura){

      HashSet<Proposta> resultado = new HashSet<>();
      StringBuilder sb = new StringBuilder();

      if(!autopropostasAlunos && !propostasDocentes && !comCandidatura && !semCandidatura)
         resultado = new HashSet<>(propostas.values());

      if(autopropostasAlunos){
         for(var proposta : propostas.values())
            if(proposta instanceof Autoproposto)
               resultado.add(proposta);
      }

      if(propostasDocentes){
         for(var proposta : propostas.values())
            if(proposta instanceof Projeto)
               resultado.add(proposta);
      }

      if(comCandidatura){
         for(var proposta : propostas.values())
            for(var candidatura : candidaturas.values())
               if (candidatura.getIdPropostas().contains(proposta.getId())) {
                  resultado.add(proposta);
                  break;
               }
      }

      if(semCandidatura){
         HashSet<String> propostasComCandidatura = new HashSet<>();

         for(var proposta : propostas.values())
            for(var candidatura : candidaturas.values())
               if (candidatura.getIdPropostas().contains(proposta.getId())) {
                  propostasComCandidatura.add(proposta.getId());
                  break;
               }

         for (var proposta : propostas.values())
            if(!propostasComCandidatura.contains(proposta.getId()))
               resultado.add(proposta);
      }

      ArrayList<Proposta> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   /**
    * Consultar propostas segundo os filtros da fase 3
    * @param autopropostasAlunos - autopropostas de alunos
    * @param propostasDocentes - propostas de docentes
    * @param propostasDisponiveis - propostas que ainda não foram atribuídas
    * @param propostasAtribuidas - propostas atribuídas
    * @return lista de propostas, segundo os filtros
    */
   public ArrayList<Proposta> consultarPropostasFase3(boolean autopropostasAlunos, boolean propostasDocentes, boolean propostasDisponiveis, boolean propostasAtribuidas){

      HashSet<Proposta> resultado = new HashSet<>();
      StringBuilder sb = new StringBuilder();

      if(!autopropostasAlunos && !propostasDocentes && !propostasDisponiveis && !propostasAtribuidas)
         resultado = new HashSet<>(propostas.values());

      if(autopropostasAlunos){
         for(var proposta : propostas.values())
            if(proposta instanceof Autoproposto)
               resultado.add(proposta);
      }

      if(propostasDocentes){
         for(var proposta : propostas.values())
            if(proposta instanceof Projeto)
               resultado.add(proposta);
      }

      if(propostasDisponiveis){

         HashSet<Proposta> propostasAtribuidaHS = new HashSet<>();

         for(var proposta : propostas.values())
            for(var propostasAtribuida : this.propostasAtribuidas.values())
               if(propostasAtribuida.getId().equals(proposta.getId())) {
                  propostasAtribuidaHS.add(proposta);
                  break;
               }

         for(var proposta : propostas.values())
            if(!propostasAtribuidaHS.contains(proposta))
               resultado.add(proposta);

      }

      if(propostasAtribuidas){
         for(var proposta : propostas.values())
            for(var propostasAtribuida : this.propostasAtribuidas.values())
               if(propostasAtribuida.getId().equals(proposta.getId())) {
                  resultado.add(proposta);
                  break;
               }
      }

      ArrayList<Proposta> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   /**
    * Consultar propostas atribuídas a um docente
    * @param email - email do docente
    * @return lista de propostas
    */
   public ArrayList<PropostaAtribuida> consultarPropostasAtribuidasDocente(String email){

      ArrayList<PropostaAtribuida> propostaAtribuidasOrientador = new ArrayList<>();

      for(var propostasAtribuidas : propostasAtribuidas.values()) {

         if(propostasAtribuidas.getEmailDocenteOrientador() == null)
            continue;

         if (propostasAtribuidas.getEmailDocenteOrientador().equalsIgnoreCase(email))
            propostaAtribuidasOrientador.add(propostasAtribuidas);
      }

      return propostaAtribuidasOrientador;
   }

   /**
    * Obter alunos sem propostas atribuída
    * @param soEstagio - só alunos com acesso a estágio ou todos os alunos
    * @return lista de alunos sem propostas atribuídas
    */
   public ArrayList<Aluno> getAlunosSemPropostaAtribuida(boolean soEstagio){

      ArrayList<Aluno> alunosSemProposta = new ArrayList<>();

      for(var aluno : alunos.values()) {

         boolean insereAluno = true;

         for (var proposta : propostas.values())
            if (proposta.getNAlunoAssociado() == aluno.getNAluno()) {
               insereAluno = false;
               break;
            }

         for (var propostaAtribuida : propostasAtribuidas.values())
            if (propostaAtribuida.getNAlunoAssociado() == aluno.getNAluno()) {
               insereAluno = false;
               break;
            }

         if (insereAluno) {

            if(soEstagio && aluno.isAcessoEstagio())
               alunosSemProposta.add(aluno);

            else if (!soEstagio) {
               alunosSemProposta.add(aluno);

            }
         }

      }
      return alunosSemProposta;
   }

   /**
    * Resumo de propostas atribuídas
    * @return um array com o número de propostas atribuídas, não atribuídas e total de propostas
    */
   public ArrayList<Integer> propostas_Atribuidas_NaoAtribuidas_Total(){
      ArrayList<Integer> resultado = new ArrayList<>(List.of(0,0,0));
      for(var proposta : propostas.values()){
         if(propostasAtribuidas.containsKey(proposta.getId()))
            resultado.set(0, resultado.get(0) + 1);
         else
            resultado.set(1, resultado.get(1) + 1);
      }
      resultado.set(2, propostas.size());

      return resultado;
   }

   /**
    * 5 Empresas com mais estágios
    * @return lista nome e número de estágios das 5 empresas com mais estágios
    */
   public HashMap<String, Number> top5EmpresasEstagio(){
      HashMap<String, Integer> top5 = new HashMap<>();

      for (var proposta : propostas.values()) {
         if(proposta instanceof Estagio e){
            if(top5.containsKey(e.getEntidadeAcolhimento())){
               top5.replace(e.getEntidadeAcolhimento(), top5.get(e.getEntidadeAcolhimento())+1);
            }else{
               top5.put(e.getEntidadeAcolhimento(), 1);
            }
         }
      }
      List<Map.Entry<String, Integer> > list =
              new LinkedList<>(top5.entrySet());

      list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

      HashMap<String, Number> top5Final = new HashMap<>();

      int cont = 0;
      for(var item : list){
         top5Final.put(item.getKey(), item.getValue());
         cont++;

         if(cont == 5)
            break;
      }
      return top5Final;
   }

   /**
    * 5 docentes com mais orientações
    * @return lista de docentes e número de orientações dos 5 docentes com mais orientações
    */
   public HashMap<String, Number> top5DocentesOrientacoes(){
      HashMap<String, Integer> top5 = new HashMap<>();

      for (var proposta : propostasAtribuidas.values()) {
         if(proposta.getEmailDocenteOrientador() != null){
            if(top5.containsKey(proposta.getEmailDocenteOrientador()))
               top5.replace(proposta.getEmailDocenteOrientador(), top5.get(proposta.getEmailDocenteOrientador())+1);
            else
               top5.put(proposta.getEmailDocenteOrientador(), 1);
         }
      }
      List<Map.Entry<String, Integer> > list =
              new LinkedList<>(top5.entrySet());

      list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

      HashMap<String, Number> top5Final = new HashMap<>();

      int cont = 0;
      for(var item : list){
         top5Final.put(docentes.get(item.getKey()).getNome(), item.getValue());
         cont++;

         if(cont == 5)
            break;
      }
      return top5Final;
   }

   /**
    * Obter número de propostas atribuídas com docente orientador
    * @return número de propostas atribuídas com docente orientador
    */
   public int getNumPropotasAtribuidasComOrientador(){
      int cont = 0;

      for(var propostaAtribuida : propostasAtribuidas.values())
         if (propostaAtribuida.getEmailDocenteOrientador() != null)
            cont++;

      return cont;
   }
}
