# jvlang-housekeeping

## 介绍

本仓库是一个 monorepo :

- `src` 目录和 maven的那些乱七八糟 是橘浪家政系统后端。
- `miniprogram` 和 `project.conf.json` 是小程序端。
  - `miniprogram_hiila_integrated` 是一个用于小程序代码生成的 `hilla` 插件。
- `frontend` 是 react 管理端。

## 软件架构

数据库采用mysql。

管理端+后端是由 hilla 框架所实现的，hilla 提供了从 java 接口到 ts 类型定义的生成方案，还提供了大量的 react crud 组件（前端表单组件 和 后端多条件查询传输层对象）。

小程序大量的组件是手糊的，没有依赖任何 npm 模块（因为我tm实在是被微信开发者工具的npm构建恶心坏了）。小程序与java后端交互的 ts 类型定义是由 hilla 生成的。

## 环境

你需要安装好这些环境。 并参考“启动”章节设置好 mysql 用户名密码的环境变量。

- mysql8.3
- node18
- jdk17

>（工程中自带了maven）

### 启动

1. 默认连接到本地3306端口的mysq数据库

2. 启动要先在环境变量中设置好 `DEV_MYSQL_USERNAME` 、`DEV_MYSQL_PASSWORD` ，详见src/main/resources/application-dev.yml；并调用src/init.sql建库，（jpa会自动建表）

3. 另外由于还没做注册功能，要现在user库中插入用户名和密码才能登录管理端。

4. 可以使用 Intellij 启动主类，也可以 `mvnw org.springframework.boot:spring-boot-maven-plugin:3.2.2:run` 来启动。

5. 启动后 hilla 会运行代码生成。然后 管理端和小程序 的 ts 类型定义 和 ts 接口定义会被生成到其 `frontend/generated` 和 `miniprogram/generated` 目录下。然后你就可以开发前端了。

## 参与贡献

1. 查看中[橘浪家政系统 (qq.com)](https://docs.qq.com/sheet/DQlFVR1haaEVZZW1E?tab=r14pmh)的进展sheet了解进展情况，确定目标
2. 新建 dev_by_xxx 分支
3. 提交代码
4. 新建 Pull Request到dev_backend

## 关于 Hilla 工程

该项目是用 Spring Boot 创建您的 Hilla 应用程序。

### Running the application

运行 Hilla 应用程序
运行应用程序有两种方法：您可以使用 IDE 来运行Application.java - 推荐这样做，因为它支持调试；或者您可以从命令行运行默认的
Maven 目标（即mvn）。

Hilla 会在保存时自动重新加载对前端和后端代码的更改。IntelliJ 要求您在更改 Java 代码后手动构建项目，而 VS Code 将在保存时自动构建项目。

### 部署到生产环境

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/myapp-1.0-SNAPSHOT.jar` (NOTE, replace
`myapp-1.0-SNAPSHOT.jar` with the name of your jar).
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>themes/</code></td><td>Custom  
CSS styles</td></tr>
  <tr><td><code>src/main/java/&lt;groupId&gt;/</code></td><td>Server-side 
source directory, contains the server-side Java views</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>Application.java</code></td><td>Server entry-point</td></tr>
</table>
