package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.report.ReportDto;
import ru.practicum.model.Report;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(source = "reportedUser.userId", target = "reportedUser")
    ReportDto mapToReportDto(Report report);
}
