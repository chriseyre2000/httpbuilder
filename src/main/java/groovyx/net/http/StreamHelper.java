package groovyx.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.codehaus.groovy.runtime.InvokerHelper;

// This is a direct copy of the leftShift operator from Groovy 2
// Groovy 3 dropped this method so it breaks everywhere.
public class StreamHelper {
    
    /**
     * Pipe an InputStream into an OutputStream for efficient stream copying.
     *
     * @param self stream on which to write
     * @param in   stream to read from
     * @return the outputstream itself
     * @throws IOException if an I/O error occurs.
     * @since 1.0
     */
    public static OutputStream leftShift(OutputStream self, InputStream in) throws IOException {
      byte[] buf = new byte[1024];
      while (true) {
          int count = in.read(buf, 0, buf.length);
          if (count == -1) break;
          if (count == 0) {
              Thread.yield();
              continue;
          }
          self.write(buf, 0, count);
      }
      self.flush();
      return self;
  }

     /**
     * Overloads the left shift operator to provide a mechanism to append
     * values to a writer.
     *
     * @param self  a Writer
     * @param value a value to append
     * @return the writer on which this operation was invoked
     * @throws IOException if an I/O error occurs.
     * @since 1.0
     */
    public static Writer leftShift(Writer self, Object value) throws IOException {
      InvokerHelper.write(self, value);
      return self;
  }
}