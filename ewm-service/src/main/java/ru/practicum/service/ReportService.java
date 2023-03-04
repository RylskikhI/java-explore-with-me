package ru.practicum.service;

import ru.practicum.dto.report.ReportDto;

public interface ReportService {
    ReportDto getReportByUserId(Long userId);
}
