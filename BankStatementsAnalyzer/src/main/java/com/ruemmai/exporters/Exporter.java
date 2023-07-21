package com.ruemmai.exporters;

import com.ruemmai.models.SummaryStatistics;

public interface Exporter {
    String export(SummaryStatistics summaryStatistics);
}