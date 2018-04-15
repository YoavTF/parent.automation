package com.cedex.os;

public class Linux {
    public static String fileSeparator = "\\";


    /**
     * function replays parent folder (without using File class) If received /
     * folder (so return the same /)
     *
     * @param childFileEntity
     *              - file or folder full path
     *
     *
     * @throws Exception
     */

    public static String getParentFolder(String childFileEntity) throws Exception {
        String parentFolder =  "";

        // String fullFileName = "c:\\temp\\file.txt";
        String[] fullFileNameAsArr = childFileEntity.split("/");
        if (fullFileNameAsArr == null) {
            throw new Exception("Can't split: " + childFileEntity + " by /");

        }
        if (fullFileNameAsArr.length > 0) {
            parentFolder = fullFileNameAsArr[0].equals("") ? "/" : fullFileNameAsArr[0];
            for (int i = 1; i < fullFileNameAsArr.length - 1; i++) {
                parentFolder = parentFolder + "/" + fullFileNameAsArr[i];
            }
        } else {
            parentFolder = "/";
        }

        if (parentFolder.length() >= 2 && parentFolder.charAt(0) == '/' && parentFolder.charAt(1) == '/') {
            parentFolder = parentFolder.substring(1, parentFolder.length());
        }
        return parentFolder;
    }

    public static String getShortFileName(String fullPathName) throws Exception {
        String shortFileName = "";
        String[] fullFileNameAsArr = fullPathName.split("/");
        if (fullFileNameAsArr == null) {
            throw new Exception("Can't split: " + fullPathName + " by /");

        }
        if (fullFileNameAsArr.length > 0) {
            shortFileName = fullFileNameAsArr[fullFileNameAsArr.length - 1];
        }

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
            if (parentFullPath.endsWith("/")) {
                if (childShortName.startsWith("/")) {
                    childFullName = parentFullPath + childShortName.substring(1, childShortName.length());
                } else {
                    childFullName = parentFullPath + childShortName;
                }

            } else {
                if (childShortName.startsWith("/")) {
                    childFullName = parentFullPath + childShortName;
                } else {
                    childFullName = parentFullPath + "/" + childShortName;
                }
            }
        } else {
            String parentFullPathNoInvertedComma = parentFullPath.substring(0, parentFullPath.length() - 1);

            if (parentFullPathNoInvertedComma.endsWith("/")) {
                if (childShortName.startsWith("/")) {
                    childFullName = parentFullPathNoInvertedComma + childShortName.substring(1, childShortName.length()) + "\"";
                } else {
                    childFullName = parentFullPathNoInvertedComma + childShortName + "\"";
                }
            } else {
                if (childShortName.startsWith("/")) {
                    childFullName = parentFullPathNoInvertedComma + childShortName + "\"";
                } else {
                    childFullName = parentFullPathNoInvertedComma + "/" + childShortName + "\"";
                }
            }
        }
        return childFullName;
    }

//    public String getMainFolder(String fullPathName) throws Exception {
//        String mainFolder = notFound;
//        RegExpr regexpr = new RegExpr(fullPathName, "([/|\\.|\\w][\\w|-]+)");
//        if (regexpr.isFound()) {
//            mainFolder = regexpr.getGroupText(2);
//        }
//        return mainFolder;
//    }
}
