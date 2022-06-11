package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.*;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;

import java.io.*;
import java.util.*;

public class ApoioPoE implements Serializable, Cloneable {

   @Serial
   private static final long serialVersionUID = 1L;
   private int faseBloqueada;

   private final HashMap<Long, Aluno> alunos;
   private final HashMap<String, Docente> docentes;
   private final HashMap<String, Proposta> propostas;
   private final HashMap<Long, Candidatura> candidaturas;
   private final HashMap<String, PropostaAtribuida> propostasAtribuidas;

   public ApoioPoE() {
      alunos = new HashMap<>();
      docentes = new HashMap<>();
      propostas = new HashMap<>();
      candidaturas = new HashMap<>();
      propostasAtribuidas = new HashMap<>();

      faseBloqueada = 0;
   }

   public int getFaseBloqueada() {
      return faseBloqueada;
   }

   public void setFaseBloqueada(int faseBloqueada) {
      this.faseBloqueada = faseBloqueada;
   }

   public boolean adicionaAluno(long nAluno, String nome, String email, String siglaCurso, String siglaRamo,
                                double classificacao, boolean acessoEstagio) {

      if (alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_NUMERO_ALUNO);
         return false;
      }

      if (docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_EMAIL);
         return false;
      }

      for (var aluno : alunos.values())
         if (aluno.getEmail().equals(email)) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_EMAIL);
            return false;
         }

      if (!Aluno.cursos.contains(siglaCurso)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_CURSO);
         return false;
      }

      if (!Aluno.ramos.contains(siglaRamo)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_RAMO);
         return false;
      }

      if (classificacao > 1.0 || classificacao < 0.0) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_CLASSIFICACAO);
         return false;
      }

      alunos.put(nAluno, new Aluno(nAluno, nome, email, siglaCurso, siglaRamo, classificacao, acessoEstagio));

      return true;
   }

   public boolean adicionaDocente(String nome, String email) {

      if (docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_EMAIL);
         return false;
      }

      for (var aluno : alunos.values())
         if (aluno.getEmail().equals(email)) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_EMAIL);
            return false;
         }

      docentes.put(email, new Docente(nome, email));

      return true;
   }

   public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado,
                                   String areasDestino, String entidadeOuDocente) {

      if (propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      if (!alunos.containsKey(nAlunoAssociado)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      String[] areas = areasDestino.trim().split("\\|");
      if (!Aluno.ramos.containsAll(List.of(areas))) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_RAMO);
         return false;
      }

      for (var proposta : this.propostas.values())
         if (proposta.getnAlunoAssociado() == nAlunoAssociado) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }

      switch (tipo) {
         case "T1" -> propostas.put(id, new Estagio(id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente));
         case "T2" -> {

            if (!docentes.containsKey(entidadeOuDocente)) {
               ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_DOCENTE);
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

   public boolean adicionaProposta(String tipo, String id, String titulo,
                                   String areasDestino, String entidadeOuDocente) {

      if (propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      String[] areas = areasDestino.trim().split("\\|");

      if (!Aluno.ramos.containsAll(List.of(areas))) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_RAMO);
         return false;
      }

      switch (tipo) {
         case "T1" -> propostas.put(id, new Estagio(id, titulo, areasDestino, entidadeOuDocente));
         case "T2" -> {

            if (!docentes.containsKey(entidadeOuDocente)) {
               ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_DOCENTE);
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

   public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado) {

      if (propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      if (!alunos.containsKey(nAlunoAssociado)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      for (var proposta : propostas.values())
         if (proposta.getnAlunoAssociado() == nAlunoAssociado) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }

      if ("T3".equals(tipo)) {
         propostas.put(id, new Autoproposto(id, titulo, nAlunoAssociado));
      } else
         return false;

      return true;
   }

   public boolean adicionaCandidatura(long nAluno, ArrayList<String> propostas) {

      if (propostas.isEmpty()) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      if (candidaturas.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_CANDIDATURA);
         return false;
      }

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      for (var proposta : propostas) {
         if (!this.propostas.containsKey(proposta)) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
            return false;
         }

         if (this.propostas.get(proposta).getnAlunoAssociado() != 0) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.PROPOSTA_JA_TEM_ALUNO_ASSOCIADO);
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
            ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM);
            return false;
         }

      }

      for (var proposta : this.propostas.values())
         if (proposta.getnAlunoAssociado() == nAluno) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_PROPOSTA);
            return false;
         }



      HashSet<String> testeDuplicados = new HashSet<>(propostas);
      if (testeDuplicados.size() != propostas.size()) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_ID_PROPOSTA);
         return false;
      }
      candidaturas.put(nAluno, new Candidatura(nAluno, propostas));

      return true;
   }

   public boolean atribuirPropostaAluno(String idProposta, long nAluno) {

      int ordemPreferencia = 1;

      if (propostasAtribuidas.containsKey(idProposta)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.PROPOSTA_JA_FOI_ATRIBUIDA);
         return false;
      }

      Proposta propostaAtual = propostas.get(idProposta);

      if (propostaAtual == null) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      for (var propostaAtribuida : propostasAtribuidas.values())
         if (propostaAtribuida.getnAlunoAssociado() == nAluno) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_PROPOSTA);
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
         ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM);
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

   public boolean atribuicaoAutomaticaPropostasComAluno() {

      HashSet<Proposta> propostasAtribuir = new HashSet<>();

      for (var proposta : propostas.values())

         if (proposta instanceof Autoproposto || (proposta instanceof Projeto && proposta.getnAlunoAssociado() != 0))
            propostasAtribuir.add(proposta);

      if (propostasAtribuir.isEmpty())
         return false;

      for (var p : propostasAtribuir)
         atribuirPropostaAluno(p.getId(), p.getnAlunoAssociado());

      return true;
   }

   public boolean atribuirPropostaDocenteOrientador(String idProposta, String email) {

      if (!propostasAtribuidas.containsKey(idProposta) && propostas.containsKey(idProposta)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.PROPOSTA_AINDA_NAO_ATRIBUIDA);
         return false;
      }

      if (!propostas.containsKey(idProposta)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      if (!docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_DOCENTE);
         return false;
      }

      propostasAtribuidas.get(idProposta).setEmailDocenteOrientador(email);

      return true;
   }

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

   public Aluno getAluno(long nAluno) {
      Aluno aux = alunos.get(nAluno);
      return aux != null ? aux.clone() : null;
   }

   public Docente getDocente(String email) {
      Docente aux = docentes.get(email);
      return aux != null ? aux.clone() : null;
   }

   public Proposta getProposta(String id) {
      Proposta aux = propostas.get(id);
      return aux != null ? aux.clone() : null;
   }

   public Candidatura getCandidatura(long nAluno) {
      Candidatura aux = candidaturas.get(nAluno);
      return aux != null ? aux.clone() : null;
   }

   public PropostaAtribuida getPropostaAtribuida(String id) {
      PropostaAtribuida aux = propostasAtribuidas.get(id);
      return aux != null ? aux.clone() : null;
   }

   public ArrayList<Aluno> getAlunos() {
      ArrayList<Aluno> alunosOrdenados = new ArrayList<>(alunos.values());

      Collections.sort(alunosOrdenados);
      return (ArrayList<Aluno>) alunosOrdenados.clone();
   }

   public ArrayList<Docente> getDocentes() {
      ArrayList<Docente> docentesOrdenados = new ArrayList<>(docentes.values());

      Collections.sort(docentesOrdenados);
      return (ArrayList<Docente>) docentesOrdenados.clone();
   }

   public ArrayList<Proposta> getPropostas() {
      ArrayList<Proposta> propostasOrdenadas = new ArrayList<>(propostas.values());
      Collections.sort(propostasOrdenadas);
      return (ArrayList<Proposta>) propostasOrdenadas.clone();
   }

   public ArrayList<Candidatura> getCandidaturas() {
      ArrayList<Candidatura> candidaturasOrdenadas = new ArrayList<>(candidaturas.values());
      Collections.sort(candidaturasOrdenadas);
      return (ArrayList<Candidatura>) candidaturasOrdenadas.clone();
   }

   public ArrayList<PropostaAtribuida> getPropostasAtribuidas() {
      ArrayList<PropostaAtribuida> propostaAtribuidaOrdenadas = new ArrayList<>(propostasAtribuidas.values());
      Collections.sort(propostaAtribuidaOrdenadas);
      return (ArrayList<PropostaAtribuida>) propostaAtribuidaOrdenadas.clone();
   }

   public boolean removeAluno(long nAluno) {

      if(!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      if(candidaturas.containsKey(nAluno))
         removeCandidatura(nAluno);

      ArrayList<String> propostasRemover = new ArrayList<>();

      for (var proposta : getPropostas()) {
         if (proposta instanceof Autoproposto && proposta.getnAlunoAssociado() == nAluno)
            propostasRemover.add(proposta.getId());

         if (proposta.getnAlunoAssociado() == nAluno)
            proposta.setnAlunoAssociado(0);
      }

      for (var p : propostasRemover)
         removeProposta(p);

      //já é removida a proposta atribuida ou no removeCandidatura() ou no removeProposta()

      return alunos.remove(nAluno) != null;
   }

   public boolean removeDocente(String email) {

      if(!docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_DOCENTE);
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

   public boolean removeProposta(String id) {

      if(!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
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

   public boolean removeCandidatura(long nAluno) {

      if(!candidaturas.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_CANDIDATURA);
         return false;
      }

      if (getCandidatura(nAluno) == null)
         return false;

      for (var propostaAtribuida : getPropostasAtribuidas())
         if (propostaAtribuida.getnAlunoAssociado() == nAluno)
            removePropostaAtribuida(propostaAtribuida.getId());

      return candidaturas.remove(nAluno) != null;
   }

   public boolean removePropostaAtribuida(String id) {

      if(!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      return propostasAtribuidas.remove(id) != null;
   }

   public boolean removeOrientadorPropostaAtribuida(String id) {

      if (!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      if (propostasAtribuidas.get(id) == null) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.PROPOSTA_AINDA_NAO_ATRIBUIDA);
         return false;
      }

      propostasAtribuidas.get(id).setEmailDocenteOrientador(null);

      return propostasAtribuidas.get(id).getEmailDocenteOrientador() == null;
   }

   public boolean propostasSufecienteParaRamo(String ramo) {
      return propostasPorRamo(ramo) >= nAlunosPorRamo(ramo);
   }

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

   public int nAlunosPorRamo(String ramo){
      int contadorAlunosRamo = 0;

      for (var aluno : alunos.values())
         if (aluno.getSiglaRamo().equals(ramo))
            contadorAlunosRamo++;

      return contadorAlunosRamo;
   }

   public boolean todasCandidaturasComPropostaAtribuida() {

      ArrayList<Long> alunosComPropostaAtribuida = new ArrayList<>();

      for (var propostasAtribuida : propostasAtribuidas.values())
         alunosComPropostaAtribuida.add(propostasAtribuida.getnAlunoAssociado());

      return alunosComPropostaAtribuida.containsAll(candidaturas.keySet());
   }

   public int calculaNumeroOrientacoesDocente(String email) {
      int contador = 0;

      for (var propostaAtribuida : getPropostasAtribuidas())
         if (propostaAtribuida.getEmailDocenteOrientador() != null &&
                 propostaAtribuida.getEmailDocenteOrientador().equals(email))
            contador++;

      return contador;
   }

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

   @Override
   protected ApoioPoE clone() {
      return new ApoioPoE(this);
   }

   public String getTipoProposta(String id) {

      if (getProposta(id) == null)
         return null;

      return getProposta(id).tipoProposta();
   }

   public boolean editaAluno(long nAluno, String nome, String siglaCurso, String siglaRamo,
                             String classificacao, String acessoEstagio) {

      double dClassificacao = 0.0;

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      if (!siglaCurso.isBlank())
         if (!Aluno.cursos.contains(siglaCurso)) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_CURSO);
            return false;
         }

      if (!siglaRamo.isBlank())
         if (!Aluno.ramos.contains(siglaRamo)) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_RAMO);
            return false;
         }

      if (!classificacao.isBlank()) {
         dClassificacao = Double.parseDouble(classificacao);

         if (dClassificacao > 1.0 || dClassificacao < 0.0) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_CLASSIFICACAO);
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

   public boolean editaDocente(String email, String nome) {

      if (!docentes.containsKey(email)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_DOCENTE);
         return false;
      }

      if (nome.isBlank())
         return false;

      docentes.get(email).setNome(nome);

      return true;
   }

   public boolean editaProposta(String id, String titulo, String ramos, String entidade_docente, String nAluno) {

      if (!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      String[] array_ramos = ramos.trim().split("\\|");
      if (!ramos.isBlank() && !Aluno.ramos.containsAll(List.of(array_ramos))) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_RAMO);
         return false;
      }

      if (!nAluno.isBlank()) {
         if (!alunos.containsKey(Long.parseLong(nAluno)) && !nAluno.equals("0")) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
            return false;
         }

         for (var proposta : this.propostas.values())
            if (proposta.getnAlunoAssociado() == Long.parseLong(nAluno) && !proposta.getId().equals(id) && !nAluno.equals("0")) {
               ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_PROPOSTA);
               return false;
            }
      }

      Proposta proposta = propostas.get(id);

      if (!titulo.isBlank())
         proposta.setTitulo(titulo);

      if (!nAluno.isBlank())
         proposta.setnAlunoAssociado(Long.parseLong(nAluno));

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

   public boolean editaProposta(String id, String titulo, String nAluno) {

      if (!propostas.containsKey(id)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
         return false;
      }

      if (!nAluno.isBlank()) {
         if (!alunos.containsKey(Long.parseLong(nAluno)) && !nAluno.equals("0")) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
            return false;
         }

         for (var proposta : this.propostas.values())
            if (proposta.getnAlunoAssociado() == Long.parseLong(nAluno) && !proposta.getId().equals(id) && !nAluno.equals("0")) {
               ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_JA_TEM_PROPOSTA);
               return false;
            }
      }

      Proposta proposta = propostas.get(id);

      if (!titulo.isBlank())
         proposta.setTitulo(titulo);

      if (!nAluno.isBlank())
         proposta.setnAlunoAssociado(Long.parseLong(nAluno));

      return true;
   }

   public boolean editaCandidatura(long nAluno, ArrayList<String> propostas) {

      if (propostas.isEmpty()) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.SEM_PROPOSTAS_ESPECIFICADAS);
         return false;
      }

      if (!alunos.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_NUMERO_ALUNO);
         return false;
      }

      if (!candidaturas.containsKey(nAluno)) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.ALUNO_NAO_TEM_CANDIDATURA);
         return false;
      }

      for (var proposta : propostas) {
         if (!this.propostas.containsKey(proposta)) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.INVALID_ID_PROPOSTA);
            return false;
         }

         if (this.propostas.get(proposta).getnAlunoAssociado() != 0) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.PROPOSTA_JA_TEM_ALUNO_ASSOCIADO);
            return false;
         }
      }

      HashSet<String> testeDuplicados = new HashSet<>(propostas);
      if (testeDuplicados.size() != propostas.size()) {
         ErrorOccurred.getInstance().setError(ErrorsTypes.DUPLICATED_ID_PROPOSTA);
         return false;
      }

      candidaturas.get(nAluno).setIdPropostas(propostas);

      return true;
   }

   public ArrayList<Aluno> consultarAlunosFase5(boolean comPropostaAtribuida){

      HashSet<Aluno> resultado = new HashSet<>();

      if(comPropostaAtribuida)

         for (var propostasAtribuidas : propostasAtribuidas.values())
            resultado.add(alunos.get(propostasAtribuidas.getnAlunoAssociado()));

      else{
         HashSet<Long> alunosComProposta = new HashSet<>();

         for(var propostasAtribuidas : propostasAtribuidas.values())
            alunosComProposta.add(propostasAtribuidas.getnAlunoAssociado());

         for(var aluno : alunos.values())
            if(!alunosComProposta.contains(aluno.getNAluno()) && candidaturas.get(aluno.getNAluno()) != null)
               resultado.add(aluno);
      }

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   public ArrayList<Aluno> consultarAlunos(boolean comOrientadorAssociado) {

      HashSet<Aluno> resultado = new HashSet<>();

      if (comOrientadorAssociado) {
         for (var propostasAtribuidas : propostasAtribuidas.values())
            if (propostasAtribuidas.getEmailDocenteOrientador() != null)
               resultado.add(alunos.get(propostasAtribuidas.getnAlunoAssociado()));
      } else
         for (var propostasAtribuidas : propostasAtribuidas.values())
            if (propostasAtribuidas.getEmailDocenteOrientador() == null)
               resultado.add(alunos.get(propostasAtribuidas.getnAlunoAssociado()));

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   public ArrayList<Aluno> consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean semCandidatura) {
      HashSet<Aluno> resultado = new HashSet<>();

      if (!autoproposta && !comCandidatura && !semCandidatura)
         resultado = new HashSet<>(alunos.values());

      if (autoproposta) {
         for (var proposta : propostas.values())
            if (proposta instanceof Autoproposto)
               resultado.add(alunos.get(proposta.getnAlunoAssociado()));
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

   public ArrayList<Aluno> consultarAlunos(boolean autoproposta, boolean comCandidatura, boolean comPropostaAtribuida, boolean semPropostaAtribuida) {

      HashSet<Aluno> resultado = new HashSet<>();
      HashSet<Aluno> resultadoComPropostaAtribuida = new HashSet<>();

      if (!autoproposta && !comCandidatura && !comPropostaAtribuida && !semPropostaAtribuida)
         resultado = new HashSet<>(alunos.values());

      if (autoproposta) {
         for (var proposta : propostas.values())
            if (proposta instanceof Autoproposto)
               resultado.add(alunos.get(proposta.getnAlunoAssociado()));
      }

      if (comCandidatura) {
         for (var candidatura : candidaturas.values())
            resultado.add(alunos.get(candidatura.getNAluno()));
      }

      if (comPropostaAtribuida) {
         for (var propostasAtribuidas : propostasAtribuidas.values()) {
            resultado.add(alunos.get(propostasAtribuidas.getnAlunoAssociado()));
            resultadoComPropostaAtribuida.add(alunos.get(propostasAtribuidas.getnAlunoAssociado()));
         }
      }

      if (semPropostaAtribuida) {
         HashSet<Long> alunosComProposta = new HashSet<>();

         for (var propostasAtribuidas : propostasAtribuidas.values())
            alunosComProposta.add(propostasAtribuidas.getnAlunoAssociado());

         for (var aluno : alunos.values())
            if (!alunosComProposta.contains(aluno.getNAluno()))
               resultado.add(aluno);
      }

      ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
      Collections.sort(resultadoOrdenado);

      return resultadoOrdenado;
   }

   public String consultarDocentes(String filtro){
      StringBuilder sb = new StringBuilder();

           if(!filtro.isBlank()) {

         if(docentes.get(filtro) == null)
            return null;

         sb.append("Docente: ").append(docentes.get(filtro).getNome()).append(System.lineSeparator());
         sb.append("Número de orientações: ").append(calculaNumeroOrientacoesDocente(filtro));

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

         sb.append("Máximo de orientações de um docente: ").append(max).append(System.lineSeparator());
         sb.append("Minimo de orientações de um docente: ").append(min).append(System.lineSeparator());
         sb.append("Média de orientações de um docente: ").append(media);
      }

           sb.append(System.lineSeparator());

           return sb.toString();
   }

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

   public ArrayList<Aluno> getAlunosSemPropostaAtribuida(boolean soEstagio){

      ArrayList<Aluno> alunosSemProposta = new ArrayList<>();

      for(var aluno : alunos.values()) {

         boolean insereAluno = true;

         for (var proposta : propostas.values())
            if (proposta.getnAlunoAssociado() == aluno.getNAluno()) {
               insereAluno = false;
               break;
            }

         for (var propostaAtribuida : propostasAtribuidas.values())
            if (propostaAtribuida.getnAlunoAssociado() == aluno.getNAluno()) {
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

}
