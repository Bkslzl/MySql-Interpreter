package edu.uob.Tools;

import java.io.*;

public class Output{
    public static PrintStream originalOut = System.out;
    public static ByteArrayOutputStream data = new ByteArrayOutputStream();

    public static PrintStream captureStream = new PrintStream(data);

    public static PrintStream originalErr = System.err;
    public static ByteArrayOutputStream errData = new ByteArrayOutputStream();
    public static PrintStream captureErrStream = new PrintStream(errData);


}
