package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.compilation.CompilationDtoList;
import ru.practicum.dto.compilation.CompilationDtoResp;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

public interface CompilationService {
    CompilationDtoResp addCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDtoResp updateCompilation(Long compId, UpdateCompilationRequest updateCompilation);

    CompilationDtoResp getCompilation(Long compId);

    CompilationDtoList getCompilations(Boolean pinned, Pageable pageable);
}
