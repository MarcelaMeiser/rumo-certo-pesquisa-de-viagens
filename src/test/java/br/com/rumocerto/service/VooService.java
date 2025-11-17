package br.com.rumocerto.service;

import br.com.rumocerto.model.Voo;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Para rodar na ordem que a gente quer
class VooServiceTest {

    private VooService vooService;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        vooService = new VooService();
        System.out.println("--------------------------------------------------");
        System.out.println("▶️ INICIANDO: " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ FIM DO TESTE");
        System.out.println("");
    }

    @Test
    @Order(1)
    @DisplayName("Teste de Carga Inicial")
    void deveListarTodos() {
        int esperado = 10;
        List<Voo> voos = vooService.listarTodos();
        int retornado = voos.size();

        System.out.println("   Cenário: Verificar se o mock carregou 10 voos.");
        System.out.println("   Esperava: " + esperado + " itens.");
        System.out.println("   Retornou: " + retornado + " itens.");

        assertEquals(esperado, retornado, "A lista inicial deve ter 10 voos mockados");
    }

    @Test
    @Order(2)
    @DisplayName("Teste Bubble Sort (Preço)")
    void testarBubbleSortPreco() {
        System.out.println("   Cenário: Ordenar voos do mais barato para o mais caro.");

        // Executa
        vooService.ordenarPorPreco();
        List<Voo> voos = vooService.listarTodos();

        // Imprime visualmente para você provar que funcionou
        System.out.println("   🔍 Verificação Visual (Top 3 mais baratos):");
        voos.stream().limit(3).forEach(v -> System.out.println("      - R$ " + v.getPreco()));

        // Validação Lógica
        for (int i = 0; i < voos.size() - 1; i++) {
            double atual = voos.get(i).getPreco();
            double proximo = voos.get(i + 1).getPreco();

            if (atual > proximo) {
                System.out.println("   ❌ FALHA: " + atual + " é maior que " + proximo);
                fail("Lista não está ordenada por preço");
            }
        }
        System.out.println("   Resultado: A lista foi ordenada corretamente (Preço atual <= Próximo).");
    }

    @Test
    @Order(3)
    @DisplayName("Teste Selection Sort (Horário)")
    void testarSelectionSortHorario() {
        System.out.println("   Cenário: Ordenar voos do mais cedo para o mais tarde.");

        // Executa
        vooService.ordenarPorHorario();
        List<Voo> voos = vooService.listarTodos();

        System.out.println("   🔍 Verificação Visual (Top 3 primeiros horários):");
        voos.stream().limit(3).forEach(v -> System.out.println("      - " + v.getHorario()));

        for (int i = 0; i < voos.size() - 1; i++) {
            LocalTime atual = voos.get(i).getHorario();
            LocalTime proximo = voos.get(i + 1).getHorario();
            assertFalse(atual.isAfter(proximo), "Erro: " + atual + " veio depois de " + proximo);
        }
        System.out.println("   Resultado: A lista foi ordenada corretamente por horário.");
    }

    @Test
    @Order(4)
    @DisplayName("Teste Quick Sort (Duração)")
    void testarQuickSortDuracao() {
        System.out.println("   Cenário: Ordenar voos do mais curto para o mais longo.");

        // Executa
        vooService.ordenarPorDuracao();
        List<Voo> voos = vooService.listarTodos();

        System.out.println("   🔍 Verificação Visual (Top 3 voos mais rápidos):");
        voos.stream().limit(3).forEach(v -> System.out.println("      - " + v.getDuracao()));

        for (int i = 0; i < voos.size() - 1; i++) {
            Duration atual = voos.get(i).getDuracao();
            Duration proximo = voos.get(i + 1).getDuracao();
            assertTrue(atual.compareTo(proximo) <= 0, "Erro ordenação duração");
        }
        System.out.println("   Resultado: A lista foi ordenada corretamente por duração.");
    }

    @Test
    @Order(5)
    @DisplayName("Teste Busca Linear")
    void testarBuscaLinear() {
        String termoBusca = "São Paulo";
        System.out.println("   Cenário: Buscar por termo '" + termoBusca + "'");

        List<Voo> resultado = vooService.buscar(termoBusca);

        System.out.println("   Esperava: Pelo menos 1 resultado.");
        System.out.println("   Retornou: " + resultado.size() + " resultados encontrados.");

        assertFalse(resultado.isEmpty(), "Deveria encontrar voos");

        // Valida se o retorno está certo
        boolean todosContemTermo = resultado.stream()
                .allMatch(v -> v.getOrigem().contains(termoBusca) || v.getDestino().contains(termoBusca));

        if(todosContemTermo) {
            System.out.println("   Validação: Todos os itens retornados contêm '" + termoBusca + "'.");
        } else {
            System.out.println("   ❌ FALHA: Algum item retornado não corresponde à busca.");
        }

        assertTrue(todosContemTermo);
    }
}