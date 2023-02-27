package ru.practicum.api.admin_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDtoResp;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDtoResp> addCompilation(@RequestBody @Valid NewCompilationDto compilation) {
        log.info("add compilation:{}", compilation);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.addCompilation(compilation));
    }

    @DeleteMapping("{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable @Min(1) Long compId) {
        log.info("delete compilation with id={}", compId);
        compilationService.deleteCompilation(compId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{compId}")
    public ResponseEntity<CompilationDtoResp> updateCompilation(@PathVariable @Min(1) Long compId,
                                                                @RequestBody UpdateCompilationRequest updateCompilation) {
        log.info("update compilation with id={} to compilation:{}", compId, updateCompilation);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.updateCompilation(compId, updateCompilation));
    }
}