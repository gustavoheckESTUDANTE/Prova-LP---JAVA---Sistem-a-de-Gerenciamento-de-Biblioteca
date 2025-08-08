import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //Scanner
    public static final Scanner SC = new Scanner(System.in);
    //Listas para os cadastros
    public static ArrayList<String> cpfsUsuarios = new ArrayList<>(); // Lista de cpfs
    public static ArrayList<String> senhasUsuarios = new ArrayList<>(); // Lista de Senhas
    //Listas e código para empréstimos
    public static int proximoCodigo = 1;
    public static ArrayList<String> emprestimosCpf = new ArrayList<>();           // CPF do usuário
    public static ArrayList<String> emprestimosTitulo = new ArrayList<>();        // Título do livro
    public static ArrayList<String> emprestimosAutor = new ArrayList<>();         // Autor do livro
    public static ArrayList<String> emprestimosDataDevolucao = new ArrayList<>(); // Data prevista para devolução
    public static ArrayList<String> codigosEmprestimos = new ArrayList<>();       // Código do empréstimo gerado

    public static void main(String[] args) {
        //Cadastro da conta admin CONF
        cpfsUsuarios.add("conf");
        senhasUsuarios.add("conf");
        int opcaoEscolhida; // Variável de escolha de menu
        //Primeiro Menu de Operações
        while (true) {
            System.out.println("1.Cadastrar Novo Usuário\n2.Fazer Login\n3.Sair do Sistema\nEscolha uma opção:");
            opcaoEscolhida = SC.nextInt();
            switch (opcaoEscolhida) {
                case 1 : {
                    cadastrarNovoUsuario(); // Chama funcionalidade de cadastro
                    break;
                }
                case 2 : {
                    //Verificação de Login e Menu de Operações pós login
                    String cpfUsuarioAtual = fazerLogin(); // Chama funcionalidade de login e salva o cpf do usuário logado
                    boolean loop = true;
                    if (cpfUsuarioAtual != null) {
                        System.out.println("Bem vindo " + cpfUsuarioAtual);
                        while (loop) {
                            System.out.println("1.Fazer Novo Empréstimo\n2.Listar Meus Empréstimos\n3.Devolver Um Livro" +
                                    ((cpfUsuarioAtual.equals("conf")) ? "\n4.Listar Todos Empréstimos\n5.Sair Da Conta" : "\n4.Sair Da Conta")
                                    + "\nEscolha uma opção"
                            ); // Sout com utilização de if ternário para imprimir menus diferentes para usuários normais e o conf
                            opcaoEscolhida = SC.nextInt();
                            switch (opcaoEscolhida) {
                                case 1: {
                                    fazerEmprestimo(cpfUsuarioAtual); // Chama funcionalidade de empréstimo
                                    break;
                                }
                                case 2: {
                                    listarEmprestimosUsuario(cpfUsuarioAtual); // Chama funcionalidade de listar empréstimos
                                    break;
                                }
                                case 3: {
                                    listarEmprestimosUsuario(cpfUsuarioAtual); // Chama funcionalidade de listar empréstimos
                                    devolverLivro(cpfUsuarioAtual); // Chama funcionalidade devolver livro
                                    break;
                                }
                                case 4: {
                                    if (!(cpfUsuarioAtual.equals("conf"))) { // Verifica se o usuário e conf e define a ação já que os menus são diferentes para cada usuário
                                        loop = false; // Retorna somente se é um usuário comum
                                        break;
                                    } else {
                                        listarTodosEmprestimos(); // Lista todos empréstimos
                                    }
                                    break;
                                }
                                case 5: {
                                    if (cpfUsuarioAtual.equals("conf")) { // Retorna somente se o usuário e o conf
                                        loop = false;
                                        break;
                                    }
                                }
                                default: {
                                    System.out.println("Opção inserida inválida por favor insira um número entre 1 e " +
                                            ((cpfUsuarioAtual.equals("conf")) ? "5" : "4")); // Sout imprimido em caso de inserção errada
                                }
                            }
                        }
                    }
                    break;
                }
                case 3 : {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Opção escolhida inválida, escolha uma opção entre 1 e 3");
                }
            }
        }
    }
    public static void cadastrarNovoUsuario () { // Funcionalidade de cadastro
        System.out.println("Insira um CPF:");
        String cpfInserido = SC.next();
        if (!(cpfsUsuarios.contains(cpfInserido))) {
            System.out.println("Insira uma senha:");
            String senhaInserida = SC.next();
            cpfsUsuarios.add(cpfInserido);
            senhasUsuarios.add(senhaInserida);
            System.out.println("Usuário " + cpfInserido + " foi cadastrado!");
        } else {
            System.out.println("O CPF já foi cadastrado no sistema!");
        }
    }
    public static String fazerLogin () { // Funcionalidade de login
        System.out.println("Insira um CPF para o login: ");
        String cpfInserido = SC.next();
        if (cpfsUsuarios.contains(cpfInserido)) {
            System.out.println("Insira uma senha para o login:");
            String senhaInserida = SC.next();
            if (senhasUsuarios.get(cpfsUsuarios.indexOf(cpfInserido)).equals(senhaInserida)) {
                System.out.println("Login efetuado com sucesso");
                return cpfInserido;
            }
        } else {
            System.out.println("CPF inválido");
            return null;
        }
        return  null;
    }

    public static void fazerEmprestimo (String cpfUsuario) { // Funcionalidade de empréstimo
        int qtdEmprestimos = 0;
        for (String cpf : emprestimosCpf) {
            if (cpf.equals(cpfUsuario)) {
                qtdEmprestimos++;
            }
        }
        if (qtdEmprestimos < 3) {
            System.out.println("Insira o título do livro:");
            String tituloLivro = SC.next();
            emprestimosTitulo.add(tituloLivro);
            System.out.println("Insira o autor do livro:");
            String autor = SC.next();
            emprestimosAutor.add(autor);
            System.out.println("Insira a data de devolução prevista do livro:");
            String dataPrevista = SC.next();
            emprestimosDataDevolucao.add(dataPrevista);
            emprestimosCpf.add(cpfUsuario);
            codigosEmprestimos.add("E" + proximoCodigo);
            proximoCodigo++;
        } else {
            System.out.println("O usuário ja possui 3 ou mais empréstimos ativos!");
        }
    }
    public static void listarEmprestimosUsuario (String cpfUsuario) { // Funcionalidade de listar empréstimo do usuário
        ArrayList<Integer> indicesLivros = new ArrayList<>();
        for (int i = 0; i < emprestimosCpf.size(); i++) {
            if (emprestimosCpf.get(i).equals(cpfUsuario)) {
                indicesLivros.add(i);
            }
        }
        for (int i = 0; i < indicesLivros.size(); i++) {
            System.out.println("Código do empréstimo: " + codigosEmprestimos.get(indicesLivros.get(i)) +
                    "\nTítulo do livro: " + emprestimosTitulo.get(indicesLivros.get(i)) +
                    "\nAutor: " + emprestimosAutor.get(indicesLivros.get(i)) +
                    "\nData de devolução prevista: " + emprestimosDataDevolucao.get(indicesLivros.get(i))
            );
        }
    }
    public static void devolverLivro (String cpfUsuario) { // Funcionalidade de devolver livro
        System.out.println("Insira o código do livro que você deseja devolver:");
        String codigoInserido = SC.next();
        if (codigosEmprestimos.contains(codigoInserido)) {
            int indiceLivro = codigosEmprestimos.indexOf(codigoInserido);
            if (emprestimosCpf.get(indiceLivro).equals(cpfUsuario)) {
                emprestimosAutor.remove(indiceLivro);
                emprestimosTitulo.remove(indiceLivro);
                emprestimosDataDevolucao.remove(indiceLivro);
                codigosEmprestimos.remove(indiceLivro);
                emprestimosCpf.remove(indiceLivro);
            } else {
                System.out.println("Não foi você que empresto esse livro!");
            }
        } else {
            System.out.println("Não existe nenhum livro com este código no sistema!");
        }
    }
    public static void listarTodosEmprestimos () { // Funcionalidade de listar todos os empréstimos
        for (int i = 0; i < emprestimosCpf.size(); i++) {
            System.out.println("Código do empréstimo: " + codigosEmprestimos.get(i) +
                    "\nTítulo do livro: " + emprestimosTitulo.get(i) +
                    "\nAutor: " + emprestimosAutor.get(i) +
                    "\nData de devolução prevista: " + emprestimosDataDevolucao.get(i)
            );
        }
    }
}