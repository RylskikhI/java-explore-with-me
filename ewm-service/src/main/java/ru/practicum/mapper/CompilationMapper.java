package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.compilation.CompilationDtoResp;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface CompilationMapper {
    @Mapping(target = "events", ignore = true)
    Compilation mapToCompilation(NewCompilationDto compilationDto);

    @Mapping(target = "id", source = "compilationId")
    CompilationDtoResp mapToCompilationResp(Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", ignore = true)
    Compilation mapToCompilation(UpdateCompilationRequest updateCompilation, @MappingTarget Compilation compilation);

    List<CompilationDtoResp> mapToCompilationRespList(List<Compilation> compilations);
}
