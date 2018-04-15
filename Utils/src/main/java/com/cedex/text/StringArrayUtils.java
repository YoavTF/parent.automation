package com.cedex.text;


import com.cedex.GlobalParameters;

import java.util.*;

/**
 * String Array Utils class<br>
 * contains utilities functions for String[] type <br>
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 12235 $
 */
public class StringArrayUtils implements GlobalParameters {

    /**
     * return String array of java's special characters
     *
     * @return
     */
    public static String[] getSpecialCharStringArray() {
        return new String[]{"\'", "\"", "\\", ")", "(", "+", "*"};
    }

    /**
     * Return the longest common sub string in array list
     * <p>
     * AAAB FFFFGAAA AAAJ (AAA)
     *
     * @param strArr
     * @return
     */
    public static String getLongestCommonSubStringInArray(ArrayList<String> strArr) {

        String commonStr = notFound;
        String smallStr = "";
        for (String s : strArr) {
            if (smallStr.length() < s.length()) {
                smallStr = s;
            }
        }
        String tempCom = "";
        char[] smallStrChars = smallStr.toCharArray();
        for (char c : smallStrChars) {
            tempCom += c;
            for (String s : strArr) {
                if (!s.contains(tempCom)) {
                    tempCom = c + "";
                    for (String s1 : strArr) {
                        if (!s1.contains(tempCom)) {
                            tempCom = "";
                            break;
                        }
                    }
                    break;
                }
            }
            if (!tempCom.equals(notFound) && tempCom.length() > commonStr.length()) {
                commonStr = tempCom;
            }
        }
        return commonStr;
    }

    /**
     * remove empty members + remove spaces from member
     *
     * @param theArrayToCleanup
     * @param removeAllOrMultiple - it true remove all or remove only sequences of chars <br>
     *                            example: <br>
     *                            if true: string before: aa [many spaces] bb <br>
     *                            string after aabb<br>
     *                            <p>
     *                            if false string before: aa [many spaces] bb <br>
     *                            string after aa [only one space]bb<br>
     * @return
     * @throws Exception
     */
    public static ArrayList<String> removeWhiteSpacesFromArrayListMembers(ArrayList<String> theArrayToCleanup, boolean removeAllOrMultiple)
            throws Exception {
        return removeAnyCharFromArrayListMembers(theArrayToCleanup, new String[]{" "}, removeAllOrMultiple);
    }

    /**
     * remove empty members and remove any chars contains in array's members
     *
     * @param theArrayToCleanup
     * @param charsToRemove
     * @return
     * @throws Exception
     */
    public static ArrayList<String> removeAnyCharFromArrayListMembers(ArrayList<String> theArrayToCleanup, String[] charsToRemove,
                                                                      boolean removeAllOrSequences) throws Exception {

        ArrayList<String> cleanedArray = new ArrayList<String>();
        ArrayList<String> cleanedArray1 = new ArrayList<String>();
        for (String currArrayMember : theArrayToCleanup) {
            boolean deleteCharFound = false;
            // String foundCharToDelete = notFound;

            if (currArrayMember != null && !currArrayMember.equals("")) {
                for (String currCharToRemove : charsToRemove) {
                    RegExpr regexpr = new RegExpr(currArrayMember, "^" + currCharToRemove + "*" + "$");
                    if (regexpr.isFound()) {
                        deleteCharFound = true;
                        // foundCharToDelete = currCharToRemove;
                        break;
                    }
                }
            } else {
                deleteCharFound = true;
            }
            if (!deleteCharFound) {
                cleanedArray.add(currArrayMember);
            }
        }
        for (String currArrayCleanedMember : cleanedArray) {
            for (String currCharToRemove : charsToRemove) {
                String replaceBy = removeAllOrSequences ? "" : currCharToRemove;
                String cleanedMember = currArrayCleanedMember.trim();
                cleanedArray1.add(cleanedMember);
            }
        }
        return cleanedArray1;
    }

    /**
     * remove duplicated string from ArrayList<String> immutable <br>
     *
     * @param theArrayToCleanup - the array to remove duplications from
     * @return
     */
    public static ArrayList<String> removeDuplicates(ArrayList<String> theArrayToCleanup) {
        return new ArrayList(new HashSet(theArrayToCleanup));

    }

    /**
     * remove duplicated string from string []
     *
     * @param arrayToRemoveDuplicatesFrom
     * @return
     */
    public static String[] removeDuplicates(String[] arrayToRemoveDuplicatesFrom) {
        HashMap<String, String> theMap = new HashMap<String, String>();
        for (String currString : arrayToRemoveDuplicatesFrom) {
            theMap.put(currString, currString);
        }
        String[] arrWithoutDuplicates = new String[theMap.size()];
        int i = 0;
        for (String currKey : theMap.keySet()) {
            arrWithoutDuplicates[i] = currKey;
            i++;
        }

        return arrWithoutDuplicates;
    }

    /**
     * function converts String[] to ArrayList<String>
     *
     * @param arrayToConvert
     * @return
     */
    public static ArrayList<String> stringArray2ArrayListString(String[] arrayToConvert) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String currString : arrayToConvert) {
            arrayList.add(currString);
        }

        return arrayList;
    }

    /**
     * converts ArrayList<String> to string[]
     *
     * @param listToConvert
     * @return
     */
    public static String[] arrayList2StringArray(ArrayList<String> listToConvert) {

        String[] stringArray = new String[listToConvert.size()];
        for (int i = 0; i < listToConvert.size(); i++) {
            stringArray[i] = listToConvert.get(i);
        }

        return stringArray;
    }

    /**
     * converts StringArray to ArrayList
     *
     * @param listToConvert
     * @return
     */
    public static ArrayList<String> stringArray2ArrayList(String[] listToConvert) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (listToConvert != null && listToConvert.length > 0) {
            Collections.addAll(arrayList, listToConvert);
        }
        return arrayList;
    }

    /**
     * converts String[] to String in line function <br>
     * (set split char is: as given by parameter: splitChar )
     *
     * @param splitChar   - split every strings from string array by its char
     * @param stringArray
     * @return
     */
    public static String stringArrayToString(String[] stringArray, String splitChar) {
        String appendedString = "";
        // String splitChar = ",";
        if (stringArray!=null) {
            for (String currString : stringArray) {
                appendedString = appendedString + currString + splitChar;
            }
            appendedString = appendedString.substring(0, appendedString.length() - 1);
        }
        return appendedString;
    }

    /**
     * converts String[] to String in line function <br>
     * (set split char is: , )
     *
     * @param stringArray
     * @return
     */
    public static String stringArrayToString(String[] stringArray) {
        return stringArrayToString(stringArray, ",");
    }

    /**
     * copy string array from on to another
     *
     * @param fullArray - contains string values for copy to another array
     * @return
     */
    public static String[] copyArray(String[] fullArray) {
        String newArray[] = new String[fullArray.length];
        System.arraycopy(fullArray, 0, newArray, 0, newArray.length);

        return newArray;
    }

    /**
     * converts String[] array
     *
     * @param theArrayToConvert the array to convert <br>
     * @return if theArrayToConvert={1,2,3} the output: {3,2,1}
     */
    public static String[] convertArray(String[] theArrayToConvert) {
        String convertedArray[] = new String[theArrayToConvert.length];

        for (int i = theArrayToConvert.length - 1, j = 0; i >= 0; i--, j++) {
            convertedArray[j] = theArrayToConvert[i];
        }

        return convertedArray;
    }

    /**
     * compare if there are at least one duplicated value between 2 string
     * arrays
     *
     * @param toLookInArray   firast array
     * @param toLookFromArray - second array
     * @return
     */
    public static boolean isStringArrayContainValueFromStringArray(String[] toLookInArray, String[] toLookFromArray) {
        boolean isContain = false;
        for (String currString : toLookFromArray) {
            if (isStringArayContainValue(toLookInArray, currString)) {
                isContain = true;
            }
        }
        return isContain;
    }

    /**
     * look for any String into String[] <br>
     * (case sensitive)
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @return
     */
    public static boolean isStringArayContainValue(String[] toLookInArray, String theValueToLookFor, boolean caseSensitive) {
        return isStringArayContainValue(toLookInArray, theValueToLookFor, true, false, false);
    }

    /**
     * look for any String into String[] <br>
     * (case sensitive)
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @return
     */
    public static boolean isStringArayContainValue(String[] toLookInArray, String theValueToLookFor, boolean caseSensitive, boolean equal, boolean bothSide) {
        if (toLookInArray == null) return false;
        ArrayList<String> convertedToLookInArray = new ArrayList(Arrays.asList(toLookInArray));
        return isArrayListContainValue(convertedToLookInArray, theValueToLookFor, caseSensitive, equal, bothSide);
    }

    /**
     * look for any String into String[] <br>
     * (case sensitive)
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @return
     */
    public static boolean isStringArayContainValue(String[] toLookInArray, String theValueToLookFor) {
//		  if (toLookInArray==null) return false;
//		  ArrayList<String> convertedToLookInArray = new ArrayList(Arrays.asList(toLookInArray));
        return isStringArayContainValue(toLookInArray, theValueToLookFor, true);
    }

    /**
     * look for any String into ArraliList of Strings<br>
     * (case sensitive)
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @return
     */
    public static boolean isArrayListContainValue(ArrayList toLookInArray, String theValueToLookFor) {
        return isArrayListContainValue(toLookInArray, theValueToLookFor, true);
    }

    /**
     * look for any String into ArraliList of Strings
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @param caseSensitive
     * @return
     */
    public static boolean isArrayListContainValue(ArrayList toLookInArray, String theValueToLookFor, boolean caseSensitive) {
        return isArrayListContainValue(toLookInArray, theValueToLookFor, caseSensitive, true, false);
    }

    /**
     * if arrayList contain duplicate values
     *
     * @param toLookInArray
     * @return true -if found duplicate values
     * @throws Exception
     */
    public static boolean isArrayListContainDuplications(ArrayList toLookInArray) throws Exception {
        boolean isDuplicatesFound = false;
        HashMap<String, Boolean> tempMap = new HashMap<String, Boolean>();
        for (int i = 0; i < toLookInArray.size(); i++) {
            String currValue = toLookInArray.get(i).toString();
            if (tempMap != null) {
                tempMap.put(currValue, true);
            } else {
                isDuplicatesFound = true;
            }
        }
        return isDuplicatesFound;
    }

    /**
     * look for any String into ArraliList of Strings
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @param caseSensitive     true for case sensitive <br>
     *                          false for not case sensitive
     * @param equal             - true for full comparison (like equal func) <br>
     *                          false for string contains other
     * @return
     */
    public static boolean isArrayListContainValue(ArrayList toLookInArray, String theValueToLookFor, boolean caseSensitive, boolean equal) {
        return isArrayListContainValue(toLookInArray, theValueToLookFor, caseSensitive, equal, false);
    }

    /**
     * look for any Regular expression pattern into ArrayList of Strings
     *
     * @param theListOfPatterns - the array list of patterns to look for
     * @param theTextToLookInto - the text to looking into
     * @param caseSensitive     true for case sensitive <br>
     *                          false for not case sensitive
     * @return
     * @throws Exception
     */
    public static boolean isArrayListMatchedAValue(String[] theListOfPatterns, String theTextToLookInto, boolean caseSensitive) throws Exception {
        boolean isExist = false;
        for (String currValue : theListOfPatterns) {
            RegExpr regexpr = new RegExpr(theTextToLookInto, currValue, caseSensitive);
            if (regexpr.isFound()) {
                isExist = true;
                break;
            }
        }

        return isExist;
    }

    /**
     * look for any String into ArrayList of Strings
     *
     * @param toLookInArray     - the array list to look into
     * @param theValueToLookFor - the value to look for
     * @param caseSensitive     true for case sensitive <br>
     *                          false for not case sensitive
     * @param equal             - true for full comparison (like equal func) <br>
     *                          false for string contains other
     * @param bothSide          - true - for any array's value contain or equal in/to the
     *                          valueTRoLookFor <br>
     *                          or valueTRoLookFor contains in any array value - <br>
     *                          false - for only one way (for any array value contain or
     *                          equal in the valueTRoLookFor)
     * @return
     */
    public static boolean isArrayListContainValue(ArrayList toLookInArray, String theValueToLookFor, boolean caseSensitive, boolean equal,
                                                  boolean bothSide) {
        boolean isExist = false;

        // for (String currValue : toLookInArray) {
        for (int i = 0; i < toLookInArray.size(); i++) {
            String currValue = toLookInArray.get(i).toString();
            if (!caseSensitive) {
                theValueToLookFor = theValueToLookFor.toLowerCase();
                currValue = currValue.toLowerCase();
            }
            if (equal) {
                if (currValue.equals(theValueToLookFor)) {
                    isExist = true;
                    break;
                } else if (theValueToLookFor.equals(currValue)) {
                    isExist = true;
                    break;
                }
            } else if (currValue.contains(theValueToLookFor)) {
                isExist = true;
                break;
            } else if (bothSide) {
                if (theValueToLookFor.contains(currValue)) {
                    isExist = true;
                    break;
                }
            }
        }
        return isExist;
    }

    /**
     * execute up to 2 searches on arrayList<br>
     * 1. look if there are equal match between arraylist members to
     * stringtolook for if true return this value<br>
     * else<br>
     * 2. 1. look if there are contains match between arraylist members to
     * stringtolook for
     *
     * @param arrayToLookInto
     * @param stringToLookFor
     * @return
     */
    public static String getBestMatchedValueFromArrayList(ArrayList<String> arrayToLookInto, String stringToLookFor, boolean caseSensitive) {
        String foundString = notFound;
        stringToLookFor = caseSensitive ? stringToLookFor : stringToLookFor.toLowerCase();
        for (String currListMember : arrayToLookInto) {
            String currListMemberModified = caseSensitive ? currListMember : currListMember.toLowerCase();

            if (currListMemberModified.equals(stringToLookFor)) {
                foundString = currListMember;
                break;
            }
        }
        if (foundString.equals(notFound)) {
            for (String currListMember : arrayToLookInto) {
                String currListMemberModified = caseSensitive ? currListMember : currListMember.toLowerCase();
                if (currListMemberModified.contains(stringToLookFor)) {
                    foundString = currListMember;
                    break;
                }
            }
        }

        return foundString;
    }

    /**
     * look for any string into array and return all array values contain this
     * string
     *
     * @param arrayToLookInto - array to look into
     * @param stringToLookFor - the sting to look for
     * @param containOrEqual  set true for contain dependency <br>
     *                        set false for equal dependency
     * @param caseSensitive   case sensitive true / false
     * @return ArrayLyst<String> of values from received array that contain
     * for received string
     * @throws Exception
     */
    public static ArrayList<String> getSubArrayListContainAString(ArrayList<String> arrayToLookInto, String stringToLookFor,
                                                                  boolean containOrEqual, boolean caseSensitive) throws Exception {
        ArrayList<String> subArray = new ArrayList<String>();
        for (String currValue : arrayToLookInto) {

            if (caseSensitive) {
                if (containOrEqual && currValue.contains(stringToLookFor)) {
                    subArray.add(currValue);
                } else if (!containOrEqual && currValue.equals(stringToLookFor)) {
                    subArray.add(currValue);
                }
            } else {
                if (containOrEqual && currValue.toLowerCase().contains(stringToLookFor.toLowerCase())) {
                    subArray.add(currValue);
                } else if (!containOrEqual && currValue.toLowerCase().equals(stringToLookFor.toLowerCase())) {
                    subArray.add(currValue);
                }
            }
        }
        return subArray;
    }

//    /**
//     * add all values from array to arrayToAppend
//     *
//     * @param array
//     * @param arrayToAppend
//     * @return
//     */
//    public static ArrayList<String> addStringArrayToArrayList(String[] array, ArrayList<String> arrayToAppend) {
//        String[] arrayToAppendStrArr = StringArrayUtils.arrayList2StringArray(arrayToAppend);
//        String[] appendendStringArray = addStringArrayToStringArray(array, arrayToAppendStrArr);
//        return StringArrayUtils.stringArray2ArrayList(appendendStringArray);
//    }

    /**
     * add a value to string[] array
     *
     * @param array
     * @param arrayToAppend
     * @return
     */
    public static String[] addStringArrayToStringArray(String[] array, String[] arrayToAppend) {
        String[] updatedArray = new String[]{};
        for (String currString : array) {
            updatedArray = addValueToStringArray(updatedArray, currString);
        }
        for (String currString : arrayToAppend) {
            updatedArray = addValueToStringArray(updatedArray, currString);
        }
        return updatedArray;
    }

    /**
     * appending a value to all elements from array
     *
     * @param theArray         - the array to append into
     * @param theValueToAppend - the value to append
     * @param beforeOrAfter    - append before a element of array or after
     */
    public static void appendValueToAllMembers(ArrayList<String> theArray, String theValueToAppend, boolean beforeOrAfter) {
        for (int i = 0; i < theArray.size(); i++) {
            String appendedArrayValue = theArray.get(i);
            if (beforeOrAfter)
                appendedArrayValue = theValueToAppend + appendedArrayValue;
            else
                appendedArrayValue = appendedArrayValue + theValueToAppend;
            theArray.set(i, appendedArrayValue);
        }
    }

    /**
     * add a value to all members of array list (before the member of after it )
     * @param arrayToAddTo - the array to add to its member a value
     * @param theValue - the value to add
     * @param beforeOrAfter if true add theValue BEFORE the member of array
     *                      if false add theValue AFTER the member of array
     * @return
     */
   public static ArrayList<String> addValueToAllMemberOfArrayList(ArrayList<String> arrayToAddTo,String theValue,boolean beforeOrAfter) {
       for (int i=0;i<arrayToAddTo.size();i++) {

           if (beforeOrAfter) {
              arrayToAddTo.set(i,theValue+arrayToAddTo.get(i));
           } else {
               arrayToAddTo.set(i,arrayToAddTo.get(i)+theValue);
           }
       }
       return arrayToAddTo;
   }
    /**
     * add a value to string[] array
     *
     * @param array
     * @param valueToAdd
     * @return
     */
    public static String[] addValueToStringArray(String[] array, String valueToAdd) {
        int arraySize = array.length + 1;
        String[] updatedArray = new String[arraySize];
        for (int i = 0; i < array.length; i++) {
            updatedArray[i] = array[i];
        }
        updatedArray[arraySize - 1] = valueToAdd;

        return updatedArray;
    }

    /**
     * get unique values from first array <br>
     * (get values from first array than not exists in the second one)
     *
     * @param firstArray
     * @param secondArray
     * @return values from firs Array than not in the second one
     * if second array is empty return first array
     */
    public static ArrayList<String> getUniqueValuesFromFirstArray(ArrayList<String> firstArray, ArrayList<String> secondArray) {
        ArrayList<String> uniqueValues = new ArrayList<String>();
        if (firstArray == null || firstArray.size() == 0) {
            return uniqueValues;
        }
        if (secondArray == null || secondArray.size() == 0) {
            return firstArray;
        }
        for (String currVauleFromFirstArray : firstArray) {
            if (!isArrayListContainValue(secondArray, currVauleFromFirstArray)) {
                uniqueValues.add(currVauleFromFirstArray);
            }
        }
        return uniqueValues;
    }

    /**
     * get unique values from first array
     *
     * @param firstArray
     * @param secondArray
     * @return
     */
    public static ArrayList<String> getUniqueValuesFromFirstArray(ArrayList<String> firstArray, String[] secondArray) {
        ArrayList<String> uniqueValues = new ArrayList<String>();
        for (String currVauleFromSecondArray : secondArray) {
            if (!isArrayListContainValue(firstArray, currVauleFromSecondArray)) {
                uniqueValues.add(currVauleFromSecondArray);
            }
        }
        return uniqueValues;
    }

    /**
     * get unique values from first array
     *
     * @param firstArray
     * @param secondArray
     * @return
     */
    public static ArrayList<String> getUniqueValuesFromFirstArray(String[] firstArray, String[] secondArray) {
        boolean isFound = false;
        ArrayList<String> uniqueValues = new ArrayList<String>();
        for (String currVauleFromFirstArray : firstArray) {
            for (String currVauleFromSecondArray : secondArray) {
                if (currVauleFromFirstArray.contains(currVauleFromSecondArray))
                    isFound = true;

            }
            if (!isFound) {
                uniqueValues.add(currVauleFromFirstArray);
            }
        }
        return uniqueValues;
    }

    /**
     * @param firstArray
     * @param secondArray
     * @return
     */
    public static ArrayList<String> getSharedSubArray(String[] firstArray, String[] secondArray) {
        ArrayList<String> sharedValues = new ArrayList<String>();
        for (String currValueFromSecondArray : secondArray) {
            for (String currValueFromFirstArray : firstArray) {
                if (currValueFromFirstArray.contains(currValueFromSecondArray))
                    sharedValues.add(currValueFromFirstArray);
            }
        }
        return sharedValues;
    }

    /**
     * convert Array to String and Replaces each substring of this string that
     * matches the given regular expression with the given replacement.
     *
     * @param arrayToConvert - array to convert
     * @param replacemant
     * @return
     */
    public static String arrayList2String(ArrayList arrayToConvert, String replacemant) {
        return arrayList2String(arrayToConvert, ",", replacemant);
    }

    /**
     * convert Array to String and Replaces each substring of this string that
     * matches the given regular expression with the given replacement.
     *
     * @param arrayToConvert - array to convert
     * @param regex
     * @param replacemant
     * @return
     */
    public static String arrayList2String(ArrayList arrayToConvert, String regex, String replacemant) {
        String arrayListString = "";
        if (arrayToConvert!=null && !arrayToConvert.isEmpty()) {
            arrayListString = Arrays.toString(arrayToConvert.toArray());
            String arrStringNoForgotten = arrayListString.replaceAll("\\[|\\]|", "");
            String arrStringNoSpaces = arrStringNoForgotten.replaceAll("\\s+", " ");
            String[] splitToArr = arrStringNoSpaces.split(",");
            String stringTrimSpacesInSunbStr = "";
            for (String currSubStr : splitToArr) {
                stringTrimSpacesInSunbStr += currSubStr.trim();
                stringTrimSpacesInSunbStr += replacemant;

            }
            arrayListString = stringTrimSpacesInSunbStr;
            // arrayListString = arrStringNoSpaces.replaceAll(",", replacemant);
        }
        return arrayListString;

    }

    /**
     * sort values from ArrayList<String>
     *
     * @param nonSortedArray - ArrayList<String> for sorting
     * @return
     */
    public static ArrayList<String> sortArrayListofStrings(ArrayList<String> nonSortedArray) {
        String[] nonSortedStringArray = arrayList2StringArray(nonSortedArray);
        Arrays.sort(nonSortedStringArray);
        ArrayList<String> sortedArray = stringArray2ArrayListString(nonSortedStringArray);

        return sortedArray;
    }
}
