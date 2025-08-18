package kz.shyngys.diary.log;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter implements Filter {

    private static final String TRACE_ID = "traceId";
    private static final String HEADER_TRACE_ID = "X-Trace-Id";

    @Override
    public void doFilter(
            jakarta.servlet.ServletRequest request,
            jakarta.servlet.ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // пробуем взять traceId из заголовка
            String traceId = httpRequest.getHeader(HEADER_TRACE_ID);

            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }

            // кладем в MDC
            MDC.put(TRACE_ID, traceId);

            // добавим traceId в ответ, чтобы клиент тоже его видел
            httpResponse.setHeader(HEADER_TRACE_ID, traceId);

            chain.doFilter(request, response);
        } finally {
            MDC.remove(TRACE_ID); // чистим после обработки
        }
    }
}