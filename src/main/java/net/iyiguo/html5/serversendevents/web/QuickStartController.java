package net.iyiguo.html5.serversendevents.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/html5/sse")
public class QuickStartController {

    public static final Logger LOGGER = LoggerFactory.getLogger(QuickStartController.class);

    /**
     * 1. 设置response header and encoding
     * 2. 推送指定次数的数据后，服务器主动关闭链接。
     * <p>
     * 服务器主动关闭后，浏览器会等待默认时间后重连服务器。一般默认时间是3秒。
     * <p>
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
        for (int i = 1; i < 6; i++) {
            writer.write("data: " + i + ", hello for server send event！" + LocalDateTime.now() + "\n\n");
            writer.flush();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
        }
        writer.close();
    }


    /**
     * 浏览器主动关闭或刷新时会服务器会抛出异常。但这是正常的。
     * https://github.com/codecentric/spring-boot-admin/issues/714
     *
     * @return
     * @see <a href="https://github.com/codecentric/spring-boot-admin/issues/714">java.io.IOException: Broken pipe #714</a>
     */
    @RequestMapping("/quick_start_easy")
    public SseEmitter quickStartEasy() {
        LOGGER.info("request quick start easy demo......");
        final SseEmitter sseEmitter = new SseEmitter();

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(() -> {
            try {

                for (int i = 1; i < 6; i++) {
                    sseEmitter.send(i + ", hello for server send event!" + LocalDateTime.now(), MediaType.TEXT_PLAIN);
                    Thread.sleep(2000);
                }

                sseEmitter.complete();

            } catch (Exception e) {
                sseEmitter.completeWithError(e);
            }
        });

        return sseEmitter;
    }
}
