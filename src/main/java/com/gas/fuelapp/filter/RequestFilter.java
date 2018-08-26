package com.gas.fuelapp.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.gas.fuelapp.service.DriverService;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
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

        System.out.println(body);
        validateDriverAccess(body);

        wrappedRequest.resetInputStream();
        chain.doFilter(wrappedRequest, response);

    }

    private void validateDriverAccess(String bodyRequest){
        JSONObject jsonObj = new JSONObject(bodyRequest);
        String driverId = jsonObj.getString("Driver Id");
        if(!driverService.existDriverIdOnDatabase(driverId)){
            System.out.println("não existe");
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