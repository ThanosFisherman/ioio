/*
 * Copyright 2011 Ytai Ben-Tsvi. All rights reserved.
 *  
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL ARSHAN POURSOHI OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied.
 */

package ioio.lib.util.pc;

import ioio.lib.spi.Logger;
import ioio.lib.util.IOIOBaseApplicationHelper;
import ioio.lib.util.IOIOConnectionManager;
import ioio.lib.util.IOIOConnectionRegistry;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.IOIOLooperProvider;

/**
 * A helper class for creating different kinds of IOIO based applications.
 * <p>
 * <i><b>Note</b>: Consider using {@link IOIOConsoleApp} or {@link IOIOSwingApp}
 * for easy creation of console- or Swing-based IOIO applications. This class is
 * intended for more advanced use-cases not covered by them.</i>
 * <p>
 * This class implements a common life-cycle for applications interacting with
 * IOIO devices. Usage is as follows:
 * <ul>
 * <li>Create an instance of {@link IOIOPcApplicationHelper}, passing a
 * {@link IOIOLooperProvider} to the constructor.</li>
 * <li>When the application starts, call {@link #start()}, which will in turn
 * attempt to create a thread for each possible IOIO connection channel. Each
 * thread will have a respective {@link IOIOLooper}, which the client provides,
 * through which the client gets context for working with the IOIO.</li>
 * <li>When the application exits, call {@link #stop()}, which will disconnect
 * all open connections and will abort and join all the threads.</li>
 */
public class IOIOPcApplicationHelper extends IOIOBaseApplicationHelper implements Logger.ILogger {
	protected int logLevel = Logger.LOG_INFO;

	static {
		IOIOConnectionRegistry
				.addBootstraps(new String[] { "ioio.lib.pc.SerialPortIOIOConnectionBootstrap" });
	}

	private final IOIOConnectionManager manager_ = new IOIOConnectionManager(
			this);

	public IOIOPcApplicationHelper(IOIOLooperProvider provider) {
		super(provider);
		Logger.log = this;
	}

	public void start() {
		manager_.start();
	}

	public void stop() {
		manager_.stop();
	}

	@Override
	public void i(String tag, String message) {
		if (logLevel >= Logger.LOG_INFO) {
			System.out.println(tag + ": " + message);
		}
	}

	@Override
	public void i(String tag, String message, Throwable exception) {
		if (logLevel >= Logger.LOG_INFO) {
			System.out.println(tag + ": " + message);
			exception.printStackTrace(System.out);
		}
	}

	@Override
	public void e(String tag, String message) {
		if (logLevel >= Logger.LOG_ERROR) {
			System.err.println(tag + ": " + message);
		}
	}

	@Override
	public void e(String tag, String message, Throwable exception) {
		if (logLevel >= Logger.LOG_ERROR) {
			System.err.println(tag + ": " + message);
			exception.printStackTrace(System.err);
		}
	}

	@Override
	public void d(String tag, String message) {
		if (logLevel >= Logger.LOG_DEBUG) {
			System.out.println(tag + ": " + message);
		}

	}

	@Override
	public void d(String tag, String message, Throwable exception) {
		if (logLevel >= Logger.LOG_DEBUG) {
			System.out.println(tag + ": " + message);
			exception.printStackTrace(System.out);
		}
	}

	@Override
	public void w(String tag, String message) {
		if (logLevel >= Logger.LOG_WARN) {
			System.out.println(tag + ": " + message);
		}
	}

	@Override
	public void w(String tag, String message, Throwable exception) {
		if (logLevel >= Logger.LOG_WARN) {
			System.out.println(tag + ": " + message);
			exception.printStackTrace(System.out);
		}
	}

	@Override
	public void v(String tag, String message) {
		if (logLevel >= Logger.LOG_VERBOSE) {
			System.out.println(tag + ": " + message);
		}
	}

	@Override
	public void v(String tag, String message, Throwable exception) {
		if (logLevel >= Logger.LOG_VERBOSE) {
			System.out.println(tag + ": " + message);
			exception.printStackTrace(System.out);
		}
	}

	@Override
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public int getLogLevel() {
		return this.logLevel;
	}
}
