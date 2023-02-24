package ru.practicum.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationDtoList;
import ru.practicum.dto.compilation.CompilationDtoResp;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.model.QCompilation;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilations;
    private final CompilationMapper mapper;
    private final EventRepository events;

    @Override
    public CompilationDtoResp addCompilation(NewCompilationDto compilationDto) {
        Set<Event> findEvents = events.findAllByEventIdIn(compilationDto.getEvents());
        Compilation compilation = mapper.mapToCompilation(compilationDto);
        compilation.setEvents(findEvents);
        return mapper.mapToCompilationResp(compilations.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        if (compilations.existsById(compId)) {
            compilations.deleteById(compId);
        } else {
            throw new EntityNotFoundException("Compilation with id=" + compId + " was not found");
        }
    }

    @Override
    @Transactional
    public CompilationDtoResp updateCompilation(Long compId, UpdateCompilationRequest updateCompilation) {
        Compilation compilation = compilations.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Compilation with id=" + compId + " was not found"));
        Set<Event> findEvents = events.findAllByEventIdIn(updateCompilation.getEvents());
        compilation = mapper.mapToCompilation(updateCompilation, compilation);
        compilation.setEvents(findEvents);
        return mapper.mapToCompilationResp(compilations.save(compilation));
    }

    @Override
    public CompilationDtoResp getCompilation(Long compId) {
        return mapper.mapToCompilationResp(compilations.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Compilation with id=" + compId + " was not found")));
    }

    @Override
    public CompilationDtoList getCompilations(Boolean pinned, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Page<Compilation> page;
        if (pinned != null) {
            booleanBuilder.and(QCompilation.compilation.pinned.eq(pinned));
        }
        if (booleanBuilder.getValue() != null) {
            page = compilations.findAll(booleanBuilder.getValue(), pageable);
        } else {
            page = compilations.findAll(pageable);
        }
        return CompilationDtoList
                .builder()
                .compilations(mapper.mapToCompilationRespList(page.getContent()))
                .build();
    }
}
