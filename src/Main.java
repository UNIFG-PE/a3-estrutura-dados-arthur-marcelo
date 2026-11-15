import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void q1(String comandos) {
        // Foi escolhida a estrutura fila que tem a complexidade O(1) para peek, add, e poll
        // Também foi usado um HashSet para garantir que duas pessoas não tem a mesma senha
        // que tem a complexidade O(1) em média para add, contains, e remove. Ou O(n) no pior caso
        Queue<Integer> fila = new Queue<>();
        HashSet<Integer> senhas = new HashSet<>();
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 1000) {
            throw new IllegalStateException("n deve ser entre 1 e 1000");
        }
        for(int i = 0; i < n; ++i) {
            String comando = listaComandos[i+1];
            Matcher matcher = Pattern.compile("CHEGAR (\\d+)").matcher(comando);
            if(matcher.matches()) {
                int x = Integer.parseInt(matcher.group(1));
                if(x < 1 || x > 999999) {
                    throw new IllegalStateException("x deve ser entre 1 e 999999");
                }
                if(!senhas.add(x)) {
                    throw new IllegalStateException("Duas pessoas tem a mesma senha");
                }
                fila.add(x);
            }else if(comando.equals("PROXIMO")) {
                System.out.println(Objects.requireNonNullElse(fila.peek(), "VAZIA"));
            }else if(comando.equals("ATENDER")) {
                Integer atendimento = fila.poll();
                if(atendimento != null) {
                    System.out.println(atendimento);
                    senhas.remove(atendimento);
                }else {
                    System.out.println("VAZIA");
                }
            }
        }
    }

    public static void q2(String comandos) {
        // Foi escolhido a estrutura Stack que tem a complexidade O(1) para push, pop, e peek
        String[] listaComandos = comandos.split("\n");
        int t = Integer.parseInt(listaComandos[0]);
        if(t < 1 || t > 50) {
            throw new IllegalStateException("n deve ser entre 1 e 50");
        }
        for(int i = 0; i < t; ++i) {
            Stack<Integer> saida = new Stack<>();
            Stack<Integer> juncao = new Stack<>();
            int n = Integer.parseInt(listaComandos[i*2+1]);
            if(n < 1 || n > 1000) {
                throw new IllegalStateException("n deve ser entre 1 e 1000");
            }
            String[] listaVagoesString = listaComandos[i*2+2].split(" ");
            int[] listaVagoes = new int[listaVagoesString.length];
            for(int j = 0; j < listaVagoes.length; ++j) listaVagoes[j] = Integer.parseInt(listaVagoesString[j]);
            int tamanhoJuncao = 0;
            int ptr = 0;
            boolean possivel = true;
            for(int j = 0; j < n; ++j) {
                int vagao = j+1;
                while(ptr < n && juncao.peek() != null && juncao.peek() == listaVagoes[ptr]) {
                    ++ptr;
                    saida.push(juncao.pop());
                    --tamanhoJuncao;
                }
                if(ptr < n && vagao == listaVagoes[ptr]) {
                    ++ptr;
                    saida.push(vagao);
                }else {
                    juncao.push(vagao);
                    ++tamanhoJuncao;
                }
            }
            for(int j = 0; j < tamanhoJuncao; ++j) {
                if(juncao.peek() != null && ptr < n && juncao.peek() == listaVagoes[ptr]) {
                    ++ptr;
                    juncao.pop();
                }else {
                    possivel = false;
                    break;
                }
            }
            System.out.println(possivel ? "SIM" : "NAO");
        }
    }

    public static void q3(String comandos) {
        // Foi escolhida a estrutura LinkedList que tem a complexidade O(n) para
        // get, add, remove, removeElement, e contains. No caso de add e remove a complexidade pode ser O(1)
        // caso o índice seja no começo da lista
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 2000) {
            throw new IllegalStateException("n deve ser entre 1 e 2000");
        }
        LinkedList<Integer> lista = new LinkedList<>();
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            switch (comando[0]) {
                case "I" -> {
                    int p = Integer.parseInt(comando[1]);
                    int x = Integer.parseInt(comando[2]);
                    if (x < 1 || x > 999999) throw new IllegalStateException("x deve ser entre 1 e 999999");
                    try {
                        lista.add(p - 1, x);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERRO");
                    }
                }
                case "R" -> {
                    int p = Integer.parseInt(comando[1]);
                    try {
                        System.out.println(lista.remove(p - 1));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERRO");
                    }
                }
                case "C" -> {
                    int p = Integer.parseInt(comando[1]);
                    try {
                        System.out.println(lista.get(p - 1));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERRO");
                    }
                }
                case "L" -> {
                    if (lista.getLength() > 0) {
                        LinkedElement<Integer> cur = lista.iterate();
                        for (int j = 0; j < lista.getLength(); ++j) {
                            System.out.print(cur.getValue() + " ");
                            cur = cur.getNext();
                        }
                        System.out.println();
                    } else {
                        System.out.println("VAZIA");
                    }
                }
            }
        }
    }

    public static void q4(String comandos) {
        // Foram escolhidas as estruturas Queue e LinkedList com complexidades anteriormente ditas
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 5000) {
            throw new IllegalStateException("n deve ser entre 1 e 5000");
        }
        Queue<Integer> filaNormal = new Queue<>();
        Queue<Integer> filaUrgente = new Queue<>();
        LinkedList<Integer> relatorio = new LinkedList<>();
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            switch(comando[0]) {
                case "NORMAL" -> {
                    int x = Integer.parseInt(comando[1]);
                    if (x < 1 || x > 999999) throw new IllegalStateException("x deve ser entre 1 e 999999");
                    filaNormal.add(x);
                }
                case "URGENTE" -> {
                    int x = Integer.parseInt(comando[1]);
                    if (x < 1 || x > 999999) throw new IllegalStateException("x deve ser entre 1 e 999999");
                    filaUrgente.add(x);
                }
                case "ATENDER" -> {
                    Integer val;
                    if (filaUrgente.peek() != null) {
                        val = filaUrgente.poll();
                    } else {
                        val = filaNormal.poll();
                    }
                    if(val != null) {
                        relatorio.add(relatorio.getLength(), val);
                        System.out.println(val);
                    }else {
                        System.out.println("VAZIA");
                    }
                }
                case "RELATORIO" -> {
                    if(relatorio.getLength() > 0) {
                        LinkedElement<Integer> cur = relatorio.iterate();
                        for (int j = 0; j < relatorio.getLength(); ++j) {
                            System.out.print(cur.getValue() + " ");
                            cur = cur.getNext();
                        }
                        System.out.println();
                    }else {
                        System.out.println("VAZIO");
                    }
                }
            }
        }
    }

    public static void q5(String comandos) {
        // Foi escolhida a estrutura array com complexidade O(1) para acesso
        // No terceiro caso a busca linear foi tecnicamente melhor, mas isso não significa uma complexidade
        // menor, pois complexidade apenas dita a taxa de crescimento do número de operações, não o número
        // de operações em si
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 100000) throw new IllegalStateException("n deve ser entre 1 e 100000");
        int[] arr = new int[n];
        String[] listaNumeros = listaComandos[1].split(" ");
        for(int i = 0; i < n; ++i) {
            arr[i] = Integer.parseInt(listaNumeros[i]);
        }
        int q = Integer.parseInt(listaComandos[2]);
        if(q < 1 || q > 1000) throw new IllegalStateException("q deve ser entre 1 e 1000");
        for(int i = 0; i < q; ++i) {
            int cur = Integer.parseInt(listaComandos[i+3]);
            Util.SearchResult resultadoLinear = Util.linearSearch(arr, cur);
            Util.SearchResult resultadoBinario = Util.binarySearch(arr, cur);
            System.out.println(resultadoLinear.index()+" "+resultadoLinear.iterations()+" "+resultadoBinario.iterations());
        }
    }

    private record Corrida(int peito, int tempo) implements Comparable<Corrida> {
        @Override
        public int compareTo(Corrida o) {
            int comparacaoTempo = Integer.compare(tempo, o.tempo);
            if (comparacaoTempo != 0) {
                return comparacaoTempo;
            }
            return Integer.compare(peito, o.peito);
        }
    }
    public static void q6(String comandos) {
        // Foi escolhido o algorítimo Quicksort que tem a complexidade no caso médio de O(n log(n))
        // no caso médio e de O(n²) no pior caso
        // Também foi usado HashSet para garantir que dois atletas não tem o mesmo número de peito
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 200000) throw new IllegalStateException("n deve ser entre 1 e 20000");
        HashSet<Integer> numerosDePeito = new HashSet<>();
        Corrida[] corridas = new Corrida[n];
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            int peito = Integer.parseInt(comando[0]);
            if(!numerosDePeito.add(peito)) {
                throw new IllegalStateException("Dois atletas tem o mesmo número de peito");
            }
            if(peito < 1 || peito > 999999) throw new IllegalStateException("Número de peito deve ser entre 1 e 999999");
            int tempo = Integer.parseInt(comando[1]);
            if(tempo < 1 || tempo > 86400) throw new IllegalStateException("Tempo deve ser entre 1 e 86400");
            corridas[i] = new Corrida(peito, tempo);
        }
        Util.quickSort(corridas, 0, corridas.length-1);
        for(int i = 0; i < n; ++i) {
            System.out.println(i+1+" "+corridas[i].peito()+" "+corridas[i].tempo());
        }
    }

    public static void q7(String comandos) {
        // Foi escolhido a estrutura BinarySearchTree que tem a complexidade O(log(n)) para
        // insert, remove, e searchAndPrint. E a complexidade O(n) para height, inorder, e preorder.
        // E no pior caso insert pode ser O(n)
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 10000) throw new IllegalStateException("n deve ser entre 1 e 10000");
        BinarySearchTree<Integer> arvore = new BinarySearchTree<>();
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            switch(comando[0]) {
                case "INSERIR" -> {
                    int x = Integer.parseInt(comando[1]);
                    if (x < 1 || x > 999999) throw new IllegalStateException("x deve ser entre 1 e 999999");
                    arvore.insert(x);
                }
                case "BUSCAR" -> {
                    int x = Integer.parseInt(comando[1]);
                    if (x < 1 || x > 999999) throw new IllegalStateException("x deve ser entre 1 e 999999");
                    arvore.searchAndPrint(x);
                }
                case "REMOVER" -> {
                    int x = Integer.parseInt(comando[1]);
                    if (x < 1 || x > 999999) throw new IllegalStateException("x deve ser entre 1 e 999999");
                    try {
                        arvore.remove(x);
                    }catch (NoSuchElementException e) {
                        System.out.println("ERRO");
                    }
                }
                case "EMORDEM" -> arvore.inorder();
                case "PREORDEM" -> arvore.preorder();
                case "ALTURA" -> System.out.println(arvore.height());
            }
        }
    }

    public static void q8(String comandos) {
        // Foi usado a estrutura AvlTree que tem O(log(n)) para insert,
        // O(n) para preorder, e O(1) para getHeight
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 100000) throw new IllegalStateException("n deve ser entre 1 e 100000");
        AvlTree<Integer> arvore = new AvlTree<>();
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            switch(comando[0]) {
                case "INSERIR" -> {
                    int x = Integer.parseInt(comando[1]);
                    if (x < 1 || x > 1000000000) throw new IllegalStateException("x deve ser entre 1 e 1000000000");
                    arvore.insert(x);
                }
                case "PREORDEM" -> arvore.preorder();
                case "ALTURA" -> System.out.println(arvore.getHeight());
            }
        }
    }

    private record Paciente(int prioridade, int chegada, String nome) implements Comparable<Paciente> {
        @Override
        public int compareTo(Paciente o) {
            int comparacaoPrioridade = Integer.compare(prioridade, o.prioridade());
            if (comparacaoPrioridade != 0) {
                return comparacaoPrioridade;
            }
            return Long.compare(o.chegada(), chegada);
        }
    }
    public static void q9(String comandos) {
        // Foi usado a estrutura MaxHeap que tem O(log(n)) para insert e extractMax
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 100000) throw new IllegalStateException("n deve ser entre 1 e 100000");
        MaxHeap<Paciente> pacientes = new MaxHeap<>(n);
        int chegada = 0;
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            switch(comando[0]) {
                case "CHEGAR" -> {
                    String nome = comando[1];
                    if(!nome.matches("^[A-Z]{1,20}$")) throw new IllegalStateException("Nome inválido");
                    int p = Integer.parseInt(comando[2]);
                    if(p < 1 || p > 5) throw new IllegalStateException("p deve ser entre 1 e 5");
                    pacientes.insert(new Paciente(p, chegada++, nome));
                }
                case "ATENDER" -> {
                    Paciente paciente;
                    try {
                        paciente = pacientes.extractMax();
                    }catch(IllegalStateException e) {
                        System.out.println("VAZIA");
                        continue;
                    }
                    System.out.println(paciente.nome()+" "+paciente.prioridade());
                }
                case "PROXIMO" -> {
                    Paciente paciente;
                    try {
                        paciente = pacientes.peekMax();
                    }catch(IllegalStateException e) {
                        System.out.println("VAZIA");
                        continue;
                    }
                    System.out.println(paciente.nome()+" "+paciente.prioridade());
                }
            }
        }
    }

    public static void q10(String comandos) {
        // Foi usada estrutura HashMap que tem em a complexidade O(1) para getSize.
        // Tem em média complexidade O(1) get, add, contains, e remove
        // ou O(n) no pior caso
        String[] listaComandos = comandos.split("\n");
        int n = Integer.parseInt(listaComandos[0]);
        if(n < 1 || n > 200000) throw new IllegalStateException("n deve ser entre 1 e 200000");
        HashMap<Long, String> tabela = new HashMap<>();
        for(int i = 0; i < n; ++i) {
            String[] comando = listaComandos[i+1].split(" ");
            switch(comando[0]) {
                case "CADASTRAR" -> {
                    long m = Long.parseLong(comando[1]);
                    if(m < 1 || m > 9999999999L) throw new IllegalStateException("m deve ser entre 1 e 9999999999");
                    String nome = comando[2];
                    if(!nome.matches("^[A-Z]{1,20}$")) throw new IllegalStateException("Nome inválido");
                    System.out.println(tabela.add(m, nome) ? "CADASTRADO" : "ATUALIZADO");
                }
                case "BUSCAR" -> {
                    long m = Long.parseLong(comando[1]);
                    if(m < 1 || m > 9999999999L) throw new IllegalStateException("m deve ser entre 1 e 9999999999");
                    System.out.println(Objects.requireNonNullElse(tabela.get(m), "NAO ENCONTRADO"));
                }
                case "REMOVER" -> {
                    long m = Long.parseLong(comando[1]);
                    if(m < 1 || m > 9999999999L) throw new IllegalStateException("m deve ser entre 1 e 9999999999");
                    System.out.println(tabela.remove(m) ? "REMOVIDO" : "NAO ENCONTRADO");
                }
                case "TOTAL" -> {
                    System.out.println(tabela.getSize());
                }
            }
        }
    }

    public static void printTestCases() {
        System.out.println("==============================================================");
        q1("""
                8
                CHEGAR 101
                CHEGAR 102
                PROXIMO
                ATENDER
                PROXIMO
                ATENDER
                ATENDER
                PROXIMO
                """
        );
        System.out.println("==============================================================");
        q2("""
                3
                5
                5 4 3 2 1
                5
                1 2 5 4 3
                5
                3 1 2 5 4
                """);
        System.out.println("==============================================================");
        q3("""
                9
                I 1 10
                I 2 30
                I 2 20
                L
                R 2
                C 2
                I 1 5
                R 4
                L
                """);
        System.out.println("==============================================================");
        q4("""
                10
                NORMAL 101
                NORMAL 102
                URGENTE 901
                URGENTE 902
                ATENDER
                ATENDER
                NORMAL 103
                ATENDER
                ATENDER
                RELATORIO
                """);
        System.out.println("==============================================================");
        q5("""
                8
                3 8 12 19 25 31 40 47
                3
                40
                5
                3
                """);
        System.out.println("==============================================================");
        q6("""
                6
                42 3610
                7 3545
                15 3610
                3 3720
                28 3545
                11 3499
                """);
        System.out.println("==============================================================");
        q7("""
                13
                INSERIR 50
                INSERIR 30
                INSERIR 70
                INSERIR 20
                INSERIR 40
                INSERIR 60
                INSERIR 35
                BUSCAR 35
                BUSCAR 65
                REMOVER 30
                REMOVER 90
                PREORDEM
                ALTURA
                """);
        System.out.println("==============================================================");
        q8("""
                10
                INSERIR 10
                INSERIR 20
                INSERIR 30
                INSERIR 25
                INSERIR 28
                INSERIR 5
                INSERIR 7
                INSERIR 20
                PREORDEM
                ALTURA
                """);
        System.out.println("==============================================================");
        q9("""
                9
                CHEGAR ANA 2
                CHEGAR BRUNO 5
                CHEGAR CAIO 3
                PROXIMO
                CHEGAR DAVI 5
                ATENDER
                ATENDER
                ATENDER
                ATENDER
                """);
        System.out.println("==============================================================");
        q10("""
                10
                CADASTRAR 20231047 ANA
                CADASTRAR 20232817 BRUNO
                CADASTRAR 20231033 CARLA
                BUSCAR 20232817
                CADASTRAR 20232817 BRUNA
                BUSCAR 20232817
                REMOVER 20231047
                BUSCAR 20231047
                REMOVER 20239999
                TOTAL
                """);
        System.out.println("==============================================================");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Mostrar resultado dos testes\n2. Fazer o teste manual");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if(choice == 1) {
            printTestCases();
        }else if(choice == 2) {
            System.out.println("Digite o número da questão de 1 a 10");
            int q = scanner.nextInt();
            scanner.nextLine();
            if(q < 1 || q > 10) {
                System.out.println("Questão inválida");
                return;
            }
            System.out.println("Digite os comandos");
            String comandos = scanner.useDelimiter("\\A").next();
            switch(q) {
                case 1 -> q1(comandos);
                case 2 -> q2(comandos);
                case 3 -> q3(comandos);
                case 4 -> q4(comandos);
                case 5 -> q5(comandos);
                case 6 -> q6(comandos);
                case 7 -> q7(comandos);
                case 8 -> q8(comandos);
                case 9 -> q9(comandos);
                case 10 -> q10(comandos);
            }
        }
    }
}