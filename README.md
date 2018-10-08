# HTML5 Server Send Event 小测试

+ 一些关于HTML5服务器发送事件的简单的小例子，用来演示服务器向浏览器推送消息。
+ _Demo项目基于Spring boot 2.0搭建_
+ 主要包含以下内容：

## 快速入门

简单直观的入门演示。

## 事件属性

测试事件流格式. 主要属性字段 id 和 retry

## 自定义事件

使用`addEventLisener`监听指定`Event`类型的消息

## 混合型消息

混合消息。一次发送多种`Event`类型的消息。

## 消息广播

模拟消息广播。系统自动生成消息，不同角色对消息进行监听。用户登录后可对未消费的过期消息继续进行消费。

# 其他

## 服务器实现

### HTTP头信息

1. Server Send Event 要求服务器发送请求头`Content-Type`值为**text/event-stream**。
2. 发送文本需要**UTF-8**编码

下面是一段服务器端示例代码：

    @Controller
    @RequestMapping("/html5/sse")
    public class QuickStartController {

        @RequestMapping("/quick_start")
        public void quickStart(HttpServletRequest req, HttpServletResponse res) throws IOException {

            // Required! set response header and encoding
            res.setContentType("text/event-stream");
            res.setCharacterEncoding("UTF-8");

            PrintWriter writer = res.getWriter();

            // push data
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

### 数据格式

单条数据信息格式如下：

    [field]: [VALUE]

_filed_可以是`id`,`event`,`retry`和`data`.

1. 每个属性一行(使用\n)，其中`data`属性可以有多行
2. 属性冒号后有且仅有一个空格分割
3. 特别的，冒号可以单独一行，此时代表注释。

一次可以发送多条数据信息，每条数据信息之间用空白行分割(使用\n\n). 属性说明如下：

#### id 属性

消息标识。服务器将该值传给浏览器后，浏览器可使用`lastEventId`属性获取，并会在下次链接时将该值存放在HTTP头的`Last-Event-ID`中，发送到服务器。

#### event 属性

事件类型。浏览器端通过`addEventListener`进行监听。

#### retry 属性

指定浏览器重新发起链接的时间间隔(异常情况下)，单位毫秒。

