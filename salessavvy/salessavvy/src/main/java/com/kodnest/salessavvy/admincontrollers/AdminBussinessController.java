package com.kodnest.salessavvy.admincontrollers;

import com.kodnest.salessavvy.services.adminservices.AdminBusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/business")
@CrossOrigin(origins = "http://localhost:9090", allowCredentials = "true")
public class AdminBussinessController {

    AdminBusinessService service;

    public AdminBussinessController(AdminBusinessService service) {
        this.service = service;
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyBusinsness(@RequestParam String date) {
        return null;
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyBusinsness(@RequestParam int month, int year) {
        return null;
    }

    @GetMapping("/yearly")
    public ResponseEntity<?> getYearlyBusinsness(@RequestParam int year) {
        return null;
    }

    @GetMapping("/overall")
    public ResponseEntity<?> getOverallBusinsness() {
        return null;
    }
}
