package com.cedex.mail.message;

import com.cedex.GlobalParameters;
import com.cedex.text.StringArrayUtils;

import java.util.ArrayList;

/**
 * Created by Romang on 27/07/2016.
 */
public class MessageReader implements GlobalParameters {

    private MessageInterface[] allMessageList;


    private String[] expectedMessageList;
    private String[] unExpectedMessageList;
    private String[] warnMessageList;
    private String[] dontCareMessageList;

    private MessageInterface[] foundExpectedMessageList;
    private MessageInterface[] foundNotExpectedMessageList;
    private MessageInterface[] foundWarnMessageList;
    private MessageInterface[] foundDontCareMessageList;
    private ArrayList<String> foundMissingMessageList = new ArrayList<>();
    /*
    is feedler function ever executed
     */
    private boolean feeded = false;

    private enum MessageType {

        EXPECTED, ACTUAL, FOUND_EXPECTED, NOT_EXPECTED, WARN, DONT_CARE, MISSING;

//        EXPECTED, NOT_EXPECTED, WARN, DONT_CARE, MISSING;

    }
//    public MessageReader(MessageInterface[] allMessageList1, String[] expectedMessageList) {
//        this.allMessageList1 = allMessageList1;
//        this.expectedMessageList=expectedMessageList;
//    }

    public MessageReader(MessageInterface[] allMessageList, String[] expectedMessageList) {
        this.allMessageList = allMessageList;
        this.expectedMessageList = expectedMessageList;
    }

    public MessageReader(MessageInterface[] allMessageList, String[] expectedMessages, String[] notExpectedMessages) {
        this(allMessageList, expectedMessages);
        this.unExpectedMessageList = notExpectedMessages;
    }

    public MessageReader(MessageInterface[] allMessageList, String[] expectedMessages, String[] notExpectedMessages, String[] warnMessageList) {
        this(allMessageList, expectedMessages, notExpectedMessages);
        this.warnMessageList = warnMessageList;
    }

    public MessageReader(MessageInterface[] allMessageList, String[] expectedMessages, String[] notExpectedMessages, String[] warnMessageList, String[] dontCareMessageList) {
        this(allMessageList, expectedMessages, notExpectedMessages, warnMessageList);
        this.dontCareMessageList = dontCareMessageList;
    }

    /**
     * feed all found array lists for future
     */
    public void feedler() {


        if (!this.feeded) {
            this.feeded = true;
            ArrayList<MessageInterface> foundExpectedMessageListLocal = new ArrayList<MessageInterface>();
            ArrayList<MessageInterface> foundWarnMessageListLocal = new ArrayList<MessageInterface>();
            ArrayList<MessageInterface> foundNotExpectedMessageListLocal = new ArrayList<MessageInterface>();
            ArrayList<MessageInterface> foundDontCareMessageListLocal = new ArrayList<MessageInterface>();
            ArrayList<String> allMessagesAsList = new ArrayList<String>();

            if (this.allMessageList != null) {
                for (MessageInterface currMessageImpl : this.allMessageList) {
                    String currMessage = currMessageImpl.getMessage();
                    allMessagesAsList.add(currMessage);
                    if (!StringArrayUtils.isStringArayContainValue(this.dontCareMessageList, currMessage, false, false, true)) {
                        if (!StringArrayUtils.isStringArayContainValue(this.warnMessageList, currMessage, false, false, true)) {
                            boolean isExpectedMessageFound = false;
                            if (StringArrayUtils.isStringArayContainValue(this.expectedMessageList, currMessage, false, false, true)) {
                                foundExpectedMessageListLocal.add(currMessageImpl);
                                isExpectedMessageFound = true;
                            }
//                        }

                            if (!isExpectedMessageFound) {
                                //fill found not Expected Message List
                                foundNotExpectedMessageListLocal.add(currMessageImpl);
                            }
                        } else {
                            //fill found warn message list
                            foundWarnMessageListLocal.add(currMessageImpl);
                        }

                    } else {
                        //fill the found dont care message list
                        foundDontCareMessageListLocal.add(currMessageImpl);
                    }
                }
                //validating the messages from expected list that not found in all list
                if (this.expectedMessageList != null) {
                    for (String currMessage : this.expectedMessageList) {
                        if (!StringArrayUtils.isArrayListContainValue(allMessagesAsList, currMessage, false, false, true)) {
                            foundMissingMessageList.add(currMessage);
                        }
                    }
                }
                foundExpectedMessageList = this.convertArrayListToArray(foundExpectedMessageListLocal);
                foundNotExpectedMessageList = this.convertArrayListToArray(foundNotExpectedMessageListLocal);
                foundWarnMessageList = this.convertArrayListToArray(foundWarnMessageListLocal);
                foundDontCareMessageList = this.convertArrayListToArray(foundDontCareMessageListLocal);
            } else {

            }
        }
    }


    /**
     * validating if local expected messages array is empty or not
     *
     * @return
     */

    public boolean isExpectedFound() {
        return isFound(MessageType.FOUND_EXPECTED);
    }


    /**
     * validating if local not expected messages array is empty or not
     *
     * @return
     */

    public boolean isNotExpectedFound() {
        return isFound(MessageType.NOT_EXPECTED);
    }

    /**
     * validating if local not expected messages array is empty or not
     *
     * @return
     */

    public boolean isMissingFound() {
        return isFound(MessageType.MISSING);
    }

    /**
     * validating if local warn messages array is empty or not
     *
     * @return
     */

    public boolean isWarnFound() {
        return isFound(MessageType.WARN);
    }

    /**
     * validating if local dont care messages array is empty or not
     *
     * @return
     */

    public boolean isDontCareFound() {
        return isFound(MessageType.DONT_CARE);
    }

    /**
     * validating if local array is empty or not (for all arrays)
     *
     * @return
     */

    public boolean isFound(MessageType messageType) {
        MessageInterface[] lookForArray = null;

        switch (messageType) {
            case FOUND_EXPECTED:
                lookForArray = foundExpectedMessageList;
                break;
            case NOT_EXPECTED:
                lookForArray = foundNotExpectedMessageList;
                break;
            case WARN:
                lookForArray = foundWarnMessageList;
                break;
            case MISSING:
                if (foundMissingMessageList == null || foundMissingMessageList.size() == 0) {
                    return false;
                } else {
                    return true;
                }
            default:
                lookForArray = foundDontCareMessageList;
        }

        if (!this.isEmpty(lookForArray)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * converts expected messages array to string of array is empty return notFound string
     *
     * @return
     */
    public String toStringExpectedMessages() {
        return this.toString(MessageType.EXPECTED);
    }

    public String toStringActualMessages() {
        return this.toString(MessageType.ACTUAL);
    }

    /**
     * converts expected not messages array to string of array is empty return notFound string
     *
     * @return
     */
    public String toStringNotExpectedMessages() {
        return this.toString(MessageType.NOT_EXPECTED);
    }

    /**
     * converts warn messages array to string of array is empty return notFound string
     *
     * @return
     */
    public String toStringWarnMessages() {
        return this.toString(MessageType.WARN);
    }

    /**
     * converts dont care messages array to string of array is empty return notFound string
     *
     * @return
     */
    public String toStringDontCareMessages() {
        return this.toString(MessageType.DONT_CARE);
    }

    /**
     * converts missing messages array to string of array is empty return notFound string
     *
     * @return
     */
    public String toStringMissingMessages() {
        return this.toString(MessageType.MISSING);
    }

    /**
     * converts array of MessageInterface to arrayList<String>
     *
     * @param convertArray
     * @return
     */
    private ArrayList<String> toArrrayList(MessageInterface[] convertArray) {
        ArrayList<String> convertedArrayList = new ArrayList<>();
        for (MessageInterface currMessage : convertArray) {
            convertedArrayList.add(currMessage.getMessage()+"\n");
        }
        return convertedArrayList;
    }

    private ArrayList<String> getArrayWithBullets(MessageType messageType) {
        ArrayList<String> lookForArray= getMessageListAsArray(messageType);
        ArrayList<String> arrayWithBulets=new ArrayList<>();
        if (!isEmpty(lookForArray)) {

            int messageIndex = 1;
            for (String currMessage : lookForArray) {
                String currMessageWithBullet= messageIndex + ". " + currMessage.trim() + "\n";
                arrayWithBulets.add(currMessageWithBullet);
                messageIndex++;
            }

            return arrayWithBulets;

        } else {
            return arrayWithBulets;
        }
    }

    private ArrayList<String> getMessageListAsArray(MessageType messageType) {
        /* EXPECTED, ACTUAL, FOUND_EXPECTED, NOT_EXPECTED, WARN, DONT_CARE,MISSING;*/
        ArrayList<String> lookForArray;
        switch (messageType) {
            case EXPECTED:
                lookForArray = StringArrayUtils.stringArray2ArrayList(expectedMessageList);
                break;
            case FOUND_EXPECTED:
//                lookForArray = foundExpectedMessageList;
                lookForArray = this.toArrrayList(foundExpectedMessageList);
                break;
            case NOT_EXPECTED:
                lookForArray = this.toArrrayList(foundNotExpectedMessageList);
                break;
            case WARN:
//                lookForArrayOld = foundWarnMessageList;
                lookForArray = this.toArrrayList(foundWarnMessageList);
                break;
            case MISSING:
                lookForArray = foundMissingMessageList;
                break;
            case ACTUAL:
                lookForArray = this.toArrrayList(allMessageList);
                break;
//                return StringArrayUtils.arrayList2String(foundMissingMessageList, ",");
//                lookForArray = this.toArrrayList(foundMissingMessageList);
            default:
//                lookForArrayOld = foundDontCareMessageList;
                lookForArray = this.toArrrayList(foundDontCareMessageList);
        }
        return lookForArray;
    }
    private String toString(MessageType messageType) {

        ArrayList<String> lookForArray= getMessageListAsArray(messageType);

        if (!isEmpty(lookForArray)) {
            String arrayAsString = "\n";
            int messageIndex = 1;
            for (String currMessage : lookForArray) {
                arrayAsString = arrayAsString + messageIndex + ". " + currMessage.trim() + "\n";
                messageIndex++;
            }
//            arrayAsString = arrayAsString.substring(0, arrayAsString.length() - 3);
            return arrayAsString;
//            return Arrays.toString(lookForArray);
        } else {
            return notFound;
        }
    }

    private boolean isEmpty(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmpty(MessageInterface[] messageInterfaceArray) {
        if (messageInterfaceArray == null || messageInterfaceArray.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * converts ArrayList<MessageInterface> to MessageInterface[]
     *
     * @param messagesListArray
     * @return
     */
    private MessageInterface[] convertArrayListToArray(ArrayList<MessageInterface> messagesListArray) {
        MessageInterface[] messageArray;
        if (messagesListArray!=null && !messagesListArray.isEmpty()) {
            messageArray = new MessageInterface[messagesListArray.size()];
            for (int i = 0; i < messagesListArray.size(); i++) {
                messageArray[i] = messagesListArray.get(i);
            }
        } else {
            messageArray=new MessageInterface[]{};
        }
        return messageArray;
    }

    public int getCountOfExpectedMessages() {
        return getCountOfMessages(MessageType.EXPECTED);
    }

    public int getCountOfNotExpectedMessages() {
        return getCountOfMessages(MessageType.NOT_EXPECTED);
    }

    public int getCountOfMissingMessages() {
        return getCountOfMessages(MessageType.MISSING);
    }

    public int getCountOfWarnMessages() {
        return getCountOfMessages(MessageType.WARN);
    }

    private int getCountOfMessages(MessageType messageType) {
        MessageInterface[] lookForArray = null;
        /* EXPECTED, ACTUAL, FOUND_EXPECTED, NOT_EXPECTED, WARN, DONT_CARE, MISSING;*/
        switch (messageType) {
            case EXPECTED:
                return expectedMessageList==null ? 0 : expectedMessageList.length;
            case NOT_EXPECTED:
                lookForArray = foundNotExpectedMessageList;
                break;
            case WARN:
                lookForArray = foundWarnMessageList;
                break;
            case MISSING:
                return foundMissingMessageList==null ? 0 : foundMissingMessageList.size();
            case ACTUAL:
                lookForArray = allMessageList;
                break;
            case FOUND_EXPECTED:
                lookForArray = foundExpectedMessageList;
                break;
            default:
                lookForArray = foundDontCareMessageList;

        }
        return lookForArray.length;
    }
    public ArrayList<String> getFoundExpectedMessageListWithBullets() {
        this.feedler();
        return getArrayWithBullets(MessageType.EXPECTED);
    }
    public ArrayList<String> getFoundMissingMessageListWithBullets() {
        this.feedler();
        return getArrayWithBullets(MessageType.MISSING);
    }
    public ArrayList<String> getFoundNotExpectedMessageListWithBullets() {
        this.feedler();
        return getArrayWithBullets(MessageType.NOT_EXPECTED);
    }
    public ArrayList<String> getFoundActualMessageListWithBullets() {
        this.feedler();
        return getArrayWithBullets(MessageType.ACTUAL);
    }
    public MessageInterface[] getFoundExpectedMessageList() {
        this.feedler();
        return foundExpectedMessageList;
    }

    public MessageInterface[] getFoundNotExpectedMessageList() {
        this.feedler();
        return foundNotExpectedMessageList;
    }

    public MessageInterface[] getFoundWarnMessageList() {
        this.feedler();
        return foundWarnMessageList;
    }

    public MessageInterface[] getFoundDontCareMessageList() {
        this.feedler();
        return foundDontCareMessageList;
    }

    public ArrayList<String> getFoundMissingMessageList() {
        this.feedler();
        return foundMissingMessageList;
    }
}




