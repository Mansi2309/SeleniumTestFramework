package base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trace {
	/**
	 * Initial tracingMode would be set to NOT_SET. After moving to TEST, it
	 * stays there. Used for controlling the behavior required for tests
	 */
	public enum TracingMode {
		NOT_SET, TEST
	};

	/**
	 * testTraceFilesMap has one entry per test thread that started tracing via
	 * doTestTracing.
	 *
	 * Key = threadId Value = traceFilePath //Note that different threads can
	 * refer to the same traceFilePath.
	 */
	private static Map<String, String> testTraceFilesMap = new HashMap<String, String>();
	/**
	 * testPrintStreamsMap has one entry per test trace file.
	 *
	 * Key = traceFilePath //Note that give test trace file could be used across
	 * multiple test threads Value = corresponding PrintStream for traceFilePath
	 */
	private static Map<String, PrintStream> testPrintStreamsMap = new HashMap<String, PrintStream>();
	/**
	 * Setting trace mode to not set initially
	 */
	private static TracingMode tracingMode = TracingMode.NOT_SET;
	/* Creating instance */
	private static Trace sInstance = new Trace();
	BasePage basePage = new BasePage();

	/* Singleton */
	private Trace() {
	}

	/* return instance */
	public static Trace getInstance() {
		return sInstance;
	}

	/* Check if trace is On or not */
	public static boolean isTraceOn() {
		if (tracingMode == TracingMode.TEST) {
			return true;
		}
		return false;
	}

	/**
	 * Enables test thread specific tracing to specified directory and file
	 * name. Supports parallel tracing across multiple test threads via multiple
	 * files.
	 */
	public static synchronized void doTestTracing(String traceDirectory, String fileName) {
		if ((tracingMode == TracingMode.NOT_SET || tracingMode == TracingMode.TEST) && traceDirectory != null
				&& !traceDirectory.isEmpty() && fileName != null && !fileName.isEmpty()) {
			turnOffTracing(fileName); // if test tracing is currently on, stop
										// it
			initTracing(traceDirectory, fileName, TracingMode.TEST);
		}
	}

	private static OutputStream getOutputStream(String traceFilePath) throws IOException {
		return new FileOutputStream(traceFilePath, true);
	}

	private static PrintStream createPrintStream(String traceFilePath) throws IOException {
		return new PrintStream(getOutputStream(traceFilePath), true);
	}

	/**
	 * 
	 * @param traceDirectory
	 * @param fileName
	 * @param tracingMode
	 */
	private static void initTracing(String traceDirectory, String fileName, TracingMode tracingMode) {
		boolean tracingStarted = false;
		String traceFilePath = null;
		try {
			traceFilePath = initPrintStream(traceDirectory, fileName, tracingMode);
			if (traceFilePath != null) {
				Trace.tracingMode = tracingMode;
			} else {
				String errMsg = " failed to start tracing: traceDirectory=" + traceDirectory + " fileName=" + fileName
						+ " traceFilePath=" + traceFilePath;
				System.err.println("[" + new Date() + "] [ERROR]" + errMsg);

			}
		} catch (Exception e) {
			String errMsg = ": Unable to open trace file: traceDirectory=" + traceDirectory + " fileName=" + fileName
					+ " traceFilePath=" + traceFilePath + ", error=" + e.getMessage();
			System.err.println("[" + new Date() + "] [ERROR]" + errMsg);
			e.printStackTrace();

		}
	}

	/**
	 * Initializing log File
	 * 
	 * @param traceDirectory
	 * @param fileName
	 * @param tracingMode
	 * @return
	 * @throws IOException
	 */
	private static String initPrintStream(String traceDirectory, String fileName, TracingMode tracingMode)
			throws IOException {
		String traceFilePath = null;
		if (validateTraceDirectoryExistence(traceDirectory, tracingMode)) {
			traceFilePath = getTraceFilePath(traceDirectory, fileName);
		}
		// open print stream, if one does not already exist
		if (!testPrintStreamsMap.containsKey(traceFilePath)) {
			PrintStream testPrintStream = createPrintStream(traceFilePath);
			testPrintStreamsMap.put(traceFilePath, testPrintStream);
			testTraceFilesMap.put(fileName, traceFilePath);
			// stash traceFile against this thread

		}
		return traceFilePath;
	}

	/**
	 * Validating trace directory
	 * 
	 * @param traceDirectory
	 * @param tracingMode
	 * @return
	 */
	private static boolean validateTraceDirectoryExistence(String traceDirectory, TracingMode tracingMode) {
		boolean isDirectoryValid = false;
		if (traceDirectory != null) {
			File check_path = new File(traceDirectory);
			if (check_path.exists()) {
				if (!check_path.isDirectory()) {
					System.err.println("[" + new Date() + "] [ERROR] : invalid directory:  " + traceDirectory);
				} else {
					isDirectoryValid = true;
				}
			} else {
				if (!check_path.mkdirs()) {
					System.err.println("[" + new Date() + "] [ERROR] " + tracingMode + ": Failed to create directory: "
							+ traceDirectory);
				} else {
					isDirectoryValid = true;
				}
			}
		}
		return isDirectoryValid;
	}

	/* Getting trace file path */
	private static String getTraceFilePath(String traceDirectory, String traceFileName) {
		String traceFilePath = null;
		if (traceFileName != null) {
			traceFilePath = traceDirectory + traceFileName;
		} else {
			System.out.println("");
		}
		return traceFilePath;
	}

	/* getting print stream based on threadId */
	private PrintStream getPrintStream(String file) {
		if (testTraceFilesMap.containsKey(file) && testPrintStreamsMap.containsKey(testTraceFilesMap.get(file))) {
			// return
			// testPrintStreamsMap.get(testTraceFilesMap.get(Thread.currentThread().getId()));
			return testPrintStreamsMap.get(testTraceFilesMap.get(file));
		}
		// Return the first print stream, if proper stream does not exist for
		// this thread
		for (Map.Entry<String, PrintStream> entry : testPrintStreamsMap.entrySet()) {
			return entry.getValue();
		}

		throw new IllegalStateException("PrintStream not configured");
	}

	/**
	 * Turn off tracing
	 */
	public static synchronized void turnOffTracing(String file) {

		String traceFilePath = testTraceFilesMap.get(file);
		if (traceFilePath != null) {
			// remove reference for traceFilePath from testTraceFilesMap first
			if (testTraceFilesMap.containsKey(file)) {
				testTraceFilesMap.remove(file);
			}

			// if no further references exist for traceFilePath, then close the
			// print stream
			if (!testTraceFilesMap.values().contains(traceFilePath)) {
				if (testPrintStreamsMap.containsKey(traceFilePath)) {
					closePrintStream(testPrintStreamsMap.get(traceFilePath), traceFilePath);
					testPrintStreamsMap.remove(traceFilePath);
				}
			}
		}

	}

	private static void closePrintStream(PrintStream ps, String filePath) {
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {

				String errMsg = "Failed to close print stream for file: " + filePath + ", errMsg=" + e.getMessage();
				System.out.println(errMsg);
			}
		}
	}

	public void debug(String msg, String fileName) {
		if (isTraceOn()) {
			write(msg, fileName);
		}
	}

	/*
	 * private static String getThreadDesc() { return "[" +
	 * Thread.currentThread().getName() + ":" + Thread.currentThread().getId() +
	 * "] "; }
	 */

	private void write(String msg, String fileName) {
		try {
			getPrintStream(fileName).println(msg);
		} catch (Exception e) {
			System.err.println("Failed to trace msg: " + msg + " errMsg=" + e.getMessage());
			e.printStackTrace();
		}
	}
}
