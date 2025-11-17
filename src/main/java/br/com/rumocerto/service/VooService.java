package br.com.rumocerto.service;

import br.com.rumocerto.model.Voo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VooService {
    private final List<Voo> voos = new ArrayList<>();

    public VooService() {
        voos.add(new Voo("São Paulo", "Rio de Janeiro", LocalDate.of(2025, 6, 1), LocalTime.of(9, 30), 199.99, Duration.ofMinutes(50)));
        voos.add(new Voo("Belo Horizonte", "Salvador", LocalDate.of(2025, 6, 2), LocalTime.of(7, 15), 349.50, Duration.ofHours(2)));
        voos.add(new Voo("Curitiba", "Porto Alegre", LocalDate.of(2025, 6, 3), LocalTime.of(12, 0), 149.00, Duration.ofMinutes(55)));
        voos.add(new Voo("Fortaleza", "Recife", LocalDate.of(2025, 6, 4), LocalTime.of(18, 45), 129.75, Duration.ofMinutes(75)));
        voos.add(new Voo("Manaus", "Belém", LocalDate.of(2025, 6, 5), LocalTime.of(6, 0), 279.99, Duration.ofHours(1).plusMinutes(30)));
        voos.add(new Voo("Brasília", "Goiânia", LocalDate.of(2025, 6, 6), LocalTime.of(14, 20), 89.90, Duration.ofMinutes(40)));
        voos.add(new Voo("Porto Alegre", "Florianópolis", LocalDate.of(2025, 6, 7), LocalTime.of(10, 10), 119.00, Duration.ofMinutes(50)));
        voos.add(new Voo("Rio de Janeiro", "São Paulo", LocalDate.of(2025, 6, 8), LocalTime.of(16, 30), 189.99, Duration.ofMinutes(55)));
        voos.add(new Voo("Salvador", "Fortaleza", LocalDate.of(2025, 6, 9), LocalTime.of(11, 5), 159.49, Duration.ofHours(1).plusMinutes(15)));
        voos.add(new Voo("Goiânia", "Brasília", LocalDate.of(2025, 6, 10), LocalTime.of(20, 0), 79.00, Duration.ofMinutes(35)));
    }

    /**
     * Retorna todos os voos em memória.
     * @return cópia da lista de voos.
     */
    public List<Voo> listarTodos() {
        return new ArrayList<>(voos);
    }

    /**
     * Busca linear (manual) por termo em origem ou destino.
     * Complexidade: O(n) no pior caso (varre todos os voos).
     * @param termo termo a ser buscado (ignora case). Se null ou vazio, retorna lista vazia.
     * @return lista de voos que contêm o termo na origem ou destino.
     */
    public List<Voo> buscar(String termo) {
        List<Voo> resultados = new ArrayList<>();
        if (termo == null || termo.trim().isEmpty()) {
            return resultados;
        }
        String t = termo.toLowerCase();
        for (Voo v : voos) {
            String origem = v.getOrigem() != null ? v.getOrigem().toLowerCase() : "";
            String destino = v.getDestino() != null ? v.getDestino().toLowerCase() : "";
            if (origem.contains(t) || destino.contains(t)) {
                resultados.add(v);
            }
        }
        return resultados;
    }

    /**
     * Ordena a lista de voos por preço em ordem crescente usando Bubble Sort.
     *
     * Complexidade:
     * - Pior caso: O(n^2)
     * - Caso médio: O(n^2)
     * - Melhor caso (já ordenado): O(n) se otimizar com flag (este impl. não para cedo): O(n^2)
     */
    public void ordenarPorPreco() {
        int n = voos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (voos.get(j).getPreco() > voos.get(j + 1).getPreco()) {
                    swap(j, j + 1);
                }
            }
        }
    }

    /**
     * Ordena a lista de voos por horário (LocalTime) em ordem crescente usando Selection Sort.
     *
     * Complexidade:
     * - Pior caso: O(n^2)
     * - Caso médio: O(n^2)
     * - Melhor caso: O(n^2)
     */
    public void ordenarPorHorario() {
        int n = voos.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                LocalTime hj = voos.get(j).getHorario();
                LocalTime hMin = voos.get(minIndex).getHorario();
                if (hj != null && (hMin == null || hj.isBefore(hMin))) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(i, minIndex);
            }
        }
    }

    /**
     * Ordena a lista de voos por duração em ordem crescente usando Quick Sort (implementação manual).
     *
     * Complexidade:
     * - Pior caso: O(n^2) (quando a partição é muito desequilibrada)
     * - Caso médio: O(n log n)
     * - Melhor caso: O(n log n)
     */
    public void ordenarPorDuracao() {
        if (voos.size() <= 1) {
            return;
        }
        quickSort(0, voos.size() - 1);
    }

    private void swap(int i, int j) {
        Voo tmp = voos.get(i);
        voos.set(i, voos.get(j));
        voos.set(j, tmp);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int p = partition(low, high);
            quickSort(low, p - 1);
            quickSort(p + 1, high);
        }
    }

    private int partition(int low, int high) {
        Duration pivot = voos.get(high).getDuracao();
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            Duration dj = voos.get(j).getDuracao();
            if (dj == null) {
                continue;
            }
            if (pivot == null || dj.compareTo(pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }
}
