package org.jboss.aerogear.core;

import org.jboss.resteasy.plugins.server.servlet.HttpServletResponseWrapper;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

class DeferringHttpServletResponseWrapper extends HttpServletResponseWrapper {
    public DeferringHttpServletResponseWrapper(HttpServletResponse response, ResteasyProviderFactory providerFactory) {
        super(response, providerFactory);
    }

    private OutputStream getUnwrappedOutputStream() throws IOException {
        return super.getOutputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new DeferringOutputStream();
    }

    private class DeferringOutputStream extends OutputStream {
        public void write(int i) throws IOException {
            getUnwrappedOutputStream().write(i);
        }

        public void write(byte[] bytes) throws IOException {
            getUnwrappedOutputStream().write(bytes);
        }

        public void write(byte[] bytes, int i, int i1) throws IOException {
            getUnwrappedOutputStream().write(bytes, i, i1);
        }

        public void flush() throws IOException {
            getUnwrappedOutputStream().flush();
        }

        public void close() throws IOException {
            getUnwrappedOutputStream().close();
        }
    }
}
