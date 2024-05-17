# MCBEHTTPStatusAPI
MCBEHTTPStatusAPI是一个基于Java 17编写的Minecraft基岩版服务器状态查询API。它可以通过HTTP请求获取Minecraft基岩版服务器的在线玩家数量、最大玩家数量和服务器版本等信息。

该项目参考了部分[点我进入](https://github.com/PetteriM1/BedrockServerQuery)代码并且进行了部分重构和优化，并提供了更简洁易用的HTTP接口。

## 安装和使用

1. 从[Releases](https://github.com/your-username/MCBEHTTPStatusAPI/releases)页面下载最新的构建版本。

2. 确保您的系统已经安装了Java 17运行时环境。

3. 下载相关jar文件

4. 在目录中，打开命令行终端并执行以下命令启动服务器：
   ```shell
   java -jar MCBEHTTPStatusAPI-1.0.jar
5. 默认情况下，服务将在本地的8888端口上运行。你可以在浏览器中访问http://localhost:8888/status?ip=xxx&port=xxx来获取服务器状态信息。

## API部分:
### 获取服务器状态
#### 请求
请求地址：/status
请求方法：GET
##### 响应
##### 响应格式：JSON
```
{
    "online": true,
    "motd": "§bXXXXX",
    "protocolVersion": 671,
    "minecraftVersion": "1.20.80",
    "playerCount": 7,
    "maxPlayers": 2333,
    "software": "WaterdogPE Proxy",
    "gamemode": "Survival",
    "html": "§bXXXXX</span>XXXX §eNetwor</span>k §cCH §dXXXX</span>XXXXX"
}
```
### 转换颜色代码
#### 请求
##### 请求路径：/convert-color?data={text}
##### 请求方法：GET
##### 参数：
##### data：待转换的文本内容（URL编码）

## 贡献和问题反馈
如果你发现了任何问题或有任何改进意见，请在GitHub上提出issue或pull request。

版权和许可
MCBEHTTPStatusAPI基于GPL许可证发布，详细信息请参阅LICENSE文件。

部分代码来自https://github.com/PetteriM1/BedrockServerQuery，并进行了重构和优化。
