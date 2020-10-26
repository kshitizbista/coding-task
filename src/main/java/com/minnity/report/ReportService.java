package com.minnity.report;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    /**
     * Return number of requests that were made for each company
     *
     * @param requestLogs must not be {@literal null}.
     * @return Map with key as company id and value as total request made by company (e.g. companyId -> requestNumber)
     */
    public Map<Integer, Long> calculateNumberOfRequestsPerCompany(List<RequestLog> requestLogs) {
        return requestLogs.stream()
                .collect(Collectors.groupingBy(RequestLog::getCompanyId, Collectors.counting()));
    }

    /**
     * Count and return requests per company that finished with an error HTTP response code (>=400)
     *
     * @param requestLogs must not be {@literal null}.
     * @return Map with key as company id and value as Lists of RequestLog made by company (e.g. companyId -> List<RequestLog>)
     */
    public Map<Integer, List<RequestLog>> findRequestsWithError(List<RequestLog> requestLogs) {
        return requestLogs.stream()
                .filter(requestLog -> requestLog.getRequestStatus() >= 400)
                .collect(Collectors.groupingBy(RequestLog::getCompanyId));
    }

    /**
     * Find and get API (requests path) that on average takes the longest time to process the request.
     *
     * @param requestLogs must not be {@literal null}.
     * @return the request url having max request duration time
     */
    public String findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
        return Collections.max(requestLogs, Comparator.comparingLong(RequestLog::getRequestDuration))
                .getRequestPath();
    }


}
