package com.zerobase.mydividend.web;

import com.zerobase.mydividend.exception.impl.TickerEmptyException;
import com.zerobase.mydividend.model.Company;
import com.zerobase.mydividend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String prefix) {
        List<String> companyNames = companyService.getCompanyNamesByPrefix(prefix);

        return ResponseEntity.ok(companyNames);
    }

    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        return ResponseEntity.ok(companyService.getAllCompany(pageable));
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();

        if (!StringUtils.hasText(ticker)) {
            throw new TickerEmptyException();
        }

        Company company = companyService.save(ticker);

        return ResponseEntity.ok(company);
    }

    @DeleteMapping("{ticker}")
    public ResponseEntity<?> deleteCompany(@PathVariable String ticker) {
        return null;
    }
}
