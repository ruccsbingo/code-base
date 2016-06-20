package mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author liubo
 * @email liubo@yidian-inc.com
 *
 */
public class Mailman {

    private BlockingQueue<WarningMail> queue;

    private String[] mailTo;

    ExecutorService executor;

    private static final Logger LOG = LoggerFactory.getLogger(Mailman.class);

    public Mailman(String mailTo) {
        this.mailTo = mailTo.split(";");
        queue = new PriorityBlockingQueue<WarningMail>(1024);
        executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                doSendMail();
            }
        });
    }

    public boolean sendMail(WarningMail mail) {
        return queue.add(mail);
    }

    private void doSendMail() {
        // List<MailBody> batch = new ArrayList<MailBody>(10);
        while (true) {
            try {
                WarningMail body = queue.take();
                LOG.warn("Sending warning email : " + body.getMsg());
                Email email = new SimpleEmail();
                email.setHostName("smtp.yidian.com");
                email.setAuthenticator(new DefaultAuthenticator("zhangbing@yidian-inc.com", "cs429001989"));
                email.setFrom("zhangbing@yidian-inc.com");
                for (String r : mailTo)
                    email.addTo(r);
                email.setSubject("WARNING: " + body.getMsg());
                Throwable detail = body.getDetail();
                StringBuilder builder = new StringBuilder();
                if (body.getDetailMsg() != null) {
                    builder.append("DETAIL MESSAGE: \n").append(body.getDetailMsg()).append("\n\n");
                }
                if (detail != null) {// normally we should have this
                    builder.append("ERROR MESSAGE: \n").append(detail.getMessage());
                    builder.append("\n\n" + " STACKTRACE: \n");
                    builder.append(Arrays.toString(detail.getStackTrace()));
                    Throwable cause = detail.getCause();
                    if (cause != null) {
                        builder.append("\n\n" + " CAUSE: \n");
                        builder.append(Arrays.toString(cause.getStackTrace()));
                    }
                }
                if (builder.length() > 0) {
                    email.setMsg(builder.toString());
                } else {
                    email.setMsg(body.getMsg());
                }
                email.send();
            } catch (EmailException e) {
                LOG.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public void retire() {
        executor.shutdown();
    }
}

