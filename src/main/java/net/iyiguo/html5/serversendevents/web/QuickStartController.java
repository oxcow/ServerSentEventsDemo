package net.iyiguo.html5.serversendevents.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/html5/sse")
public class QuickStartController {

    public static final Logger LOGGER = LoggerFactory.getLogger(QuickStartController.class);

    /**
     * 1. 设置response header and encoding
     * 2. 推送指定次数的数据后，服务器主动关闭链接。
     *
     * 服务器主动关闭后，浏览器会等待默认时间后重连服务器。一般默认时间是3秒。
     *
     * 可以通过 retry 属性设置浏览器重连时间
     *
     * @param req
     * @param res
     * @throws IOException
     */
    @RequestMapping("/quick_start")
    public void quickStart(HttpServletRequest req, HttpServletResponse res) throws IOException {

        LOGGER.info("request quick start demo......");

        // 设置响应头
        res.setContentType("text/event-stream");
        res.setCharacterEncoding("UTF-8");

        PrintWriter writer = res.getWriter();

        // 链接异常后，8秒后客户端重链
        // writer.write("retry: 8000\ndata: quick start!\n\n");
        // writer.flush();

        // 推送数据
        for (int i = 0; i < 5; i++) {
            writer.write("data: " + i + ", hello for server send event！" + LocalDateTime.now() + "\n\n");
            writer.flush();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
        }

        writer.close();
    }
}
