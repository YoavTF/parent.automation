package com.cedex.mail.message;

import jsystem.utils.MailMessage;

/**
 * Created by Romang on 28/07/2016.
 * overrides MailMessage by adding getMessage function
 *
 */
public class EmailMessage implements MessageInterface {

    MailMessage mailMessage;
    String messageIdentifier;

    public EmailMessage(MailMessage mailMessage,String messageIdentifier) {
        this.mailMessage=mailMessage;
        this.messageIdentifier=messageIdentifier;
    }

    /**
     * return the email subject as message
     * @return
     */
    @Override
    public String getMessage() {
        String messageBody=this.mailMessage.getContent();
//        String fromIdentificatorStr = "Source:" + messageIdentifier;
        if (messageBody.contains(messageIdentifier)) {
            return this.mailMessage.getSubject();
        } else {
            return null;
        }
    }


    /**
     * get email object itself with all parameters
     * @return
     */
    public MailMessage getMailMessageObject() {
        return mailMessage;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "mailMessage=" + getMessage() +
                '}';
    }
}
