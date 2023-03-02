package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.report.ReportDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.ReportMapper;
import ru.practicum.repository.ReportRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper mapper;

    @Override
    public ReportDto getReportByUserId(Long userId) {
        return mapper.mapToReportDto(reportRepository.findByReportedUserUserId(userId)
                .orElseThrow(() -> new NotFoundException("Report with reported user id=" + userId + " not found")));
    }
}
