package com.cedex.Linux;

import com.cedex.LocalMachineInterface;
import com.cedex.text.RegExpr;
import jsystem.framework.report.ListenerstManager;
import jsystem.framework.report.Reporter;
import jsystem.framework.system.SystemObjectImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class represents a automation machine that a jsystem running on
 * (env1auto,env2auto ...) <br>
 * Last changed: $LastChangedDate: 2012-09-10 13:29:15 +0300 (Mon, 10 Sep 2012)
 * $
 *
 * @author $Author: romang $
 * @version $Revision: 12235 $
 */
public class LinuxLocal extends SystemObjectImpl implements LocalMachineInterface {

    /**
     * a reference to the report mechanism. Used to add reports to the log
     */
    public static Reporter report = ListenerstManager.getInstance();

    // password of automation machine
    public static String password = "axxana";
    public static String passwordMain = "axxana";
    public static String user = "root";

    public static String endOfLine = "\r\n";

    private static String runnCommand(String commandAsString) throws Exception {
        String commandOutPut = "";

        try {

            Process p = Runtime.getRuntime().exec(commandAsString);

            p.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            // String output = "";

            while ((line = buf.readLine()) != null) {
                commandOutPut += line + "\n";
            }

            report.report("output: " + commandOutPut);
        } catch (Exception e) {
            report.report("Caught exception: " + e.getMessage());
        }

        return commandOutPut;

    }

    /**
     * check whether a known directory exists
     *
     * @param dirPath - directory to search
     * @return - true if directory exists
     * @throws Exception
     */
    public static boolean isDirExists(String dirPath) throws Exception {

        report.startLevel("checking if dir: " + dirPath + " exists ?");
        boolean exist = false;
        try {

            if (!dirPath.substring(0, 1).contains("/")) {
                dirPath = "/".concat(dirPath);
            }

            String commandAsString = "ls -d " + dirPath;
            String result = runnCommand(commandAsString);
            report.report("output:" + result);
            if (!result.contains("No such file or directory")) {
                report.report("Folder: " + dirPath + " exists");
                exist = true;
            } else {
                report.report("Folder: " + dirPath + " not exists");
            }

        } catch (Exception e) {
            report.report("got exception " + e.getMessage());
        } finally {

            report.stopLevel();
        }
        return exist;

    }

    /**
     * create a folder
     *
     * @param folderFullPath folder full path
     * @throws Exception
     */
    public static void createFolder(String folderFullPath) throws Exception {
        report.startLevel("creating folder if needed ...");
        try {

            String commandAsString = "mkdir -p " + folderFullPath;
            report.report("creating folder " + folderFullPath);
            runnCommand(commandAsString);

        } catch (Exception e) {
            report.report("got exception: " + e.getMessage());
        } finally {
            report.stopLevel();
        }
    }

    /**
     * run pkill command
     *
     * @param processName the process name to kill
     * @throws Exception
     */
    public static void killProcessByName(String processName) throws Exception {
        report.startLevel("creating folder if needed ...");
        try {
            String commandAsString = "pkill " + processName;
            report.report("run command:   " + commandAsString);
            runnCommand(commandAsString);

        } catch (Exception e) {
            report.report("got exception: " + e.getMessage());
        } finally {
            report.stopLevel();
        }
    }

    /**
     * extract tar files in linux without using conn <br>
     * should run on local linux machine <br>
     *
     * @throws Exception
     **/
    public static void extractTarArchive(String archiveFullPath, String destinationFolder) throws Exception {
        String commandAsString = "tar -C " + destinationFolder + " -xvvf " + archiveFullPath;
        report.startLevel("extracting: " + archiveFullPath + " to: " + destinationFolder);
        runnCommand(commandAsString);
        report.stopLevel();

    }

    /**
     * get ig address for received host name
     *
     * @return
     * @throws Exception
     */
    @Override
    public String getIpAddressFromHostName(String hostName) throws Exception {
        String commandAsString = "ping " + hostName + " -c 1";

        String commandOutPut = runnCommand(commandAsString);
        RegExpr regexpr = new RegExpr(commandOutPut, "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
        report.report("command: " + commandAsString + " output: " + commandOutPut);
        String ipShort = "";
        if (regexpr.isFound()) {
            ipShort = regexpr.getGroupText(2);
        }
        return ipShort;
    }

    /**
     * get automation machine local ip address
     *
     * @return
     * @throws Exception
     */
    @Override
    public String getIpAddress() throws Exception {
        // String commandAsString = "ping " + LinuxLocal.getHostName()
        // +
        // " -c 1| awk '{print $5}'| grep -e '\\d*\\.\\d*.\\d*.\\d*'| awk -F'[:,(,)]' '{print $2}'";
        String ipShort = getIpAddressFromHostName(getHostName());
        report.report("Automation machine ip address: " + ipShort);

        return ipShort;

    }

    /**
     * get machine hostname
     *
     * @throws Exception
     **/
    @Override
    public String getHostName() throws Exception {
        String CommandAsString = "hostname";

        String commandOutPut = runnCommand(CommandAsString);

        String[] hostanmeAsArr = commandOutPut.split("\\.");
        String hosnameShort = hostanmeAsArr[0];
        hosnameShort = hosnameShort.replaceAll(endOfLine, "");
        // hosnameShort = hosnameShort.replaceAll("\\r", "");

        // commandOutPut = commandOutPut.replaceAll(endOfLine, "");
        report.report("Automation machine hostname: " + hosnameShort);

        return hosnameShort;
    }

    /**
     * get list of file for folder in linux without using conn <br>
     * should run on local linux machine <br>
     *
     * @throws Exception
     **/
    public static String[] listFilesForFolder(String folder, SortFileOrder sortOrder, String[] whiteListFileExtentions, int lastFiles)
            throws Exception {
        String commandAsString = "";
        if (sortOrder == SortFileOrder.DESC) {
            commandAsString = "ls -lt " + folder;
        } else {
            commandAsString = "ls -ltr " + folder;
        }
        report.startLevel("looking for files like: " + Arrays.toString(whiteListFileExtentions) + " in folder: " + folder);
        String output = runnCommand(commandAsString);

        ArrayList<String> tempFilesList = new ArrayList<String>();

        Pattern p = Pattern.compile("[a-z|-]+\\s+\\d+\\s+[A-z]+\\s+[A-z]+\\s+\\d+\\s+[A-z]+\\s*\\d+\\s+\\d+:\\d+\\s+(.*)",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(output);

        int filesCounter = 0;
        boolean stopLoop = false;
        while (!stopLoop) {
            if (!m.find()) {
                report.report("reached last file in folder: " + folder);
                stopLoop = true;
            }
            String fileName = m.group(1);
            report.report("found file: " + fileName);
            for (String currWhiteListExtention : whiteListFileExtentions) {
                if (fileName.endsWith(currWhiteListExtention)) {
                    report.report("Found snap file: " + fileName);
                    tempFilesList.add(fileName);
                    filesCounter++;
                } else {
                    report.report("file name: " + fileName + " not contains: " + currWhiteListExtention);
                }
                if (filesCounter >= lastFiles) {
                    report.report("Found all files: " + lastFiles);
                    tempFilesList.add(fileName);
                    stopLoop = true;
                }

            }
        }

        String[] foundFilesList = new String[tempFilesList.size()];
        report.report("only last: " + lastFiles + " files will be returned");
        report.stopLevel();
        for (int i = 0; i < lastFiles; i++) {
            foundFilesList[i] = tempFilesList.get(i);
        }

        return foundFilesList;

    }

}
