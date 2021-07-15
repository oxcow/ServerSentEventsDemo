package net.iyiguo.html5.sse.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.iyiguo.html5.sse.util.ThreadUtils;

@Controller
@RequestMapping("/html5/sse")
public class SayHiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SayHiController.class);

    @Value("${thread.sleep.milliseconds}")
    private final Long SLEEP_TIME_MILLISECONDS = 2000L;

    @RequestMapping("/event_fields")
    public void eventFields(HttpServletRequest req, HttpServletResponse res) throws IOException {

        LOGGER.debug("request event fields demo......");

        // 设置响应头
        res.setContentType("text/event-stream");
        res.setCharacterEncoding("UTF-8");

        int retry = RandomUtils.nextInt(15000, 18000);

        PrintWriter writer = res.getWriter();
        for (int i = 1; i < 6; i++) {

            StringBuilder messageSb = new StringBuilder();
            messageSb.append("id: ").append(i).append("\n");
            // only set once
            if (i == 1) {
                messageSb.append("retry: ").append(retry).append("\n");

                messageSb.append("data: ").append("发送一条ID=").append(i)
                        .append("的消息，并设置下次异常重连间隔时间为:").append(retry).append("ms");
            } else {
                messageSb.append("data: ").append("发送一条ID=").append(i).append("的消息");
            }

            messageSb.append("\n\n");

            writer.write(messageSb.toString());
            writer.flush();

            ThreadUtils.sleep(SLEEP_TIME_MILLISECONDS);
        }

        writer.close();

    }

    @RequestMapping("/customer_event")
    public void customerEvent(HttpServletRequest req, HttpServletResponse res) throws IOException {

        LOGGER.debug("request customer event demo......");

        // 设置响应头
        res.setContentType("text/event-stream");
        res.setCharacterEncoding("UTF-8");


        PrintWriter writer = res.getWriter();
        for (int i = 1; i < 6; i++) {

            StringBuilder message1 = new StringBuilder();
            message1.append("event: ").append("event_one").append("\n");
            message1.append("data: ").append("first event!")
                    .append("(").append(i).append(")")
                    .append("\n\n");

            StringBuilder message2 = new StringBuilder();
            message2.append("event: ").append("event_two").append("\n");
            message2.append("data: ").append("second event!")
                    .append("(").append(i).append(")")
                    .append("\n\n");

            int randomInt = RandomUtils.nextInt(1, 4);

            String message = message1.toString() + message2.toString();

            if (randomInt == 1) {
                message = message1.toString();
            }

            if (randomInt == 2) {
                message = message2.toString();
            }

            writer.write(message);
            writer.flush();

            ThreadUtils.sleep(SLEEP_TIME_MILLISECONDS);

        }
        writer.close();
    }

    @RequestMapping("/mixed_event")
    public void mixedEvent(HttpServletRequest req, HttpServletResponse res) throws IOException {

        LOGGER.debug("request mixed event demo......");

        // 设置响应头
        res.setContentType("text/event-stream");
        res.setCharacterEncoding("UTF-8");

        PrintWriter writer = res.getWriter();
        for (int i = 1; i < 6; i++) {

            String message = "event: event_one\ndata: first event!(" + i + ")\n\n"
                    + "event: event_two\ndata: second event!(" + i + ")\n\n"
                    + "data: message part one(" + i + "_1)\n"
                    + "data: message part two(" + i + "_2)\n\n";

            writer.write(message);
            writer.flush();

            ThreadUtils.sleep(SLEEP_TIME_MILLISECONDS);
        }

        writer.close();
    }
}
