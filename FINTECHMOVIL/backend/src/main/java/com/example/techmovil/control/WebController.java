package com.example.techmovil.control;

import com.example.techmovil.modelo.Venta;
import com.example.techmovil.servicio.ProductoService;
import com.example.techmovil.servicio.UsuarioService;
import com.example.techmovil.servicio.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final VentaService ventaService;

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/home")
    public String home() { return "home"; }

    @GetMapping("/")
    public String index() { return "redirect:/home"; }

    @GetMapping("/inventario")
    public String verInventario(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "inventario";
    }

    @GetMapping("/personal")
    public String verPersonal(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "personal";
    }

    @GetMapping("/ventas/nueva")
    public String nuevaVenta(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("productos", productoService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());
        return "registrar_venta";
    }

    @PostMapping("/ventas/guardar")
    public String guardarVenta(@ModelAttribute("venta") Venta venta, Model model) {
        Venta guardada = ventaService.save(venta);
        model.addAttribute("venta", guardada);
        return "factura";
    }
}
