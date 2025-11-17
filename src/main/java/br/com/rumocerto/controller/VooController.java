package br.com.rumocerto.controller;

import br.com.rumocerto.model.Voo;
import br.com.rumocerto.service.VooService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VooController {

    // Instância do service gerenciada pelo controller (permite reset)
    private VooService service = new VooService();

    /**
     * GET "/" - lista todos os voos na view "index".
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("voos", service.listarTodos());
        return "index";
    }

    /**
     * GET "/buscar" - busca por termo em origem ou destino.
     * Se termo for vazio/nulo, retorna todos.
     */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "termo", required = false) String termo, Model model) {
        List<Voo> resultados;
        if (termo == null || termo.trim().isEmpty()) {
            resultados = service.listarTodos();
        } else {
            resultados = service.buscar(termo);
        }
        model.addAttribute("voos", resultados);
        model.addAttribute("termo", termo);
        return "index";
    }

    /**
     * GET "/ordenar/{criterio}" - ordena pelo critério e redireciona para "/".
     * Critérios suportados: "preco", "horario", "duracao".
     */
    @GetMapping("/ordenar/{criterio}")
    public String ordenar(@PathVariable("criterio") String criterio) {
        if ("preco".equalsIgnoreCase(criterio)) {
            service.ordenarPorPreco();
        } else if ("horario".equalsIgnoreCase(criterio)) {
            service.ordenarPorHorario();
        } else if ("duracao".equalsIgnoreCase(criterio)) {
            service.ordenarPorDuracao();
        }
        return "redirect:/";
    }

    /**
     * GET "/resetar" - restaura a lista original recriando o service.
     */
    @GetMapping("/resetar")
    public String resetar() {
        service = new VooService();
        return "redirect:/";
    }
}

