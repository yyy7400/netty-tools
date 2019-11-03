# netty-tools
基于springboot构建的netty工具，自定义协议、解决粘包/拆包、心跳机制、断开自动重连机制

协议中数据采用utf-8编码，结构为 header + body，header固定占用20个字节。body长度不定

|  --------------------   header  -----------------  |-- body --|
|  start  |  id  |  mainType  |  subType  |  length  |   body   |

- start: 占用4byte，消息头部标识位
- id: 占用8byte，表示消息唯一标识，可以用时间戳生成
- mainType：占用2byte，主消息类型
- subType：占用2byte，子消息类型
- length：占用4byte，消息数据体（body）的长度
- body：占用len(body)byte，数据为""或者是json结构
