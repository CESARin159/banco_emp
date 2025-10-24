package banco.controllers; // Verifica que el paquete sea correcto

import banco.models.CuentaBancaria;
import banco.services.CuentaBancariaService; // Asumiendo que tienes un servicio
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas") // <-- URL de base (ej: http://localhost:8080/api/cuentas)
public class CuentaBancariaController {

    private final CuentaBancariaService cuentaBancariaService;

    // Inyección por constructor (la mejor práctica)
    public CuentaBancariaController(CuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }

    // 1. OBTENER TODAS LAS CUENTAS (GET /api/cuentas)
    @GetMapping
    public List<CuentaBancaria> getAllCuentas() {
        return cuentaBancariaService.findAll();
    }

    // 2. OBTENER POR ID (GET /api/cuentas/{id})
    @GetMapping("/{id}")
    public ResponseEntity<CuentaBancaria> getCuentaById(@PathVariable Integer id) {
        return cuentaBancariaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVA CUENTA (POST /api/cuentas)
    @PostMapping
    public CuentaBancaria createCuenta(@RequestBody CuentaBancaria cuenta) {
        return cuentaBancariaService.save(cuenta);
    }

    // 4. ACTUALIZAR CUENTA (PUT /api/cuentas/{id})
    @PutMapping("/{id}")
    public ResponseEntity<CuentaBancaria> updateCuenta(@PathVariable Integer id, @RequestBody CuentaBancaria cuenta) {
        // Lógica de actualización, asegurando que el ID sea correcto
        if (!id.equals(cuenta.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuentaBancariaService.update(cuenta));
    }

    // 5. ELIMINAR CUENTA (DELETE /api/cuentas/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Integer id) {
        cuentaBancariaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}