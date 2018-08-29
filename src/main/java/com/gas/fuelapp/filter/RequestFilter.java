package com.gas.fuelapp.filter;

import com.gas.fuelapp.exception.InvalidAccessException;
import com.gas.fuelapp.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;


@Component
@Slf4j
public class RequestFilter implements Filter {


    @Autowired
    DriverService driverService;

    public void destroy() {
        // Nothing to do
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
                (HttpServletRequest) request);
        String body = IOUtils.toString(wrappedRequest.getReader());
        log.info("Body Request: {}", body);
        validateDriverAccess(body);
        wrappedRequest.resetInputStream();
        chain.doFilter(wrappedRequest, response);

    }

    private void validateDriverAccess(String bodyRequest) {
        JSONObject jsonObj = new JSONObject(bodyRequest);
        String driverId = jsonObj.getString("Driver Id");
        if (StringUtils.isBlank(driverId) || !driverService.existDriverIdOnDatabase(driverId)) {
            log.error("Invalid access for Driver id: {}", driverId);
            throw new InvalidAccessException(String.format("This Driver Id [%s] does not have access", driverId));
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
        // Nothing to do
    }

    private static class ResettableStreamHttpServletRequest extends
            HttpServletRequestWrapper {

        private byte[] rawData;
        private HttpServletRequest request;
        private ResettableServletInputStream servletStream;

        public ResettableStreamHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
            this.servletStream = new ResettableServletInputStream();
        }


        public void resetInputStream() {
            servletStream.stream = new ByteArrayInputStream(rawData);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader());
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return servletStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader());
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return new BufferedReader(new InputStreamReader(servletStream));
        }


        private class ResettableServletInputStream extends ServletInputStream {

            private InputStream stream;

            @Override
            public int read() throws IOException {
                return stream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        }
    }

}