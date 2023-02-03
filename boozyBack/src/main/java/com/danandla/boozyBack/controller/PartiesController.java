package com.danandla.boozyBack.controller;

import com.danandla.boozyBack.entity.PartyEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.PartyModel;
import com.danandla.boozyBack.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parties")
public class PartiesController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getParties() {
        try {
            return ResponseEntity.ok(partyService.getAllParties());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/menu")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getMenu(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(partyService.getMenu(id));
        } catch (ItemNameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity deletePartyById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(partyService.deleteById(id));
        } catch (ItemNameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity addParty(@RequestBody PartyModel newParty) {
        try {
            partyService.addParty(newParty);
            return ResponseEntity.ok("party successfully added");
        } catch (ItemNameUsedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity editParty(@RequestBody PartyModel newParty) {
        try {
            partyService.editParty(newParty);
            return ResponseEntity.ok("party successfully added");
        } catch (ItemIdNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/available")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getAvailable(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(partyService.getAvailableCocktails(id));
        } catch (ItemIdNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
