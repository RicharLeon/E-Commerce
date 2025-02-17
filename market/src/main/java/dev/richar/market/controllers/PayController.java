    package dev.richar.market.controllers;

    import dev.richar.market.models.entity.Pay;
    import dev.richar.market.models.services.IPayService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @CrossOrigin(origins = "*")
    @RequestMapping("/pay")
    public class PayController {

        @Autowired
        private IPayService payService;

        @GetMapping
        public ResponseEntity<?> getAllPays(){
            return ResponseEntity.ok(payService.findAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getPayById(Integer id){
            return ResponseEntity.ok(payService.findById(id));
        }

        @PostMapping
        public ResponseEntity<?> savePay(@RequestBody Pay pay){
            return ResponseEntity.ok(payService.save(pay));
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> updatePay(@PathVariable Integer id, @RequestBody Pay pay){
            return ResponseEntity.ok(payService.update(pay, id));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deletePay(@PathVariable Integer id){
            payService.delete(id);
            return ResponseEntity.ok().build();
        }

    }
