package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.*;

import java.io.*;
import java.util.*;

public class ApoioPOE implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;
   private int faseBloqueada;

   private HashMap<Long, Aluno> alunos;            //fizemos o equal para cada um para não inserir repetidos
   private HashMap<String, Docente> docentes;
   private HashMap<String, Proposta> propostas;
   private HashMap<Long, Candidatura> candidaturas;
   private HashMap<String, PropostaAtribuida> propostasAtribuidas;

   public ApoioPOE(){
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
                                double classificacao, boolean acessoEstagio){

      if(alunos.containsKey(nAluno) || docentes.containsKey(email))
         return false;

      if(!Aluno.cursos.contains(siglaCurso) || !Aluno.ramos.contains(siglaRamo))
         return false;

      if(classificacao > 1.0 || classificacao < 0.0)
         return false;

      alunos.put(nAluno, new Aluno(nAluno, nome, email, siglaCurso, siglaRamo, classificacao, acessoEstagio));

      return true;
   }

   public boolean adicionaDocente(String nome, String email){

      if(docentes.containsKey(email))
         return false;

      for (var aluno: alunos.values())
         if(aluno.getEmail().equals(email))
            return false;

      docentes.put(email, new Docente(nome, email));

      return true;
   }

   public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado,
                                   String areasDestino, String entidadeOuDocente){

      if(propostas.containsKey(id))
         return false;

      if(!alunos.containsKey(nAlunoAssociado))
         return false;

      String[] areas = areasDestino.split("\\|");
      if(!Aluno.ramos.containsAll(List.of(areas)))
         return false;

      switch (tipo){
         case "T1" -> {propostas.put(id, new Estagio(id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente));}
         case "T2" -> {

            if(!docentes.containsKey(entidadeOuDocente))
               return false;

            propostas.put(id, new Projeto(id, titulo, nAlunoAssociado, areasDestino, entidadeOuDocente));
         }
         default -> {return false;}
      }

      return true;
   }
   public boolean adicionaProposta(String tipo, String id, String titulo,
                                   String areasDestino, String entidadeOuDocente){

      if(propostas.containsKey(id))
         return false;

      String[] areas = areasDestino.split("\\|");
      if(!Aluno.ramos.containsAll(List.of(areas)))
         return false;

      switch (tipo){
         case "T1" -> {propostas.put(id, new Estagio(id, titulo, areasDestino, entidadeOuDocente));}
         case "T2" -> {

            if(!docentes.containsKey(entidadeOuDocente))
               return false;

            propostas.put(id, new Projeto(id, titulo, areasDestino, entidadeOuDocente));
         }
         default -> {return false;}
      }

      return true;
   }
   public boolean adicionaProposta(String tipo, String id, String titulo, long nAlunoAssociado){

      if(propostas.containsKey(id))
         return false;

      if(!alunos.containsKey(nAlunoAssociado))
         return false;

      for (var proposta : propostas.values())
         if(proposta.getnAlunoAssociado() == nAlunoAssociado)
            return false;

      switch (tipo){
         case "T3" -> {propostas.put(id, new Autoproposto(id, titulo, nAlunoAssociado));}
         default -> {return false;}
      }

      return true;
   }

   public boolean adicionaCandidatura(long nAluno, ArrayList<String> propostas){

      if(propostas.isEmpty())
         return false;

      if(candidaturas.containsKey(nAluno))
         return false;

      if(!alunos.containsKey(nAluno))
         return false;

      for (var proposta : propostas) {
         if (!this.propostas.containsKey(proposta))
            return false;

         if(this.propostas.get(proposta).getnAlunoAssociado() != 0)
            return false;
      }

      for (var proposta : this.propostas.values())
         if(proposta.getnAlunoAssociado() == nAluno)
            return false;

      candidaturas.put(nAluno, new Candidatura(nAluno, propostas));

      return true;
   }

   public boolean atribuirPropostaAluno(String idProposta, long nAluno){

      int ordemPreferencia = 1;

      if(propostasAtribuidas.containsKey(idProposta))
         return false;

      Proposta propostaAtual = propostas.get(idProposta);

      if(propostaAtual == null)
         return false;

      if(!alunos.containsKey(nAluno))
         return false;

      for(var propostaAtribuida : propostasAtribuidas.values())
         if(propostaAtribuida.getnAlunoAssociado() == nAluno)
            return false;

      Candidatura candidaturaAluno = getCandidatura(nAluno);
      if(candidaturaAluno != null){
         for(var idProp : candidaturaAluno.getIdPropostas()){

            if(idProp.equals(idProposta)) {
               ordemPreferencia = candidaturaAluno.getIdPropostas().indexOf(idProp)+1;
               break;
            }
         }
      }

      propostasAtribuidas.put(idProposta, new PropostaAtribuida(idProposta, propostaAtual.getTitulo(),
                                                                  nAluno, ordemPreferencia));

      return true;
   }
   public boolean atribuicaoAutomaticaPropostasComAluno(){

      HashSet<Proposta> propostasAtribuir = new HashSet<>();

      for(var proposta : propostas.values())

         if(proposta instanceof Autoproposto || (proposta instanceof Projeto && proposta.getnAlunoAssociado() != 0))
            propostasAtribuir.add(proposta);

      if(propostasAtribuir.isEmpty())
         return false;

      for(var p : propostasAtribuir)
         atribuirPropostaAluno(p.getId(), p.getnAlunoAssociado());

      return true;
   }

   public boolean atribuirPropostaDocenteOrientador(String idProposta, String email){

      if(!propostasAtribuidas.containsKey(idProposta))
         return false;

      if(!docentes.containsKey(email))
         return false;

      propostasAtribuidas.get(idProposta).setEmailDocenteOrientador(email);

      return true;
   }
   public boolean associacaoAutomaticaDocentesProponentes(){

      boolean associadoAlgum = false;

      for(var propostaAtribuida : getPropostasAtribuidas()){

         if(getProposta(propostaAtribuida.getId()) instanceof Projeto p) {
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
   public PropostaAtribuida getPropostaAtribuida(String id){
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

   //TODO: verificações
   public boolean removeAluno(long nAluno){
      return alunos.remove(nAluno) != null;
   }
   public boolean removeDocente(String email){return docentes.remove(email) != null;}
   public boolean removeProposta(String id){return propostas.remove(id) != null;}
   public boolean removeCandidatura(long id){return candidaturas.remove(id) != null;}
   public boolean removePropostaAtribuida(String id){return propostasAtribuidas.remove(id) != null;}
   public boolean removeOrientadorPropostaAtribuida(String id){

      if(propostasAtribuidas.get(id) == null)
         return false;

      propostasAtribuidas.get(id).setEmailDocenteOrientador(null);

      return propostasAtribuidas.get(id).getEmailDocenteOrientador() == null;
   }

   public boolean propostasSufecienteParaRamo(String ramo){

      int contadorPropostaRamo = 0;

      for (var proposta : propostas.values()) {

         if(proposta instanceof Projeto p){

            if(List.of(p.getRamosDestino().split("\\|")).contains(ramo))
               contadorPropostaRamo++;

         } else if(proposta instanceof Estagio e){

            if(List.of(e.getAreasDestino().split("\\|")).contains(ramo))
               contadorPropostaRamo++;
         }
      }

      int contadorAlunosRamo = 0;

      for (var aluno : alunos.values())
         if(aluno.getSiglaRamo().equals(ramo))
            contadorAlunosRamo++;

      return contadorPropostaRamo >= contadorAlunosRamo;
   }
   public boolean todasCandidaturasComPropostaAtribuida(){

      ArrayList<Long> alunosComPropostaAtribuida = new ArrayList<>();

      for(var propostasAtribuida : propostasAtribuidas.values())
         alunosComPropostaAtribuida.add(propostasAtribuida.getnAlunoAssociado());

      return alunosComPropostaAtribuida.containsAll(candidaturas.keySet());
   }

   public int calculaNumeroOrientacoesDocente(String email){
      int contador = 0;

      for(var propostaAtribuida : getPropostasAtribuidas())
         if(propostaAtribuida.getEmailDocenteOrientador() != null &&
                 propostaAtribuida.getEmailDocenteOrientador().equals(email))
            contador++;

      return contador;
   }
}
