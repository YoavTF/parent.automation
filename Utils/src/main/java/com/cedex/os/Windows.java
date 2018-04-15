package com.cedex.os;

import java.util.ArrayList;

public class Windows {
    public static String fileSeparator = "\\";


    public static String getParentFolder(String childFileEntity) throws Exception {
        String parentFolder = "";

        // String fullFileName = "c:\\temp\\file.txt";
        // String[] fullFileNameAsArr = childFileEntity.split("\\\\");
        String[] fullFileNameAsArr = childFileEntity.split(Windows.fileSeparator + Windows.fileSeparator);
        if (fullFileNameAsArr == null) {
            throw new Exception("Can't split: " + childFileEntity + " by " + Windows.fileSeparator + Windows.fileSeparator);

        }
        ArrayList<String> fullFileNameAsArrLst = new ArrayList<String>();
        parentFolder = fullFileNameAsArr[0];
        boolean inFor = false;
        for (int i = 1; i < fullFileNameAsArr.length - 1; i++) {
            parentFolder = parentFolder + "\\" + fullFileNameAsArr[i];
            inFor = true;
        }

        parentFolder = inFor ? parentFolder : parentFolder + "\\";
        return parentFolder;
    }

    public static String getShortFileName(String fullPathName) throws Exception {
        String shortFileName = "";

        // String fullFileName = "c:\\temp\\file.txt";
        // String[] fullFileNameAsArr = fullPathName.split("\\\\");
        String[] fullFileNameAsArr = fullPathName.split(Windows.fileSeparator + Windows.fileSeparator);
        if (fullFileNameAsArr == null) {
            throw new Exception("Can't split: " + fullPathName + " by " + Windows.fileSeparator + Windows.fileSeparator);

        }
        shortFileName = fullFileNameAsArr[fullFileNameAsArr.length - 1];

        return shortFileName;
    }
    /**
     * add child file/folder name to parent full path <br>
     * replacing cases like: patent +"/"+child <br>
     *
     * @param childShortName
     *              - short name of child file/folder
     *
     *
     * @throws Exception
     */

    public static String appendFullChildName(String parentFullPath, String childShortName) throws Exception {
        String childFullName;

        if (!parentFullPath.endsWith("\"")) {
            if (parentFullPath.endsWith(Windows.fileSeparator)) {
                if (childShortName.startsWith(Windows.fileSeparator)) {
                    childFullName = parentFullPath + childShortName.substring(1, childShortName.length());
                } else {
                    childFullName = parentFullPath + childShortName;
                }

            } else {
                if (childShortName.startsWith("/")) {
                    childFullName = parentFullPath + childShortName;
                } else {
                    childFullName = parentFullPath + Windows.fileSeparator + childShortName;
                }
            }
        } else {
            String parentFullPathNoInvertedComma = parentFullPath.substring(0, parentFullPath.length() - 1);

            if (parentFullPathNoInvertedComma.endsWith(Windows.fileSeparator)) {
                if (childShortName.startsWith(Windows.fileSeparator)) {
                    childFullName = parentFullPathNoInvertedComma + childShortName.substring(1, childShortName.length()) + "\"";
                } else {
                    childFullName = parentFullPathNoInvertedComma + childShortName + "\"";
                }
            } else {
                if (childShortName.startsWith(Windows.fileSeparator)) {
                    childFullName = parentFullPathNoInvertedComma + childShortName + "\"";
                } else {
                    childFullName = parentFullPathNoInvertedComma + Windows.fileSeparator + childShortName + "\"";
                }
            }
        }
        return childFullName;
    }

}
