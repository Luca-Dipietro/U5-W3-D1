package lucadipietro.U5_W3_D1.controllers;

import lucadipietro.U5_W3_D1.entities.Dipendente;
import lucadipietro.U5_W3_D1.exceptions.BadRequestException;
import lucadipietro.U5_W3_D1.payloads.DipendenteLoginDTO;
import lucadipietro.U5_W3_D1.payloads.DipendenteLoginResponseDTO;
import lucadipietro.U5_W3_D1.payloads.DipendentiDTO;
import lucadipietro.U5_W3_D1.services.AuthService;
import lucadipietro.U5_W3_D1.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendentiService dipendentiService;

    @PostMapping("/login")
    public DipendenteLoginResponseDTO login(@RequestBody DipendenteLoginDTO body){
        return new DipendenteLoginResponseDTO(authService.authenticateUserAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente saveDipendente(@RequestBody @Validated DipendentiDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw new BadRequestException(validationResult.getAllErrors());
        } else {
            return this.dipendentiService.save(body);
        }
    }
}
